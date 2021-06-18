package com.sundeinfo.sypglass.apis.tag;

import com.sundeinfo.core.mvc.controller.AbstractController;
import com.sundeinfo.core.request.GetCallback;
import com.sundeinfo.core.request.PostCallback;
import com.sundeinfo.core.request.RequestState;
import com.sundeinfo.core.request.ResponseCallback;
import com.sundeinfo.core.request.result.Result;
import com.sundeinfo.security.utility.AuthenticationGetter;
import com.sundeinfo.sypglass.define.TagType;
import com.sundeinfo.sypglass.dto.TagDto;
import com.sundeinfo.sypglass.service.TagService;
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
public class UserTagController extends AbstractController<UserTagController> {

    @Autowired
    private TagService tagService;

    @Autowired
    private AuthenticationGetter authenticationGetter;

    @ApiOperation(value="查找用户自定义标签", notes="查找用户自定义标签")
    @GetMapping(value = "tags")
    public ResponseEntity<Result<List<TagDto>>> getTags(){
        return requestHandler.doGet(
                new GetCallback<List<TagDto>>() {
                    @Override
                    public RequestState doCheck() {
                        return RequestState.SUCCESS;
                    }
                    @Override
                    public List<TagDto> invoke() throws Exception {
                        return tagService.findUserTags(authenticationGetter.getAnyUser().getUser().getId());
                    }
                }
        );
    }

    @ApiOperation(value="新增用户标签", notes="新增用户标签")
    @PostMapping(value = "tag")
    public ResponseEntity<Result<TagDto>> postTag(@Valid @RequestBody TagDto tagDto){
        return requestHandler.doPost(
                new ResponseCallback<TagDto>() {
                    @Override
                    public RequestState doCheck() {
                        return RequestState.SUCCESS;
                    }
                    @Override
                    public RequestState invoke() throws Exception {
                        tagDto.setType((byte)TagType.USER.code());
                        int i = tagService.save(tagDto,authenticationGetter.getAnyUser().getUser().getId());
                        return i > 0? RequestState.SUCCESS : RequestState. DB_UPDATE_ERROR;
                    }
                    @Override
                    public TagDto response() throws Exception {
                        return tagDto;
                    }
                }
        );
    }

    @ApiOperation(value="修正用户标签", notes="修正用户标签")
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

    @ApiOperation(value="删除用户标签", notes="删除用户标签")
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