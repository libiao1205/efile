package com.sundeinfo.sypglass.apis.member;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sundeinfo.core.mvc.controller.AbstractController;
import com.sundeinfo.core.request.GetCallback;
import com.sundeinfo.core.request.PostCallback;
import com.sundeinfo.core.request.RequestState;
import com.sundeinfo.core.request.ResponseCallback;
import com.sundeinfo.core.request.result.Result;
import com.sundeinfo.sypglass.define.SypGlass;
import com.sundeinfo.sypglass.dto.DepartmentDto;
import com.sundeinfo.sypglass.dto.RegisterDto;
import com.sundeinfo.sypglass.dto.UserGroupDto;
import com.sundeinfo.sypglass.model.Member;
import com.sundeinfo.sypglass.service.DepartmentService;
import com.sundeinfo.sypglass.service.MemberService;
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
public class MemberController extends AbstractController<MemberController> {

    @Autowired
    private MemberService memberService;

    @Autowired
    private UserService userService;

    @Autowired
    private DepartmentService departmentService;

    @ApiOperation(value="依据部门ID查找组织列表", notes="依据部门ID查找组织列表")
    @GetMapping(value = "dept/{id}/members")
    public ResponseEntity<Result<List<Member>>> getMember(@PathVariable(value = "id") int id){
        return requestHandler.doGet(
                new GetCallback<List<Member>>() {
                    @Override
                    public RequestState doCheck() {
                        return RequestState.SUCCESS;
                    }
                    @Override
                    public List<Member> invoke() throws Exception {
                        return memberService.getMemberByDeptId(id);
                    }
                }
        );
    }

    @ApiOperation(value="查找用户组列表", notes="查找用户组列表")
    @GetMapping(value = "groups/{pagenum}-{pagesize}")
    public ResponseEntity<Result<PageInfo<DepartmentDto>>> getUserGroup(@PathVariable(value = "pagenum") int pagenum, @PathVariable(value = "pagesize") int pagesize){
        return requestHandler.doGet(
                new GetCallback<PageInfo<DepartmentDto>>() {
                    @Override
                    public RequestState doCheck() {
                        return RequestState.SUCCESS;
                    }
                    @Override
                    public PageInfo<DepartmentDto> invoke() throws Exception {
                        PageHelper.startPage(pagenum, pagesize);
                        List<DepartmentDto> departmentDtos = departmentService.findByType(SypGlass.DEPT_TYPE_GROUP);
                        return new PageInfo<>(departmentDtos);
                    }
                }
        );
    }

    @ApiOperation(value="新增用户组", notes="新增用户组")
    @PostMapping(value = "group")
    public ResponseEntity<Result<DepartmentDto>> postUserGroup(@RequestBody UserGroupDto userGroupDto){
        return requestHandler.doPost(
                new ResponseCallback<DepartmentDto>() {
                    @Override
                    public RequestState doCheck() throws Exception {
                        return RequestState.SUCCESS;
                    }

                    @Override
                    public RequestState invoke() throws Exception {
                        int result = departmentService.save(userGroupDto);
                        if (result < 1){
                            return RequestState.DB_INSERT_ERROR;
                        }
                        return RequestState.SUCCESS;
                    }

                    @Override
                    public DepartmentDto response() throws Exception {
                        DepartmentDto departmentDto = new DepartmentDto();
                        departmentDto.setId(userGroupDto.getId());
                        departmentDto.setParentId(1);
                        departmentDto.setType(userGroupDto.getType());
                        departmentDto.setStatus(userGroupDto.getStatus());
                        return departmentDto;
                    }
                }
        );
    }

    @ApiOperation(value="编辑用户组", notes="编辑用户组")
    @PutMapping(value = "group")
    public ResponseEntity<Result> putUserGroup(@RequestBody UserGroupDto userGroupDto){
        return requestHandler.doPost(
                new PostCallback() {
                    @Override
                    public RequestState doCheck() {
                        return RequestState.SUCCESS;
                    }
                    @Override
                    public RequestState invoke() throws Exception {
                        int i = departmentService.mod(userGroupDto);
                        return i > 0? RequestState.SUCCESS : RequestState. DB_DELETE_ERROR;
                    }
                }
        );
    }

    @ApiOperation(value="删除用户组", notes="删除用户组")
    @DeleteMapping(value = "group/{id}")
    public ResponseEntity<Result> deleteUserGroup(@PathVariable(value = "id") int id){
        return requestHandler.doPost(
                new PostCallback() {
                    @Override
                    public RequestState doCheck() {
                        return RequestState.SUCCESS;
                    }
                    @Override
                    public RequestState invoke() throws Exception {
                        int i = departmentService.delete(id);
                        return i > 0? RequestState.SUCCESS : RequestState. DB_DELETE_ERROR;
                    }
                }
        );
    }

    @ApiOperation(value="删除用户组成员", notes="删除用户组成员")
    @DeleteMapping(value = "group/{deptid}/user/{userid}")
    public ResponseEntity<Result> deleteGroupUser(@PathVariable(value = "deptid") int deptid,@PathVariable(value = "userid") int userid){
        return requestHandler.doPost(
                new PostCallback() {
                    @Override
                    public RequestState doCheck() {
                        return RequestState.SUCCESS;
                    }
                    @Override
                    public RequestState invoke() throws Exception {
                        int i = departmentService.deleteDepartmentUser(deptid,userid);
                        return i > 0? RequestState.SUCCESS : RequestState. DB_DELETE_ERROR;
                    }
                }
        );
    }
}