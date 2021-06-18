package com.sundeinfo.sypglass.apis.message;

import com.sundeinfo.core.mvc.controller.AbstractController;
import com.sundeinfo.core.request.GetCallback;
import com.sundeinfo.core.request.RequestState;
import com.sundeinfo.core.request.ResponseCallback;
import com.sundeinfo.core.request.result.Result;
import com.sundeinfo.security.model.AnyUser;
import com.sundeinfo.security.utility.AuthenticationGetter;
import com.sundeinfo.sypglass.dto.MessageSettingDto;
import com.sundeinfo.sypglass.service.MessageSettingService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Api(description = "用户通知系统API")
@RestController
@RequestMapping("notice")
public class MessageSettingController  extends AbstractController<MessageSettingController> {

    @Autowired
    private MessageSettingService settingService;
    @Autowired
    private AuthenticationGetter authenticationGetter;

    @ApiOperation(value="查找用户通知设定", notes="查找用户通知设定")
    @GetMapping(value = "user")
    public ResponseEntity<Result<MessageSettingDto>> getSetting(){
        return requestHandler.doGet(
                new GetCallback<MessageSettingDto>() {
                    @Override
                    public RequestState doCheck() {
                        return RequestState.SUCCESS;
                    }
                    @Override
                    public MessageSettingDto invoke() throws Exception {
                        AnyUser anyUser = authenticationGetter.getAnyUser();
                        MessageSettingDto settingDto = settingService.findByUser(anyUser.getUser().getId());
                        return settingDto;
                    }
                }
        );
    }

    @ApiOperation(value="编辑用户通知设定", notes="编辑用户通知设定")
    @PostMapping(value = "editUserSetting")
    public ResponseEntity<Result<MessageSettingDto>> postUser(@RequestBody MessageSettingDto settingDto){
        return requestHandler.doPost(
                new ResponseCallback<MessageSettingDto>() {
                    @Override
                    public RequestState doCheck() throws Exception{
                        return RequestState.SUCCESS;
                    }
                    @Override
                    public RequestState invoke() throws Exception {
                        AnyUser anyUser = authenticationGetter.getAnyUser();
                        settingDto.setUserid(anyUser.getUser().getId());
                        int iRet = 0;
                        if (settingDto.getId() == 0){
                            iRet = settingService.save(settingDto);
                            settingDto.setId(iRet);
                        }else{
                            iRet = settingService.editMessageSetting(settingDto);
                        }
                        if(iRet > 0) {
                            return RequestState.SUCCESS;
                        }else{
                            return RequestState. DB_INSERT_ERROR;
                        }
                    }
                    @Override
                    public MessageSettingDto response() throws Exception {
                        return settingDto;
                    }
                }
        );
    }
}
