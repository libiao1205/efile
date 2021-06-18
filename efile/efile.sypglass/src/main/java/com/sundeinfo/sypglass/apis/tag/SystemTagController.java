package com.sundeinfo.sypglass.apis.tag;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sundeinfo.core.mvc.controller.AbstractController;
import com.sundeinfo.core.request.GetCallback;
import com.sundeinfo.core.request.PostCallback;
import com.sundeinfo.core.request.RequestState;
import com.sundeinfo.core.request.result.Result;
import com.sundeinfo.sypglass.dto.TagDto;
import com.sundeinfo.sypglass.service.TagService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Api(description = "管理系统API")
@RestController
@RequestMapping("admin")
public class SystemTagController extends AbstractController<SystemTagController> {

    @Autowired
    private TagService tagService;


    @ApiOperation(value="查找系统自定义标签", notes="查找系统自定义标签")
    @GetMapping(value = "tags")
    public ResponseEntity<Result<PageInfo<TagDto>>> getTags(@PathVariable int pagenum, @PathVariable int pagesize){
        return requestHandler.doGet(
                new GetCallback<PageInfo<TagDto>>() {
                    @Override
                    public RequestState doCheck() {
                        return RequestState.SUCCESS;
                    }
                    @Override
                    public PageInfo<TagDto> invoke() throws Exception {
                        PageHelper.startPage(pagenum, pagesize);
                        List<TagDto> tagDtos = tagService.findSystemTags();
                        PageInfo<TagDto> pageInfo = new PageInfo<>(tagDtos);
                        return pageInfo;
                    }
                }
        );
    }

    @ApiOperation(value="新增系统标签", notes="新增系统标签")
    @PostMapping(value = "tag")
    public ResponseEntity<Result> postTag(@Valid @RequestBody TagDto tagDto){
        return requestHandler.doPost(
                new PostCallback() {
                    @Override
                    public RequestState doCheck() {
                        return RequestState.SUCCESS;
                    }
                    @Override
                    public RequestState invoke() throws Exception {
                        int i = tagService.save(tagDto.getName());
                        return i > 0? RequestState.SUCCESS : RequestState. DB_UPDATE_ERROR;
                    }
                }
        );
    }

    @ApiOperation(value="修正系统标签", notes="修正系统标签")
    @PutMapping(value = "tag")
    public ResponseEntity<Result> putTag(@Valid @RequestBody TagDto tagDto){
        return requestHandler.doPost(
                new PostCallback() {
                    @Override
                    public RequestState doCheck() {
                        return RequestState.SUCCESS;
                    }
                    @Override
                    public RequestState invoke() throws Exception {
                        int i = tagService.modName(tagDto.getId(),tagDto.getName());
                        return i > 0? RequestState.SUCCESS : RequestState. DB_UPDATE_ERROR;
                    }
                }
        );
    }

    @ApiOperation(value="删除系统标签", notes="删除系统标签")
    @DeleteMapping(value = "tag")
    public ResponseEntity<Result> deleteTag(@Valid @RequestBody TagDto tagDto){
        return requestHandler.doPost(
                new PostCallback() {
                    @Override
                    public RequestState doCheck() {
                        return RequestState.SUCCESS;
                    }
                    @Override
                    public RequestState invoke() throws Exception {
                        int i = tagService.delete(tagDto.getId());
                        return i > 0? RequestState.SUCCESS : RequestState. DB_UPDATE_ERROR;
                    }
                }
        );
    }
}