package com.sundeinfo.sypglass.organization.apis;

import com.sundeinfo.core.mvc.controller.AbstractController;
import com.sundeinfo.core.request.PostCallback;
import com.sundeinfo.core.request.PostCheckResultCallback;
import com.sundeinfo.core.request.RequestState;
import com.sundeinfo.core.request.result.Result;
import com.sundeinfo.core.token.Token;
import com.sundeinfo.security.utility.AuthenticationGetter;
import com.sundeinfo.sypglass.organization.configuration.ConfigService;
import com.sundeinfo.sypglass.organization.dto.PasswordDto;
import com.sundeinfo.sypglass.organization.dto.UserDto;
import com.sundeinfo.sypglass.organization.service.UserService;
import com.sundeinfo.sypglass.organization.utility.MD5;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @author chihai
 * @ClassName LoginPsdController
 * @Description
 * @CreateTime 2018/12/5 14:22
 * @Version V1.0
 */
@Api(description = "用户系统API")
@RestController
@RequestMapping("relay")
public class LoginPsdController extends AbstractController<LoginPsdController>{

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationGetter authenticationGetter;

    @Autowired
    private ConfigService configService;

    @ApiOperation(value="修改登录密码", notes="用户密码更新")
    @PostMapping("password")
    @Token
    public ResponseEntity<Result> updateUserPsd( @Valid @RequestBody PasswordDto passwordDto){
        return requestHandler.doPost(
                new PostCheckResultCallback() {
                    String passwordCheckResult;
                    @Override
                    public RequestState doCheck() {
                        passwordCheckResult = configService.checkPsdComplexity(passwordDto.getCheckPass1());
                        if(StringUtils.isNotEmpty(passwordCheckResult)) {
                            logger.info(String.format("密码格式不正确:%s",passwordCheckResult));
                            return RequestState.PSD_FORMAT_MISTAKE;
                        }
                        return RequestState.SUCCESS;
                    }

                    @Override
                    public String getCheckMessage(RequestState state) {
                        if (state == RequestState.PSD_FORMAT_MISTAKE){
                            return passwordCheckResult;
                        }
                        return null;
                    }
                    @Override
                    public RequestState invoke() throws Exception {
                        UserDto userDto = userService.findById(passwordDto.getId());
                        if(!MD5.checkPassword(userDto.getPassword(), passwordDto.getPassword())) {
                            return RequestState.PARAM_USELESS_USERNAME_PASSWORD;
                        }
                        int i = userService.updateUserPsd(passwordDto);
                        return i > 0? RequestState.SUCCESS : RequestState. DB_UPDATE_ERROR;
                    }

                    @Override
                    public String getInvokeMessage(RequestState state) {
                        return null;
                    }
                }
        );

    }

    @ApiOperation(value="关闭提醒修改密码功能", notes="关闭提醒修改密码功能")
    @PostMapping("updatePasswordRemind")
    @Token
    public ResponseEntity<Result> updateUserPsdExceed(){
        return requestHandler.doPost(
            new PostCallback() {
                @Override
                public RequestState doCheck() throws Exception {
                    return RequestState.SUCCESS;
                }
                @Override
                public RequestState invoke() throws Exception {
                    Long id = authenticationGetter.getAnyUser().getUser().getId();
                    int i = userService.updatePasswordExceedNotRemind(id);
                    return i > 0? RequestState.SUCCESS : RequestState. DB_UPDATE_ERROR;
                }
            }
        );
    }
}
