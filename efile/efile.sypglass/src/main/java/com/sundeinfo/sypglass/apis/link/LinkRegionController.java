package com.sundeinfo.sypglass.apis.link;


import com.sundeinfo.core.token.Token;
import com.sundeinfo.core.mvc.controller.AbstractController;
import com.sundeinfo.core.request.GetCallback;
import com.sundeinfo.core.request.RequestState;
import com.sundeinfo.core.request.result.Result;
import com.sundeinfo.sypglass.dto.LinkDto;
import com.sundeinfo.sypglass.service.LinkService;
import com.sundeinfo.sypglass.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Api(description = "外链API")
@RestController
@RequestMapping("app/link")
public class LinkRegionController  extends AbstractController<LinkRegionController> {

    @Autowired
    private LinkService linkService;

    @Autowired
    private UserService userService;

    @ApiOperation(value="查找单一外链信息(需要密码凭证)", notes="查找单一外链信息(需要密码凭证)")
    @Token()
    @GetMapping(value = "{random}")
    public ResponseEntity<Result<LinkDto>> getLinkNeedPassword(@PathVariable String random, @RequestParam String password){
        return requestHandler.doGet(
                new GetCallback<LinkDto>() {
                    @Override
                    public RequestState doCheck()
                    {
                        if ("".equals(password)){
                            return RequestState.REQUEST_PASSWORD_NULL;
                        }
                        switch (linkService.checkValid(random,password)){
                            case UNKNOWN:
                                return RequestState.PARAM_LINK_NULL;
                            case EXPIRE:
                                return RequestState.PARAM_LINK_EXPIRE;
                            case DELETE:
                                return RequestState.PARAM_LINK_DELETE;
                            default:
                                return RequestState.SUCCESS;
                        }
                    }
                    @Override
                    public LinkDto invoke() throws Exception {
                        LinkDto linkDto = linkService.findByRandom(random);
                        if (linkDto == null){return null;}
                        //if (linkDto.getDeleted()) return null;
                        linkDto.setUser(userService.findById(linkDto.getUserId()));
                        return linkDto;
                    }
                }
        );
    }

}
