package com.sundeinfo.core.mvc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component()
@SuppressWarnings(value="unchecked")
public class ServiceManager {

    @Autowired
    private ServiceStore serviceStore;

    public Service findService(Class type) throws Exception {
        Service service = null;
        for(Service serviceimpl : serviceStore.getService().values())
        {
            if(type.isAssignableFrom(serviceimpl.getClass()))
            {
                return serviceimpl;
            }
        }
        throw new Exception("Service is not find");
    }
}