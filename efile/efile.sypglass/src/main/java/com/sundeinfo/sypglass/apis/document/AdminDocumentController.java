package com.sundeinfo.sypglass.apis.document;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sundeinfo.core.utility.StringUtils;
import com.sundeinfo.security.model.AnyUser;
import com.sundeinfo.core.token.Token;
import com.sundeinfo.core.mvc.controller.AbstractController;
import com.sundeinfo.core.request.*;
import com.sundeinfo.core.request.result.Result;
import com.sundeinfo.core.utility.ReflectUtils;
import com.sundeinfo.security.utility.AuthenticationGetter;
import com.sundeinfo.sypglass.define.FolderStatus;
import com.sundeinfo.sypglass.define.SypGlass;
import com.sundeinfo.sypglass.dto.*;
import com.sundeinfo.sypglass.model.Document;
import com.sundeinfo.sypglass.service.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Api(description = "管理系统API")
@RestController
@RequestMapping("admin")
public class AdminDocumentController extends AbstractController<AdminDocumentController> {

    @Value("${spring.http.multipart.maxFileSize}")
    private String fileSize;

    @Autowired
    private DocumentService documentService;

    @Autowired
    private FolderService folderService;

    @Autowired
    private FileService fileService;

    @Autowired
    private FolderIpService folderIpService;

    @Autowired
    private AdminService adminService;

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationGetter authenticationGetter;

    @ApiOperation(value="查找文件夹详细信息并包含子文件夹树", notes="查找文件夹详细信息并包含子文件夹树")
    @GetMapping(value = "folder/tree")
    public ResponseEntity<Result<FolderDto>> getFolder(){
        return requestHandler.doGet(
                new GetCallback<FolderDto>() {
                    @Override
                    public RequestState doCheck() {
                        return RequestState.SUCCESS;
                    }
                    @Override
                    public FolderDto invoke() throws Exception {
                        return folderService.findChildrenTreeByFolderId(SypGlass.ROOT_DEPT_ID);
                    }
                }
        );
    }

    @ApiOperation(value="查找文件夹详细信息", notes="查找文件夹详细信息")
    @GetMapping(value = "folder/{id}")
    public ResponseEntity<Result<FolderDto>> getFolder(@PathVariable(value = "id") long id){
        return requestHandler.doGet(
                new GetCallback<FolderDto>() {
                    @Override
                    public RequestState doCheck() {
                        return RequestState.SUCCESS;
                    }
                    @Override
                    public FolderDto invoke() throws Exception {
                        FolderDto folderDto = folderService.findById(id);
                        folderDto.setFolderIpDto(folderIpService.findIpInfoByFolderId(folderDto.getId()));

                        List<FolderDto> parents = folderService.findParentListByFolderId(id);
                        folderDto.confirmFileSize(parents,Integer.parseInt(fileSize.replace("MB","")));

                        List<Long> parentFolderIds = ReflectUtils.reflectDataList(parents,"id",Long.class);
                        List<AdminFolderDto> adminFolderDtos = adminService.findAdminByFolders(parentFolderIds);
                        List<Long> userIds = ReflectUtils.reflectDataList(adminFolderDtos,"userId",Long.class);
                        if (userIds.size() > 0){
                            folderDto.setAdmins(userService.findByIds(userIds));
                        }
                        return folderDto;
                    }
                }
        );
    }

    @ApiOperation(value="依据文件夹ID查找下级一层文件夹", notes="依据文件夹ID查找下级一层文件夹")
    @GetMapping(value = "folder/{id}/children")
    public ResponseEntity<Result<List<FolderDto>>> getChildrenFolders(@PathVariable(value = "id") long id){
        return requestHandler.doGet(
                new GetCallback<List<FolderDto>>() {
                    @Override
                    public RequestState doCheck() {
                        return RequestState.SUCCESS;
                    }
                    @Override
                    public List<FolderDto> invoke() throws Exception {
                        if (id == 0){
                            AdminDto adminDto = (AdminDto)authenticationGetter.getAnyUser().getAdmin();
                            if (adminDto.getLevel() == 1){
                                return folderService.findByParentId(id);
                            }else {
                                List<FolderDto> result = new ArrayList<>();
                                List<AdminFolderDto> adminFolderDtos = adminDto.getAdminFolders();
                                if (adminFolderDtos != null) {
                                    for (AdminFolderDto adminFolder:adminFolderDtos) {
                                        result.add(folderService.findById(adminFolder.getFolderId()));
                                    }
                                }
                                return result;
                            }
                        }else{
                            return folderService.findByParentId(id);
                        }
                    }
                }
        );
    }

    @ApiOperation(value="依据文件夹ID查找资源列表", notes="依据文件夹ID查找资源列表")
    @GetMapping(value = "folder/{id}/docs")
    public ResponseEntity<Result<List<Document>>> getDocument(@PathVariable(value = "id") int id){
        return requestHandler.doGet(
                new GetCallback<List<Document>>() {
                    @Override
                    public RequestState doCheck() {
                        return RequestState.SUCCESS;
                    }
                    @Override
                    public List<Document> invoke() throws Exception {
                        if (id == 0){
                            AdminDto adminDto = (AdminDto)authenticationGetter.getAnyUser().getAdmin();
                            if (adminDto.getLevel() == 1){
                                return documentService.findDocumentByFolderId(id);
                            }else {
                                List<Document> result = new ArrayList<>();
                                List<AdminFolderDto> adminFolderDtos = adminDto.getAdminFolders();
                                if (adminFolderDtos != null) {
                                    for (AdminFolderDto adminFolder:adminFolderDtos) {
                                        result.add(documentService.findDocumentByid(adminFolder.getFolderId(),"Folder"));
                                    }
                                }
                                return result;
                            }
                        }else{
                            return documentService.findDocumentByFolderId(id);
                        }
                    }
                }
        );
    }

    @ApiOperation(value="依据文件夹ID查找资源列表", notes="依据文件夹ID查找资源列表")
    @GetMapping(value = "folder/{id}/files/{pagenum}-{pagesize}")
    public ResponseEntity<Result<PageInfo<FileDto>>> getFile(@PathVariable(value = "id") long id, @PathVariable(value = "pagenum") int pagenum, @PathVariable(value = "pagesize") int pagesize){
        return requestHandler.doGet(
                new GetCallback<PageInfo<FileDto>>() {
                    @Override
                    public RequestState doCheck() {
                        return RequestState.SUCCESS;
                    }
                    @Override
                    public PageInfo<FileDto> invoke() throws Exception {
                        PageHelper.startPage(pagenum, pagesize);
                        List<FileDto> fileDtos = fileService.findByFolderWithoutDelete(id);
                        PageInfo<FileDto> pageInfo = new PageInfo<>(fileDtos);
                        return pageInfo;
                    }
                }
        );
    }

    @ApiOperation(value="依据关键字查找文档", notes="依据关键字查找文档")
    @GetMapping(value = "search/file/{pagenum}-{pagesize}")
    public ResponseEntity<Result<PageInfo<FileDto>>> search(@PathVariable int pagenum, @PathVariable int pagesize,@RequestParam String key){
        return requestHandler.doGet(
                new GetCallback<PageInfo<FileDto>>() {
                    @Override
                    public RequestState doCheck() {
                        return RequestState.SUCCESS;
                    }
                    @Override
                    public PageInfo<FileDto> invoke() throws Exception {
                        PageHelper.startPage(pagenum, pagesize);
                        List<FileDto> fileDtos = fileService.findBySearchKey(key);
                        PageInfo<FileDto> pageInfo = new PageInfo<>(fileDtos);
                        return pageInfo;
                    }
                }
        );
    }

    @ApiOperation(value="添加文件夹", notes="添加文件夹")
    @PostMapping(value = "folder")
    public ResponseEntity<Result<FolderDto>> postFolder(@RequestBody FolderDto folderDto){
        return requestHandler.doPost(
                new ResponseCallback<FolderDto>() {
                    @Override
                    public RequestState doCheck() throws Exception{
                        return RequestState.SUCCESS;
                    }
                    @Override
                    public RequestState invoke() throws Exception {
                        AnyUser anyUser = authenticationGetter.getAnyUser();
                        FolderDto parentFolderDto = folderService.findById(folderDto.getParentId());
                        FolderDto newFolder = folderService.createNew()
                                .setParentId(folderDto.getParentId())
                                .setName(folderDto.getName())
                                .setStatus((byte)FolderStatus.NORMAL.code())
                                .setCreateUserId(anyUser.getUser().getId())
                                .setCreateUserName(anyUser.getUser().getName())
                                .setCreateDate(new Date())
                                .setIsDelete((byte) 0)
                                .setParentPath(parentFolderDto.getFullPath())
                                .setParentPathName(parentFolderDto.getFullName())
                                .build();

                        int i = folderService.save(newFolder);
                        if (i > 0){
                            folderDto.setId(newFolder.getId());
                            return RequestState.SUCCESS;
                        } else{
                            return RequestState. DB_INSERT_ERROR;
                        }
                    }
                    @Override
                    public FolderDto response() throws Exception {
                        return folderDto;
                    }
                }
        );
    }

    @ApiOperation(value="编辑文件夹最大上传大小", notes="编辑文件夹最大上传大小")
    @PutMapping(value = "folder/{id}/filesize/{size}")
    public ResponseEntity<Result> putFolderFileSize(@PathVariable(value = "id") int id,@PathVariable(value = "size") int size){
        return requestHandler.doPost(
                new PostCallback() {
                    @Override
                    public RequestState doCheck() {
                        return RequestState.SUCCESS;
                    }
                    @Override
                    public RequestState invoke() throws Exception {
                        int i = folderService.modFolderFileSize(id,size);
                        //清除子级对父级的缓存
                        FolderDto folderDto = folderService.findChildrenListByFolderId(id,authenticationGetter.getAnyUser().getUser().getId());
                        List<FolderDto> children = folderDto.getChildren();
                        children.forEach(dto ->{
                            folderService.cacheEvict(dto.getId());
                        });
                        return i > 0? RequestState.SUCCESS : RequestState.DB_UPDATE_ERROR;
                    }
                }
        );
    }

    @ApiOperation(value="编辑文件夹管理员", notes="编辑文件夹管理员")
    @PutMapping(value = "folder/{id}/admin")
    public ResponseEntity<Result> putFolderAdmin(@PathVariable(value = "id") int id,@RequestBody AdminFolderDto adminFolderDto){
        return requestHandler.doPost(
                new PostCallback() {
                    @Override
                    public RequestState doCheck() {
                        if (adminService.isSysAdmin(adminFolderDto.getUserId())){
                            return RequestState.PARAM_USELESS_ADMIN;
                        }
                        if (adminService.hasSameFolderAdmin(adminFolderDto)){
                            return RequestState.PARAM_USELESS_ADMIN_FOLDER;
                        }
                        return RequestState.SUCCESS;
                    }
                    @Override
                    public RequestState invoke() throws Exception {
                        adminFolderDto.setFolderId(id);
                        int i = adminService.save(adminFolderDto);
                        return i > 0? RequestState.SUCCESS : RequestState.DB_UPDATE_ERROR;
                    }
                }
        );
    }

    @ApiOperation(value="取得文件夹管理员列表", notes="取得文件夹管理员列表")
    @GetMapping(value = "folder/{id}/admin")
    public ResponseEntity<Result<List<UserDto>>> getFolderAdmin(@PathVariable(value = "id") int id){

        return requestHandler.doGet(
                new GetCallback<List<UserDto>>() {
                    @Override
                    public RequestState doCheck() {
                        return RequestState.SUCCESS;
                    }
                    @Override
                    public List<UserDto> invoke() throws Exception {
                        return  adminService.findByFolder(id);
                    }
                }
        );
    }

    @ApiOperation(value="删除文件夹", notes="删除文件夹")
    @DeleteMapping(value = "folder")
    public ResponseEntity<Result> deleteFolder(@RequestBody FolderDto folderDto){
        return requestHandler.doPost(
                new PostCallback() {
                    @Override
                    public RequestState doCheck() {
                        return RequestState.SUCCESS;
                    }
                    @Override
                    public RequestState invoke() throws Exception {
                        //List<FolderDto> folderDtos = folderService.findChildrenListByFolderId(folderDto.getId());
                        //List<Long> ids = ReflectUtils.reflectDataList(folderDtos,"id",Long.class);
                        //int i = folderService.delete(ids,(UserDto)authenticationGetter.getAnyUser().getUser());
                        int i = folderService.delete(folderDto.getId(),(UserDto)authenticationGetter.getAnyUser().getUser());
                        return i > 0? RequestState.SUCCESS : RequestState.DB_DELETE_ERROR;
                    }
                }
        );
    }

    @ApiOperation(value="删除多个文件夹", notes="删除多个文件夹")
    @DeleteMapping(value = "folders")
    public ResponseEntity<Result> deleteFolders(@RequestBody List<FolderDto> folderDtos){
        return requestHandler.doPost(
                new PostCallback() {
                    @Override
                    public RequestState doCheck() {
                        return RequestState.SUCCESS;
                    }
                    @Override
                    public RequestState invoke() throws Exception {
                        //List<FolderDto> deleteFolderDtos = new ArrayList<>();
                        //for (FolderDto folderDto:folderDtos) {
                        //    deleteFolderDtos.addAll(folderService.findChildrenListByFolderId(folderDto.getId()));
                        //}
                        //List<Long> ids = ReflectUtils.reflectDataList(deleteFolderDtos,"id",Long.class);
                        List<Long> ids = ReflectUtils.reflectDataList(folderDtos,"id",Long.class);
                        int i = folderService.delete(ids,(UserDto)authenticationGetter.getAnyUser().getUser());
                        return i > 0? RequestState.SUCCESS : RequestState.DB_DELETE_ERROR;
                    }
                }
        );
    }

}
