package com.sundeinfo.security.service.impl;

import com.sundeinfo.core.mvc.service.AbstractService;
import com.sundeinfo.security.domain.PositionDomain;
import com.sundeinfo.security.domain.UserDomain;
import com.sundeinfo.security.service.PositionDomainService;
import com.sundeinfo.security.service.UserDomainService;

public class DefaultUserService extends AbstractService<DefaultUserService,UserDomain> implements UserDomainService<DefaultUserService,UserDomain> {

    @Override
    public UserDomain findByUsername(String username) throws InstantiationException, IllegalAccessException, NoSuchFieldException {
        return null;
    }

    @Override
    public UserDomain createNewUser() {
        return null;
    }
}


