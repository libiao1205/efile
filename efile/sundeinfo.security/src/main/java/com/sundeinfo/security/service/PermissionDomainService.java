package com.sundeinfo.security.service;

import com.sundeinfo.core.mvc.service.Service;
import com.sundeinfo.security.domain.PermissionDomain;

import java.util.List;

public interface PermissionDomainService<T,D> extends Service<T,D> {

    List<D> findAll() throws InstantiationException,IllegalAccessException,NoSuchFieldException;
}
