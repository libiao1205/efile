package com.sundeinfo.security.strategy;

import com.sundeinfo.security.service.*;
import com.sundeinfo.security.service.impl.*;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class SecurityServiceConfigurationSupport implements ApplicationContextAware {

    private ApplicationContext applicationContext;

    private AnyUserService anyUserService;

    private GeneralMetadataSourceService generalMetadataSourceService;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    public void setServices(){
        anyUserService = (AnyUserService)applicationContext.getBean("anyUserService");
        anyUserService.setServices(new DefaultUserService(),
                new DefaultAdminService(),
                new DefaultOrganizationService(),
                new DefaultRoleService(),
                new DefaultRolePermissionService());
        generalMetadataSourceService = (GeneralMetadataSourceService)applicationContext.getBean("generalMetadataSourceService");
        generalMetadataSourceService.setServices(new DefaultPermissionService());
    }

    public void setServices(UserDomainService userService,
                            AdminDomainService adminService,
                            OrganizationDomainService organizationService,
                            RoleDomainService roleService,
                            RolePermissionDomainService rolePermissionService,
                            PermissionDomainService permissionDomainService){
        anyUserService = (AnyUserService)applicationContext.getBean("anyUserService");
        anyUserService.setServices(userService, adminService, organizationService, roleService, rolePermissionService);
        generalMetadataSourceService = (GeneralMetadataSourceService)applicationContext.getBean("generalMetadataSourceService");
        generalMetadataSourceService.setServices(permissionDomainService);
    }

}
