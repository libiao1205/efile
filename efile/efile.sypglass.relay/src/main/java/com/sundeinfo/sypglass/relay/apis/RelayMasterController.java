package com.sundeinfo.sypglass.relay.apis;


import com.sundeinfo.core.mvc.controller.AbstractController;
import com.sundeinfo.core.request.GetCallback;
import com.sundeinfo.core.request.RequestState;
import com.sundeinfo.core.request.result.Result;
import com.sundeinfo.sypglass.relay.model.relay.DepartmentInfo;
import com.sundeinfo.sypglass.relay.model.relay.UserInfo;
import com.sundeinfo.sypglass.relay.service.OriginalDepartmentService;
import com.sundeinfo.sypglass.relay.service.OriginalUserService;
import com.sundeinfo.sypglass.relay.utility.DateUtils;
import com.sundeinfo.core.utility.StringUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Api(description = "批处理API")
@RestController
@RequestMapping("relay")
public class RelayMasterController extends AbstractController<RelayMasterController> {

    @Value("${Relay.Department}")
    private String relayDepartment;

    @Value("${Relay.User}")
    private String relayUser;

    @Autowired
    private OriginalDepartmentService originalDepartmentService;

    @Autowired
    private OriginalUserService originalUserService;

    @ApiOperation(value="查找最新部门列表", notes="查找最新部门列表")
    @GetMapping(value="/depts")
    public ResponseEntity<Result<List<DepartmentInfo>>> getOriginalDepartment(@RequestParam String date)
    {
        return requestHandler.doGet(
                new GetCallback<List<DepartmentInfo>>() {
                    @Override
                    public RequestState doCheck() {
                        if (!"true".equals(relayDepartment)){
                            return RequestState.RELAY_SETTING_DEPARTMENT_OFF;
                        }
                        return RequestState.SUCCESS;
                    }
                    @Override
                    public List<DepartmentInfo> invoke() throws Exception {
                        int result = originalDepartmentService.importDepartment();
                        if (result < 1){
                            return null;
                        }
                        if (StringUtils.isNotBlank(date)){
                            Date searchDate = DateUtils.StringToDate(date);
                            return originalDepartmentService.findByDate(searchDate);
                        }else{
                            return originalDepartmentService.findAll();
                        }

                    }
                }
        );
    }


    @ApiOperation(value="查找最新用户列表", notes="查找最新用户列表")
    @GetMapping(value="/users")
    public ResponseEntity<Result<List<UserInfo>>> getOriginalUser(@RequestParam String date)
    {
        return requestHandler.doGet(
                new GetCallback<List<UserInfo>>() {
                    @Override
                    public RequestState doCheck() {
                        if (!"true".equals(relayUser)){
                            return RequestState.RELAY_SETTING_USER_OFF;
                        }
                        return RequestState.SUCCESS;
                    }
                    @Override
                    public List<UserInfo> invoke() throws Exception {
                        int result = originalUserService.importUser();
                        if (result < 1){
                            return null;
                        }
                        if (StringUtils.isNotBlank(date)){
                            Date searchDate = DateUtils.StringToDate(date);
                            return originalUserService.findByDate(searchDate);
                        }else{
                            return originalUserService.findAll();
                        }

                    }
                }
        );
    }

}