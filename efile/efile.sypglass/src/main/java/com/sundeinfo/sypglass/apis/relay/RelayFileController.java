package com.sundeinfo.sypglass.apis.relay;

import com.mongodb.gridfs.GridFSInputFile;
import com.sundeinfo.core.log.ActionLog;
import com.sundeinfo.core.mvc.controller.AbstractController;
import com.sundeinfo.core.request.PostCallback;
import com.sundeinfo.core.request.RequestState;
import com.sundeinfo.core.request.result.Result;
import com.sundeinfo.core.utility.ConvertUtils;
import com.sundeinfo.sypglass.configuration.ConfigService;
import com.sundeinfo.sypglass.define.FileStatus;
import com.sundeinfo.sypglass.define.FolderStatus;
import com.sundeinfo.sypglass.dto.DepartmentDto;
import com.sundeinfo.sypglass.dto.FileDto;
import com.sundeinfo.sypglass.dto.FolderDto;
import com.sundeinfo.sypglass.dto.UserDto;
import com.sundeinfo.sypglass.model.Config;
import com.sundeinfo.sypglass.model.File;
import com.sundeinfo.sypglass.model.Folder;
import com.sundeinfo.sypglass.model.FolderSetting;
import com.sundeinfo.sypglass.service.*;
import com.sundeinfo.sypglass.utility.DateUtils;
import com.sundeinfo.sypglass.utility.FileUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Api(description = "OA导入API")
@RestController
@RequestMapping("relay")
public class RelayFileController extends AbstractController<RelayFileController> {

    @Value("${file.folderTmpPath}")
    private String requestTxtTmpPath;

    @Autowired
    private GridfsService gridfsService;

    @Autowired
    private JoinService joinService;

    @Autowired
    private FolderService folderService;

    @Autowired
    private FileService fileService;

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private UserService userService;

    @Autowired
    private FolderSettingService folderSettingService;

    @Autowired
    private ConfigService configService;

    @ApiOperation(value="文件导入", notes="文件导入")
    @PostMapping("request/{request}/fileId/{fileId}/date/{date}/workflow/{workflow}/comp/{comp}/dept/{dept}/user/{user}")
    public ResponseEntity<Result> uploadFile(@PathVariable(value = "request") long request,
                                             @PathVariable(value = "fileId") long fileId,
                                             @PathVariable(value = "date") String date,
                                             @PathVariable(value = "workflow") long workflow,
                                             @PathVariable(value = "comp") long comp,
                                             @PathVariable(value = "dept") long dept,
                                             @PathVariable(value = "user") long user,
                                             @RequestPart("name")  String name,
                                             @RequestPart("file") MultipartFile file,
                                             @RequestParam String comment) {
        return requestHandler.doPost(
                new PostCallback() {
                    @Override
                    public RequestState doCheck() {
                        if (file == null){
                            return RequestState.PARAM_USELESS;
                        }
                        return RequestState.SUCCESS;
                    }
                    @Override
                    public RequestState invoke() throws Exception {
                        RequestState result;
                        List<FileDto> files = fileService.findByOaFileId(fileId);
                        if(files == null || files.size() == 0){
                            result = saveFile(file,request,name,fileId,date,workflow,comp,dept,user,comment);
                            if (result != RequestState.SUCCESS){
                                return result;
                            }
                        }else{
                            result = updateFile(file,user,comment,files);
                            if (result != RequestState.SUCCESS){
                                return result;
                            }
                        }

                        return RequestState.SUCCESS;
                    }
                }
        );
    }

    private RequestState saveFile(MultipartFile file,long request,String name,long fileId,String date,long workflow,long comp, long dept,long user,String comment){
        try {
            long userId = joinService.findSysUserId(user);
            UserDto userDto = userService.findById(userId);
            if (userDto == null){
                logger.error("没有找到OA系统对应的用户，用户id：" + user);
                return RequestState.RELAY_USER_NULL;
            }

            long deptId = joinService.findSysDeptId(0,comp);
            DepartmentDto departmentDto = departmentService.findById(deptId);
            if (departmentDto == null){
                logger.error("没有找到OA系统对应的部门，基地id:" + comp + "  部门id:" + dept);
                return RequestState.RELAY_DEPT_NULL;
            }

            String kind = null;
            List<Config> configList = configService.findByNameAndCode("relay_setting","kind");
            for (Config config :configList) {
                if (Integer.parseInt(config.getValue().split("\\^")[1]) == workflow){
                    kind = config.getValue().split("\\^")[0];
                    break;
                }
            }
            if (StringUtils.isEmpty(kind)){
                logger.error("没有在m_config中找到OA系统对应的workflow的文档类型配置，workflow:" + workflow);
                return RequestState.RELAY_KIND_NULL;
            }

            Date dateValue = DateUtils.strToDate(date);
            Calendar c = Calendar.getInstance();
            c.setTime(dateValue);
            String year = c.get(Calendar.YEAR) + "";

            FolderSetting setting = folderSettingService.findByKey(workflow,String.valueOf(comp),kind,year);
            if (setting == null){
                logger.warn(String.format("FolderSetting的配置异常，没有符合条件的配置或者找到多个配置。异常配置，workflow{%s},comp{%s},kind{%s},year{%s}",workflow,comp,kind,year));
                List<FolderSetting> historySettings = folderSettingService.findByKey(workflow,String.valueOf(comp),kind);
                if (historySettings.isEmpty()){
                    logger.error("FolderSetting尝试新建文件夹配置失败,上层配置不全");
                    return RequestState.RELAY_SETTING_ERROR;
                }else{
                    setting = historySettings.get(0);
                    String[] fullPath = setting.getFullpath().split("/");
                    if (fullPath.length < 6){
                        logger.error("FolderSetting尝试新建文件夹配置失败,同级配置路径小于5层");
                        return RequestState.RELAY_SETTING_ERROR;
                    }
                    //找到年份上层目录,并比较是否和流程名称一致
                    FolderDto folderDto = folderService.findById(Integer.parseInt(fullPath[4]));
                    //新建年份目录
                    FolderDto newYearFolder = folderService.createNew()
                            .setParentId(folderDto.getId())
                            .setName(year)
                            .setStatus((byte)FolderStatus.NORMAL.code())
                            .setCreateUserId(1)
                            .setCreateUserName("Root")
                            .setCreateDate(new Date())
                            .setIsDelete((byte) 0)
                            .setParentPath(folderDto.getFullPath())
                            .setParentPathName(folderDto.getFullName())
                            .build();
                    int i = folderService.save(newYearFolder);
                    if (i == 0){
                        logger.error(String.format("FolderSetting尝试新建文件夹配置失败,新建文件夹失败。workflow{%s},comp{%s},kind{%s},year{%s}",workflow,comp,kind,year));
                        return RequestState.RELAY_SETTING_FOLDER_ERROR;
                    }
                    //复制新的目录信息建立文件夹配置
                    FolderSetting newYearSetting = new FolderSetting();
                    BeanUtils.copyProperties(setting,newYearSetting);
                    newYearSetting.setId(null);
                    newYearSetting.setThirddictcode(year);
                    newYearSetting.setFolderid(newYearFolder.getId());
                    newYearSetting.setFoldername(newYearFolder.getName());
                    newYearSetting.setFullpath(newYearFolder.getFullPath());
                    newYearSetting.setFullname(newYearFolder.getFullName());
                    int v = folderSettingService.save(newYearSetting);
                    if (v == 0)
                    {
                        logger.error(String.format("FolderSetting尝试新建文件夹配置失败,新建配置失败。workflow{%s},comp{%s},kind{%s},year{%s}",workflow,comp,kind,year));
                        return RequestState.RELAY_SETTING_FOLDER_ERROR;
                    }
                    setting = newYearSetting;
                    logger.info(String.format("FolderSetting已经新建完成要求的配置目录。workflow{%s},comp{%s},kind{%s},year{%s}",workflow,comp,kind,year));
                }
            }

            FolderDto parentFolderDto = folderService.findById(setting.getFolderid());
            if (parentFolderDto == null){
                logger.error(String.format("文件夹配置异常，已配置的文件夹没有找到。异常配置，workflow{%s},comp{%s},kind{%s},year{%s}",workflow,comp,kind,year));
                return RequestState.RELAY_SETTING_FOLDER_ERROR;
            }

            //建立workflow专属文件夹
            FolderDto requestfolderDto = folderService.findWorkflowFolderByParentAndWorkflow(parentFolderDto.getId(),request);
            if (requestfolderDto == null){
                requestfolderDto = folderService.createNew()
                                    .setParentId(parentFolderDto.getId())
                                    .setName(name)
                                    .setStatus((byte)FolderStatus.NORMAL.code())
                                    .setCreateUserId(1)
                                    .setCreateUserName("Root")
                                    .setCreateDate(new Date())
                                    .setIsDelete((byte) 0)
                                    .setParentPath(parentFolderDto.getFullPath())
                                    .setParentPathName(parentFolderDto.getFullName())
                                    .setOaworkflowid(request)
                                    .setOaworkflowinfo(name)
                                    .build();
                int i = folderService.save(requestfolderDto);
                if (i == 0){
                    logger.error(String.format("新建文件夹失败。workflow{%s},comp{%s},kind{%s},year{%s}",workflow,comp,kind,year));
                    return RequestState.RELAY_SETTING_CREATE_FOLDER_ERROR;
                }
            }
            //做一个新的流程说明文档
            GridFSInputFile requestTxt = createProcessFile(request);
            if (requestTxt == null) {
                logger.error(String.format("新建外链文件失败。workflow{%s},comp{%s},kind{%s},year{%s}",workflow,comp,kind,year));
                return RequestState.FILE_INSERT_ERROR;
            }
            //查找原来的流程说明文档的数据
            List<FileDto> requestTxtFileDtos = fileService.findByOaFileId(request);
            FileDto requestTxtFileDto = null;
            for (FileDto fileDto:requestTxtFileDtos) {
                if (fileDto.getName().trim().equals(request+"")){//流程说明文档的名称是request编号
                    requestTxtFileDto = fileDto;
                    break;
                }
            }
            if (requestTxtFileDto == null){
                //如果没有流程说明文档，就新建一个
                requestTxtFileDto = fileService.createNew()
                        .setFolderId(requestfolderDto.getId())
                        .setName(request+"")
                        .setSuffix("txt")
                        .setStatus(FileStatus.NORMAL)
                        .setSize("15B")//这文件的大小不重要，随便写一个
                        .setIsDelete((byte)0)
                        .setCreateUserId(userDto.getId())
                        .setCreateUserName(userDto.getName())
                        .setCreateDate(new Date())
                        .setLastUpdateUserId(userDto.getId())
                        .setLastUpdateUserName(userDto.getName())
                        .setLastUpdateDate(new Date())
                        .setComment("系统生成的OA地址流程文档")
                        .setSaveId(requestTxt.getId().toString())
                        .setSaveMD5(requestTxt.getMD5())
                        .setSaveLength(requestTxt.getLength())
                        .setParentPath(requestfolderDto.getFullPath())
                        .setParentPathName(requestfolderDto.getFullName())
                        .setOaFileId(request) //用request来标识文件夹内唯一性
                        .build();
                fileService.save(requestTxtFileDto);
                //FileUtils.deleteFile(filepath + filename);
            }else{
                //如果原来有流程说明文档，就更新一个
                requestTxtFileDto.setLastUpdateUserId(userDto.getId());
                requestTxtFileDto.setLastUpdateUserName(userDto.getName());
                requestTxtFileDto.setLastUpdateDate(new Date());
                requestTxtFileDto.setComment("系统生成的OA地址流程文档");
                requestTxtFileDto.setSaveId(requestTxt.getId().toString());
                requestTxtFileDto.setSaveMD5(requestTxt.getMD5());
                requestTxtFileDto.setSaveLength(requestTxt.getLength());
                fileService.update(requestTxtFileDto);
            }

            //文件保存
            GridFSInputFile input = gridfsService.save(file);
            if (input == null) {
                return RequestState.FILE_INSERT_ERROR;
            }
            //该文件为mutilpart文件
            String originalFilename = file.getOriginalFilename();
            //获取文件名
            String filename = originalFilename.substring(0,originalFilename.lastIndexOf("."));
            //获取文件名后缀 
            String suffix = originalFilename.substring(originalFilename.lastIndexOf(".")+1);

            FileDto fileDto = fileService.createNew()
                    .setFolderId(requestfolderDto.getId())
                    .setName(filename)
                    .setSuffix(suffix)
                    .setStatus(FileStatus.NORMAL)
                    .setSize(FileUtils.getFileSize(file))
                    .setIsDelete((byte)0)
                    .setCreateUserId(userDto.getId())
                    .setCreateUserName(userDto.getName())
                    .setCreateDate(new Date())
                    .setLastUpdateUserId(userDto.getId())
                    .setLastUpdateUserName(userDto.getName())
                    .setLastUpdateDate(new Date())
                    .setComment(comment)
                    .setSaveId(input.getId().toString())
                    .setSaveMD5(input.getMD5())
                    .setSaveLength(input.getLength())
                    .setParentPath(requestfolderDto.getFullPath())
                    .setParentPathName(requestfolderDto.getFullName())
                    .setOaFileId(fileId)
                    .build();

            fileService.save(fileDto);

            return RequestState.SUCCESS;
        }catch(Exception e) {
            logger.error("文件传输时发生异常：" + e.getMessage());
            e.printStackTrace();
            return RequestState.DB_INSERT_ERROR;
        }
    }

    private RequestState updateFile(MultipartFile file,long user,String comment,List<FileDto> files){
        try {

            long userId = joinService.findSysUserId(user);
            UserDto userDto = userService.findById(userId);
            if (userDto == null){
                logger.error("没有找到OA系统对应的用户，用户id：" + user);
                return RequestState.RELAY_USER_NULL;
            }

            GridFSInputFile input = gridfsService.save(file);
            if (input == null) {
                return RequestState.FILE_INSERT_ERROR;
            }

            //该文件为mutilpart文件
            String originalFilename = file.getOriginalFilename();
            //获取文件名
            String filename = originalFilename.substring(0,originalFilename.lastIndexOf("."));
            //获取文件名后缀 
            String suffix = originalFilename.substring(originalFilename.lastIndexOf(".")+1);

            for (FileDto fileDto:files) {
                fileDto.setName(filename);
                fileDto.setSuffix(suffix);
                fileDto.setSize(FileUtils.getFileSize(file));
                fileDto.setLastUpdateUserId(userDto.getId());
                fileDto.setLastUpdateUserName(userDto.getName());
                fileDto.setLastUpdateDate(new Date());
                fileDto.setComment(comment);
                fileDto.setSaveId(input.getId().toString());
                fileDto.setSaveMD5(input.getMD5());
                fileDto.setSaveLength(input.getLength());

                fileService.update(fileDto);
            }

            return RequestState.SUCCESS;
        }catch(Exception e) {
            logger.error("文件传输时发生异常：" + e.getMessage());
            e.printStackTrace();
            return RequestState.DB_UPDATE_ERROR;
        }
    }

    private GridFSInputFile createProcessFile(long request) throws IOException {
        String filepath = requestTxtTmpPath + "/";
        String filename = request + ".txt";
        String oaUrl = "http://10.1.4.5/workflow/request/ViewRequest.jsp?requestid=" + request + "&ismonitor=1";
        String content = "OA URL：" + oaUrl;
        FileUtils.writeFile(filepath + filename,content);
        return gridfsService.save(filepath,filename);
    }

}
