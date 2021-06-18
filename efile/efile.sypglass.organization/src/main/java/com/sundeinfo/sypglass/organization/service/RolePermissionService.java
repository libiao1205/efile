package com.sundeinfo.sypglass.organization.service;

import com.sundeinfo.core.mvc.service.AbstractService;
import com.sundeinfo.security.service.RolePermissionDomainService;
import com.sundeinfo.sypglass.organization.dto.RolePermissionDto;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("RolePermissionService")
public class RolePermissionService extends AbstractService<RolePermissionService,RolePermissionDto> implements RolePermissionDomainService<RolePermissionService,RolePermissionDto> {
    @Override
    public List<RolePermissionDto> findPermissionByRoleIds(List<Long> ids){
        return new ArrayList<>();
    }
}
