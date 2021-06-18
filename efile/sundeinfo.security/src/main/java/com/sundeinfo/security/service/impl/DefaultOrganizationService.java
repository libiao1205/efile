package com.sundeinfo.security.service.impl;

import com.sundeinfo.core.mvc.service.AbstractService;
import com.sundeinfo.security.domain.OrganizationDomain;
import com.sundeinfo.security.service.OrganizationDomainService;

import java.util.List;

public class DefaultOrganizationService extends AbstractService<DefaultOrganizationService,OrganizationDomain> implements OrganizationDomainService<DefaultOrganizationService,OrganizationDomain> {
    @Override
    public List<OrganizationDomain> findOrganizationByUserId(long id) throws IllegalAccessException, InstantiationException, NoSuchFieldException {
        return null;
    }
}
