package com.sundeinfo.security.service.impl;

import com.sundeinfo.core.mvc.service.AbstractService;
import com.sundeinfo.security.domain.PermissionDomain;
import com.sundeinfo.security.service.PermissionDomainService;

import java.util.List;

public class DefaultPermissionService extends AbstractService<DefaultPermissionService,PermissionDomain> implements PermissionDomainService<DefaultPermissionService,PermissionDomain> {
    @Override
    public List<PermissionDomain> findAll() throws InstantiationException, IllegalAccessException, NoSuchFieldException {
        return null;
    }
}


