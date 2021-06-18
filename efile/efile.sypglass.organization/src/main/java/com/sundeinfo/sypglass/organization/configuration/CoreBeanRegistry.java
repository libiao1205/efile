package com.sundeinfo.sypglass.organization.configuration;

import com.fasterxml.jackson.databind.ser.std.StdKeySerializers;
import com.sundeinfo.core.log.LogConfigurationSupport;
import com.sundeinfo.security.service.AdminDomainService;
import com.sundeinfo.security.service.PermissionDomainService;
import com.sundeinfo.security.service.RoleDomainService;
import com.sundeinfo.security.service.RolePermissionDomainService;
import com.sundeinfo.security.service.impl.DefaultAdminService;
import com.sundeinfo.security.service.impl.DefaultPermissionService;
import com.sundeinfo.security.service.impl.DefaultRolePermissionService;
import com.sundeinfo.security.service.impl.DefaultRoleService;
import com.sundeinfo.security.strategy.SecurityServiceConfigurationSupport;
import com.sundeinfo.sypglass.organization.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CoreBeanRegistry {

    @Autowired
    private ApplicationContext applicationContext;

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
    public LogConfigurationSupport logConfigurationSupport(){
        LogConfigurationSupport logConfigurationSupport = new LogConfigurationSupport();
        logConfigurationSupport.setApplicationContext(applicationContext);
        return logConfigurationSupport;
    }

    @Bean
    public SecurityServiceConfigurationSupport securityServiceConfigurationSupport(){
        SecurityServiceConfigurationSupport securityServiceConfigurationSupport = new SecurityServiceConfigurationSupport();
        securityServiceConfigurationSupport.setApplicationContext(applicationContext);


        securityServiceConfigurationSupport.setServices(userService,adminService,departmentService,roleService,rolePermissionService,permissionService);
        return securityServiceConfigurationSupport;
    }
}