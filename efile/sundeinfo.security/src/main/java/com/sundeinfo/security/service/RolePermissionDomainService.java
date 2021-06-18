package com.sundeinfo.security.service;

import com.sundeinfo.core.mvc.service.Service;

import java.util.List;

public interface RolePermissionDomainService<T,D> extends Service<T,D> {

    List<D> findPermissionByRoleIds(List<Long> ids) throws InstantiationException,IllegalAccessException,NoSuchFieldException;

}
