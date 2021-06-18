package com.sundeinfo.security.service.impl;

import com.sundeinfo.core.mvc.service.AbstractService;
import com.sundeinfo.security.domain.PositionDomain;
import com.sundeinfo.security.model.AnyResource;
import com.sundeinfo.security.service.PositionDomainService;
import com.sundeinfo.security.service.ResourceDomainService;

public class DefaultResourceService extends AbstractService<ResourceDomainService,AnyResource> implements ResourceDomainService{

    @Override
    public AnyResource getResourceById(String kind, long id) throws InstantiationException, IllegalAccessException, NoSuchFieldException {
        return null;
    }
}


