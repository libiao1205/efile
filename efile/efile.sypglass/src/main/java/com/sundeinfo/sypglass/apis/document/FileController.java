package com.sundeinfo.sypglass.apis.document;

import com.alibaba.fastjson.JSONArray;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.gridfs.GridFSDBFile;
import com.mongodb.gridfs.GridFSInputFile;
import com.sundeinfo.core.http.HttpService;
import com.sundeinfo.core.log.ActionLog;
import com.sundeinfo.core.mvc.controller.AbstractController;
import com.sundeinfo.core.permission.FunctionType;
import com.sundeinfo.core.request.GetCallback;
import com.sundeinfo.core.request.PostCallback;
import com.sundeinfo.core.request.RequestState;
import com.sundeinfo.core.request.result.Result;
import com.sundeinfo.core.utility.ReflectUtils;
import com.sundeinfo.core.utility.StringUtils;
import com.sundeinfo.file.component.FileOperator;
import com.sundeinfo.file.component.FilePreviewNormalizeManager;
import com.sundeinfo.file.model.FilePreviewAttribute;
import com.sundeinfo.security.model.AnyUser;
import com.sundeinfo.security.utility.AuthenticationGetter;
import com.sundeinfo.sypglass.annotation.SendMessage;
import com.sundeinfo.sypglass.component.FolderBundle;
import com.sundeinfo.sypglass.component.PermissionAdapter;
import com.sundeinfo.sypglass.component.SendMessageAdapter;
import com.sundeinfo.sypglass.define.FileDocType;
import com.sundeinfo.sypglass.define.FileStatus;
import com.sundeinfo.sypglass.define.MessageType;
import com.sundeinfo.sypglass.define.SypGlass;
import com.sundeinfo.sypglass.dto.*;
import com.sundeinfo.sypglass.log.file.*;
import com.sundeinfo.sypglass.log.multi.DeleteFilesLog;
import com.sundeinfo.sypglass.model.ExternalDB;
import com.sundeinfo.sypglass.service.ExternalDataSourceService;
import com.sundeinfo.sypglass.service.GridfsService;
import com.sundeinfo.sypglass.service.FileService;
import com.sundeinfo.sypglass.service.FolderService;
import com.sundeinfo.sypglass.utility.FileUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.*;
import java.net.URLEncoder;
import java.util.*;

import static java.util.UUID.randomUUID;

@Api(description = "用户系统API")
@RestController
@RequestMapping("user")
public class FileController extends AbstractController<FileController> {

    @Value("${file.folderTmpPath}")
    private String tmpPath;

    @Value("${solr.sync.url}")
    private String solrSyncUrl;

    @Autowired
    private FolderService folderService;

    @Autowired
    private FileService fileService;

    @Autowired
    private ExternalDataSourceService externalDataSourceService;

    @Autowired
    private SendMessageAdapter sendMessageAdapter;

    @Autowired
    private FolderBundle folderBundle;

    @Autowired
    private GridfsService gridfsService;

    @Autowired
    private HttpService httpService;

    @Autowired
    private FileOperator fileOperator;

    @Autowired
    private PermissionAdapter permissionAdapter;

    @Autowired
    private FilePreviewNormalizeManager filePreviewNormalizeManager;

    @Autowired
    private AuthenticationGetter authenticationGetter;

    private Map<String,String> downLoadAllowGuid = new HashMap<String,String>();

    @ApiOperation(value="查找单一文件信息", notes="查找单一文件信息")
    @GetMapping("file/{id}")
    @ActionLog(execClass = ReadFileLog.class)
    public ResponseEntity<Result<FileDto>> getFile(@PathVariable(value = "id") long id){
        return requestHandler.doGet(
                new GetCallback<FileDto>() {
                    private FileDto fileDto;
                    @Override
                    public RequestState doCheck() throws IllegalAccessException, InstantiationException, NoSuchFieldException {
                        fileDto = fileService.findWithAdditionalById(id);
                        if (fileDto == null){
                            return RequestState.FILE_STATUS_DELETE;
                        }
                        return RequestState.SUCCESS;
                    }
                    @Override
                    public FileDto invoke() throws Exception {
                        fileDto.setFolder(folderService.findById(fileDto.getFolderId()));
                        fileDto.setStatusName(SypGlass.getFileStatusName(fileDto.getStatus()));
                        List<FolderDto> parents =  folderService.findParentListByFolderId(fileDto.getFolderId());
                        permissionAdapter.setFilePermissionMap(fileDto,parents);
                        return fileDto;
                    }
                }
        );
    }

    @ApiOperation(value="查找文献履历", notes="查找文献履历")
    @GetMapping("file/{id}/seq/{seq}")
    @ActionLog(execClass = ReadFileLog.class)
    public ResponseEntity<Result<FileLiteratureDto>> getLiteratureHistory(@PathVariable(value = "id") long id, @PathVariable(value = "seq") int seq){
        return requestHandler.doGet(
                new GetCallback<FileLiteratureDto>() {
                    private FileLiteratureDto fileDto;
                    @Override
                    public RequestState doCheck() throws IllegalAccessException, InstantiationException, NoSuchFieldException {
                        return RequestState.SUCCESS;
                    }
                    @Override
                    public FileLiteratureDto invoke() throws Exception {
                        FileLiteratureDto fileLiteratureDto = new FileLiteratureDto();
                        fileLiteratureDto.setFileAccessoryHistoryDto(fileService.findFileAccessoryHistoriesByFileId(id,seq));
                        fileLiteratureDto.setFileMetadataHistoryDto(fileService.findFileMetadataHistoriesByFileId(id,seq));
                        return fileLiteratureDto;
                    }
                }
        );
    }

    @ApiOperation(value="文件预览", notes="文件预览")
    @GetMapping("file/{fileid}/accessory/{id}/seq/{seq}/preview")
    @ActionLog(execClass = PreviewFileLog.class)
    public ResponseEntity<Result<FilePreviewDto>> previewFile(@PathVariable(value = "fileid") long fileid,
                                                              @PathVariable(value = "id") long id,
                                                              @PathVariable(value = "seq") int seq,
                                                              HttpServletRequest request, HttpServletResponse response) throws Exception {
        return requestHandler.doGet(
                new GetCallback<FilePreviewDto>() {
                    private FileDto fileDto;
                    @Override
                    public RequestState doCheck() throws IllegalAccessException, InstantiationException, NoSuchFieldException {
                        fileDto = fileService.findWithAdditionalById(fileid);
                        if (fileDto.getStatus() == 9){
                            return RequestState.FILE_STATUS_DELETE;
                        }
                        return RequestState.SUCCESS;
                    }
                    @Override
                    public FilePreviewDto invoke() throws Exception {
                        FileAccessoryDto fileAccessoryDto = null;
                        for(FileAccessoryDto fileAccessoryDto1:fileDto.getFileAccessoryDto()){
                            if (fileAccessoryDto1.getId() == id){
                                fileAccessoryDto = fileAccessoryDto1;
                            }
                        }
                        if (fileAccessoryDto == null) return null;
                        FilePreviewDto filePreviewDto = fileService.createPreviewDto(fileAccessoryDto);

                        List<FolderDto> parents =  folderService.findParentListByFolderId(fileDto.getFolderId());
                        permissionAdapter.setFilePermissionMap(fileDto,parents);
                        filePreviewDto.setPermissionMap(fileDto.getPermissionMap());

                        GridFSDBFile gridFSDBFile = gridfsService.getById(new ObjectId(filePreviewDto.getSaveId()));
                        if (gridFSDBFile == null) {
                            return null;
                        }

                        try {
                            String fileName = randomUUID().toString()+"."+fileAccessoryDto.getSuffix();
                            File file = fileOperator.create(tmpPath  +  fileName);
                            gridFSDBFile.writeTo(file);
                            FilePreviewAttribute attribute = filePreviewNormalizeManager.normalize(file);
                            filePreviewDto.setUrl(attribute);
                            AnyUser anyUser = authenticationGetter.getAnyUser();
                            fileService.addActionHistory(id,FunctionType.READ,anyUser.getUser().getId(),anyUser.getUser().getName());
                            return filePreviewDto;
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        return null;
                    }
                }
        );
    }



    @ApiOperation(value="文件预览", notes="文件预览")
    @GetMapping("file/{id}/seq/{seq}/preview")
    @ActionLog(execClass = PreviewFileLog.class)
    public ResponseEntity<Result<FilePreviewDto>> previewFile(@PathVariable(value = "id") long id,@PathVariable(value = "seq") int seq) throws Exception {
        return requestHandler.doGet(
                new GetCallback<FilePreviewDto>() {
                    private FileDto fileDto;
                    @Override
                    public RequestState doCheck() throws IllegalAccessException, InstantiationException, NoSuchFieldException {
                        fileDto = fileService.findWithAdditionalById(id);
                        if (fileDto.getStatus() == 9){
                            return RequestState.FILE_STATUS_DELETE;
                        }
                        return RequestState.SUCCESS;
                    }
                    @Override
                    public FilePreviewDto invoke() throws Exception {

                        FilePreviewDto filePreviewDto = fileService.createPreviewDto(fileDto,seq == 0 ? fileDto.getFileHistoryDto().size():seq);

                        List<FolderDto> parents =  folderService.findParentListByFolderId(fileDto.getFolderId());
                        permissionAdapter.setFilePermissionMap(fileDto,parents);
                        filePreviewDto.setPermissionMap(fileDto.getPermissionMap());
                        //************临时代码防止预览文献时报错************
                        if(StringUtils.isBlank(filePreviewDto.getSaveId())){
                            return filePreviewDto;
                        }
                        //**************************************************
                        GridFSDBFile gridFSDBFile = gridfsService.getById(new ObjectId(filePreviewDto.getSaveId()));
                        if (gridFSDBFile == null) {
                            return null;
                        }

                        try {
                            String fileName = randomUUID().toString()+"."+fileDto.getSuffix();
                            File file = fileOperator.create(tmpPath  +  fileName);
                            gridFSDBFile.writeTo(file);
                            FilePreviewAttribute attribute = filePreviewNormalizeManager.normalize(file);
                            filePreviewDto.setUrl(attribute);
                            AnyUser anyUser = authenticationGetter.getAnyUser();
                            fileService.addActionHistory(id,FunctionType.READ,anyUser.getUser().getId(),anyUser.getUser().getName());
                            return filePreviewDto;
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        return null;
                    }
                }
        );
    }

    @ApiOperation(value="查询文献元数据和附件", notes="查询文献元数据和附件")
    @GetMapping("file/{id}/Literature")
    @ActionLog(execClass = PreviewFileLog.class)
    public ResponseEntity<Result<FileDto>> getLiterature(@PathVariable(value = "id") long id) throws Exception {
        return requestHandler.doGet(
                new GetCallback<FileDto>() {
                    private FileDto fileDto;
                    @Override
                    public RequestState doCheck() throws IllegalAccessException, InstantiationException, NoSuchFieldException {
                        fileDto = fileService.findWithAdditionalById(id);
                        if (fileDto.getStatus() == 9){
                            return RequestState.FILE_STATUS_DELETE;
                        }
                        return RequestState.SUCCESS;
                    }
                    @Override
                    public FileDto invoke() throws Exception {
                        return fileDto;
                    }
                }
        );
    }

    @ApiOperation(value="文献履历附件预览", notes="文献履历附件预览")
    @GetMapping("file/{id}/seq/{saveId}/Literature/preview")
    @ActionLog(execClass = PreviewFileLog.class)
    public ResponseEntity<Result<FilePreviewDto>> previewLiteratureFile(@PathVariable(value = "id") long id, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return requestHandler.doGet(
                new GetCallback<FilePreviewDto>() {
                    @Override
                    public RequestState doCheck() throws IllegalAccessException, InstantiationException, NoSuchFieldException {

                        return RequestState.SUCCESS;
                    }
                    @Override
                    public FilePreviewDto invoke() throws Exception {
                        FileAccessoryHistoryDto fileAccessoryHistoryDto = fileService.findFileAccessoryHistoriesById(id);
                        //**************************************************
                        GridFSDBFile gridFSDBFile = gridfsService.getById(new ObjectId(fileAccessoryHistoryDto.getSaveId()));
                        if (gridFSDBFile == null) {
                            return null;
                        }
                        FilePreviewDto filePreviewDto = new FilePreviewDto();
                        try {
                            String fileName = randomUUID().toString()+"."+fileAccessoryHistoryDto.getSuffix();
                            File file = fileOperator.create(tmpPath  +  fileName);
                            gridFSDBFile.writeTo(file);
                            FilePreviewAttribute attribute = filePreviewNormalizeManager.normalize(file);
                            filePreviewDto.setUrl(attribute);
                            filePreviewDto.setSuffix(fileAccessoryHistoryDto.getSuffix());
                            filePreviewDto.setName(fileAccessoryHistoryDto.getName());
                            AnyUser anyUser = authenticationGetter.getAnyUser();
                            fileService.addActionHistory(fileAccessoryHistoryDto.getFileId(),FunctionType.READ,anyUser.getUser().getId(),anyUser.getUser().getName());
                            return filePreviewDto;
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        return null;
                    }
                }
        );
    }

    @ApiOperation(value="文件改变路径和文件改名", notes="文件改变路径和文件改名")
    @PutMapping("file")
    @SendMessage(type = MessageType.MODIFY)
    @ActionLog(execClass = RenameFileLog.class)
    public ResponseEntity<Result> putFile(@Valid @RequestBody DocumentEditDto documentEditDto) {
        return requestHandler.doPost(
                new PostCallback() {
                    private FileDto fileDto;
                    @Override
                    public RequestState doCheck() throws IllegalAccessException, InstantiationException {
                        if(documentEditDto.getId() < 1){
                            return RequestState.PARAM_USELESS;
                        }
                        fileDto = fileService.findById(documentEditDto.getId());
                        if (fileDto == null){
                            return RequestState.PARAM_USELESS;
                        }
                        return RequestState.SUCCESS;
                    }
                    @Override
                    public RequestState invoke() throws Exception {
                        AnyUser anyUser = authenticationGetter.getAnyUser();

                        if (fileDto.getFolderId() != documentEditDto.getParentId()){
                            FolderDto folderDto = folderService.findById(documentEditDto.getParentId());
                            fileDto.setFolderId(folderDto.getId());
                            fileDto.setParentPath(folderDto.getFullPath());
                            fileDto.setParentPathName(folderDto.getFullName());
                        }

                        fileDto.setName(documentEditDto.getName());
                        fileDto.setLastUpdateUserId(anyUser.getUser().getId());
                        fileDto.setLastUpdateUserName(anyUser.getUser().getName());
                        fileDto.setLastUpdateDate(new Date());
                        int i = fileService.modFileNameAndFolder(fileDto);
                        return i > 0? RequestState.SUCCESS : RequestState. DB_INSERT_ERROR;
                    }
                }
        );
    }

    @ApiOperation(value="上传文件", notes="上传文件")
    @PostMapping("folder/{id}/file")
    @ActionLog(execClass = AddFileLog.class)
    public ResponseEntity<Result> uploadFile(@RequestPart("file") MultipartFile[] files,
                                                @PathVariable(value = "id") long id,
                                                @RequestParam String send,
                                                @RequestParam String comment) {
        return requestHandler.doPost(
                new PostCallback() {
                    @Override
                    public RequestState doCheck() {
                        return (files == null || files.length == 0) ? RequestState.PARAM_USELESS : RequestState.SUCCESS;
                    }
                    @Override
                    public RequestState invoke() throws Exception {
                        for (MultipartFile file:files){
                            RequestState result = saveFile(file,id,send,comment);
                            if (result != RequestState.SUCCESS){
                                return result;
                            }
                        }
                        return RequestState.SUCCESS;
                    }
                }
        );
    }

    @ApiOperation(value="文件更新", notes="文件更新")
    @PostMapping("folder/{id}/update")
    @ActionLog(execClass = AddFileLog.class)
    public ResponseEntity<Result> uploadOldFile(@RequestPart("file") MultipartFile[] files,
                                                @PathVariable(value = "id") long id,
                                                @RequestParam String send,
                                                @RequestParam String comment) {
        return requestHandler.doPost(
                new PostCallback() {
                    @Override
                    public RequestState doCheck() {
                        return (files == null || files.length == 0) ? RequestState.PARAM_USELESS : RequestState.SUCCESS;
                    }
                    @Override
                    public RequestState invoke() throws Exception {
                        List<FileDto> folderExistingFileList = fileService.findByFolderWithHistoryWithoutDelete(id);
                        for (MultipartFile file:files){
                            FileDto existing = getExisting(file,folderExistingFileList);
                            if (existing == null){
                                RequestState result = saveFile(file,id,send,comment);
                                if (result != RequestState.SUCCESS){
                                    return result;
                                }
                            }else{
                                existing.setComment(comment);
                                RequestState result = updateFile(file,send,existing);
                                if (result != RequestState.SUCCESS){
                                    return result;
                                }
                            }
                        }
                        return RequestState.SUCCESS;
                    }
                }
        );
    }

    @ApiOperation(value="文件夹新增", notes="文件夹新增")
    @PostMapping("folder/new")
    @ActionLog(execClass = AddFileLog.class)
    public ResponseEntity<Result> uploadNewFileWithFolder(@RequestParam("file") MultipartFile[] files,
                                                          @RequestParam("id") String id,
                                                          @RequestParam("send") String send,
                                                          @RequestParam("comment") String comment) {
        return requestHandler.doPost(
                new PostCallback() {
                    @Override
                    public RequestState doCheck() {
                        return (files == null || files.length == 0) ? RequestState.PARAM_USELESS : RequestState.SUCCESS;
                    }
                    @Override
                    public RequestState invoke() throws Exception {
                        File ioFile;
                        List<FileDto> folderExistingFileList;
                        for (MultipartFile file:files){
                            ioFile = new File(file.getOriginalFilename());
                            long folderId = folderBundle.bundle(Long.parseLong(id),ioFile.getParent());
                            folderExistingFileList = fileService.findByFolderWithHistoryWithoutDelete(folderId);
                            FileDto existing = getExisting(file,folderExistingFileList);
                            if (existing == null){
                                RequestState result = saveFile(file,folderId,send,comment);
                                if (result != RequestState.SUCCESS){
                                    return result;
                                }
                            }else{
                                existing.setComment(comment);
                                RequestState result = updateFile(file,send,existing);
                                if (result != RequestState.SUCCESS){
                                    return result;
                                }
                            }
                        }
                        return RequestState.SUCCESS;
                    }
                }
        );
    }

    private FileDto getExisting(MultipartFile file,List<FileDto> folderExistingFileList){
        File ioFile = new File(file.getOriginalFilename());
        for (FileDto existing:folderExistingFileList) {
            if (ioFile.getName().equals(existing.getName() + "." + existing.getSuffix())){
                return existing;
            }
        }
        return null;
    }

    private RequestState saveFile(MultipartFile file,long folderId,String sendUser,String comment){
        try {
            GridFSInputFile input = gridfsService.save(file);
            if (input == null) {
                return RequestState.FILE_INSERT_ERROR;
            }
            //该文件为mutilpart文件
            //文件夹上传时是全路径，文件上传时是文件名称，所以这里需要取得实际文件名称
            String clientLocalName = file.getOriginalFilename();
            String originalFilename = clientLocalName.split("/")[clientLocalName.split("/").length - 1];

            //获取文件名        
            String filename = originalFilename.substring(0,originalFilename.lastIndexOf("."));
            //获取文件名后缀 
            String suffix = originalFilename.substring(originalFilename.lastIndexOf(".")+1);

            AnyUser anyUser = authenticationGetter.getAnyUser();
            FolderDto parentFolderDto = folderService.findById(folderId);

            FileDto fileDto = fileService.createNew()
                    .setFolderId(folderId)
                    .setDocType(FileDocType.FILE)
                    .setName(filename)
                    .setSuffix(suffix)
                    .setStatus(FileStatus.NORMAL)
                    .setSize(FileUtils.getFileSize(file))
                    .setIsDelete((byte)0)
                    .setCreateUserId(anyUser.getUser().getId())
                    .setCreateUserName(anyUser.getUser().getName())
                    .setCreateDate(new Date())
                    .setLastUpdateUserId(anyUser.getUser().getId())
                    .setLastUpdateUserName(anyUser.getUser().getName())
                    .setLastUpdateDate(new Date())
                    .setComment(comment)
                    .setSaveId(input.getId().toString())
                    .setSaveMD5(input.getMD5())
                    .setSaveLength(input.getLength())
                    .setParentPath(parentFolderDto.getFullPath())
                    .setParentPathName(parentFolderDto.getFullName())
                    .build();

            int result = fileService.save(fileDto);

            if (result > 0){
                String url = solrSyncUrl + fileDto.getId();
                httpService.doPost(url);
            }

            if (result > 0){
                sendMessageAdapter.send(sendUser,fileDto.getId(),MessageType.NEW);
                return RequestState.SUCCESS;
            }else {
                return RequestState.ERROR;
            }


        }catch(Exception e) {
            logger.error("没有找到文件");
            return RequestState.DB_UPDATE_ERROR;
        }
    }

    private class GridFSFile{
        public GridFSInputFile file;
        public String name;
        public String suffix;
        public String size;
    }

    private GridFSFile getGridFSFile(MultipartFile file){
        GridFSInputFile input = gridfsService.save(file);
        if (input == null) {
            return null;
        }
        //该文件为mutilpart文件
        //文件夹上传时是全路径，文件上传时是文件名称，所以这里需要取得实际文件名称
        String clientLocalName = file.getOriginalFilename();
        String originalFilename = clientLocalName.split("/")[clientLocalName.split("/").length - 1];

        //获取文件名        
        String filename = originalFilename.substring(0,originalFilename.lastIndexOf("."));
        //获取文件名后缀 
        String suffix = originalFilename.substring(originalFilename.lastIndexOf(".")+1);

        GridFSFile gridFSFile = new GridFSFile();
        gridFSFile.file = input;
        gridFSFile.name = filename;
        gridFSFile.suffix = suffix;
        gridFSFile.size = FileUtils.getFileSize(file);

        return gridFSFile;
    }

    @ApiOperation(value="上传文献", notes="上传文献")
    @PostMapping("folder/{id}/literature")
    @ActionLog(execClass = AddFileLog.class)
    public ResponseEntity<Result> uploadLiterature(@PathVariable(value = "id") long id,
                                                    LiteratureUploadDto literatureUploadDto) {
        return requestHandler.doPost(
                new PostCallback() {
                    @Override
                    public RequestState doCheck() throws Exception {
                        if(StringUtils.isBlank(literatureUploadDto.getMetaStr())){
                            return RequestState.PARAM_USELESS;
                        }
                        String str = literatureUploadDto.getMetaStr().replaceAll( "\"(\\w+)\"(\\s*:\\s*)" ,  "$1$2" );
                        List<FileMetadataDto> list = JSONArray.parseArray(str, FileMetadataDto.class);
                        literatureUploadDto.setMetadata(list);
                        if (literatureUploadDto == null) return RequestState.PARAM_USELESS;
                        if (literatureUploadDto.getMetadata() == null || literatureUploadDto.getMetadata().size() == 0) return RequestState.PARAM_USELESS;
                        return RequestState.SUCCESS;
                    }
                    @Override
                    public RequestState invoke() throws Exception {
                        //先存实际文件
                        List<GridFSFile> inputFiles = new ArrayList<>();
                        if (literatureUploadDto.getFiles() != null && literatureUploadDto.getFiles().length > 0){
                            for(MultipartFile multipartFile : literatureUploadDto.getFiles()){
                                GridFSFile gridFSFile = getGridFSFile(multipartFile);
                                if (gridFSFile == null) {
                                    return RequestState.FILE_INSERT_ERROR;
                                }else{

                                    inputFiles.add(gridFSFile);
                                }
                            }
                        }

                        AnyUser anyUser = authenticationGetter.getAnyUser();
                        FolderDto parentFolderDto = folderService.findById(id);

                        List<FileMetadataDto> fileMetadataDtos = literatureUploadDto.getMetadata();
                        FileDto fileDto = fileService.createNew()
                                .setFolderId(id)
                                .setDocType(FileDocType.LITERATURE)
                                .setName(getValue(fileMetadataDtos,"name"))
                                //将文献的作者、来源存入file中用于排序
                                .setRankAuthor(getValue(fileMetadataDtos,"author"))
                                .setRankSource(getValue(fileMetadataDtos,"source"))
                                .setStatus(FileStatus.NORMAL)
                                .setIsDelete((byte)0)
                                .setCreateUserId(anyUser.getUser().getId())
                                .setCreateUserName(anyUser.getUser().getName())
                                .setCreateDate(new Date())
                                .setLastUpdateUserId(anyUser.getUser().getId())
                                .setLastUpdateUserName(anyUser.getUser().getName())
                                .setLastUpdateDate(new Date())
                                .setComment(literatureUploadDto.getComment())
                                .setParentPath(parentFolderDto.getFullPath())
                                .setParentPathName(parentFolderDto.getFullName())
                                .build();

                        int result = 0;
                        result = fileService.save(fileDto);
                        result = fileService.saveFileMetadataList(fileDto.getId(),fileMetadataDtos,anyUser.getUser().getId(),anyUser.getUser().getName());

                        List<FileAccessoryDto> fileAccessoryDtoList = new ArrayList<>();
                        for (GridFSFile gridFSFile:inputFiles){
                            FileAccessoryDto fileAccessoryDto = new FileAccessoryDto();
                            fileAccessoryDto.setType(1);//普通文件
                            fileAccessoryDto.setFileId(fileDto.getId());
                            fileAccessoryDto.setFolderId(id);
                            fileAccessoryDto.setName(gridFSFile.name);
                            fileAccessoryDto.setSuffix(gridFSFile.suffix);
                            fileAccessoryDto.setStatus((byte)FileStatus.NORMAL.code());
                            fileAccessoryDto.setSize(gridFSFile.size);
                            fileAccessoryDto.setIsDelete((byte)0);
                            fileAccessoryDto.setCreateUserId(anyUser.getUser().getId());
                            fileAccessoryDto.setCreateUsername(anyUser.getUser().getName());
                            fileAccessoryDto.setCreateDate(new Date());
                            fileAccessoryDto.setLastUpdateUserId(anyUser.getUser().getId());
                            fileAccessoryDto.setLastUpdateUsername(anyUser.getUser().getName());
                            fileAccessoryDto.setLastUpdateDate(new Date());
                            fileAccessoryDto.setComment(literatureUploadDto.getComment());
                            fileAccessoryDto.setSaveId(gridFSFile.file.getId().toString());
                            fileAccessoryDto.setSaveMD5(gridFSFile.file.getMD5());
                            fileAccessoryDto.setSaveLength(gridFSFile.file.getLength());
                            fileAccessoryDtoList.add(fileAccessoryDto);
                        }

                        //追加数据库链接
                        if (literatureUploadDto.getDatabase() != null && literatureUploadDto.getDatabase() > 0){
                            ExternalDBDto externalDB = externalDataSourceService.findDBById(literatureUploadDto.getDatabase());
                            FileAccessoryDto fileAccessoryDto = new FileAccessoryDto();
                            fileAccessoryDto.setType(2);//数据库链接
                            fileAccessoryDto.setFileId(fileDto.getId());
                            fileAccessoryDto.setFolderId(id);
                            fileAccessoryDto.setName(externalDB.getDbName());
                            fileAccessoryDto.setStatus((byte)FileStatus.NORMAL.code());
                            fileAccessoryDto.setIsDelete((byte)0);
                            fileAccessoryDto.setCreateUserId(anyUser.getUser().getId());
                            fileAccessoryDto.setCreateUsername(anyUser.getUser().getName());
                            fileAccessoryDto.setCreateDate(new Date());
                            fileAccessoryDto.setLastUpdateUserId(anyUser.getUser().getId());
                            fileAccessoryDto.setLastUpdateUsername(anyUser.getUser().getName());
                            fileAccessoryDto.setLastUpdateDate(new Date());
                            fileAccessoryDto.setSaveId(String.valueOf(externalDB.getId()));
                            fileAccessoryDto.setComment(literatureUploadDto.getComment());
                            fileAccessoryDtoList.add(fileAccessoryDto);
                        }

                        //保存所有附件
                        result = fileService.saveFileAccessoryList(fileAccessoryDtoList);
                        //保存记录
                        result = fileService.saveLiteratureHistory(fileAccessoryDtoList,fileDto.getId(),fileMetadataDtos,anyUser.getUser().getId(),anyUser.getUser().getName(),1);

                        String url = solrSyncUrl + fileDto.getId();
                        httpService.doPost(url);

                        //sendMessageAdapter.send(literatureUploadDto.getSender(),fileDto.getId(),MessageType.NEW);

                        return RequestState.SUCCESS;
                    }
                }
        );
    }

    private String getValue(List<FileMetadataDto> fileMetadataDtos , String code){
        for(FileMetadataDto fileMetadataDto:fileMetadataDtos){
            if (fileMetadataDto.getCode().equals(code)){
                return fileMetadataDto.getValue();
            }
        }
        return "";
    }

    @ApiOperation(value="指定文件更新", notes="指定文件更新")
    @PostMapping("file/{id}/upload")
    @SendMessage(type = MessageType.MODIFY,idSeq = 1)
    @ActionLog(execClass = UpdateFileLog.class)
    public ResponseEntity<Result> uploadModifyFile(@RequestPart("file") MultipartFile file,
                                                   @PathVariable long id,
                                                   @RequestParam String send,
                                                   @RequestParam String comment) {
        return requestHandler.doPost(
                new PostCallback() {
                    @Override
                    public RequestState doCheck() {
                        return (file == null) ? RequestState.PARAM_USELESS : RequestState.SUCCESS;
                    }
                    @Override
                    public RequestState invoke() throws Exception {
                        FileDto fileDto = fileService.findWithAdditionalById(id);
                        fileDto.setComment(comment);
                        return updateFile(file,send,fileDto);
                    }
                }
        );
    }

    @ApiOperation(value="文献更新", notes="文献更新")
    @PostMapping("file/{id}/upload/literature")
    @SendMessage(type = MessageType.MODIFY,idSeq = 1)
    @ActionLog(execClass = UpdateFileLog.class)
    public ResponseEntity<Result> uploadLiteratureFile(LiteratureUploadDto literatureUploadDto,@PathVariable long id) {
        return requestHandler.doPost(
                new PostCallback() {
                    @Override
                    public RequestState doCheck() throws Exception {
                        if(StringUtils.isBlank(literatureUploadDto.getMetaStr())){
                            return RequestState.PARAM_USELESS;
                        }
                        if(StringUtils.isNotBlank(literatureUploadDto.getDeleteStr())){
                            String deleteStr = literatureUploadDto.getDeleteStr().replaceAll( "\"(\\w+)\"(\\s*:\\s*)" ,  "$1$2" );
                            List<Long> deleteList = JSONArray.parseArray(deleteStr, Long.class);
                            literatureUploadDto.setDeleteList(deleteList);
                        }
                        String str = literatureUploadDto.getMetaStr().replaceAll( "\"(\\w+)\"(\\s*:\\s*)" ,  "$1$2" );
                        List<FileMetadataDto> list = JSONArray.parseArray(str, FileMetadataDto.class);
                        literatureUploadDto.setMetadata(list);
                        if (literatureUploadDto == null) return RequestState.PARAM_USELESS;
                        if (literatureUploadDto.getMetadata() == null || literatureUploadDto.getMetadata().size() == 0) return RequestState.PARAM_USELESS;
                        return RequestState.SUCCESS;
                    }
                    @Override
                    public RequestState invoke() throws Exception {
                        //存新增文件
                        List<GridFSFile> inputFilesInsert = new ArrayList<>();
                        if (literatureUploadDto.getFiles() != null && literatureUploadDto.getFiles().length > 0){
                            for(MultipartFile multipartFile : literatureUploadDto.getFiles()){
                                GridFSFile gridFSFile = getGridFSFile(multipartFile);
                                if (gridFSFile == null) {
                                    return RequestState.FILE_INSERT_ERROR;
                                }else{
                                    inputFilesInsert.add(gridFSFile);
                                }
                            }
                        }
                        //存修改文件
                        List<GridFSFile> inputFilesUpdate = new ArrayList<>();
                        if (literatureUploadDto.getCoverFiles() != null && literatureUploadDto.getCoverFiles().length > 0){
                            for(MultipartFile multipartFile : literatureUploadDto.getCoverFiles()){
                                GridFSFile gridFSFile = getGridFSFile(multipartFile);
                                if (gridFSFile == null) {
                                    return RequestState.FILE_INSERT_ERROR;
                                }else{
                                    inputFilesUpdate.add(gridFSFile);
                                }
                            }
                        }
                        AnyUser anyUser = authenticationGetter.getAnyUser();
                        List<FileMetadataDto> fileMetadataDtos = literatureUploadDto.getMetadata();
                        //修改u_file表
                        int result = 0;
                        FileDto fileDto = fileService.findWithAdditionalById(id);
                        fileDto.setName(getValue(fileMetadataDtos,"name"));
                        fileDto.setLastUpdateUserId(anyUser.getUser().getId());
                        fileDto.setLastUpdateUserName(anyUser.getUser().getName());
                        fileDto.setLastUpdateDate(new Date());
                        fileDto.setComment(literatureUploadDto.getComment());
                        fileDto.setRankAuthor(getValue(fileMetadataDtos,"author"));
                        fileDto.setRankSource(getValue(fileMetadataDtos,"source"));
                        fileService.update(fileDto);

                        //更新元数据
                        result = fileService.saveFileMetadataList(fileDto.getId(),fileMetadataDtos,anyUser.getUser().getId(),anyUser.getUser().getName());

                        List<FileAccessoryDto> fileAccessoryDtoList = fileService.findFileAccessoryByFileId(id);
                        List<FileAccessoryDto> oldFileAccessoryDtoList = new ArrayList<>();
                        List<FileAccessoryDto> newFileAccessoryDtoList = new ArrayList<>();
                        //更新附件List
                        for(FileAccessoryDto fileAccessoryDto:fileAccessoryDtoList){
                            for(GridFSFile gridFSFile:inputFilesUpdate){
                                if(fileAccessoryDto.getName().equals(gridFSFile.name)){
                                    fileAccessoryDto.setName(gridFSFile.name);
                                    fileAccessoryDto.setSuffix(gridFSFile.suffix);
                                    fileAccessoryDto.setSize(gridFSFile.size);
                                    fileAccessoryDto.setSaveId(gridFSFile.file.getId().toString());
                                    fileAccessoryDto.setSaveMD5(gridFSFile.file.getMD5());
                                    fileAccessoryDto.setSaveLength(gridFSFile.file.getLength());
                                    oldFileAccessoryDtoList.add(fileAccessoryDto);
                                }
                            }
                            //数据库连接没有版本概念，先删除旧的再添加新的
                            if(fileAccessoryDto.getType() == 2){
                                literatureUploadDto.getDeleteList().add(Long.valueOf(fileAccessoryDto.getId()));
                            }
                        }
                        //新增附件List
                        for (GridFSFile gridFSFile:inputFilesInsert){
                            FileAccessoryDto fileAccessoryDto = new FileAccessoryDto();
                            fileAccessoryDto.setType(1);//普通文件
                            fileAccessoryDto.setFileId(fileDto.getId());
                            fileAccessoryDto.setFolderId(id);
                            fileAccessoryDto.setName(gridFSFile.name);
                            fileAccessoryDto.setSuffix(gridFSFile.suffix);
                            fileAccessoryDto.setStatus((byte)FileStatus.NORMAL.code());
                            fileAccessoryDto.setSize(gridFSFile.size);
                            fileAccessoryDto.setIsDelete((byte)0);
                            fileAccessoryDto.setCreateUserId(anyUser.getUser().getId());
                            fileAccessoryDto.setCreateUsername(anyUser.getUser().getName());
                            fileAccessoryDto.setCreateDate(new Date());
                            fileAccessoryDto.setLastUpdateUserId(anyUser.getUser().getId());
                            fileAccessoryDto.setLastUpdateUsername(anyUser.getUser().getName());
                            fileAccessoryDto.setLastUpdateDate(new Date());
                            fileAccessoryDto.setComment(literatureUploadDto.getComment());
                            fileAccessoryDto.setSaveId(gridFSFile.file.getId().toString());
                            fileAccessoryDto.setSaveMD5(gridFSFile.file.getMD5());
                            fileAccessoryDto.setSaveLength(gridFSFile.file.getLength());
                            newFileAccessoryDtoList.add(fileAccessoryDto);
                        }

                        //追加数据库链接
                        if (literatureUploadDto.getDatabase() != null && literatureUploadDto.getDatabase() > 0){
                            ExternalDBDto externalDB = externalDataSourceService.findDBById(literatureUploadDto.getDatabase());
                            FileAccessoryDto fileAccessoryDto = new FileAccessoryDto();
                            fileAccessoryDto.setType(2);//数据库链接
                            fileAccessoryDto.setFileId(fileDto.getId());
                            fileAccessoryDto.setFolderId(id);
                            fileAccessoryDto.setName(externalDB.getDbName());
                            fileAccessoryDto.setStatus((byte)FileStatus.NORMAL.code());
                            fileAccessoryDto.setIsDelete((byte)0);
                            fileAccessoryDto.setCreateUserId(anyUser.getUser().getId());
                            fileAccessoryDto.setCreateUsername(anyUser.getUser().getName());
                            fileAccessoryDto.setCreateDate(new Date());
                            fileAccessoryDto.setLastUpdateUserId(anyUser.getUser().getId());
                            fileAccessoryDto.setLastUpdateUsername(anyUser.getUser().getName());
                            fileAccessoryDto.setLastUpdateDate(new Date());
                            fileAccessoryDto.setSaveId(String.valueOf(externalDB.getId()));
                            fileAccessoryDto.setComment(literatureUploadDto.getComment());
                            newFileAccessoryDtoList.add(fileAccessoryDto);
                        }


                        //保存所有新增附件
                        result = fileService.saveFileAccessoryList(newFileAccessoryDtoList);
                        //保存所有更新附件
                        result = fileService.updateFileAccessoryList(oldFileAccessoryDtoList);

                        //删除附件
                        if(literatureUploadDto.getDeleteList() != null){
                            literatureUploadDto.getDeleteList().forEach(id ->{
                                fileService.deleteFileAccessory(id,anyUser.getUser().getId(),anyUser.getUser().getName());
                            });
                        }
                        //添加版本记录
                        List<FileAccessoryDto> fileAccessoryDtos = fileService.findFileAccessoryByFileId(id);
                        result = fileService.saveLiteratureHistory(fileAccessoryDtos,fileDto.getId(),fileMetadataDtos,anyUser.getUser().getId(),anyUser.getUser().getName(),fileDto.getFileHistoryDto().size()+1);

                        String url = solrSyncUrl + fileDto.getId();
                        httpService.doPost(url);

                        //sendMessageAdapter.send(literatureUploadDto.getSender(),fileDto.getId(),MessageType.NEW);

                        return RequestState.SUCCESS;
                    }
                }
        );
    }

    private RequestState updateFile(MultipartFile file,String sendUser,FileDto fileDto){
        try {
            GridFSInputFile input = gridfsService.save(file);
            if (input == null) {
                return RequestState.FILE_INSERT_ERROR;
            }
            AnyUser anyUser = authenticationGetter.getAnyUser();

            //该文件为mutilpart文件
            //文件夹上传时是全路径，文件上传时是文件名称，所以这里需要取得实际文件名称
            String clientLocalName = file.getOriginalFilename();
            String originalFilename = clientLocalName.split("/")[clientLocalName.split("/").length - 1];

            //获取文件名  
            String filename = originalFilename.substring(0,originalFilename.lastIndexOf("."));
            //获取文件名后缀 
            String suffix = originalFilename.substring(originalFilename.lastIndexOf(".")+1);

            fileDto.setName(filename);
            fileDto.setSuffix(suffix);
            fileDto.setSize(FileUtils.getFileSize(file));
            fileDto.setLastUpdateUserId(anyUser.getUser().getId());
            fileDto.setLastUpdateUserName(anyUser.getUser().getName());
            fileDto.setLastUpdateDate(new Date());
            fileDto.setSaveId(input.getId().toString());
            fileDto.setSaveMD5(input.getMD5());
            fileDto.setSaveLength(input.getLength());

            int result = fileService.update(fileDto);
            if (result > 0){
                sendMessageAdapter.send(sendUser,fileDto.getId(),MessageType.MODIFY);
                return RequestState.SUCCESS;
            }else {
                return RequestState.ERROR;
            }
        }catch(Exception e) {
            logger.error("没有找到文件");
            return RequestState.DB_UPDATE_ERROR;
        }
    }


    @ApiOperation(value="获取文件下载资格GUID", notes="获取文件下载GUID")
    @GetMapping("file/{id}/seq/{seq}/download")
    @SendMessage(type = MessageType.DOWNLOAD)
    @ActionLog(execClass = DownloadFileLog.class)
    public FilePreviewDto getDownLoadGuid(@PathVariable(value = "id") long id,@PathVariable(value = "seq") int seq) throws Exception {

        FileDto fileDto = fileService.findWithAdditionalById(id);
        FilePreviewDto filePreviewDto = fileService.createPreviewDto(fileDto,seq == 0 ? fileDto.getFileHistoryDto().size():seq);
        if (fileDto.getStatus() == 9){
            filePreviewDto.setStatus(fileDto.getStatus());
            return filePreviewDto;
        }

        GridFSDBFile gridFSDBFile = gridfsService.getById(new ObjectId(filePreviewDto.getSaveId()));
        if (gridFSDBFile == null) {
            return null;
        }
        //String fileName = gridFSDBFile.getFilename().replace(",", "");
        String OriginalfileName = gridFSDBFile.getFilename().replace(",", "");
        //处理斜杠问题 比如：A+SUV转移武汉/VW标准/DIN/DIN EN 10263-3 2002-02 EN.pdf 保存到本地会出错
        String[] fileNameSplit = OriginalfileName.split("/");
        String fileName = fileNameSplit[fileNameSplit.length -1];
        //文件保存到本地目录
        File file = fileOperator.create(tmpPath  +  fileName);
        gridFSDBFile.writeTo(file);


        AnyUser anyUser = authenticationGetter.getAnyUser();
        fileService.addActionHistory(id,FunctionType.DOWNLOAD,anyUser.getUser().getId(),anyUser.getUser().getName());

        filePreviewDto.setGuid(UUID.randomUUID().toString());
        downLoadAllowGuid.put(filePreviewDto.getGuid(), fileName);
        return filePreviewDto;
    }

    @ApiOperation(value="下载文献附件", notes="下载文献附件")
    @GetMapping("file/{saveId}/download")
    @SendMessage(type = MessageType.DOWNLOAD)
    @ActionLog(execClass = DownloadFileLog.class)
    public void getDownLoadLiterature(@PathVariable(value = "saveId") String saveId, HttpServletResponse response) throws Exception {
        GridFSDBFile gridFSDBFile = gridfsService.getById(new ObjectId(saveId));
        if (gridFSDBFile == null) {
            return;
        }
        //String fileName = gridFSDBFile.getFilename().replace(",", "");
        String OriginalfileName = gridFSDBFile.getFilename().replace(",", "");
        //处理斜杠问题 比如：A+SUV转移武汉/VW标准/DIN/DIN EN 10263-3 2002-02 EN.pdf 保存到本地会出错
        String[] fileNameSplit = OriginalfileName.split("/");
        String fileName = fileNameSplit[fileNameSplit.length -1];
        //文件保存到本地目录
        File file = fileOperator.create(tmpPath  +  fileName);
        gridFSDBFile.writeTo(file);
        OutputStream outputStream = null;
        try {
            // 通知浏览器进行文件下载
            outputStream = response.getOutputStream();
            response.addHeader("Access-Control-Expose-Headers","Content-Disposition");
            response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "utf-8"));
            response.addHeader("Content-Length", "" + file.length());
            response.setContentType("application/octet-stream");

            download(outputStream,file.getPath());
            outputStream.flush();
            outputStream.close();

        } catch (Exception e) {
            try{
                if (outputStream != null) {
                    outputStream.close();
                }
            }catch (Exception ignored){}
            e.printStackTrace();
        }
    }

    @ApiOperation(value="文件下载", notes="文件下载")
    @GetMapping("file/download/{id}/{guid}")
    public void downloadFile(@PathVariable(value = "id") long id,@PathVariable(value = "guid") String guid,
                             HttpServletRequest request, HttpServletResponse response) throws Exception {
        String fileName = "";
        // 该方法无需权限验证，但需要其他有权限验证方法验证后获取到GUID后方可进行下载
        synchronized (this) {
            if (downLoadAllowGuid.containsKey(guid)) {
                fileName = downLoadAllowGuid.get(guid);
                downLoadAllowGuid.remove(guid);
            } else {
                // 没有申请下载标识
                return;
            }
        }

        OutputStream outputStream = null;
        try {
            //文件保存到本地目录
            File file = fileOperator.create(tmpPath  +  fileName);
            // 通知浏览器进行文件下载
            outputStream = response.getOutputStream();
            response.addHeader("Access-Control-Expose-Headers","Content-Disposition");
            response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "utf-8"));
            response.addHeader("Content-Length", "" + file.length());
            response.setContentType("application/octet-stream");

            download(outputStream,file.getPath());
            outputStream.flush();
            outputStream.close();

        } catch (Exception e) {
            try{
                if (outputStream != null) {
                    outputStream.close();
                }
            }catch (Exception ignored){}
            e.printStackTrace();
        }
    }

    private void download(OutputStream outputStream,String filename) throws IOException {
        logger.info("----------------------------------开始下载文件：" + filename + "----------------------------------");
        byte[] buff = new byte[1024*1024*2];
        BufferedInputStream bis = null;
        // 读取filename
        bis = new BufferedInputStream(new FileInputStream(new File(filename)));
        int i = bis.read(buff);
        int size = i;
        while (i != -1) {
            size = size + i;
            //logger.info("文件正在加入缓冲区: " + size);
            outputStream.write(buff, 0, buff.length);
            outputStream.flush();
            i = bis.read(buff);
        }
        logger.info("----------------------------------结束下载文件：" + filename + "----------------------------------");
    }

    @ApiOperation(value="删除多个文件", notes="删除多个文件")
    @DeleteMapping("files")
    @ActionLog(execClass = DeleteFilesLog.class)
    public ResponseEntity<Result> deleteFiles(@RequestBody List<FileDto> fileDtos){
        return requestHandler.doPost(
                new PostCallback() {
                    @Override
                    public RequestState doCheck() {
                        return RequestState.SUCCESS;
                    }
                    @Override
                    public RequestState invoke() throws Exception {
                        List<Long> ids = ReflectUtils.reflectDataList(fileDtos,"id",Long.class);
                        int i = fileService.modFilesStatusToDelete(ids,(UserDto) authenticationGetter.getAnyUser().getUser());
                        return i > 0? RequestState.SUCCESS : RequestState.DB_DELETE_ERROR;
                    }
                }
        );
    }

    @ApiOperation(value="删除文件", notes="删除文件")
    @DeleteMapping("file")
    @ActionLog(execClass = DeleteFileLog.class)
    @SendMessage(type = MessageType.DELETE)
    public ResponseEntity<Result> deleteFile(@RequestBody FileDto fileDto){
        return requestHandler.doPost(
                new PostCallback() {
                    @Override
                    public RequestState doCheck() {
                        return RequestState.SUCCESS;
                    }
                    @Override
                    public RequestState invoke() throws Exception {
                        int i = fileService.modFileStatusToDelete(fileDto.getId(),(UserDto) authenticationGetter.getAnyUser().getUser());
                        AnyUser anyUser = authenticationGetter.getAnyUser();
                        fileService.addActionHistory(fileDto.getId(),FunctionType.DELETE,anyUser.getUser().getId(),anyUser.getUser().getName());
                        return i > 0? RequestState.SUCCESS : RequestState.DB_DELETE_ERROR;
                    }
                }
        );
    }

    @ApiOperation(value="查找文件阅览信息", notes="查找文件阅览信息")
    @GetMapping("file/{id}/actionhistory")
    public ResponseEntity<Result<List<ActionHistoryDto>>> getFileActionHistory(@PathVariable(value = "id") long id){
        return requestHandler.doGet(
                new GetCallback<List<ActionHistoryDto>>() {
                    @Override
                    public RequestState doCheck() {
                        return RequestState.SUCCESS;
                    }
                    @Override
                    public List<ActionHistoryDto> invoke() throws Exception {
                        return fileService.findActionHistory(id);
                    }
                }
        );
    }
}
