package com.sundeinfo.security.service.impl;

import com.sundeinfo.core.mvc.service.AbstractService;
import com.sundeinfo.security.domain.PositionDomain;
import com.sundeinfo.security.domain.RolePermissionDomain;
import com.sundeinfo.security.service.PositionDomainService;
import com.sundeinfo.security.service.RolePermissionDomainService;

import java.util.List;

public class DefaultRolePermissionService extends AbstractService<DefaultRolePermissionService,RolePermissionDomain> implements RolePermissionDomainService<DefaultRolePermissionService,RolePermissionDomain> {

    @Override
    public List<RolePermissionDomain> findPermissionByRoleIds(List<Long> ids) throws InstantiationException, IllegalAccessException, NoSuchFieldException {
        return null;
    }
}


