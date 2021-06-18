package com.sundeinfo.security.service.impl;

import com.sundeinfo.core.mvc.service.AbstractService;
import com.sundeinfo.security.domain.PositionDomain;
import com.sundeinfo.security.domain.RoleDomain;
import com.sundeinfo.security.service.PositionDomainService;
import com.sundeinfo.security.service.RoleDomainService;

import java.util.List;

public class DefaultRoleService extends AbstractService<DefaultRoleService,RoleDomain> implements RoleDomainService<DefaultRoleService,RoleDomain> {

    @Override
    public List<RoleDomain> findCompleteRolesByUserId(long id) throws InstantiationException, IllegalAccessException, NoSuchFieldException {
        return null;
    }
}


