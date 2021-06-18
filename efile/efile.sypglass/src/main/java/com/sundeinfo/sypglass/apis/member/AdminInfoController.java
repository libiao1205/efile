package com.sundeinfo.sypglass.apis.member;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sundeinfo.core.mvc.controller.AbstractController;
import com.sundeinfo.core.request.GetCallback;
import com.sundeinfo.core.request.PostCallback;
import com.sundeinfo.core.request.RequestState;
import com.sundeinfo.core.request.result.Result;
import com.sundeinfo.core.utility.ReflectUtils;
import com.sundeinfo.security.model.AnyUser;
import com.sundeinfo.security.utility.AuthenticationGetter;
import com.sundeinfo.sypglass.dto.AdminDto;
import com.sundeinfo.sypglass.dto.AdminFolderDto;
import com.sundeinfo.sypglass.dto.UserDto;
import com.sundeinfo.sypglass.service.AdminService;
import com.sundeinfo.sypglass.service.DepartmentService;
import com.sundeinfo.sypglass.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(description = "管理系统API")
@RestController
@RequestMapping("admin")
public class AdminInfoController extends AbstractController<AdminInfoController> {

    @Autowired
    private UserService userService;

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private AdminService adminService;
    @Autowired
    private AuthenticationGetter authenticationGetter;


    @ApiOperation(value="批量查找管理员用户列表", notes="批量查找管理员用户列表")
    @GetMapping(value = "adminusers/{pagenum}-{pagesize}")
    public ResponseEntity<Result<PageInfo<UserDto>>> getUsers(@PathVariable(value = "pagenum") int pagenum, @PathVariable(value = "pagesize") int pagesize){
        return requestHandler.doGet(
                new GetCallback<PageInfo<UserDto>>() {
                    @Override
                    public RequestState doCheck() {
                        return RequestState.SUCCESS;
                    }
                    @Override
                    public PageInfo<UserDto> invoke() throws Exception {
                        PageHelper.startPage(pagenum, pagesize);
                        List<AdminDto> adminDtos = adminService.findAll();
                        List<Long> ids = ReflectUtils.reflectDataList(adminDtos,"userId",Long.class);
                        List<UserDto> userDtos = userService.findByIds(ids);
                        for (UserDto userDto:userDtos) {
                            userDto.setAdmin(adminService.findByUser(userDto.getId()));
                            userDto.setDepartments(userService.findUserDepartments(userDto.getId()));
                        }
                        PageInfo<UserDto> pageInfo = new PageInfo<>(userDtos);
                        return pageInfo;
                    }
                }
        );
    }

    @ApiOperation(value="删除管理员", notes="删除管理员")
    @DeleteMapping(value = "admin/{id}")
    public ResponseEntity<Result> deleteAdmin(@PathVariable long id){
        return requestHandler.doPost(
                new PostCallback() {
                    @Override
                    public RequestState doCheck() {
                        return RequestState.SUCCESS;
                    }
                    @Override
                    public RequestState invoke() throws Exception {
                        int i = 0;
                        AnyUser anyUser = authenticationGetter.getAnyUser();
                        AdminDto adminDto = adminService.findById(id);
                        if(anyUser.getUser().getId() == adminDto.getUserId()){
                            return RequestState. DB_DELETE_ERROR_OWN;
                        }
                        if(adminDto.getLevel()== 1){
                            i = adminService.deleteSystemAdminByAdminId(id);
                        }else{
                            i = adminService.deleteFolderAdminByAdminId(id);
                        }
                        return i > 0? RequestState.SUCCESS : RequestState. DB_UPDATE_ERROR;
                    }
                }
        );
    }


    @ApiOperation(value="查找管理员对应的文件夹列表", notes="查找管理员对应的文件夹列表")
    @GetMapping(value = "userFolders/{adminid}")
    public ResponseEntity<Result<PageInfo<AdminFolderDto>>> getUserFolders(@PathVariable(value = "adminid") long adminid){
        return requestHandler.doGet(
                new GetCallback<PageInfo<AdminFolderDto>>() {
                    @Override
                    public RequestState doCheck() {
                        return RequestState.SUCCESS;
                    }
                    @Override
                    public PageInfo<AdminFolderDto> invoke() throws Exception {
                        List<AdminFolderDto> list = adminService.findFolderByAdminId(adminid);
                        PageInfo<AdminFolderDto> pageInfo = new PageInfo<>(list);
                        return pageInfo;
                    }
                }
        );
    }

    @ApiOperation(value="删除管理员指定的文件夹权限", notes="删除管理员指定的文件夹权限")
    @DeleteMapping(value = "deleteFolderByAdmin/{adminfolderid}")
    public ResponseEntity<Result> deleteAdminFolder(@PathVariable long adminfolderid){
        return requestHandler.doPost(
                new PostCallback() {
                    @Override
                    public RequestState doCheck() {
                        return RequestState.SUCCESS;
                    }
                    @Override
                    public RequestState invoke() throws Exception {
                        int i = adminService.deleteAdminFolderById(adminfolderid);
                        return i > 0? RequestState.SUCCESS : RequestState. DB_UPDATE_ERROR;
                    }
                }
        );
    }

}