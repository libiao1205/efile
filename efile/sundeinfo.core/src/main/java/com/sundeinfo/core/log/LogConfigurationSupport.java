package com.sundeinfo.core.log;

import com.sundeinfo.core.log.impl.DefaultLogService;
import com.sundeinfo.core.mvc.service.Service;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class LogConfigurationSupport implements ApplicationContextAware {

    private ApplicationContext applicationContext;


    private LogService logService;

    public LogConfigurationSupport() {
        logService = new DefaultLogService();
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    public void setService(LogService logService){
        this.logService = logService;
    }

    public LogService getService(){
        return logService;
    }
}
