package com.sundeinfo.sypglass.apis.link;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sundeinfo.core.mvc.controller.AbstractController;
import com.sundeinfo.core.request.GetCallback;
import com.sundeinfo.core.request.PostCallback;
import com.sundeinfo.core.request.RequestState;
import com.sundeinfo.core.request.ResponseCallback;
import com.sundeinfo.core.request.result.Result;
import com.sundeinfo.core.utility.ReflectUtils;
import com.sundeinfo.security.model.AnyUser;
import com.sundeinfo.sypglass.dto.UserDto;
import com.sundeinfo.sypglass.model.Document;
import com.sundeinfo.sypglass.service.*;
import com.sundeinfo.security.utility.AuthenticationGetter;
import com.sundeinfo.sypglass.dto.LinkDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Api(description = "用户系统API")
@RestController
@RequestMapping("user")
public class LinkController extends AbstractController<LinkController> {

    @Autowired
    private LinkService linkService;

    @Autowired
    private DocumentService documentService;

    @Autowired
    private AuthenticationGetter authenticationGetter;

    @ApiOperation(value="批量查找用户的外链信息", notes="批量查找用户的外链信息")
    @GetMapping(value = "links/{pagenum}-{pagesize}")
    public ResponseEntity<Result<PageInfo<LinkDto>>> getUserLinks(@PathVariable(value = "pagenum") int pagenum, @PathVariable(value = "pagesize") int pagesize){
        return requestHandler.doGet(
                new GetCallback<PageInfo<LinkDto>>() {
                    @Override
                    public RequestState doCheck() {
                        return RequestState.SUCCESS;
                    }
                    @Override
                    public PageInfo<LinkDto> invoke() throws Exception {
                        AnyUser anyUser = authenticationGetter.getAnyUser();
                        PageHelper.startPage(pagenum, pagesize);
                        List<LinkDto> linkDtos = linkService.findByUser(anyUser.getUser().getId());
                        PageInfo<LinkDto> pageInfo = new PageInfo<>(linkDtos);
                        return pageInfo;
                    }
                }
        );
    }

    @ApiOperation(value="批量查找用户的外链信息", notes="批量查找用户的外链信息")
    @GetMapping(value = "linksbysearchkey/{pagenum}-{pagesize}")
    public ResponseEntity<Result<PageInfo<LinkDto>>> getUserLinksBySearchKey(@PathVariable(value = "pagenum") int pagenum, @PathVariable(value = "pagesize") int pagesize,@RequestParam String key){
        return requestHandler.doGet(
                new GetCallback<PageInfo<LinkDto>>() {
                    @Override
                    public RequestState doCheck() {
                        return RequestState.SUCCESS;
                    }
                    @Override
                    public PageInfo<LinkDto> invoke() throws Exception {
                        AnyUser anyUser = authenticationGetter.getAnyUser();
                        PageHelper.startPage(pagenum, pagesize);
                        List<LinkDto> linkDtos = linkService.findByUserAndKey(anyUser.getUser().getId(),key);
                        PageInfo<LinkDto> pageInfo = new PageInfo<>(linkDtos);
                        return pageInfo;
                    }
                }
        );
    }

    @ApiOperation(value="查找单一外链信息(需要登录用户名)", notes="查找单一外链信息(需要登录用户名)")
    @GetMapping(value = "link/{random}")
    public ResponseEntity<Result<LinkDto>> getLink(@PathVariable(value = "random") String random){
        return requestHandler.doGet(
                new GetCallback<LinkDto>() {
                    @Override
                    public RequestState doCheck() {
                        return RequestState.SUCCESS;
                    }
                    @Override
                    public LinkDto invoke() throws Exception {
                        AnyUser anyUser = authenticationGetter.getAnyUser();
                        LinkDto linkDto = linkService.findByRandom(random);
                        if (linkDto == null) return null;
                        if (linkDto.getUserId() != anyUser.getUser().getId()) return null;
                        linkDto.setUser((UserDto) anyUser.getUser());
                        return linkDto;
                    }
                }
        );
    }

    @ApiOperation(value="依据文件夹ID查找外链的下级资源列表", notes="依据文件夹ID查找外链的下级资源列表")
    @GetMapping(value= "link/folder/{id}/docs")
    public ResponseEntity<Result<List<Document>>> getDocument(@PathVariable(value = "id") int id){
        return requestHandler.doGet(
                new GetCallback<List<Document>>() {
                    @Override
                    public RequestState doCheck() {
                        return RequestState.SUCCESS;
                    }
                    @Override
                    public List<Document> invoke() throws Exception {
                        return documentService.findDocumentByFolderId(id);
                    }
                }
        );
    }

    @ApiOperation(value="新增外链", notes="新增外链")
    @PostMapping(value = "link")
    //TODO:@Permissionable(function = FunctionType.NEW)
    public ResponseEntity<Result<LinkDto>> postLink(@Valid @RequestBody LinkDto linkDto){
        return requestHandler.doPost(
                new ResponseCallback<LinkDto>() {
                    @Override
                    public RequestState doCheck() throws Exception{
                        if (linkDto.getResources().size() < 1){
                            logger.error("没有外链文件！");
                            return RequestState.PARAM_USELESS;
                        }
                        if (linkService.hasSameRandom(linkDto.getRandomFolderName())){
                            logger.error("有相同的Random！");
                            return RequestState.PARAM_USELESS_RANDOM;
                        }
                        return RequestState.SUCCESS;
                    }
                    @Override
                    public RequestState invoke() throws Exception {
                        AnyUser anyUser = authenticationGetter.getAnyUser();
                        LinkDto newData = linkService.createNew(linkDto,anyUser.getUser().getId());
                        int i = linkService.save(newData);
                        return i > 0? RequestState.SUCCESS : RequestState.DB_INSERT_ERROR;
                    }
                    @Override
                    public LinkDto response() throws Exception {
                        return linkDto;
                    }
                }
        );
    }

    @ApiOperation(value="编辑外链", notes="编辑外链")
    @PutMapping(value = "link")
    public ResponseEntity<Result> putLink(@Valid @RequestBody LinkDto linkDto){
        return requestHandler.doPost(
                new PostCallback() {
                    @Override
                    public RequestState doCheck() throws Exception{
                        if (linkService.hasSameRandom(linkDto.getRandomFolderName())){
                            logger.error("有相同的Random！");
                            return RequestState.PARAM_USELESS_RANDOM;
                        }
                        return RequestState.SUCCESS;
                    }
                    @Override
                    public RequestState invoke() throws Exception {
                        AnyUser anyUser = authenticationGetter.getAnyUser();
                        LinkDto newData = linkService.createNew(linkDto,anyUser.getUser().getId());
                        int i = linkService.mod(newData);
                        return i > 0? RequestState.SUCCESS : RequestState.DB_UPDATE_ERROR;
                    }
                }
        );
    }

    @ApiOperation(value="删除外链", notes="删除外链")
    @DeleteMapping(value = "link")
    //TODO:@Permissionable(function = FunctionType.DELETE)
    public ResponseEntity<Result> deleteLink(@RequestBody LinkDto linkDto){
        return requestHandler.doPost(
                new PostCallback() {
                    @Override
                    public RequestState doCheck() {
                        return RequestState.SUCCESS;
                    }
                    @Override
                    public RequestState invoke() throws Exception {
                        int i = linkService.modToDelete(linkDto.getId(),authenticationGetter.getAnyUser().getUser().getId());
                        return i > 0? RequestState.SUCCESS : RequestState.DB_DELETE_ERROR;
                    }
                }
        );
    }

    @ApiOperation(value="删除外链", notes="删除外链")
    @DeleteMapping(value = "links")
    public ResponseEntity<Result> deleteLinks(@RequestBody List<LinkDto> linkDtos){
        return requestHandler.doPost(
                new PostCallback() {
                    @Override
                    public RequestState doCheck() {
                        return RequestState.SUCCESS;
                    }
                    @Override
                    public RequestState invoke() throws Exception {
                        List<Long> ids = ReflectUtils.reflectDataList(linkDtos,"id",Long.class);
                        int i = linkService.modToDelete(ids,authenticationGetter.getAnyUser().getUser().getId());
                        return i > 0? RequestState.SUCCESS : RequestState.DB_DELETE_ERROR;
                    }
                }
        );
    }
}
