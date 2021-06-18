package com.sundeinfo.sypglass.apis.role;

import com.sundeinfo.core.mvc.controller.AbstractController;
import com.sundeinfo.core.request.GetCallback;
import com.sundeinfo.core.request.RequestState;
import com.sundeinfo.core.request.result.Result;
import com.sundeinfo.sypglass.component.PermissionAdapter;
import com.sundeinfo.sypglass.define.SypGlass;
import com.sundeinfo.sypglass.dto.RoleDto;
import com.sundeinfo.sypglass.dto.RolePermissionDto;
import com.sundeinfo.sypglass.service.RolePermissionService;
import com.sundeinfo.sypglass.service.RoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api(description = "权限系统API")
@RestController
@RequestMapping("admin")
public class RoleAdaptController extends AbstractController<RoleAdaptController> {

    @Autowired
    private RoleService roleService;

    @Autowired
    private RolePermissionService rolePermissionService;

    @Autowired
    private PermissionAdapter permissionAdapter;

    @ApiOperation(value="依据部门ID查找特定资源的权限列表", notes="依据部门ID查找特定资源的权限列表")
    @PutMapping(value = "dept/{id}/docs/rps")
    public ResponseEntity<Result<Map<String,Map<String,Boolean>>>> getRPByDept(@PathVariable(value = "id") long id, @RequestBody List<String> keys){
        return requestHandler.doGet(
                new GetCallback<Map<String,Map<String,Boolean>>>() {
                    @Override
                    public RequestState doCheck() {
                        return RequestState.SUCCESS;
                    }
                    @Override
                    public Map<String,Map<String,Boolean>> invoke() throws Exception {
                        List<RoleDto> roleDtos  = roleService.findCompleteRolesByDeptId(id);
                        List<RolePermissionDto> rolePermissionDtoList = new ArrayList<>();
                        for (RoleDto roleDto:roleDtos) {
                            rolePermissionDtoList.addAll(rolePermissionService.findPermissionByRoleId(roleDto.getId()));
                        }
                        Map<String,Map<String,Boolean>> permissionMaps = new HashMap<>();
                        for (String key:keys) {
                            permissionMaps.put(key, permissionAdapter.getPermissionMap(key,rolePermissionDtoList));
                        }
                        return permissionMaps;
                    }
                }
        );
    }

    @ApiOperation(value="依据人员ID查找特定资源的权限列表", notes="依据人员ID查找特定资源的权限列表")
    @PutMapping(value = "user/{id}/docs/rps")
    public ResponseEntity<Result<Map<String,Map<String,Boolean>>>> getRPByUser(@PathVariable(value = "id") long id,@RequestBody List<String> keys){
        return requestHandler.doGet(
                new GetCallback<Map<String,Map<String,Boolean>>>() {
                    @Override
                    public RequestState doCheck() {
                        return RequestState.SUCCESS;
                    }
                    @Override
                    public Map<String,Map<String,Boolean>> invoke() throws Exception {
                        List<RoleDto> roleDtos =  roleService.findCompleteRolesByUserId(id);
                        List<RolePermissionDto> rolePermissionDtoList = new ArrayList<>();
                        for (RoleDto roleDto:roleDtos) {
                            rolePermissionDtoList.addAll(rolePermissionService.findPermissionByRoleId(roleDto.getId()));
                        }
                        Map<String,Map<String,Boolean>> permissionMaps = new HashMap<>();
                        for (String key:keys) {
                            permissionMaps.put(key, permissionAdapter.getPermissionMap(key,rolePermissionDtoList));
                        }
                        return permissionMaps;
                    }
                }
        );
    }

    @ApiOperation(value="依据文件夹ID查找特定组织人员的权限列表", notes="依据文件夹ID查找特定组织人员的权限列表")
    @PutMapping(value = "folder/{id}/members/rps")
    public ResponseEntity<Result<Map<String,Map<String,Boolean>>>> getRPByFolder(@PathVariable(value = "id") int id,@RequestBody List<String> keys){
        return requestHandler.doGet(
                new GetCallback<Map<String,Map<String,Boolean>>>() {
                    @Override
                    public RequestState doCheck() {
                        return RequestState.SUCCESS;
                    }
                    @Override
                    public Map<String,Map<String,Boolean>> invoke() throws Exception {
                        Map<String,Map<String,Boolean>> permissionMaps = new HashMap<>();
                        for (String key:keys) {
                            String keySplit[] = key.split("-");
                            List<RoleDto> roleDtos;
                            if(SypGlass.MEMBER_KIND_DEPT.equals(keySplit[0])){
                                roleDtos = roleService.findCompleteRolesByDeptId(Long.parseLong(keySplit[1]));
                            }else{
                                roleDtos = roleService.findCompleteRolesByUserId(Long.parseLong(keySplit[1]));
                            }
                            List<RolePermissionDto> rolePermissionDtoList = new ArrayList<>();
                            for (RoleDto roleDto:roleDtos) {
                                rolePermissionDtoList.addAll(rolePermissionService.findPermissionByRoleId(roleDto.getId()));
                            }
                            permissionMaps.put(key, permissionAdapter.getPermissionMap(SypGlass.RESOURCE_TYPE_FOLDER,id,rolePermissionDtoList));
                        }
                        return permissionMaps;
                    }
                }
        );
    }

    @ApiOperation(value="依据文件ID查找特定组织人员的权限列表", notes="依据文件ID查找特定组织人员的权限列表")
    @PutMapping(value = "file/{id}/members/rps")
    public ResponseEntity<Result<Map<String,Map<String,Boolean>>>> getRPByfile(@PathVariable(value = "id") int id,@RequestBody List<String> keys){
        return requestHandler.doGet(
                new GetCallback<Map<String,Map<String,Boolean>>>() {
                    @Override
                    public RequestState doCheck() {
                        return RequestState.SUCCESS;
                    }
                    @Override
                    public Map<String,Map<String,Boolean>> invoke() throws Exception {
                        Map<String,Map<String,Boolean>> permissionMaps = new HashMap<>();
                        for (String key:keys) {
                            String keySplit[] = key.split("-");
                            List<RoleDto> roleDtos;
                            if(SypGlass.MEMBER_KIND_DEPT.equals(keySplit[0])){
                                roleDtos = roleService.findCompleteRolesByDeptId(Long.parseLong(keySplit[1]));
                            }else{
                                roleDtos = roleService.findCompleteRolesByUserId(Long.parseLong(keySplit[1]));
                            }
                            List<RolePermissionDto> rolePermissionDtoList = new ArrayList<>();
                            for (RoleDto roleDto:roleDtos) {
                                rolePermissionDtoList.addAll(rolePermissionService.findPermissionByRoleId(roleDto.getId()));
                            }
                            permissionMaps.put(key, permissionAdapter.getPermissionMap(SypGlass.RESOURCE_TYPE_FILE,id,rolePermissionDtoList));
                        }
                        return permissionMaps;
                    }
                }
        );
    }
}