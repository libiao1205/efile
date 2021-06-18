package com.sundeinfo.sypglass.service;

import com.sundeinfo.core.mvc.service.AbstractService;
import com.sundeinfo.core.permission.PermissionHelper;
import com.sundeinfo.core.utility.ConvertUtils;
import com.sundeinfo.security.domain.RolePermissionDomain;
import com.sundeinfo.security.service.PermissionDomainService;
import com.sundeinfo.security.utility.AuthenticationGetter;
import com.sundeinfo.sypglass.define.SypGlass;
import com.sundeinfo.sypglass.dto.FolderDto;
import com.sundeinfo.sypglass.dto.PermissionDto;
import com.sundeinfo.sypglass.dto.RoleDto;
import com.sundeinfo.sypglass.dto.RolePermissionDto;
import com.sundeinfo.sypglass.exception.RoleException;
import com.sundeinfo.sypglass.mapper.PermissionMapper;
import com.sundeinfo.sypglass.mapper.RolePermissionMapper;
import com.sundeinfo.sypglass.model.Permission;
import com.sundeinfo.sypglass.model.PermissionExample;
import com.sundeinfo.sypglass.model.RolePermission;
import com.sundeinfo.sypglass.model.RolePermissionExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service("PermissionService")
public class PermissionService extends AbstractService<PermissionService,PermissionDto> implements PermissionDomainService<PermissionService,PermissionDto> {

    @Autowired
    private PermissionMapper permissionMapper;

    @Override
    public List<PermissionDto> findAll() throws InstantiationException, IllegalAccessException {
        PermissionExample example = new PermissionExample();
        PermissionExample.Criteria criteria = example.createCriteria();
        criteria.andIdIsNotNull();
        List<Permission> permissions = permissionMapper.selectByExample(example);
        return ConvertUtils.convertList(permissions,PermissionDto.class);
    }
}
