package com.sundeinfo.security.service;

import com.sundeinfo.core.mvc.service.Service;
import com.sundeinfo.security.domain.RoleDomain;
import com.sundeinfo.security.domain.RolePermissionDomain;

import java.util.List;

public interface RoleDomainService<T,D> extends Service<T,D> {

    List<D> findCompleteRolesByUserId(long id) throws InstantiationException,IllegalAccessException,NoSuchFieldException;


}
