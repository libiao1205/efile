package com.sundeinfo.security.service;

import com.sundeinfo.core.mvc.service.Service;
import com.sundeinfo.security.domain.OrganizationDomain;

import java.util.List;

public interface OrganizationDomainService<T,D> extends Service<T,D> {

    List<D> findOrganizationByUserId(long id) throws IllegalAccessException, InstantiationException,NoSuchFieldException;

}
