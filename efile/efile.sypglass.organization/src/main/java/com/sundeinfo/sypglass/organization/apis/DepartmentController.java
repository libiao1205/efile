package com.sundeinfo.sypglass.organization.apis;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sundeinfo.core.mvc.controller.AbstractController;
import com.sundeinfo.core.request.GetCallback;
import com.sundeinfo.core.request.PostCallback;
import com.sundeinfo.core.request.RequestState;
import com.sundeinfo.core.request.ResponseCallback;
import com.sundeinfo.core.request.result.Result;
import com.sundeinfo.core.utility.ReflectUtils;
import com.sundeinfo.sypglass.organization.define.DepartmentStatus;
import com.sundeinfo.sypglass.organization.define.SypGlass;
import com.sundeinfo.sypglass.organization.dto.DepartmentDto;
import com.sundeinfo.sypglass.organization.dto.UserDto;
import com.sundeinfo.sypglass.organization.service.DepartmentService;
import com.sundeinfo.sypglass.organization.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Api(description = "管理系统API")
@RestController
@RequestMapping("relay")
public class DepartmentController extends AbstractController<DepartmentController> {

    @Autowired
    private UserService userService;

    @Autowired
    private DepartmentService departmentService;

    @ApiOperation(value="查找部门详细信息并包含下级部门", notes="查找部门详细信息包含下级部门")
    @GetMapping(value = "depts")
    public ResponseEntity<Result<DepartmentDto>> getDept(){
        return requestHandler.doGet(
                new GetCallback<DepartmentDto>() {
                    @Override
                    public RequestState doCheck() {
                        return RequestState.SUCCESS;
                    }
                    @Override
                    public DepartmentDto invoke() throws Exception {
                        return departmentService.findChildrenTreeByDeptId(SypGlass.ROOT_DEPT_ID);
                    }
                }
        );
    }

    @ApiOperation(value="查找部门详细信息并包含下级部门", notes="查找部门详细信息包含下级部门")
    @GetMapping(value = "dept/{id}")
    public ResponseEntity<Result<DepartmentDto>> getDeptInfo(@PathVariable(value = "id") long id){
        return requestHandler.doGet(
                new GetCallback<DepartmentDto>() {
                    @Override
                    public RequestState doCheck() {
                        return RequestState.SUCCESS;
                    }
                    @Override
                    public DepartmentDto invoke() throws Exception {
                        DepartmentDto departmentDto = departmentService.findById(id);
                        departmentDto.setChildren(departmentService.findChildrenByDeptId(id));
                        return departmentDto;
                    }
                }
        );
    }

    @ApiOperation(value="查找部门组织树", notes="查找部门组织树")
    @GetMapping(value = "dept/{id}/organization")
    public ResponseEntity<Result<List<String>>> getDeptOrganization(@PathVariable(value = "id") long id){
        return requestHandler.doGet(
                new GetCallback<List<String>>() {
                    @Override
                    public RequestState doCheck() {
                        return RequestState.SUCCESS;
                    }
                    @Override
                    public List<String> invoke() throws Exception {
                        List<String > result = new ArrayList<>();
                        List<DepartmentDto> departmentDtos = departmentService.findParentListByDeptId(id);
                        List<Long> ids = ReflectUtils.reflectDataList(departmentDtos,"id",Long.class);
                        result.add(StringUtils.join(ids.toArray(), "/"));
                        return result;
                    }
                }
        );
    }

    @ApiOperation(value="依据部门ID查找用户列表", notes="依据部门ID查找用户列表")
    @GetMapping(value = "dept/{id}/users/{pagenum}-{pagesize}")
    public ResponseEntity<Result<PageInfo<UserDto>>> getDeptUser(@PathVariable(value = "id") int id, @PathVariable(value = "pagenum") int pagenum, @PathVariable(value = "pagesize") int pagesize){
        return requestHandler.doGet(
                new GetCallback<PageInfo<UserDto>>() {
                    @Override
                    public RequestState doCheck() {
                        return RequestState.SUCCESS;
                    }
                    @Override
                    public PageInfo<UserDto> invoke() throws Exception {
                        PageHelper.startPage(pagenum, pagesize);
                        List<UserDto> userDtos = userService.findByDept(id);
                        for (UserDto userDto:userDtos) {
                            userDto.setDepartments(departmentService.findOrganizationByUserId(userDto.getId()));
                        }
                        //return userDtos;
                        return new PageInfo<>(userDtos);
                    }
                }
        );
    }

    @ApiOperation(value="新增部门", notes="新增部门")
    @PostMapping(value = "dept")
    public ResponseEntity<Result<DepartmentDto>> postDept(@Valid @RequestBody DepartmentDto departmentDto){
        return requestHandler.doPost(
                new ResponseCallback<DepartmentDto>(){
                    @Override
                    public RequestState doCheck() throws Exception {
                        DepartmentDto dto = departmentService.findById(departmentDto.getParentId());
                        if(dto == null){
                            logger.info("没有找到上级部门");
                            return RequestState.PARAM_USELESS;
                        }
                        return RequestState.SUCCESS;
                    }
                    @Override
                    public RequestState invoke() throws Exception {
                        departmentDto.setStatus((byte)DepartmentStatus.NORMAL.getStatus());
                        int i = departmentService.save(departmentDto);
                        return i > 0? RequestState.SUCCESS : RequestState. DB_INSERT_ERROR;
                    }
                    @Override
                    public DepartmentDto response() throws Exception {
                        return departmentDto;
                    }
                }
        );
    }

    @ApiOperation(value="编辑部门", notes="编辑部门")
    @PutMapping(value = "dept")
    public ResponseEntity<Result> putDept(@RequestBody DepartmentDto departmentDto){
        return requestHandler.doPost(
                new PostCallback() {
                    @Override
                    public RequestState doCheck() throws Exception {
                        List<DepartmentDto> departmentDtos = departmentService.findChildrenListByDeptId(departmentDto.getId());
                        for (DepartmentDto child:departmentDtos) {
                            if (child.getId() == departmentDto.getParentId()){
                                logger.info("不能把自己或者自己的子部门设为上级部门");
                                return RequestState.PARAM_USELESS_PARENT_DEPT;
                            }
                        }
                        return RequestState.SUCCESS;
                    }
                    @Override
                    public RequestState invoke() throws Exception {
                        int i = departmentService.mod(departmentDto);
                        return i > 0? RequestState.SUCCESS : RequestState. DB_UPDATE_ERROR;
                    }
                }
        );
    }

    @ApiOperation(value="封存部门", notes="封存部门")
    @DeleteMapping(value = "dept")
    public ResponseEntity<Result> deleteUser(@RequestBody DepartmentDto departmentDto){
        return requestHandler.doPost(
                new PostCallback() {
                    @Override
                    public RequestState doCheck() {
                        return RequestState.SUCCESS;
                    }
                    @Override
                    public RequestState invoke() throws Exception {
                        int i = departmentService.blocked(departmentDto);
                        return i > 0? RequestState.SUCCESS : RequestState. DB_DELETE_ERROR;
                    }
                }
        );
    }
}
