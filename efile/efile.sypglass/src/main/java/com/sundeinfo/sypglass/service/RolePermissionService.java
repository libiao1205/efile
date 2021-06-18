package com.sundeinfo.sypglass.service;

import com.sundeinfo.core.mvc.service.AbstractService;
import com.sundeinfo.core.permission.FunctionType;
import com.sundeinfo.core.permission.PermissionHelper;
import com.sundeinfo.core.utility.ConvertUtils;
import com.sundeinfo.core.utility.ReflectUtils;
import com.sundeinfo.security.service.RolePermissionDomainService;
import com.sundeinfo.sypglass.define.SypGlass;
import com.sundeinfo.sypglass.dto.FolderDto;
import com.sundeinfo.sypglass.dto.RolePermissionDto;
import com.sundeinfo.sypglass.mapper.RolePermissionMapper;
import com.sundeinfo.sypglass.model.RolePermission;
import com.sundeinfo.sypglass.model.RolePermissionExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("RolePermissionService")
public class RolePermissionService extends AbstractService<RolePermissionService,RolePermissionDto> implements RolePermissionDomainService<RolePermissionService,RolePermissionDto> {

    @Autowired
    private RolePermissionMapper rolePermissionMapper;

//    public List<RolePermissionDto> cloneDefault(List<RolePermissionDto> parents,long newRoleId) throws InstantiationException, IllegalAccessException {
//        List<RolePermissionDto> news = new ArrayList<>();
//        RolePermissionDto rolePermissionDto;
//        for (RolePermissionDto parent: parents) {
//            if (parent.getCanInherit() != 0){
//                rolePermissionDto = ConvertUtils.convert(parent,RolePermissionDto.class);
//                rolePermissionDto.setId(0);
//                rolePermissionDto.setRoleId(newRoleId);
//                rolePermissionDto.setValue(parent.getValue() < PermissionHelper.getDefault()? parent.getValue(): PermissionHelper.getDefault());
//                news.add(rolePermissionDto);
//            }
//        }
//        return news;
//    }

    public List<RolePermissionDto> createNews(List<RolePermissionDto> parents, FolderDto folder){
        List<RolePermissionDto> newRolePermisssions = new ArrayList<>();
        for (RolePermissionDto parent: parents) {
            newRolePermisssions.add(createNew(parent,folder));
        }
        return newRolePermisssions;
    }

    public RolePermissionDto createNew(RolePermissionDto parent,FolderDto folder){
        RolePermissionDto rolePermissionDto = new RolePermissionDto();
        rolePermissionDto.setRoleId(parent.getRoleId());
        rolePermissionDto.setCode(SypGlass.RESOURCE_TYPE_FOLDER);
        rolePermissionDto.setResourceId(folder.getId());
        rolePermissionDto.setValue(parent.getValue());
        rolePermissionDto.setFullPath(folder.getFullPath());
        rolePermissionDto.setFullName(folder.getFullName());
        return rolePermissionDto;
    }

    public RolePermissionDto createNew(long roleId,FolderDto folder){
        RolePermissionDto rolePermissionDto = new RolePermissionDto();
        rolePermissionDto.setRoleId(roleId);
        rolePermissionDto.setCode(SypGlass.RESOURCE_TYPE_FOLDER);
        rolePermissionDto.setResourceId(folder.getId());
        rolePermissionDto.setFullPath(folder.getFullPath());
        rolePermissionDto.setFullName(folder.getFullName());
        return rolePermissionDto;
    }



    @Override
    public List<RolePermissionDto> findPermissionByRoleIds(List<Long> ids) throws InstantiationException, IllegalAccessException, NoSuchFieldException {
        RolePermissionExample example = new RolePermissionExample();
        RolePermissionExample.Criteria criteria = example.createCriteria();
        criteria.andRoleidIn(ids);
        List<RolePermission> rolePermissions = rolePermissionMapper.selectByExample(example);
        return ConvertUtils.convertList(rolePermissions,RolePermissionDto.class);
    }

    public List<RolePermissionDto> findPermissionByRoleId(long id) throws InstantiationException, IllegalAccessException, NoSuchFieldException {
        RolePermissionExample example = new RolePermissionExample();
        RolePermissionExample.Criteria criteria = example.createCriteria();
        criteria.andRoleidEqualTo(id);
        List<RolePermission> rolePermissions = rolePermissionMapper.selectByExample(example);
        return ConvertUtils.convertList(rolePermissions,RolePermissionDto.class);
    }

    public List<RolePermissionDto> findByRoleAndResource(long roleId,String kindCode,long resourceId) throws InstantiationException, IllegalAccessException {
        RolePermissionExample example = new RolePermissionExample();
        RolePermissionExample.Criteria criteria = example.createCriteria();
        criteria.andRoleidEqualTo(roleId);
        criteria.andCodeEqualTo(kindCode);
        criteria.andResourceidEqualTo(resourceId);
        List<RolePermission> rolePermissions = rolePermissionMapper.selectByExample(example);
        return ConvertUtils.convertList(rolePermissions,RolePermissionDto.class);
    }

    public List<RolePermissionDto> findByResource(String kindCode,long id) throws InstantiationException, IllegalAccessException {
        RolePermissionExample example = new RolePermissionExample();
        RolePermissionExample.Criteria criteria = example.createCriteria();
        criteria.andCodeEqualTo(kindCode);
        criteria.andResourceidEqualTo(id);
        //隐藏系统管理员
        criteria.andMemberidNotEqualTo(1L);
        List<RolePermission> rolePermissions = rolePermissionMapper.selectByExample(example);
        return ConvertUtils.convertList(rolePermissions,RolePermissionDto.class);
    }

    public List<RolePermissionDto> findByResourceFullPath(String kindCode,long id) throws InstantiationException, IllegalAccessException {
        RolePermissionExample example = new RolePermissionExample();
        RolePermissionExample.Criteria criteria = example.createCriteria();
        criteria.andCodeEqualTo(kindCode);
        criteria.andFullpathLike("%/" + id + "%");
        List<RolePermission> rolePermissions = rolePermissionMapper.selectByExample(example);
        return ConvertUtils.convertList(rolePermissions,RolePermissionDto.class);
    }

    /********************************以下数据库读写****************************************/

    public int saveAll(List<RolePermissionDto> rolePermissionDtos) throws IllegalAccessException, InstantiationException {
        for(RolePermissionDto rolePermissionDto:rolePermissionDtos) {
            save(rolePermissionDto);
        }
        return 1;
    }

    public int save(RolePermissionDto rolePermissionDto) throws InstantiationException, IllegalAccessException {
        RolePermission rolePermission = ConvertUtils.convert(rolePermissionDto,RolePermission.class);
        rolePermission.setId(null);
        rolePermissionMapper.insert(rolePermission);
        rolePermissionDto.setId(rolePermission.getId());
        return 1;
    }

    public int modify(List<RolePermissionDto> rolePermissionDtos) throws NoSuchFieldException, IllegalAccessException {
        List<Long> ids = ReflectUtils.reflectDataList(rolePermissionDtos,"id",Long.class);
        RolePermission rolePermission = new RolePermission();
        rolePermission.setValue(rolePermissionDtos.get(0).getValue());

        RolePermissionExample example = new RolePermissionExample();
        RolePermissionExample.Criteria criteria = example.createCriteria();
        criteria.andIdIn(ids);

        return rolePermissionMapper.updateByExampleSelective(rolePermission,example);
    }

    public int delete(List<Long> ids){
        RolePermissionExample example = new RolePermissionExample();
        RolePermissionExample.Criteria criteria = example.createCriteria();
        criteria.andIdIn(ids);
        rolePermissionMapper.deleteByExample(example);
        return 1;
    }

    public int deleteByRoleId(Long id){
        RolePermissionExample example = new RolePermissionExample();
        RolePermissionExample.Criteria criteria = example.createCriteria();
        criteria.andRoleidEqualTo(id);
        rolePermissionMapper.deleteByExample(example);
        return 1;
    }

    public int deleteByRoleIds(List<Long> ids){
        RolePermissionExample example = new RolePermissionExample();
        RolePermissionExample.Criteria criteria = example.createCriteria();
        criteria.andRoleidIn(ids);
        rolePermissionMapper.deleteByExample(example);
        return 1;
    }

    public int deleteByMemberIds(byte type,List<Long> ids){
        RolePermissionExample example = new RolePermissionExample();
        RolePermissionExample.Criteria criteria = example.createCriteria();
        criteria.andTypeEqualTo(type);
        criteria.andMemberidIn(ids);
        rolePermissionMapper.deleteByExample(example);
        return 1;
    }

}
