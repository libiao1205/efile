package com.sundeinfo.sypglass.apis.role;

import com.sundeinfo.core.mvc.controller.AbstractController;
import com.sundeinfo.core.permission.PermissionHelper;
import com.sundeinfo.core.request.GetCallback;
import com.sundeinfo.core.request.PostCallback;
import com.sundeinfo.core.request.RequestState;
import com.sundeinfo.core.request.ResponseCallback;
import com.sundeinfo.core.request.result.Result;
import com.sundeinfo.core.utility.ReflectUtils;
import com.sundeinfo.sypglass.component.PermissionAdapter;
import com.sundeinfo.sypglass.define.SypGlass;
import com.sundeinfo.sypglass.dto.*;
import com.sundeinfo.sypglass.exception.RoleException;
import com.sundeinfo.sypglass.model.RolePermission;
import com.sundeinfo.sypglass.service.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.rmi.CORBA.Util;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api(description = "权限系统API")
@RestController
@RequestMapping("admin")
public class RoleController extends AbstractController<RoleController> {

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private RolePermissionService rolePermissionService;

    @ApiOperation(value="依据部门ID查找权限列表", notes="依据部门ID查找权限列表")
    @GetMapping(value = "dept/{id}/rps")
    public ResponseEntity<Result<List<RolePermissionDto>>> getRPByDept(@PathVariable(value = "id") int id){
        return requestHandler.doGet(
                new GetCallback<List<RolePermissionDto>>() {
                    @Override
                    public RequestState doCheck() {
                        return RequestState.SUCCESS;
                    }
                    @Override
                    public List<RolePermissionDto> invoke() throws Exception {
                        List<RoleDto> roleDtos = roleService.findCompleteRolesByDeptId(id);
                        if (roleDtos == null || roleDtos.size() == 0){
                            return new ArrayList<>();
                        }
                        List<Long> ids = ReflectUtils.reflectDataList(roleDtos,"id",Long.class);
                        List<RolePermissionDto> rolePermissionDtos = rolePermissionService.findPermissionByRoleIds(ids);
                        rolePermissionDtos.forEach(rp -> rp.setExplication(PermissionHelper.getPermissionMapByFunction(rp.getValue())));

                        RoleDto self = roleService.findRoleSelfByDepartmentId(id);
                        rolePermissionDtos.forEach(rp -> rp.reckonInheritType(self));

                        return rolePermissionDtos;
                    }
                }
        );
    }



    @ApiOperation(value="依据人员ID查找权限列表", notes="依据人员ID查找权限列表")
    @GetMapping(value = "user/{id}/rps")
    public ResponseEntity<Result<List<RolePermissionDto>>> getRPByUser(@PathVariable(value = "id") int id){
        return requestHandler.doGet(
                new GetCallback<List<RolePermissionDto>>() {
                    @Override
                    public RequestState doCheck() {
                        return RequestState.SUCCESS;
                    }
                    @Override
                    public List<RolePermissionDto> invoke() throws Exception {
                        List<RoleDto> roleDtos = roleService.findCompleteRolesByUserId(id);
                        if (roleDtos == null || roleDtos.size() == 0){
                            return new ArrayList<>();
                        }
                        List<Long> ids = ReflectUtils.reflectDataList(roleDtos,"id",Long.class);
                        List<RolePermissionDto> rolePermissionDtos = rolePermissionService.findPermissionByRoleIds(ids);
                        rolePermissionDtos.forEach(rp -> rp.setExplication(PermissionHelper.getPermissionMapByFunction(rp.getValue())));

                        RoleDto self = roleService.findRoleSelfByUserId(id);
                        rolePermissionDtos.forEach(rp -> rp.reckonInheritType(self));

                        return rolePermissionDtos;
                    }
                }
        );
    }

    @ApiOperation(value="依据文件夹ID查找权限列表", notes="依据文件夹ID查找权限列表")
    @GetMapping(value = "folder/{id}/rps")
    public ResponseEntity<Result<List<RolePermissionDto>>> getRPByFolder(@PathVariable(value = "id") int id){
        return requestHandler.doGet(
                new GetCallback<List<RolePermissionDto>>() {
                    @Override
                    public RequestState doCheck() {
                        return RequestState.SUCCESS;
                    }
                    @Override
                    public List<RolePermissionDto> invoke() throws Exception {
                        List<RolePermissionDto> rolePermissionDtos = rolePermissionService.findByResource(SypGlass.RESOURCE_TYPE_FOLDER,id);
                        rolePermissionDtos.forEach(rp -> rp.setExplication(PermissionHelper.getPermissionMapByFunction(rp.getValue())));
                        return rolePermissionDtos;
                    }
                }
        );
    }

    @ApiOperation(value="依据文件ID查找权限列表", notes="依据文件ID查找权限列表")
    @GetMapping(value = "file/{id}/rps")
    public ResponseEntity<Result<List<RolePermissionDto>>> getRPByfile(@PathVariable(value = "id") int id){
        return requestHandler.doGet(
                new GetCallback<List<RolePermissionDto>>() {
                    @Override
                    public RequestState doCheck() {
                        return RequestState.SUCCESS;
                    }
                    @Override
                    public List<RolePermissionDto> invoke() throws Exception {
                        List<RolePermissionDto> rolePermissionDtos = rolePermissionService.findByResource(SypGlass.RESOURCE_TYPE_FILE,id);
                        rolePermissionDtos.forEach(rp -> rp.setExplication(PermissionHelper.getPermissionMapByFunction(rp.getValue())));
                        return rolePermissionDtos;
                    }
                }
        );
    }



    @ApiOperation(value="新增权限", notes="新增权限")
    @PostMapping(value = "role")
    public ResponseEntity<Result<List<RolePermissionDto>>> postPermission(@RequestBody RoleDto roleDto){
        return requestHandler.doPost(
                new ResponseCallback<List<RolePermissionDto>>(){
                    private  RoleDto roleSelf = null;
                    @Override
                    public RequestState doCheck() throws Exception {
                        if (roleDto.getRolePermissions() == null || roleDto.getRolePermissions().size() == 0){
                            return RequestState.PARAM_PERMISSION_NULL;
                        }
                        if (roleDto.getDeptId() == 0 && roleDto.getUserId() == 0){
                            return RequestState.PARAM_PERMISSION_NULL_MEMBER;
                        }
                        roleSelf = roleService.findByTypeAndMemberId(roleDto.getType(),roleDto.getDeptId(),roleDto.getUserId());
                        return RequestState.SUCCESS;
                    }
                    @Override
                    public RequestState invoke() throws Exception {
                        if (roleSelf != null){
                            List<RolePermissionDto> rolePermissionDtos = roleDto.getRolePermissions();
                            for (RolePermissionDto rolePermission:rolePermissionDtos) {
                                List<RolePermissionDto> saved = rolePermissionService.findByRoleAndResource(roleSelf.getId(),rolePermission.getCode(),rolePermission.getResourceId());
                                if (saved != null && saved.size() > 0){
                                    //走修正逻辑
                                    rolePermission.setId(saved.get(0).getId());
                                    rolePermission.setValue(PermissionHelper.getPermissionByMap(rolePermission.getExplication()));
                                    int i = rolePermissionService.modify(rolePermissionDtos);
                                    return i > 0? RequestState.SUCCESS : RequestState. DB_INSERT_ERROR;
                                }
                            }
                        }

                        if (roleSelf == null){
                            String name = String.format("Type:(%s);UserId:(%s);DeptId:(%s)",roleDto.getType(),roleDto.getUserId(),roleDto.getDeptId());
                            roleSelf = roleService.createNew(name,roleDto.getUserId(),roleDto.getDeptId(),roleDto.getType());
                            int i = roleService.save(roleSelf);
                            if (i < 1)
                                return RequestState. DB_INSERT_ERROR;
                        }
                        long memberId = getMemberId(roleDto);
                        String memberName = getMemberName(roleDto);
                        List<RolePermissionDto> rolePermissionDtos = roleDto.getRolePermissions();
                        for (RolePermissionDto rolePermission:rolePermissionDtos) {
                            rolePermission.setRoleId(roleSelf.getId());
                            rolePermission.setType(roleDto.getType());
                            rolePermission.setMemberId(memberId);
                            rolePermission.setMemberName(memberName);
                            rolePermission.setValue(PermissionHelper.getPermissionByMap(rolePermission.getExplication()));
                        }
                        int i = rolePermissionService.saveAll(rolePermissionDtos);
                        return i > 0? RequestState.SUCCESS : RequestState. DB_INSERT_ERROR;
                    }
                    @Override
                    public List<RolePermissionDto> response() throws Exception {
                        return roleDto.getRolePermissions();
                    }
                }

        );
    }

    @ApiOperation(value="编辑权限", notes="编辑权限")
    @PutMapping(value = "role")
    public ResponseEntity<Result> putPermission(@RequestBody RoleDto roleDto){
        return requestHandler.doPost(
                new PostCallback(){
                    @Override
                    public RequestState doCheck() throws Exception {
                        if (roleDto.getRolePermissions() == null || roleDto.getRolePermissions().size() == 0){
                            return RequestState.PARAM_PERMISSION_NULL;
                        }
                        return RequestState.SUCCESS;
                    }
                    @Override
                    public RequestState invoke() throws Exception {
                        List<RolePermissionDto> rolePermissionDtos = roleDto.getRolePermissions();
                        for (RolePermissionDto rolePermission:rolePermissionDtos) {
                            rolePermission.setValue(PermissionHelper.getPermissionByMap(rolePermission.getExplication()));
                        }
                        int i = rolePermissionService.modify(rolePermissionDtos);
                        return i > 0? RequestState.SUCCESS : RequestState. DB_INSERT_ERROR;
                    }
                }

        );
    }

    @ApiOperation(value="用户权限复制", notes="用户权限复制")
    @PostMapping(value = "copyRole")
    public ResponseEntity<Result> copyPermission(@RequestBody CopyPermissionDto copyPermissionDto){
        return requestHandler.doPost(
                new PostCallback(){
                    @Override
                    public RequestState doCheck() throws Exception {
                        if (copyPermissionDto.getFrom() == null || copyPermissionDto.getFrom().size() == 0){
                            return RequestState.PARAM_PERMISSION_NULL_ROLE;
                        }
                        if (copyPermissionDto.getTo() == null || copyPermissionDto.getTo().size() == 0){
                            return RequestState.PARAM_PERMISSION_NULL_ROLE;
                        }
                        return RequestState.SUCCESS;
                    }
                    @Override
                    public RequestState invoke() throws Exception {
                        List<RoleDto> fromRoleDtos = new ArrayList<>();
                        RoleDto roleDto;
                        for (UserDto userDto : copyPermissionDto.getFrom()){
                            roleDto = roleService.findRoleSelfByUserId(userDto.getId());
                            if (roleDto != null){
                                fromRoleDtos.add(roleDto);
                            }
                        }

                        List<RoleDto> toRoleDtos = new ArrayList<>();
                        for (UserDto userDto : copyPermissionDto.getTo()){
                            roleDto = roleService.findRoleSelfByUserId(userDto.getId());
                            if (roleDto != null){
                                toRoleDtos.add(roleDto);
                            }else{
                                String name = String.format("Type:(%s);UserId:(%s);DeptId:(%s)","2",userDto.getId(),"0");
                                roleDto = roleService.createNew(name,userDto.getId(),0,(byte)2);
                                int i = roleService.save(roleDto);
                                if (i < 1){
                                    return RequestState.DB_INSERT_ERROR;
                                }
                                toRoleDtos.add(roleDto);
                            }
                        }
                        List<RolePermissionDto> fromRolePermissionList = new ArrayList<>();
                        List<Long> roleIds = new ArrayList<>();
                        for (RoleDto from:fromRoleDtos) {
                            roleIds.add(from.getId());
                            fromRolePermissionList.addAll(rolePermissionService.findPermissionByRoleId(from.getId()));
                        }

                        List<RolePermissionDto> toRolePermissionList;
                        for (RoleDto to:toRoleDtos){
                            long memberId = getMemberId(to);
                            String memberName = getMemberName(to);

                            toRolePermissionList = rolePermissionService.findPermissionByRoleId(to.getId());
                            for (RolePermissionDto fromPermission : fromRolePermissionList){
                                //这里如何确认权限本身对同一资源没有冲突
                                fromPermission.setRoleId(to.getId());
                                fromPermission.setType(to.getType());
                                fromPermission.setMemberId(memberId);
                                fromPermission.setMemberName(memberName);
                                toRolePermissionList.add(fromPermission);
                            }
                            int i =rolePermissionService.deleteByRoleId(to.getId());
                            if (i == 0){ return RequestState.DB_UPDATE_ERROR;}
                            i = rolePermissionService.saveAll(toRolePermissionList);
                            if (i == 0){ return RequestState.DB_UPDATE_ERROR;}
                            if (copyPermissionDto.getIsMove() == 1){
                                //是移动的情况下，直接删除来源权限
                                i = roleService.delete(roleIds);
                                if (i == 0){ return RequestState.DB_UPDATE_ERROR;}
                            }
                        }
                        return RequestState.SUCCESS;
                    }
                }

        );
    }

    @ApiOperation(value="删除权限", notes="删除权限")
    @DeleteMapping(value = "role")
    public ResponseEntity<Result> deletePermission(@RequestBody RoleDto roleDto){
        return requestHandler.doPost(
                new PostCallback(){
                    @Override
                    public RequestState doCheck() throws Exception {
                        return RequestState.SUCCESS;
                    }
                    @Override
                    public RequestState invoke() throws Exception {
                        List<Long> ids = ReflectUtils.reflectDataList(roleDto.getRolePermissions(),"id",Long.class);
                        int i = rolePermissionService.delete(ids);
                        return i > 0? RequestState.SUCCESS : RequestState. DB_INSERT_ERROR;
                    }
                }

        );
    }

    private long getMemberId(RoleDto roleDto) throws RoleException {
        switch (roleDto.getType()){
            case 1:
                return roleDto.getDeptId();
            case 2:
            case 3:
                return roleDto.getUserId();
            default:
                throw new RoleException("权限设定没有设定针对的角色",0);
        }
    }

    private String getMemberName(RoleDto roleDto) throws IllegalAccessException, InstantiationException, RoleException {
        switch (roleDto.getType()){
            case 1:
                DepartmentDto departmentDto = departmentService.findById(roleDto.getDeptId());
                return departmentDto.getName();
            case 2:
            case 3:
                UserDto userDto = userService.findById(roleDto.getUserId());
                return userDto.getName();
            default:
                throw new RoleException("权限设定没有设定针对的角色",0);
        }
    }
}
