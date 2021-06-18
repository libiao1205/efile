package com.sundeinfo.sypglass.solr.configuration;

import com.sundeinfo.core.log.LogConfigurationSupport;
import com.sundeinfo.security.strategy.SecurityServiceConfigurationSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CoreBeanRegistry {

    @Autowired
    private ApplicationContext applicationContext;

    @Bean
    public LogConfigurationSupport logConfigurationSupport(){
        LogConfigurationSupport logConfigurationSupport = new LogConfigurationSupport();
        logConfigurationSupport.setApplicationContext(applicationContext);
        return logConfigurationSupport;
    }

    @Bean
    public SecurityServiceConfigurationSupport securityServiceConfigurationSupport(){
        SecurityServiceConfigurationSupport securityServiceConfigurationSupport = new SecurityServiceConfigurationSupport();
        securityServiceConfigurationSupport.setApplicationContext(applicationContext);
        securityServiceConfigurationSupport.setServices();
        return securityServiceConfigurationSupport;
    }
}