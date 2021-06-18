package com.sundeinfo.security.service;

import com.sundeinfo.core.mvc.service.Service;

public interface UserDomainService<T,D> extends Service<T,D> {

    D findByUsername(String username)  throws InstantiationException,IllegalAccessException,NoSuchFieldException;

    D createNewUser();
}
