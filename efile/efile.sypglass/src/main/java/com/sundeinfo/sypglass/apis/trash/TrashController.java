package com.sundeinfo.sypglass.apis.trash;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sundeinfo.core.mvc.controller.AbstractController;
import com.sundeinfo.core.request.GetCallback;
import com.sundeinfo.core.request.PostCallback;
import com.sundeinfo.core.request.RequestState;
import com.sundeinfo.core.request.result.Result;
import com.sundeinfo.core.utility.ReflectUtils;
import com.sundeinfo.security.model.AnyUser;
import com.sundeinfo.security.utility.AuthenticationGetter;
import com.sundeinfo.sypglass.dto.UserDto;
import com.sundeinfo.sypglass.model.Document;
import com.sundeinfo.sypglass.service.DocumentService;
import com.sundeinfo.sypglass.service.FileService;
import com.sundeinfo.sypglass.service.FolderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Api(description = "用户系统API")
@RestController
@RequestMapping("user")
public class TrashController  extends AbstractController<TrashController> {

    @Autowired
    private DocumentService documentService;

    @Autowired
    private FolderService folderService;

    @Autowired
    private FileService fileService;

    @Autowired
    private AuthenticationGetter authenticationGetter;

    @ApiOperation(value="查找回收站数据列表", notes="查找回收站数据列表")
    @GetMapping(value = "trash/{pagenum}-{pagesize}")
    public ResponseEntity<Result<PageInfo<Document>>> getDocument(@PathVariable(value = "pagenum") int pagenum,
                                                                  @PathVariable(value = "pagesize") int pagesize){
        return requestHandler.doGet(
                new GetCallback<PageInfo<Document>>() {
                    @Override
                    public RequestState doCheck() {
                        return RequestState.SUCCESS;
                    }
                    @Override
                    public PageInfo<Document> invoke() throws Exception {
                        PageHelper.startPage(pagenum, pagesize);

                        if (authenticationGetter.getAnyUser().getAdmin() == null){
                            List<Document> documents = documentService.findTrashDocumentByUserId(authenticationGetter.getAnyUser().getUser().getId());
                            PageInfo<Document> pageInfo = new PageInfo<>(documents);
                            return pageInfo;
                        }else{
                            if (authenticationGetter.getAnyUser().getAdmin().getLevel() == 1){
                                List<Document> documents = documentService.findTrashDocument();
                                PageInfo<Document> pageInfo = new PageInfo<>(documents);
                                return pageInfo;
                            }else{
                                List<Document> documents = documentService.findTrashDocumentByFolder(authenticationGetter.getAnyUser().getAdmin());
                                PageInfo<Document> pageInfo = new PageInfo<>(documents);
                                return pageInfo;
                            }

                        }



                    }
                }
        );
    }

    @ApiOperation(value="查找回收站数据列表", notes="查找回收站数据列表")
    @GetMapping(value = "search/trash/{pagenum}-{pagesize}")
    public ResponseEntity<Result<PageInfo<Document>>> getDocumentBySearchKey(@PathVariable(value = "pagenum") int pagenum,
                                                                             @PathVariable(value = "pagesize") int pagesize,
                                                                             @RequestParam String value){
        return requestHandler.doGet(
                new GetCallback<PageInfo<Document>>() {
                    @Override
                    public RequestState doCheck() {
                        return RequestState.SUCCESS;
                    }
                    @Override
                    public PageInfo<Document> invoke() throws Exception {
                        PageHelper.startPage(pagenum, pagesize);

                        if (authenticationGetter.getAnyUser().getAdmin() == null){
                            List<Document> documents = documentService.findTrashDocumentByUserIdAndSearchKey(authenticationGetter.getAnyUser().getUser().getId(),value);
                            PageInfo<Document> pageInfo = new PageInfo<>(documents);
                            return pageInfo;
                        }else{
                            if (authenticationGetter.getAnyUser().getAdmin().getLevel() == 1){
                                List<Document> documents = documentService.findTrashDocumentBySearchKey(value);
                                PageInfo<Document> pageInfo = new PageInfo<>(documents);
                                return pageInfo;
                            }else{
                                List<Document> documents = documentService.findTrashDocumentByFolderAndSearchKey(authenticationGetter.getAnyUser().getAdmin(),value);
                                PageInfo<Document> pageInfo = new PageInfo<>(documents);
                                return pageInfo;
                            }
                        }
                    }
                }
        );
    }

    @ApiOperation(value="回收站文件恢复", notes="回收站文件恢复")
    @PutMapping("trash")
    public ResponseEntity<Result> putToNormal(@Valid @RequestBody Document document) {
        return requestHandler.doPost(
                new PostCallback() {
                    @Override
                    public RequestState doCheck() {
                        return RequestState.SUCCESS;
                    }
                    @Override
                    public RequestState invoke() throws Exception {
                        if ("folder".equals(document.getKind())){
                            int i = folderService.modFolderStatusToNormal(document.getId());
                            return i > 0? RequestState.SUCCESS : RequestState. DB_INSERT_ERROR;
                        }else if("file".equals(document.getKind())){
                            int i = fileService.modFileStatusToNormal(document.getId());
                            return i > 0? RequestState.SUCCESS : RequestState. DB_INSERT_ERROR;
                        }else{
                            return RequestState.ERROR404;
                        }
                    }
                }
        );
    }

    @ApiOperation(value="回收站文件批量恢复", notes="回收站文件批量恢复")
    @PutMapping("trashs")
    public ResponseEntity<Result> putToNormals(@Valid @RequestBody List<Document> documents) {
        return requestHandler.doPost(
                new PostCallback() {
                    @Override
                    public RequestState doCheck() {
                        return RequestState.SUCCESS;
                    }
                    @Override
                    public RequestState invoke() throws Exception {
                        List<Document> files = new ArrayList<>();
                        List<Document> folders = new ArrayList<>();
                        for (Document document:documents) {
                            if ("folder".equals(document.getKind())){
                                folders.add(document);
                            }else{
                                files.add(document);
                            }
                        }
                        if (files.size() > 0){
                            List<Long> fileIds = ReflectUtils.reflectDataList(files,"id",Long.class);
                            int result = fileService.modFilesStatusToNormal(fileIds);
                            if (result < 1){
                                return RequestState.DB_UPDATE_ERROR;
                            }
                        }
                        if (folders.size() > 0){
                            List<Long> folderIds = ReflectUtils.reflectDataList(folders,"id",Long.class);
                            int result = folderService.modFoldersStatusToNormal(folderIds);
                            if (result < 1){
                                return RequestState.DB_UPDATE_ERROR;
                            }
                        }
                        return RequestState.SUCCESS;
                    }
                }
        );
    }

    @ApiOperation(value="回收站文件彻底删除", notes="回收站文件彻底删除")
    @DeleteMapping("trash")
    public ResponseEntity<Result> deleteDocument(@Valid @RequestBody Document document) {
        return requestHandler.doPost(
                new PostCallback() {
                    @Override
                    public RequestState doCheck() {
                        return RequestState.SUCCESS;
                    }
                    @Override
                    public RequestState invoke() throws Exception {
                        AnyUser user = authenticationGetter.getAnyUser();
                        if ("folder".equals(document.getKind())){
                            int i = folderService.delete(document.getId(),(UserDto) user.getUser());
                            return i > 0? RequestState.SUCCESS : RequestState. DB_INSERT_ERROR;
                        }else if("file".equals(document.getKind())){
                            int i = fileService.delete(document.getId(),(UserDto) user.getUser());
                            return i > 0? RequestState.SUCCESS : RequestState. DB_INSERT_ERROR;
                        }else{
                            return RequestState.ERROR404;
                        }
                    }
                }
        );
    }

    @ApiOperation(value="回收站文件彻底删除", notes="回收站文件彻底删除")
    @DeleteMapping("trashs")
    public ResponseEntity<Result> deleteDocuments(@Valid @RequestBody List<Document> documents) {
        return requestHandler.doPost(
                new PostCallback() {
                    @Override
                    public RequestState doCheck() {
                        return RequestState.SUCCESS;
                    }
                    @Override
                    public RequestState invoke() throws Exception {
                        AnyUser user = authenticationGetter.getAnyUser();
                        List<Document> files = new ArrayList<>();
                        List<Document> folders = new ArrayList<>();
                        for (Document document:documents) {
                            if ("folder".equals(document.getKind())){
                                folders.add(document);
                            }else{
                                files.add(document);
                            }
                        }
                        if (files.size() > 0){
                            List<Long> fileIds = ReflectUtils.reflectDataList(files,"id",Long.class);
                            int result = fileService.delete(fileIds,(UserDto) user.getUser());
                            if (result < 1){
                                return RequestState.DB_UPDATE_ERROR;
                            }
                        }
                        if (folders.size() > 0){
                            List<Long> folderIds = ReflectUtils.reflectDataList(folders,"id",Long.class);
                            int result = folderService.delete(folderIds,(UserDto) user.getUser());
                            if (result < 1){
                                return RequestState.DB_UPDATE_ERROR;
                            }
                        }
                        return RequestState.SUCCESS;
                    }
                }
        );
    }
}
