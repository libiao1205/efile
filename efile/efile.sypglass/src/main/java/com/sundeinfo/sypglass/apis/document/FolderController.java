package com.sundeinfo.sypglass.apis.document;


import com.sundeinfo.core.log.ActionLog;
import com.sundeinfo.core.permission.PermissionHelper;
import com.sundeinfo.core.utility.ReflectUtils;
import com.sundeinfo.security.model.AnyUser;
import com.sundeinfo.core.mvc.controller.AbstractController;
import com.sundeinfo.core.request.*;
import com.sundeinfo.core.request.result.Result;
import com.sundeinfo.sypglass.annotation.IpChecked;
import com.sundeinfo.sypglass.component.PermissionAdapter;
import com.sundeinfo.sypglass.define.FolderStatus;
import com.sundeinfo.sypglass.define.SortType;
import com.sundeinfo.sypglass.define.SypGlass;
import com.sundeinfo.sypglass.dto.*;
import com.sundeinfo.sypglass.log.folder.AddFolderLog;
import com.sundeinfo.sypglass.log.folder.DeleteFolderLog;
import com.sundeinfo.sypglass.log.folder.RenameFolderLog;
import com.sundeinfo.sypglass.log.multi.DeleteFoldersLog;
import com.sundeinfo.sypglass.service.*;
import com.sundeinfo.security.utility.AuthenticationGetter;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

@Api(description = "用户系统API")
@RestController
@RequestMapping("user")
public class FolderController extends AbstractController<FolderController> {

    @Value("${spring.http.multipart.maxFileSize}")
    private String fileSize;

    @Autowired
    private FolderService folderService;

    @Autowired
    private AuthenticationGetter authenticationGetter;

    @Autowired
    private PermissionAdapter permissionAdapter;

    @ApiOperation(value="查找文件夹完整信息", notes="查找文件夹完整信息")
    @GetMapping("folder/{id}")
    public ResponseEntity<Result<FolderDto>> getFolderInfo(@PathVariable(value = "id") int id){
        return requestHandler.doGet(
                new GetCallback<FolderDto>() {
                    @Override
                    public RequestState doCheck() {
                        return RequestState.SUCCESS;
                    }
                    @Override
                    public FolderDto invoke() throws Exception {
                        FolderDto folderDto = null;
                        if (id == 0){
                            List<FolderDto> root = folderService.findByParentId(id);
                            folderDto = new FolderDto();
                            folderDto.setId(0);
                            permissionAdapter.setFolderPermissionMap(folderDto,root);
                            return folderDto;
                        }else {
                            folderDto = folderService.findCompleteById(id);
                            List<FolderDto> parents = folderService.findParentListByFolderId(id);
                            folderDto.confirmFileSize(parents,Integer.parseInt(fileSize.replace("MB","")));
                            permissionAdapter.setFolderPermissionMap(folderDto,parents);
                        }
                        return folderDto;
                    }
                }
        );
    }

    @ApiOperation(value="查找文件夹的下级一层子文件夹列表", notes="查找文件夹的下级一层子文件夹列表")
    @GetMapping("folder/{id}/children")
    @IpChecked
    public ResponseEntity<Result<List<FolderDto>>> getFolderChildrenList(@PathVariable(value = "id") int id){
        return requestHandler.doGet(
                new GetCallback<List<FolderDto>>() {
                    @Override
                    public RequestState doCheck() {
                        return RequestState.SUCCESS;
                    }
                    @Override
                    public List<FolderDto> invoke() throws Exception {
                        FolderDto folderDto = null;
                        if (id == 0){
                            List<FolderDto> root = folderService.findByParentId(id);
                            folderDto = new FolderDto();
                            folderDto.setId(0);
                            folderDto.setChildren(root);
                            permissionAdapter.setFolderPermissionMap(folderDto,root);
                        }else {
                            folderDto = folderService.findChildrenListByFolderId(id,authenticationGetter.getAnyUser().getUser().getId());
                            if (folderDto == null) return new ArrayList<>();
                            List<FolderDto> parents = folderService.findParentListByFolderId(id);
                            permissionAdapter.setFolderPermissionMap(folderDto,parents);

                        }
                        return folderDto.getChildren();
                    }
                }
        );
    }



    @ApiOperation(value="添加文件夹", notes="添加文件夹")
    @PostMapping("folder")
    @ActionLog(execClass = AddFolderLog.class)
    public ResponseEntity<Result<FolderDto>> postFolder(@RequestBody FolderDto folderDto){
        return requestHandler.doPost(
                new ResponseCallback<FolderDto>() {
                    FolderDto newFolder = null;
                    @Override
                    public RequestState doCheck() throws Exception{
                        return RequestState.SUCCESS;
                    }
                    @Override
                    public RequestState invoke() throws Exception {
                        AnyUser anyUser = authenticationGetter.getAnyUser();
                        FolderDto parentFolderDto = folderService.findById(folderDto.getParentId());
                        newFolder = folderService.createNew()
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
                        return i > 0? RequestState.SUCCESS : RequestState. DB_INSERT_ERROR;
                    }
                    @Override
                    public FolderDto response() throws Exception {
                        List<FolderDto> parents = folderService.findParentListByFolderId(newFolder.getId());
                        permissionAdapter.setFolderPermissionMap(newFolder,parents);
                        return newFolder;
                    }
                }
        );
    }

    @ApiOperation(value="文件夹改变路径和文件夹改名", notes="文件夹改变路径和文件夹改名")
    @PutMapping("folder")
    @ActionLog(execClass = RenameFolderLog.class)
    public ResponseEntity<Result> putFolder(@Valid @RequestBody DocumentEditDto documentEditDto) {
        return requestHandler.doPost(
                new PostCallback() {
                    private FolderDto folderDto;
                    @Override
                    public RequestState doCheck() throws IllegalAccessException, InstantiationException {
                        if(documentEditDto.getId() < 1){
                            return RequestState.PARAM_USELESS;
                        }
                        folderDto = folderService.findById(documentEditDto.getId());
                        if (folderDto == null){
                            return RequestState.PARAM_USELESS;
                        }
                        if (folderDto.getParentId() != documentEditDto.getParentId()){
                            List<FolderDto> folderDtos = folderService.findChildrenListByFolderId(documentEditDto.getId());
                            for (FolderDto child:folderDtos) {
                                if (child.getId() == documentEditDto.getParentId()){
                                    logger.info("不能把自己或者自己的子文件夹设为上级文件夹");
                                    return RequestState.PARAM_USELESS_PARENT_DEPT;
                                }
                            }
                        }
                        return RequestState.SUCCESS;
                    }
                    @Override
                    public RequestState invoke() throws Exception {

                        if (folderDto.getParentId() != documentEditDto.getParentId()){
                            FolderDto parent = folderService.findById(documentEditDto.getParentId());
                            folderDto.setParentId(parent.getId());
                            folderDto.setParentPath(parent.getFullPath());
                            folderDto.setParentPathName(parent.getFullName());
                        }
                        folderDto.setName(documentEditDto.getName());
                        int i = folderService.modFolderNameAndParent(folderDto);
                        return i > 0? RequestState.SUCCESS : RequestState. DB_INSERT_ERROR;
                    }
                }
        );
    }

    @ApiOperation(value="删除多个文件夹", notes="删除多个文件夹")
    @DeleteMapping("folders")
    @ActionLog(execClass = DeleteFoldersLog.class)
    public ResponseEntity<Result> deleteFolders(@RequestBody List<FolderDto> folderDtos){
        return requestHandler.doPost(
                new PostCallback() {
                    @Override
                    public RequestState doCheck() {
                        return RequestState.SUCCESS;
                    }
                    @Override
                    public RequestState invoke() throws Exception {
                        List<Long> ids = ReflectUtils.reflectDataList(folderDtos,"id",Long.class);
                        int i = folderService.modFoldersStatusToDelete(ids,(UserDto) authenticationGetter.getAnyUser().getUser());
                        return i > 0? RequestState.SUCCESS : RequestState.DB_DELETE_ERROR;
                    }
                }
        );
    }

    @ApiOperation(value="删除文件夹", notes="删除文件夹")
    @DeleteMapping("folder")
    @ActionLog(execClass = DeleteFolderLog.class)
    public ResponseEntity<Result> deleteFolder(@RequestBody FolderDto folderDto){
        return requestHandler.doPost(
                new PostCallback() {
                    @Override
                    public RequestState doCheck() {
                        return RequestState.SUCCESS;
                    }
                    @Override
                    public RequestState invoke() throws Exception {
                        int i = folderService.modFolderStatusToDelete(folderDto.getId(),(UserDto) authenticationGetter.getAnyUser().getUser());
                        return i > 0? RequestState.SUCCESS : RequestState.DB_DELETE_ERROR;
                    }
                }
        );
    }

    @ApiOperation(value="文件夹排序上升", notes="文件夹排序上升")
    @PutMapping("folder/up")
    public ResponseEntity<Result> upFolder(@RequestBody FolderDto folderDto){
        return requestHandler.doPost(
                new PostCallback() {
                    @Override
                    public RequestState doCheck() {
                        return RequestState.SUCCESS;
                    }
                    @Override
                    public RequestState invoke() throws Exception {
                        int i = folderService.sortFolder(folderDto.getId(),(UserDto) authenticationGetter.getAnyUser().getUser(),SortType.UP);
                        return i > 0? RequestState.SUCCESS : RequestState.DB_DELETE_ERROR;
                    }
                }
        );
    }

    @ApiOperation(value="文件夹排序下降", notes="文件夹排序下降")
    @PutMapping("folder/down")
    public ResponseEntity<Result> downFolder(@RequestBody FolderDto folderDto){
        return requestHandler.doPost(
                new PostCallback() {
                    @Override
                    public RequestState doCheck() {
                        return RequestState.SUCCESS;
                    }
                    @Override
                    public RequestState invoke() throws Exception {
                        int i = folderService.sortFolder(folderDto.getId(),(UserDto) authenticationGetter.getAnyUser().getUser(),SortType.DOWN);
                        return i > 0? RequestState.SUCCESS : RequestState.DB_DELETE_ERROR;
                    }
                }
        );
    }

    @ApiOperation(value="文件夹隐藏", notes="文件夹隐藏")
    @PutMapping("folder/disabled")
    public ResponseEntity<Result> disabledFolder(@RequestBody FolderDto folderDto){
        return requestHandler.doPost(
                new PostCallback() {
                    @Override
                    public RequestState doCheck() {
                        return RequestState.SUCCESS;
                    }
                    @Override
                    public RequestState invoke() throws Exception {
                        int i = folderService.disabledFolder(authenticationGetter.getAnyUser().getUser().getId(),folderDto.getId());
                        return i > 0? RequestState.SUCCESS : RequestState.DB_DELETE_ERROR;
                    }
                }
        );
    }

    @ApiOperation(value="文件夹再表示", notes="文件夹再表示")
    @PutMapping("folder/display")
    public ResponseEntity<Result> displayFolder(@RequestBody FolderDto folderDto){
        return requestHandler.doPost(
                new PostCallback() {
                    @Override
                    public RequestState doCheck() {
                        return RequestState.SUCCESS;
                    }
                    @Override
                    public RequestState invoke() throws Exception {
                        int i = folderService.displayFolder(authenticationGetter.getAnyUser().getUser().getId(),folderDto.getId());
                        return i > 0? RequestState.SUCCESS : RequestState.DB_DELETE_ERROR;
                    }
                }
        );
    }
}
