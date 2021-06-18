package com.sundeinfo.security.service.impl;

import com.sundeinfo.core.mvc.service.AbstractService;
import com.sundeinfo.security.domain.AdminDomain;
import com.sundeinfo.security.service.AdminDomainService;

public class DefaultAdminService extends AbstractService<DefaultAdminService,AdminDomain> implements AdminDomainService<DefaultAdminService,AdminDomain> {
    @Override
    public AdminDomain findByUser(long id) throws InstantiationException, IllegalAccessException, NoSuchFieldException {
        return null;
    }
}
