package com.sundeinfo.sypglass.apis.document;

import com.alibaba.fastjson.JSONArray;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sundeinfo.core.log.ActionLog;
import com.sundeinfo.core.mvc.controller.AbstractController;
import com.sundeinfo.core.request.GetCallback;
import com.sundeinfo.core.request.PostCallback;
import com.sundeinfo.core.request.RequestState;
import com.sundeinfo.core.request.result.Result;
import com.sundeinfo.core.utility.StringUtils;
import com.sundeinfo.security.utility.AuthenticationGetter;
import com.sundeinfo.sypglass.annotation.IpChecked;
import com.sundeinfo.sypglass.component.PermissionAdapter;
import com.sundeinfo.sypglass.define.FileDocType;
import com.sundeinfo.sypglass.define.SypGlass;
import com.sundeinfo.sypglass.dto.FolderDto;
import com.sundeinfo.sypglass.dto.UserDto;
import com.sundeinfo.sypglass.dto.UserFolderSortJsonDto;
import com.sundeinfo.sypglass.log.multi.DeleteDocumentsLog;
import com.sundeinfo.sypglass.model.Document;
import com.sundeinfo.sypglass.model.UserFolderSort;
import com.sundeinfo.sypglass.service.DocumentService;
import com.sundeinfo.sypglass.service.FileService;
import com.sundeinfo.sypglass.service.FolderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.method.P;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Api(description = "用户系统API")
@RestController
@RequestMapping("user")
public class UserDocumentController extends AbstractController<UserDocumentController> {

    @Autowired
    private DocumentService documentService;

    @Autowired
    private FolderService folderService;

    @Autowired
    private FileService fileService;

    @Autowired
    private AuthenticationGetter authenticationGetter;

    @Autowired
    private PermissionAdapter permissionAdapter;

    @ApiOperation(value="依据文件夹ID查找资源列表", notes="依据文件夹ID查找资源列表")
    @GetMapping("folder/{id}/docs/{pagenum}-{pagesize}")
    @IpChecked
    public ResponseEntity<Result<PageInfo<Document>>> getDocument(@PathVariable(value = "id") int id,
                                                                  @PathVariable(value = "pagenum") int pagenum,
                                                                  @PathVariable(value = "pagesize") int pagesize,
                                                                  @RequestParam(value = "column") String column,
                                                                  @RequestParam(value = "order") String order,
                                                                  @RequestParam(value = "rankField") String rankField,
                                                                  @RequestParam(value = "sort") String sort){
        return requestHandler.doGet(
                new GetCallback<PageInfo<Document>>() {
                    @Override
                    public RequestState doCheck() {
                        return RequestState.SUCCESS;
                    }
                    @Override
                    public PageInfo<Document> invoke() throws Exception {
                        PageHelper.startPage(pagenum, pagesize);
                        String sortOrder = "id ASC";
                        if (StringUtils.isNotBlank(column) && StringUtils.isNotBlank(order)){
                            String sortColumnName = column;
                            if (StringUtils.isEqualsString("name",column)){
                                sortColumnName = "CONVERT(NAME USING gbk)";
                            }
                            if (StringUtils.isEqualsString("ascending",order)){
                                sortOrder = sortColumnName + " ASC";
                            }else if (StringUtils.isEqualsString("descending",order)){
                                sortOrder = sortColumnName + " DESC";
                            }
                        }
                        if(StringUtils.isNotBlank(rankField) && StringUtils.isNotBlank(sort)){
                            sortOrder = "CONVERT("+rankField + " USING gbk) " + sort;
                        }
                        List<Document> documents = documentService.findDocumentByFolderIdAndUserid(id,authenticationGetter.getAnyUser().getUser().getId(),sortOrder);
                        UserFolderSort userFolderSort = folderService.findUserFolderSortByParentAndUser(id,authenticationGetter.getAnyUser().getUser().getId());
                        if (userFolderSort != null){
                            List<UserFolderSortJsonDto> oldSortDtos = JSONArray.parseArray(userFolderSort.getSortjson(), UserFolderSortJsonDto.class);
                            for (Document current : documents) {
                                Optional<UserFolderSortJsonDto> firstSortDto = oldSortDtos.stream().filter(sortfolder -> current.getId() == sortfolder.getId()).findFirst();
                                firstSortDto.ifPresent(userFolderSortJsonDto -> current.setSortNo(userFolderSortJsonDto.getSortNo()));
                                if (SypGlass.RESOURCE_TYPE_FILE.equals(current.getKind()) && current.getDocType() != null && current.getDocType() == FileDocType.LITERATURE.getStatus()){
                                    //如果是文献类型就追加附件信息和元数据信息
                                    current.setFileAccessoryDto(fileService.findFileAccessoryByFileId(current.getId()));
                                    current.setFileMetadataDto(fileService.findFileMetadataByFileId(current.getId()));
                                }
                            }
                            Collections.sort(documents);
                        }else {
                            for (Document current : documents) {
                                if (SypGlass.RESOURCE_TYPE_FILE.equals(current.getKind()) && current.getDocType() != null && current.getDocType() == FileDocType.LITERATURE.getStatus()){
                                    //如果是文献类型就追加附件信息和元数据信息
                                    current.setFileAccessoryDto(fileService.findFileAccessoryByFileId(current.getId()));
                                    current.setFileMetadataDto(fileService.findFileMetadataByFileId(current.getId()));
                                }
                            }
                        }
                        List<FolderDto> parentFolderDtos = folderService.findParentListByFolderId(id);
                        if (parentFolderDtos.get(0).getId() == SypGlass.ROOT_FOLDER_ID){
                            Collections.reverse(parentFolderDtos);
                        }
                        permissionAdapter.setDocumentPermissionMap(documents,parentFolderDtos);
                        return new PageInfo<>(documents);
                    }
                }
        );
    }

    @ApiOperation(value="删除多个资源", notes="删除多个资源")
    @DeleteMapping("docs")
    @ActionLog(execClass = DeleteDocumentsLog.class)
    public ResponseEntity<Result> deleteDocuments(@RequestBody List<Document> documents){
        return requestHandler.doPost(
                new PostCallback() {
                    @Override
                    public RequestState doCheck() {
                        return RequestState.SUCCESS;
                    }
                    @Override
                    public RequestState invoke() throws Exception {
                        List<Long> folderIds = new ArrayList<>();
                        List<Long> fileIds = new ArrayList<>();
                        for (Document document:documents) {
                            if (SypGlass.RESOURCE_TYPE_FOLDER.equals(document.getKind())){
                                folderIds.add(document.getId());
                            }else{
                                fileIds.add(document.getId());
                            }
                        }
                        if (folderIds.size() > 0) {
                            int i = folderService.modFoldersStatusToDelete(folderIds,(UserDto) authenticationGetter.getAnyUser().getUser());
                            if (i == 0) return RequestState.DB_DELETE_ERROR;
                        }
                        if (fileIds.size() > 0) {
                            int i = fileService.modFilesStatusToDelete(fileIds,(UserDto) authenticationGetter.getAnyUser().getUser());
                            return i > 0? RequestState.SUCCESS : RequestState.DB_DELETE_ERROR;
                        }
                        return RequestState.SUCCESS;
                    }
                }
        );
    }

}
