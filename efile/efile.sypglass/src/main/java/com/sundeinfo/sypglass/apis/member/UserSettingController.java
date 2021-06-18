package com.sundeinfo.sypglass.apis.member;

import com.sundeinfo.core.mvc.controller.AbstractController;
import com.sundeinfo.core.request.GetCallback;
import com.sundeinfo.core.request.PostCallback;
import com.sundeinfo.core.request.RequestState;
import com.sundeinfo.core.request.ResponseCallback;
import com.sundeinfo.core.request.result.Result;
import com.sundeinfo.security.model.AnyUser;
import com.sundeinfo.security.utility.AuthenticationGetter;
import com.sundeinfo.sypglass.dto.MessageSettingDto;
import com.sundeinfo.sypglass.dto.UserDto;
import com.sundeinfo.sypglass.dto.UserSettingDetailDto;
import com.sundeinfo.sypglass.dto.UserSettingDto;
import com.sundeinfo.sypglass.service.MessageSettingService;
import com.sundeinfo.sypglass.service.UserSettingService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Api(description = "用户设定系统API")
@RestController
@RequestMapping("user")
public class UserSettingController extends AbstractController<UserSettingController> {

    @Autowired
    private UserSettingService settingService;
    @Autowired
    private AuthenticationGetter authenticationGetter;

    @ApiOperation(value="查找用户设定", notes="查找用户通知设定")
    @GetMapping(value = "setting")
    public ResponseEntity<Result<List<UserSettingDto>>> getSetting(){
        return requestHandler.doGet(
                new GetCallback<List<UserSettingDto>>() {
                    @Override
                    public RequestState doCheck() {
                        return RequestState.SUCCESS;
                    }
                    @Override
                    public List<UserSettingDto> invoke() throws Exception {
                        AnyUser anyUser = authenticationGetter.getAnyUser();
                        return settingService.findUserSettingByUser(anyUser.getUser().getId());
                    }
                }
        );
    }

    @ApiOperation(value="查找用户特定类型的设定", notes="查找用户特定类型的设定")
    @GetMapping(value = "setting/{kind}")
    public ResponseEntity<Result<List<UserSettingDto>>> getSetting(@PathVariable(value = "kind") String kind){
        return requestHandler.doGet(
                new GetCallback<List<UserSettingDto>>() {
                    @Override
                    public RequestState doCheck() {
                        return RequestState.SUCCESS;
                    }
                    @Override
                    public List<UserSettingDto> invoke() throws Exception {
                        AnyUser anyUser = authenticationGetter.getAnyUser();
                        return settingService.findUserSettingByUserAndKind(anyUser.getUser().getId(),kind);
                    }
                }
        );
    }

    @ApiOperation(value="查找用户特定设定的详细", notes="查找用户特定设定的详细")
    @GetMapping(value = "setting/{id}/detail")
    public ResponseEntity<Result<List<UserDto>>> getSettingDetail(@PathVariable(value = "id") long id){
        return requestHandler.doGet(
                new GetCallback<List<UserDto>>() {
                    @Override
                    public RequestState doCheck() {
                        return RequestState.SUCCESS;
                    }
                    @Override
                    public List<UserDto> invoke() throws Exception {
                        //AnyUser anyUser = authenticationGetter.getAnyUser();
                        List<UserSettingDetailDto> settingDetailDtos = settingService.findDetailBySettingId(id);
                        List<UserDto> userDtos = new ArrayList<>();
                        if (settingDetailDtos == null){
                            return userDtos;
                        }
                        UserDto userDto;
                        for (UserSettingDetailDto userSettingDetailDto:settingDetailDtos){
                            userDto = new UserDto();
                            userDto.setId(Long.parseLong(userSettingDetailDto.getCode()));
                            userDto.setName(userSettingDetailDto.getValue());
                            userDtos.add(userDto);
                        }
                        return userDtos;
                    }
                }
        );
    }

    @ApiOperation(value="新增用户组", notes="新增用户组")
    @PostMapping(value = "setting")
    public ResponseEntity<Result<UserSettingDto>> postUserSettingDetial(@RequestBody UserSettingDto userSettingDto){
        return requestHandler.doPost(
                new ResponseCallback<UserSettingDto>() {
                    @Override
                    public RequestState doCheck() throws Exception {
                        if (userSettingDto.getDetails().size() == 0){
                            return RequestState.PARAM_NULL;
                        }
                        return RequestState.SUCCESS;
                    }
                    @Override
                    public RequestState invoke() throws Exception {
                        AnyUser anyUser = authenticationGetter.getAnyUser();
                        userSettingDto.setUserId(anyUser.getUser().getId());
                        int result = settingService.save(userSettingDto);
                        if (result < 1){
                            return RequestState.DB_INSERT_ERROR;
                        }
                        return RequestState.SUCCESS;
                    }

                    @Override
                    public UserSettingDto response() throws Exception {
                        return userSettingDto;
                    }
                }
        );
    }

    @ApiOperation(value="编辑用户组", notes="编辑用户组")
    @PutMapping(value = "setting")
    public ResponseEntity<Result<UserSettingDto>> putUserSettingDetial(@RequestBody UserSettingDto userSettingDto){
        return requestHandler.doPost(
                new ResponseCallback<UserSettingDto>() {
                    @Override
                    public RequestState doCheck() throws Exception {
                        if (userSettingDto.getDetails().size() == 0){
                            return RequestState.PARAM_NULL;
                        }
                        return RequestState.SUCCESS;
                    }
                    @Override
                    public RequestState invoke() throws Exception {
                        AnyUser anyUser = authenticationGetter.getAnyUser();
                        userSettingDto.setUserId(anyUser.getUser().getId());
                        int result = settingService.mod(userSettingDto);
                        if (result < 1){
                            return RequestState.DB_INSERT_ERROR;
                        }
                        return RequestState.SUCCESS;
                    }

                    @Override
                    public UserSettingDto response() throws Exception {
                        return userSettingDto;
                    }
                }
        );
    }

    @ApiOperation(value="删除用户组", notes="删除用户组")
    @DeleteMapping(value = "setting")
    public ResponseEntity<Result> deleteUserSettingDetial(@RequestBody UserSettingDto userSettingDto){
        return requestHandler.doPost(
                new PostCallback() {
                    @Override
                    public RequestState doCheck() {
                        return RequestState.SUCCESS;
                    }
                    @Override
                    public RequestState invoke() throws Exception {
                        int i = settingService.delete(userSettingDto);
                        return i > 0? RequestState.SUCCESS : RequestState.DB_DELETE_ERROR;
                    }
                }
        );


    }
}
