package com.sundeinfo.sypglass.organization.service;

import com.sundeinfo.core.mvc.service.AbstractService;
import com.sundeinfo.core.utility.ConvertUtils;
import com.sundeinfo.core.utility.ReflectUtils;
import com.sundeinfo.security.service.OrganizationDomainService;
import com.sundeinfo.sypglass.organization.define.DepartmentStatus;
import com.sundeinfo.sypglass.organization.define.SypGlass;
import com.sundeinfo.sypglass.organization.dto.RoleDto;
import com.sundeinfo.sypglass.organization.exception.RoleException;
import com.sundeinfo.sypglass.organization.mapper.DepartmentMapper;
import com.sundeinfo.sypglass.organization.mapper.OrganizationMapper;
import com.sundeinfo.sypglass.organization.mapper.UserDepartmentMapper;
import com.sundeinfo.sypglass.organization.dto.DepartmentDto;
import com.sundeinfo.sypglass.organization.model.Department;
import com.sundeinfo.sypglass.organization.model.DepartmentExample;
import com.sundeinfo.sypglass.organization.model.UserDepartmentExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service("DepartmentService")
public class DepartmentService extends AbstractService<DepartmentService,DepartmentDto> implements OrganizationDomainService<DepartmentService,DepartmentDto> {

    @Autowired
    private DepartmentMapper departmenMapper;

    @Autowired
    private UserDepartmentMapper userDepartmentMapper;

    @Autowired
    private OrganizationMapper organizationMapper;

    @Autowired
    private UserService userService;


    @Override
    public List<DepartmentDto> findOrganizationByUserId(long id) throws IllegalAccessException, InstantiationException,NoSuchFieldException {
        return userService.findUserAllDepartments(id);
    }

    public DepartmentDto findById(long id) throws InstantiationException, IllegalAccessException {
        Department department = departmenMapper.selectByPrimaryKey((Long)id);
        return ConvertUtils.convert(department,DepartmentDto.class);
    }

    public List<DepartmentDto> findByIds(List<Long> ids) throws InstantiationException, IllegalAccessException {
        DepartmentExample example = new DepartmentExample();
        DepartmentExample.Criteria criteria = example.createCriteria();
        criteria.andIdIn(ids);
        List<Department> departments = departmenMapper.selectByExample(example);
        return ConvertUtils.convertList(departments,DepartmentDto.class);
    }

    public List<DepartmentDto> findByIdsWithoutGroupType(List<Long> ids) throws InstantiationException, IllegalAccessException {
        DepartmentExample example = new DepartmentExample();
        DepartmentExample.Criteria criteria = example.createCriteria();
        criteria.andIdIn(ids);
        criteria.andTypeEqualTo(SypGlass.DEPT_TYPE_DEPT);
        List<Department> departments = departmenMapper.selectByExample(example);
        return ConvertUtils.convertList(departments,DepartmentDto.class);
    }

    public List<DepartmentDto> findByType(byte type) throws InstantiationException, IllegalAccessException {
        DepartmentExample example = new DepartmentExample();
        DepartmentExample.Criteria criteria = example.createCriteria();
        criteria.andTypeEqualTo(type);
        List<Department> permissions = departmenMapper.selectByExample(example);
        return ConvertUtils.convertList(permissions,DepartmentDto.class);
    }

    public List<DepartmentDto> findParentListByDeptId(long id) throws InstantiationException, IllegalAccessException {
        List<Department> departments = organizationMapper.selectDeptAndParentsById(id);
        return ConvertUtils.convertList(departments,DepartmentDto.class);
    }

    public List<DepartmentDto> findChildrenByDeptId(long id) throws InstantiationException, IllegalAccessException {
        DepartmentExample example = new DepartmentExample();
        DepartmentExample.Criteria criteria = example.createCriteria();
        criteria.andParentidEqualTo(id);
        List<Department> departments = departmenMapper.selectByExample(example);
        return ConvertUtils.convertList(departments,DepartmentDto.class);
    }

    public List<DepartmentDto> findChildrenListByDeptId(long id) throws InstantiationException, IllegalAccessException {
        if (id == 0 || id == 1){
            DepartmentExample example = new DepartmentExample();
            DepartmentExample.Criteria criteria = example.createCriteria();
            criteria.andIdIsNotNull();
            List<Department> departments = departmenMapper.selectByExample(example);
            return ConvertUtils.convertList(departments,DepartmentDto.class);
        }else{
            List<Department> departments = organizationMapper.selectDeptAndChildrenById(id);
            return ConvertUtils.convertList(departments,DepartmentDto.class);
        }
    }

    public DepartmentDto findChildrenTreeByDeptId(long id) throws IllegalAccessException, InstantiationException, NoSuchFieldException, RoleException {
        List<Department> departments = organizationMapper.selectDeptAndChildrenById(id);
        //找根节点
        Department rootDept = departments.stream().filter(dept-> dept.getId() == SypGlass.ROOT_DEPT_ID).findFirst().get();
        DepartmentDto rootDeptDto = ConvertUtils.convert(rootDept,DepartmentDto.class);
        //加子节点
        List<DepartmentDto> childDepartments = getChildren(departments,rootDeptDto);
        if (childDepartments != null){
            rootDeptDto.setChildren(childDepartments);
        }
        return rootDeptDto;
    }

    private List<DepartmentDto> getChildren(List<Department> departments,DepartmentDto parent) throws InstantiationException, IllegalAccessException, NoSuchFieldException, RoleException {
        List<Department> children = departments.stream().filter(dept-> dept.getParentid() == parent.getId()).collect(Collectors.toList());
        if (children == null || children.size() == 0) return null;
        List<DepartmentDto> childrenDto = new ArrayList<>();
        for (Department child:children) {
            DepartmentDto childDto = ConvertUtils.convert(child,DepartmentDto.class);
            childDto.setParent(parent);
            List<DepartmentDto> childDepartments = getChildren(departments,childDto);
            if (childDepartments != null){
                childDto.setChildren(childDepartments);
            }

            childrenDto.add(childDto);
        }
        return childrenDto;
    }
    /********************************以下数据库读写****************************************/

    @Transactional
    public int save(DepartmentDto departmentDto) throws InstantiationException, IllegalAccessException {
        Department department = ConvertUtils.convert(departmentDto,Department.class);
        departmenMapper.insert(department);
        departmentDto.setId(department.getId());
        return 1;
    }

    @Transactional
    public int mod(DepartmentDto departmentDto) throws InstantiationException, IllegalAccessException {
        Department department = ConvertUtils.convert(departmentDto,Department.class);
        department.setStatus(null);
        DepartmentExample example = new DepartmentExample();
        DepartmentExample.Criteria criteria = example.createCriteria();
        criteria.andIdEqualTo(departmentDto.getId());
        departmenMapper.updateByExampleSelective(department,example);
        return 1;
    }

    @Transactional
    public int blocked(DepartmentDto departmentDto) throws IllegalAccessException, InstantiationException,NoSuchFieldException{
        //封存部门及所有子部门
        Department department = new Department();
        department.setStatus((byte)DepartmentStatus.DELETE.getStatus());

        List<Department> departments = organizationMapper.selectDeptAndChildrenById(departmentDto.getId());
        List<Long> ids = ReflectUtils.reflectDataList(departments,"id",Long.class);
        DepartmentExample example = new DepartmentExample();
        DepartmentExample.Criteria criteria = example.createCriteria();
        criteria.andIdIn(ids);

        return departmenMapper.updateByExampleSelective(department,example);
    }

    @Transactional
    public int delete(long id){
        //删除部门，并且删除对应权限和部门下的员工链接
        int i = 0;
        i = departmenMapper.deleteByPrimaryKey(id);
        if (i == 0){
            return 0;
        }

        List<Long> ids = new ArrayList<Long>();
        ids.add(id);

        UserDepartmentExample example = new UserDepartmentExample();
        UserDepartmentExample.Criteria criteria = example.createCriteria();
        criteria.andDeptidEqualTo(id);
        i = userDepartmentMapper.deleteByExample(example);
        if( i== 0){
            return 0;
        }else{
            return 1;
        }
    }

    public int deleteDepartmentUser(long deptId,long userId){
        UserDepartmentExample example = new UserDepartmentExample();
        UserDepartmentExample.Criteria criteria = example.createCriteria();
        criteria.andDeptidEqualTo(deptId);
        criteria.andUseridEqualTo(userId);
        int i = userDepartmentMapper.deleteByExample(example);
        if( i== 0){
            return 0;
        }else{
            return 1;
        }
    }
}


