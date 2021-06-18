package com.sundeinfo.sypglass.apis.trash;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.huaban.analysis.jieba.JiebaSegmenter;
import com.huaban.analysis.jieba.SegToken;
import com.sundeinfo.core.mvc.controller.AbstractController;
import com.sundeinfo.core.request.GetCallback;
import com.sundeinfo.core.request.PostCallback;
import com.sundeinfo.core.request.RequestState;
import com.sundeinfo.core.request.result.Result;
import com.sundeinfo.core.utility.ReflectUtils;
import com.sundeinfo.security.model.AnyUser;
import com.sundeinfo.security.utility.AuthenticationGetter;
import com.sundeinfo.sypglass.model.Document;
import com.sundeinfo.sypglass.service.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;


@Api(description = "管理系统API")
@RestController
@RequestMapping("admin")
public class TrashManageController extends AbstractController<TrashManageController> {

    @Autowired
    private DocumentService documentService;

    @Autowired
    private FolderService folderService;

    @Autowired
    private FileService fileService;

    @Autowired
    private BlockedService blockedService;

    @Autowired
    private SearchService searchService;

    @Autowired
    private AuthenticationGetter authenticationGetter;

    @ApiOperation(value="查找彻底删除的文件列表", notes="查找彻底删除的文件列表")
    @GetMapping(value = "file/deleted/{pagenum}-{pagesize}")
    public ResponseEntity<Result<PageInfo<Document>>> getDocument(@PathVariable(value = "pagenum") int pagenum, @PathVariable(value = "pagesize") int pagesize){
        return requestHandler.doGet(
                new GetCallback<PageInfo<Document>>() {
                    @Override
                    public RequestState doCheck() {
                        return RequestState.SUCCESS;
                    }
                    @Override
                    public PageInfo<Document> invoke() throws Exception {
                        PageHelper.startPage(pagenum, pagesize);
                        List<Document> documents = documentService.findDeletedDocument();
                        PageInfo<Document> pageInfo = new PageInfo<>(documents);
                        return pageInfo;
                    }
                }
        );
    }

    @ApiOperation(value="依据关键字查找彻底删除的文件列表", notes="依据关键字查找彻底删除的文件列表")
    @GetMapping(value = "file/deleted/search/{pagenum}-{pagesize}")
    public ResponseEntity<Result<PageInfo<Document>>> searchDocument(@PathVariable(value = "pagenum") int pagenum, @PathVariable(value = "pagesize") int pagesize,@RequestParam String name){
        return requestHandler.doGet(
                new GetCallback<PageInfo<Document>>() {
                    @Override
                    public RequestState doCheck() {
                        return RequestState.SUCCESS;
                    }
                    @Override
                    public PageInfo<Document> invoke() throws Exception {
                        JiebaSegmenter segmenter = new JiebaSegmenter();
                        List<SegToken> segTokens = segmenter.process(name, JiebaSegmenter.SegMode.INDEX);
                        logger.info("分词后检索关键词：" + segTokens.toString());

                        PageHelper.startPage(pagenum, pagesize);
                        List<Document> documents = searchService.searchDeletedWithJieba(name,segTokens);
                        PageInfo<Document> pageInfo = new PageInfo<>(documents);
                        return pageInfo;
                    }
                }
        );
    }

    @ApiOperation(value="恢复删除的文件", notes="恢复彻底删除的文件")
    @PutMapping("file/deleted")
    public ResponseEntity<Result> rollbackDocument(@Valid @RequestBody Document document) {
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
                            int i = folderService.rollback(document.getId());
                            return i > 0? RequestState.SUCCESS : RequestState. DB_INSERT_ERROR;
                        }else if("file".equals(document.getKind())){
                            int i = fileService.rollback(document.getId());
                            return i > 0? RequestState.SUCCESS : RequestState. DB_INSERT_ERROR;
                        }else{
                            return RequestState.ERROR404;
                        }
                    }
                }
        );
    }

    @ApiOperation(value="批量恢复删除的文件", notes="恢复彻底删除的文件")
    @PutMapping("file/deleteds")
    public ResponseEntity<Result> rollbackDocuments(@Valid @RequestBody List<Document> documents) {
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
                            int result = fileService.rollback(fileIds);
                            if (result < 1){
                                return RequestState.DB_UPDATE_ERROR;
                            }
                        }
                        if (folders.size() > 0){
                            List<Long> folderIds = ReflectUtils.reflectDataList(folders,"id",Long.class);
                            int result = folderService.rollback(folderIds);
                            if (result < 1){
                                return RequestState.DB_UPDATE_ERROR;
                            }
                        }
                        return RequestState.SUCCESS;

                    }
                }
        );
    }

    @ApiOperation(value="彻底删除文件", notes="彻底删除文件")
    @DeleteMapping("file/deleted")
    public ResponseEntity<Result> deleteDocument(@Valid @RequestBody Document document) {
        return requestHandler.doPost(
                new PostCallback() {
                    @Override
                    public RequestState doCheck() {
                        return RequestState.SUCCESS;
                    }
                    @Override
                    public RequestState invoke() throws Exception {
                        if ("folder".equals(document.getKind())){
                            int i = blockedService.blockFolder(document.getId());
                            return i > 0? RequestState.SUCCESS : RequestState. DB_INSERT_ERROR;
                        }else if("file".equals(document.getKind())){
                            int i = blockedService.blockFile(document.getId());
                            return i > 0? RequestState.SUCCESS : RequestState. DB_INSERT_ERROR;
                        }else{
                            return RequestState.ERROR404;
                        }

                    }
                }
        );
    }

    @ApiOperation(value="批量彻底删除文件", notes="批量彻底删除文件")
    @DeleteMapping("file/deleteds")
    public ResponseEntity<Result> deleteDocuments(@Valid @RequestBody List<Document> documents) {
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
                            int result = blockedService.blockFiles(fileIds);
                            if (result < 1){
                                return RequestState.DB_UPDATE_ERROR;
                            }
                        }
                        if (folders.size() > 0){
                            for (Document document:documents){
                                int i = blockedService.blockFolder(document.getId());
                                if (i < 1){
                                    return RequestState.DB_UPDATE_ERROR;
                                }
                            }
                        }
                        return RequestState.SUCCESS;

                    }
                }
        );
    }
}
