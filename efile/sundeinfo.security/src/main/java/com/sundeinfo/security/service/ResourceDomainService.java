package com.sundeinfo.security.service;

import com.sundeinfo.core.mvc.service.Service;
import com.sundeinfo.security.model.AnyResource;

public interface ResourceDomainService extends Service<ResourceDomainService,AnyResource> {

    AnyResource getResourceById(String kind, long id) throws InstantiationException,IllegalAccessException,NoSuchFieldException;

}