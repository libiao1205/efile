package com.sundeinfo.core.mvc.service;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component("ServiceStore")
public class ServiceStore implements InitializingBean, ApplicationContextAware {
    private ApplicationContext applicationContext;
    private Map<String, Service> map;

    @Override
    public void afterPropertiesSet() throws Exception {
        map = applicationContext.getBeansOfType(Service.class,true,true);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    public Map<String, Service> getService()
    {
        return map;
    }
}
