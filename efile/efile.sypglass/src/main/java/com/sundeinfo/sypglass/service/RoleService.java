package com.sundeinfo.sypglass.service;

import com.sundeinfo.core.mvc.service.AbstractService;
import com.sundeinfo.core.request.RequestState;
import com.sundeinfo.core.utility.ConvertUtils;
import com.sundeinfo.core.utility.ReflectUtils;
import com.sundeinfo.security.service.RoleDomainService;
import com.sundeinfo.sypglass.dto.DepartmentDto;
import com.sundeinfo.sypglass.dto.PositionDto;
import com.sundeinfo.sypglass.dto.RoleDto;
import com.sundeinfo.sypglass.dto.RolePermissionDto;
import com.sundeinfo.sypglass.exception.RoleException;
import com.sundeinfo.sypglass.mapper.RoleMapper;
import com.sundeinfo.sypglass.model.Role;
import com.sundeinfo.sypglass.model.RoleExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service("RoleService")
public class RoleService extends AbstractService<RoleService,RoleDto> implements RoleDomainService<RoleService,RoleDto> {

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private RolePermissionService rolePermissionService;

    @Autowired
    private DepartmentService departmentService;

    public RoleDto createNew(String name,long userId,long deptId,byte type){
        RoleDto roleDto = new RoleDto();
        roleDto.setName(name);
        switch (type){
            case 1:
                roleDto.setDeptId(deptId);
            case 2:
                roleDto.setUserId(userId);
            case 3:
                roleDto.setDeptId(deptId);
                roleDto.setUserId(userId);
        }
        roleDto.setPositionId(0);//目前没有启用职位级别，所以这里固定设置0；
        roleDto.setStatus((byte)1);
        roleDto.setType(type);
        return roleDto;
    }

    public RoleDto findById(long id) throws InstantiationException, IllegalAccessException {
        Role role = roleMapper.selectByPrimaryKey((Long)id);
        return ConvertUtils.convert(role,RoleDto.class);
    }

    public RoleDto findByTypeAndMemberId(byte type,long deptId, long userId) throws IllegalAccessException, RoleException, InstantiationException {
        switch (type){
            case 1:
            case 3:
                return findRoleSelfByDepartmentId(deptId);
            case 2:
                return findRoleSelfByUserId(userId);
            default:
                return null;
        }
    }

    public RoleDto findRoleSelfByDepartmentId(long id) throws IllegalAccessException, InstantiationException, RoleException {
        RoleExample example = new RoleExample();
        RoleExample.Criteria criteria = example.createCriteria();
        criteria.andDeptidEqualTo(id);
        criteria.andUseridEqualTo(0L);
        List<Role> roles = roleMapper.selectByExample(example);
        if (roles == null || roles.size() == 0) return null;
        if (roles.size() > 1) throw new RoleException("角色设定超过一个！",id);
        return ConvertUtils.convert(roles.get(0),RoleDto.class);
    }

    public RoleDto findRoleSelfByUserId(long id) throws IllegalAccessException, InstantiationException, RoleException {
        RoleExample example = new RoleExample();
        RoleExample.Criteria criteria = example.createCriteria();
        criteria.andUseridEqualTo(id);
        List<Role> roles = roleMapper.selectByExample(example);
        if (roles == null || roles.size() == 0) return null;
        if (roles.size() > 1) throw new RoleException("角色设定超过一个！",id);
        return ConvertUtils.convert(roles.get(0),RoleDto.class);
    }

    @Override
    public List<RoleDto> findCompleteRolesByUserId(long id) throws InstantiationException, IllegalAccessException, NoSuchFieldException {
        List<DepartmentDto> userDepartments = departmentService.findOrganizationByUserId(id);
        List<Long> relevantDepartmentIds = new ArrayList<>();
        for (DepartmentDto departmentDto :userDepartments) {
            List<DepartmentDto> parents = departmentService.findParentListByDeptId(departmentDto.getId());
            relevantDepartmentIds.addAll(ReflectUtils.reflectDataList(parents,"id",Long.class));
        }
        RoleExample example = new RoleExample();
        RoleExample.Criteria userCriteria = example.createCriteria();
        userCriteria.andUseridEqualTo(id);

        RoleExample.Criteria deptCriteria = example.createCriteria();
        deptCriteria.andDeptidIn(relevantDepartmentIds);

        example.or(deptCriteria);

        List<Role> roles = roleMapper.selectByExample(example);
        return ConvertUtils.convertList(roles,RoleDto.class);
    }

    public List<RoleDto> findCompleteRolesByDeptId(long id) throws InstantiationException, IllegalAccessException, NoSuchFieldException {

        List<Long> relevantDepartmentIds = new ArrayList<>();
        List<DepartmentDto> parents = departmentService.findParentListByDeptId(id);
        relevantDepartmentIds.addAll(ReflectUtils.reflectDataList(parents,"id",Long.class));

        RoleExample example = new RoleExample();
        RoleExample.Criteria criteria = example.createCriteria();
        criteria.andDeptidIn(relevantDepartmentIds);
        criteria.andUseridEqualTo(0L);
        List<Role> roles = roleMapper.selectByExample(example);
        return ConvertUtils.convertList(roles,RoleDto.class);
    }

    /********************************以下数据库读写****************************************/
    public int save(RoleDto roleDto) throws InstantiationException, IllegalAccessException {
        Role role = ConvertUtils.convert(roleDto,Role.class);
        roleMapper.insert(role);
        roleDto.setId(role.getId());
        return 1;
    }

    public int delete(List<Long> ids) throws IllegalAccessException, NoSuchFieldException, InstantiationException {
        RoleExample example = new RoleExample();
        RoleExample.Criteria criteria = example.createCriteria();
        criteria.andIdIn(ids);
        roleMapper.deleteByExample(example);
        //删除角色时同时删除角色-权限关联
        rolePermissionService.deleteByRoleIds(ids);
        return 1;
    }

    public int deleteByDepartment(List<Long> ids){
        RoleExample example = new RoleExample();
        RoleExample.Criteria criteria = example.createCriteria();
        criteria.andDeptidIn(ids);
        roleMapper.deleteByExample(example);
        //删除角色时同时删除角色-权限关联
        rolePermissionService.deleteByMemberIds((byte)1,ids);
        return 1;
    }

}