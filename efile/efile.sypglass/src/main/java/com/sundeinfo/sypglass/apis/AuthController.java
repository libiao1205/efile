package com.sundeinfo.sypglass.apis;


import com.sundeinfo.core.token.Token;
import com.sundeinfo.core.log.ActionLog;
import com.sundeinfo.core.mvc.controller.AbstractController;
import com.sundeinfo.core.request.GetCallback;
import com.sundeinfo.core.request.RequestHandler;
import com.sundeinfo.core.request.RequestState;
import com.sundeinfo.core.request.ResponseCallback;
import com.sundeinfo.core.request.result.Result;
import com.sundeinfo.security.model.AnyUser;
import com.sundeinfo.security.strategy.token.AuthenticationResponse;
import com.sundeinfo.security.utility.AuthorityCreater;
import com.sundeinfo.security.utility.TokenResource;
import com.sundeinfo.sypglass.configuration.ConfigService;
import com.sundeinfo.sypglass.define.SypGlassConfig;
import com.sundeinfo.sypglass.dto.UserDto;
import com.sundeinfo.sypglass.log.LoginLogInfo;
import com.sundeinfo.sypglass.model.Config;
import com.sundeinfo.sypglass.model.User;
import com.sundeinfo.sypglass.service.AuthService;
import com.sundeinfo.sypglass.service.UserService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Api(description = "认证系统API")
@RestController
@RequestMapping("auth")
public class AuthController extends AbstractController<AuthController> {

    @Autowired
    private TokenResource tokenResource;

    @Autowired
    private RequestHandler requestHandler;

    @Autowired
    private AuthService authService;

    @Autowired
    private ConfigService configService;

    @Autowired
    private UserService userService;

    @PostMapping(value = "login")
    @ActionLog(execClass = LoginLogInfo.class)
    public ResponseEntity<Result<AuthenticationResponse>> createToken(
            HttpServletRequest request,String username,String password) throws AuthenticationException {

        return requestHandler.doPost(
                new ResponseCallback<AuthenticationResponse>() {
                    String token = "";
                    String mistakeCount = "";
                    @Override
                    public AuthenticationResponse response() throws Exception {
                        return createJwtAuthenticationResponse(token,mistakeCount);
                    }
                    @Override
                    public RequestState doCheck() {
                        if (!StringUtils.hasText(username) || !StringUtils.hasText(password)){
                            return RequestState.PARAM_USELESS;
                        }
                        return RequestState.SUCCESS;
                    }
                    @Override
                    public RequestState invoke() throws Exception {
                        try{
                            if(!getLoginMistakeCount(username)){
                                return RequestState.ACCOUNT_LOCK;
                            }
                            token = authService.login(username,password);
                            if(StringUtils.hasText(token)){
                                return RequestState.SUCCESS;
                            }
                            else{
                                return RequestState.PARAM_USELESS_USERNAME_PASSWORD;
                            }
                        }catch (Exception be){
                            mistakeCount = takeLoginMistakeCount(username);
                            logger.error("登录失败:", be);
                            return RequestState.SUCCESS;
                        }
                    }
                }
        );
    }

    @Token
    @GetMapping(value = "refresh")
    public ResponseEntity<Result<AuthenticationResponse>> refreshAuthenticationToken(HttpServletRequest request) throws AuthenticationException{

        return requestHandler.doGet(
                new GetCallback<AuthenticationResponse>() {
                    @Override
                    public RequestState doCheck() {
                        return RequestState.SUCCESS;
                    }

                    @Override
                    public AuthenticationResponse invoke() throws Exception {
                        String oldToken = request.getHeader(tokenResource.getRequestheader());
                        String newToken = authService.refresh(oldToken);

                        return createJwtAuthenticationResponse(newToken,"");
                    }
                }
        );
    }

    private AuthenticationResponse createJwtAuthenticationResponse(String token,String mistakeCount){
        AuthenticationResponse response = new AuthenticationResponse();
        if(StringUtils.hasText(mistakeCount)){
            response.setMistakeMsg(mistakeCount);
            return response;
        }
        Assert.hasText(token,"Token为空!");
        AnyUser anyUser = (AnyUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        response.setName(anyUser.getUser().getName());
        response.setCaptcha("未实装");
        response.setOperation(AuthorityCreater.getAdminAuthority(anyUser.getAdmin()));
        response.setLevel(AuthorityCreater.getAdminLevel(anyUser.getAdmin()));
        response.setToken(token);
        this.passwordExceedRemind(anyUser,response);
        User user = new User();
        user.setId(anyUser.getUser().getId());
        user.setPsdmistakecount(0L);
        userService.updateByPrimaryKeySelective(user);
        return response;
    }

    private void passwordExceedRemind(AnyUser anyUser,AuthenticationResponse response){
        Config config = configService.findOneByNameAndCode(SypGlassConfig.PASSWORD_EXCEED,SypGlassConfig.PASSWORD_USABLE_DAY);
        if(config == null || 0 == config.getStatus()){
            return;
        }
        if(anyUser.getUser().getLastPasswordResetDate() == null){
            userService.updateLastPasswordResetDate(anyUser);
        }else{
            if(anyUser.getUser().getRemind() == null || anyUser.getUser().getRemind()){
                List<Config> configList = configService.findByName(SypGlassConfig.PASSWORD_EXCEED);
                String usableDay = "0";
                String aheadDay = "0";
                for(Config dto : configList){
                    if(SypGlassConfig.PASSWORD_USABLE_DAY.equals(dto.getCode())){
                        usableDay = dto.getValue();
                    }
                    if(SypGlassConfig.PASSWORD_AHEAD_DAY.equals(dto.getCode())){
                        aheadDay = dto.getValue();
                    }
                }
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(anyUser.getUser().getLastPasswordResetDate());
                calendar.add(Calendar.DATE, Integer.valueOf(usableDay) - Integer.valueOf(aheadDay));
                if(calendar.getTime().before(new Date())){
                    response.setExceed(true);
                }else{
                    response.setExceed(false);
                }
            }
        }
    }

    private String takeLoginMistakeCount(String username) throws InstantiationException, IllegalAccessException {

        Config config = configService.findOneByNameAndCode(SypGlassConfig.PSD_LOCK,SypGlassConfig.PSD_MISTAKE_COUNT);
        if(config == null || 0 == config.getStatus()){
            return "用户名或者密码不正确";
        }
        UserDto userDto = userService.findByUsername(username);
        if(userDto == null){
            return "用户名或者密码不正确";
        }
        long PsdMistakeCount = userDto.getPsdMistakeCount() == null ? 0 : userDto.getPsdMistakeCount();
        User user = new User();
        user.setId(userDto.getId());
        user.setPsdmistakecount(PsdMistakeCount + 1);
        userService.updateByPrimaryKeySelective(user);
        Long limit = Long.valueOf(config.getValue()) - user.getPsdmistakecount();
        if(limit > 0){
            return "用户名或者密码不正确,还有"+ limit +"次机会";
        }else{
            return "账号已被锁定，请联系管理员重置密码";
        }
    }
    private Boolean getLoginMistakeCount(String username)throws InstantiationException, IllegalAccessException{
        Config config = configService.findOneByNameAndCode(SypGlassConfig.PSD_LOCK,SypGlassConfig.PSD_MISTAKE_COUNT);
        if(config == null || 0 == config.getStatus()){
            return true;
        }
        UserDto userDto = userService.findByUsername(username);
        if(userDto == null){
            return true;
        }
        long PsdMistakeCount = userDto.getPsdMistakeCount() == null ? 0 : userDto.getPsdMistakeCount();
        if(Long.valueOf(config.getValue()) == PsdMistakeCount){
            return false;
        }
        return true;
    }

}