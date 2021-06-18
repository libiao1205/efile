package com.sundeinfo.sypglass.configuration;


import com.sundeinfo.core.interceptor.AdditionalJacksonAnnotationIntrospector;
import com.sundeinfo.core.log.LogConfigurationSupport;
import com.sundeinfo.security.strategy.SecurityServiceConfigurationSupport;
import com.sundeinfo.security.utility.AuthenticationGetter;
import com.sundeinfo.sypglass.mapper.ActionLogMapper;
import com.sundeinfo.sypglass.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperFactoryBean;

@Configuration
public class CoreBeanRegistry {

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private ActionLogMapper actionLogMapper;

    @Autowired
    private AuthenticationGetter authenticationGetter;


    @Autowired
    private UserService userService;

    @Autowired
    private AdminService adminService;

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private RolePermissionService rolePermissionService;

    @Autowired
    private PermissionService permissionService;

    @Bean
    public Jackson2ObjectMapperFactoryBean jackson2ObjectMapperFactoryBeanOP(){
        Jackson2ObjectMapperFactoryBean jackson2ObjectMapperFactoryBean = new Jackson2ObjectMapperFactoryBean();
        jackson2ObjectMapperFactoryBean.setAnnotationIntrospector(new AdditionalJacksonAnnotationIntrospector());
        return  jackson2ObjectMapperFactoryBean;
    }

    @Bean
    public LogConfigurationSupport logConfigurationSupport(){
        LogConfigurationSupport logConfigurationSupport = new LogConfigurationSupport();
        logConfigurationSupport.setApplicationContext(applicationContext);
        ActionLogService actionLogService = new ActionLogService();
        actionLogService.setActionLogMapper(actionLogMapper);
        actionLogService.setAuthenticationGetter(authenticationGetter);
        logConfigurationSupport.setService(actionLogService);
        return logConfigurationSupport;
    }

    @Bean
    public SecurityServiceConfigurationSupport securityServiceConfigurationSupport(){
        SecurityServiceConfigurationSupport securityServiceConfigurationSupport = new SecurityServiceConfigurationSupport();
        securityServiceConfigurationSupport.setApplicationContext(applicationContext);
        securityServiceConfigurationSupport.setServices(userService, adminService,departmentService,roleService,rolePermissionService,permissionService);
        return securityServiceConfigurationSupport;
    }
}