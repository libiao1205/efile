package com.sundeinfo.security.service;

import com.sundeinfo.core.mvc.service.Service;
import com.sundeinfo.security.domain.AdminDomain;

public interface AdminDomainService<T,D> extends Service<T,D> {

    D findByUser(long id) throws InstantiationException,IllegalAccessException,NoSuchFieldException;

}
