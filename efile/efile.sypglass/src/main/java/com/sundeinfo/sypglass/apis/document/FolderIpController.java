package com.sundeinfo.sypglass.apis.document;

import com.sundeinfo.core.mvc.controller.AbstractController;
import com.sundeinfo.core.request.GetCallback;
import com.sundeinfo.core.request.PostCallback;
import com.sundeinfo.core.request.RequestState;
import com.sundeinfo.core.request.result.Result;
import com.sundeinfo.security.utility.AuthenticationGetter;
import com.sundeinfo.sypglass.dto.AdminDto;
import com.sundeinfo.sypglass.dto.AdminFolderDto;
import com.sundeinfo.sypglass.dto.FolderDto;
import com.sundeinfo.sypglass.dto.FolderIpDto;
import com.sundeinfo.sypglass.service.FolderIpService;
import com.sundeinfo.sypglass.service.FolderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Api(description = "管理系统API")
@RestController
@RequestMapping("admin")
public class FolderIpController extends AbstractController<FolderIpController> {

    @Autowired
    private FolderIpService folderIpService;

    @Autowired
    private AuthenticationGetter authenticationGetter;

    @ApiOperation(value="依据文件夹ID查找IP白名单", notes="依据文件夹ID查找IP白名单")
    @GetMapping(value = "folder/ips")
    public ResponseEntity<Result<List<FolderDto>>> getWhiteIpFolders(){
        return requestHandler.doGet(
                new GetCallback<List<FolderDto>>() {
                    @Override
                    public RequestState doCheck() {
                        return RequestState.SUCCESS;
                    }
                    @Override
                    public List<FolderDto> invoke() throws Exception {
                        AdminDto adminDto = (AdminDto)authenticationGetter.getAnyUser().getAdmin();
                        List<FolderDto> allWhiteIpFolders = folderIpService.findWhiteIpFolders();
                        if (adminDto.getLevel() == 1){
                            return allWhiteIpFolders;
                        }else {
                            List<FolderDto> whiteIpFolders = new ArrayList<>();
                            List<AdminFolderDto> adminFolderDtos = adminDto.getAdminFolders();
                            if (adminFolderDtos != null) {
                                for (AdminFolderDto adminFolder:adminFolderDtos) {
                                    for(FolderDto whiteIpFolder : allWhiteIpFolders){
                                        if (adminFolder.getFolderId() == whiteIpFolder.getId() || whiteIpFolder.isMyParent(adminFolder.getFolderId())){
                                            whiteIpFolders.add(whiteIpFolder);
                                        }
                                    }
                                }
                            }
                            return whiteIpFolders;
                        }
                    }
                }
        );
    }

    @ApiOperation(value="依据文件夹ID查找IP白名单", notes="依据文件夹ID查找IP白名单")
    @GetMapping(value = "folder/{id}/ip")
    public ResponseEntity<Result<FolderIpDto>> getFolderIp(@PathVariable(value = "id") int id){
        return requestHandler.doGet(
                new GetCallback<FolderIpDto>() {
                    @Override
                    public RequestState doCheck() {
                        return RequestState.SUCCESS;
                    }
                    @Override
                    public FolderIpDto invoke() throws Exception {
                        return folderIpService.findIpInfoByFolderId(id);
                    }
                }
        );
    }

    @ApiOperation(value="编辑文件夹IP白名单", notes="编辑文件夹IP白名单")
    @PutMapping(value = "folder/{id}/ip")
    public ResponseEntity<Result> putFolderIP(@PathVariable(value = "id") int id,@RequestBody FolderIpDto folderIpDto){
        return requestHandler.doPost(
                new PostCallback() {
                    @Override
                    public RequestState doCheck() {
                        if ("".equals(folderIpDto.getIpInfo().trim())) return RequestState.PARAM_USELESS;
                        return RequestState.SUCCESS;
                    }
                    @Override
                    public RequestState invoke() throws Exception {
                        folderIpDto.setFolderId(id);
                        int i = folderIpService.addIpInfo(folderIpDto);
                        return i > 0? RequestState.SUCCESS : RequestState.DB_UPDATE_ERROR;
                    }
                }
        );
    }

    @ApiOperation(value="删除文件夹IP白名单", notes="删除文件夹IP白名单")
    @DeleteMapping(value = "folder/{id}/ip")
    public ResponseEntity<Result> deleteFolderIP(@PathVariable(value = "id") int id){
        return requestHandler.doPost(
                new PostCallback() {
                    @Override
                    public RequestState doCheck() {
                        return RequestState.SUCCESS;
                    }
                    @Override
                    public RequestState invoke() throws Exception {
                        int i = folderIpService.deleteIpInfo(id);
                        return i > 0? RequestState.SUCCESS : RequestState.DB_UPDATE_ERROR;
                    }
                }
        );
    }
}