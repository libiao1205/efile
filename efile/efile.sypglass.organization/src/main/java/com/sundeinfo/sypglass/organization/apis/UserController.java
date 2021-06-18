package com.sundeinfo.sypglass.organization.apis;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sundeinfo.core.mvc.controller.AbstractController;
import com.sundeinfo.core.request.*;
import com.sundeinfo.core.request.result.Result;
import com.sundeinfo.core.utility.ConvertUtils;
import com.sundeinfo.core.utility.ReflectUtils;
import com.sundeinfo.sypglass.organization.configuration.ConfigService;
import com.sundeinfo.sypglass.organization.dto.DepartmentDto;
import com.sundeinfo.sypglass.organization.dto.RegisterDto;
import com.sundeinfo.sypglass.organization.dto.UserDto;
import com.sundeinfo.sypglass.organization.configuration.ConfigService;
import com.sundeinfo.sypglass.organization.dto.UserInfoDto;
import com.sundeinfo.sypglass.organization.service.DepartmentService;
import com.sundeinfo.sypglass.organization.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@Api(description = "管理系统API")
@RestController
@RequestMapping("relay")
public class UserController extends AbstractController<UserController> {

    @Autowired
    private UserService userService;

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private ConfigService configService;

    @ApiOperation(value="批量查找用户列表", notes="批量查找用户列表")
    @GetMapping(value = "users/{pagenum}-{pagesize}")
    public ResponseEntity<Result<PageInfo<UserDto>>> getUsers(@PathVariable(value = "pagenum") int pagenum,
                                                              @PathVariable(value = "pagesize") int pagesize,
                                                              @RequestParam boolean islove){
        return requestHandler.doGet(
                new GetCallback<PageInfo<UserDto>>() {
                    @Override
                    public RequestState doCheck() {
                        return RequestState.SUCCESS;
                    }
                    @Override
                    public PageInfo<UserDto> invoke() throws Exception {
                        PageHelper.startPage(pagenum, pagesize);
                        List<UserDto> userDtos = userService.findAll(islove,true);
                        for (UserDto userDto:userDtos) {
                            userDto.setDepartments(userService.findUserDepartments(userDto.getId()));
                        }
                        return new PageInfo<>(userDtos);
                    }
                }
        );
    }

    @ApiOperation(value="批量查找用户列表", notes="批量查找用户列表")
    @GetMapping(value = "search/users/{pagenum}-{pagesize}")
    public ResponseEntity<Result<PageInfo<UserDto>>> getUsersBySearchKey(@PathVariable(value = "pagenum") int pagenum,
                                                                         @PathVariable(value = "pagesize") int pagesize,
                                                                         @RequestParam String name,
                                                                         @RequestParam boolean islove){
        return requestHandler.doGet(
                new GetCallback<PageInfo<UserDto>>() {
                    @Override
                    public RequestState doCheck() {
                        return RequestState.SUCCESS;
                    }
                    @Override
                    public PageInfo<UserDto> invoke() throws Exception {
                        PageHelper.startPage(pagenum, pagesize);
                        List<UserDto> userDtos = userService.findBySearchKey(name,islove);
                        for (UserDto userDto:userDtos) {
                            userDto.setDepartments(userService.findUserDepartments(userDto.getId()));
                        }
                        return new PageInfo<>(userDtos);
                    }
                }
        );
    }

    @ApiOperation(value="查找用户", notes="查找用户")
    @GetMapping(value = "user/{id}")
    public ResponseEntity<Result<UserDto>> getUser(@PathVariable(value = "id") long id){
        return requestHandler.doGet(
                new GetCallback<UserDto>() {
                    @Override
                    public RequestState doCheck() {
                        return RequestState.SUCCESS;
                    }
                    @Override
                    public UserDto invoke() throws Exception {
                        UserDto userDto = userService.findById(id);
                        List<DepartmentDto> deptDtos = userService.findUserDepartments(userDto.getId());
                        userDto.setDepartments(deptDtos);
                        userDto.setInfo(userService.findUserInfoByUserId(id));
                        return userDto;
                    }
                }
        );
    }

    @ApiOperation(value="查找用户组织树", notes="查找用户组织树")
    @GetMapping(value = "user/{id}/organization")
    public ResponseEntity<Result<List<String>>> getUserOrganization(@PathVariable(value = "id") long id){
        return requestHandler.doGet(
                new GetCallback<List<String>>() {
                    @Override
                    public RequestState doCheck() {
                        return RequestState.SUCCESS;
                    }
                    @Override
                    public List<String> invoke() throws Exception {
                        List<String > result = new ArrayList<>();
                        List<DepartmentDto> userDepartments = userService.findUserDepartments(id);
                        for (DepartmentDto department:userDepartments) {
                            List<DepartmentDto> departmentDtos = departmentService.findParentListByDeptId(department.getId());
                            List<Long> ids = ReflectUtils.reflectDataList(departmentDtos,"id",Long.class);
                            result.add(StringUtils.join(ids.toArray(), "/"));
                        }
                        return result;
                    }
                }
        );
    }

    @ApiOperation(value="用户新增", notes="用户新增")
    @PostMapping(value = "user")
    public ResponseEntity<Result<RegisterDto>> postUser(@RequestBody RegisterDto registerDto){
        return requestHandler.doPost(
                new ResponseCheckResultCallback<RegisterDto>() {
                    String passwordCheckResult;
                    @Override
                    public RequestState doCheck() throws Exception{
                        UserDto oldUserDto = userService.findByUsername(registerDto.getUsername());
                        if (oldUserDto != null){
                            logger.info("传入的用户Code已经存在");
                            return RequestState.PARAM_USELESS_USERNAME;
                        }
                        DepartmentDto departmentDto = departmentService.findById(registerDto.getDeptId());
                        if(departmentDto == null){
                            logger.info("传入的部门编号查找不到部门");
                            return RequestState.PARAM_USELESS;
                        }
                        passwordCheckResult = configService.checkPsdComplexity(registerDto.getPassword());
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
                        UserDto userDto = ConvertUtils.convert(registerDto,UserDto.class);
                        userDto.setDepartments(registerDto.getDepartments());
                        int i = userService.save(userDto,userDto.getDepartments());
                        if(i > 0) {
                            registerDto.setId(userDto.getId());
                            return RequestState.SUCCESS;
                        }else{
                            return RequestState. DB_INSERT_ERROR;
                        }
                    }

                    @Override
                    public String getInvokeMessage(RequestState state) {
                        return null;
                    }

                    @Override
                    public RegisterDto response() throws Exception {
                        return registerDto;
                    }
                }
        );
    }

    @ApiOperation(value="用户基本信息编辑", notes="用户基本信息编辑")
    @PutMapping(value = "user/{id}/base")
    public ResponseEntity<Result> putUser(@PathVariable long id,@RequestBody RegisterDto registerDto){
        return requestHandler.doPost(
                new PostCallback() {
                    @Override
                    public RequestState doCheck() throws Exception {
                        UserDto oldUserDto = userService.findByUsername(registerDto.getUsername());
                        if (oldUserDto != null && oldUserDto.getId() != registerDto.getId()){
                            logger.info("传入的用户Code已经存在");
                            return RequestState.PARAM_USELESS_USERNAME;
                        }
                        return RequestState.SUCCESS;
                    }
                    @Override
                    public RequestState invoke() throws Exception {
                        UserDto userDto = ConvertUtils.convert(registerDto,UserDto.class);
                        int i = userService.modBaseInfo(userDto,registerDto.getOldDeptId(),registerDto.getDeptId());
                        return i > 0? RequestState.SUCCESS : RequestState. DB_UPDATE_ERROR;
                    }
                }
        );
    }

    @ApiOperation(value="编辑用户密码", notes="编辑用户密码")
    @PutMapping(value = "user/{id}/password")
    public ResponseEntity<Result> putUserPassword(@PathVariable long id,@RequestBody RegisterDto registerDto){
        return requestHandler.doPost(
                new PostCheckResultCallback() {
                    String passwordCheckResult;
                    @Override
                    public RequestState doCheck() throws Exception {
                        passwordCheckResult = configService.checkPsdComplexity(registerDto.getPassword());
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
                        UserDto userDto = ConvertUtils.convert(registerDto,UserDto.class);
                        int i = userService.modPassword(userDto);
                        return i > 0? RequestState.SUCCESS : RequestState. DB_UPDATE_ERROR;
                    }

                    @Override
                    public String getInvokeMessage(RequestState state) {
                        return null;
                    }
                }
        );
    }

    @ApiOperation(value="编辑用户个人信息", notes="编辑用户个人信息")
    @PutMapping(value = "user/{id}/info")
    public ResponseEntity<Result> putUserInfo(@PathVariable long id,@RequestBody UserInfoDto userInfoDto){
        return requestHandler.doPost(
                new PostCallback() {
                    @Override
                    public RequestState doCheck() throws Exception {
                        return RequestState.SUCCESS;
                    }
                    @Override
                    public RequestState invoke() throws Exception {
                        UserDto userDto = userService.findById(id);
                        userDto.setInfo(userService.findUserInfoByUserId(id));
                        int i =  userService.saveInfo(userDto,userInfoDto);
                        return i > 0? RequestState.SUCCESS : RequestState. DB_UPDATE_ERROR;
                    }
                }
        );
    }

//    @ApiOperation(value="设置为系统管理员", notes="设置为系统管理员")
//    @PutMapping(value = "user/{id}/sysadmin")
//    public ResponseEntity<Result> putUserAdmin(@PathVariable long id,@RequestBody RegisterDto registerDto){
//        return requestHandler.doPost(
//                new PostCallback() {
//                    @Override
//                    public RequestState doCheck() throws Exception {
//                        if (adminService.isSysAdmin(registerDto.getId())){
//                            return RequestState.PARAM_USELESS_ADMIN;
//                        }
//                        return RequestState.SUCCESS;
//                    }
//                    @Override
//                    public RequestState invoke() throws Exception {
//                        int i = adminService.saveSysAdmin(registerDto.getId(),registerDto.getDepartments().get(0).getId());
//                        return i > 0? RequestState.SUCCESS : RequestState. DB_UPDATE_ERROR;
//                    }
//                }
//        );
//    }

    @ApiOperation(value="编辑用户部门", notes="编辑用户部门")
    @PutMapping(value = "user/{id}/dept")
    public ResponseEntity<Result> putUserDept(@PathVariable long id,@RequestBody RegisterDto registerDto){
        return requestHandler.doPost(
                new PostCallback() {
                    @Override
                    public RequestState doCheck() throws Exception {
                        DepartmentDto departmentDto = departmentService.findById(registerDto.getDeptId());
                        if(departmentDto == null){
                            logger.info("传入的部门编号查找不到部门");
                            return RequestState.PARAM_USELESS;
                        }
                        return RequestState.SUCCESS;
                    }
                    @Override
                    public RequestState invoke() throws Exception {
                        int i = userService.modUserDept(registerDto.getId(),registerDto.getOldDeptId(),registerDto.getDeptId());
                        return i > 0? RequestState.SUCCESS : RequestState. DB_UPDATE_ERROR;
                    }
                }
        );
    }

    @ApiOperation(value="用户封禁", notes="用户封禁")
    @PutMapping(value = "user/{id}/lock")
    public ResponseEntity<Result> putUserLock(@PathVariable long id,@RequestBody RegisterDto registerDto){
        return requestHandler.doPost(
                new PostCallback() {
                    @Override
                    public RequestState doCheck() throws Exception {
                        return RequestState.SUCCESS;
                    }
                    @Override
                    public RequestState invoke() throws Exception {
                        int i = userService.modUserLock(id);
                        return i > 0? RequestState.SUCCESS : RequestState. DB_UPDATE_ERROR;
                    }
                }
        );
    }

    @ApiOperation(value="用户离职", notes="用户离职")
    @PutMapping(value = "user/{id}/leave")
    public ResponseEntity<Result> leaveUser(@PathVariable long id,@RequestBody UserDto userDto){
        return requestHandler.doPost(
                new PostCallback() {
                    @Override
                    public RequestState doCheck() {
                        return RequestState.SUCCESS;
                    }
                    @Override
                    public RequestState invoke() throws Exception {
                        int i = userService.modUserLeave(userDto.getId());
                        return i > 0? RequestState.SUCCESS : RequestState. DB_UPDATE_ERROR;
                    }
                }
        );
    }

    @ApiOperation(value="用户解封", notes="用户解封")
    @PutMapping(value = "user/{id}/normal")
    public ResponseEntity<Result> putUserNormal(@PathVariable long id,@RequestBody RegisterDto registerDto){
        return requestHandler.doPost(
                new PostCallback() {
                    @Override
                    public RequestState doCheck() throws Exception {
                        return RequestState.SUCCESS;
                    }
                    @Override
                    public RequestState invoke() throws Exception {
                        int i = userService.modUserNormal(id);
                        return i > 0? RequestState.SUCCESS : RequestState. DB_UPDATE_ERROR;
                    }
                }
        );
    }

    @ApiOperation(value="用户删除", notes="用户删除")
    @DeleteMapping(value = "user")
    public ResponseEntity<Result> deleteUser(@RequestBody UserDto userDto){
        return requestHandler.doPost(
                new PostCallback() {
                    @Override
                    public RequestState doCheck() {
                        return RequestState.SUCCESS;
                    }
                    @Override
                    public RequestState invoke() throws Exception {
                        int i = userService.delete(userDto);
                        return i > 0? RequestState.SUCCESS : RequestState. DB_UPDATE_ERROR;
                    }
                }
        );
    }


}
