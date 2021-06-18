package com.sundeinfo.security.strategy;

import com.sundeinfo.core.mvc.service.AbstractService;
import com.sundeinfo.core.utility.ReflectUtils;
import com.sundeinfo.security.domain.*;
import com.sundeinfo.security.model.*;
import com.sundeinfo.security.service.*;
import com.sundeinfo.security.utility.AuthorityCreater;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AnyUserService extends AbstractService<AnyUserService,AnyUser> implements UserDetailsService {

    private UserDomainService userService;

    private AdminDomainService adminService;

    private OrganizationDomainService organizationService;

    private RoleDomainService roleService;

    private RolePermissionDomainService rolePermissionService;

    public void setServices(UserDomainService userService,
                            AdminDomainService adminService,
                            OrganizationDomainService organizationService,
                            RoleDomainService roleService,
                            RolePermissionDomainService rolePermissionService){
        this.userService = userService;
        this.adminService = adminService;
        this.organizationService = organizationService;
        this.roleService = roleService;
        this.rolePermissionService = rolePermissionService;
    }

    public UserDetails loadUserByUsername(String username){
        return getAnyUser(username);
    }

    public AnyUser getAnyUser(String username){
        try{
            UserDomain user = (UserDomain)userService.findByUsername(username);
            if(user == null) {
                throw new UsernameNotFoundException("用户名不存在");
            }
            AdminDomain admin = (AdminDomain)adminService.findByUser(user.getId());
            List<OrganizationDomain> organizations = organizationService.findOrganizationByUserId(user.getId());
            List<RoleDomain> roles = new ArrayList<>();
            List<RolePermissionDomain> rolePermissions = new ArrayList<>();
            List<GeneralUserAuthority> authorities = new ArrayList<>();

            if (organizations.size() > 0){
                roles = roleService.findCompleteRolesByUserId(user.getId());
            }
            if (roles.size() > 0){
                List<Long> roleIds = ReflectUtils.reflectDataList(roles,"id",Long.class);
                rolePermissions = rolePermissionService.findPermissionByRoleIds(roleIds);
            }
            if (admin != null) {
                authorities.add(new GeneralUserAuthority(AuthorityCreater.getAdminAuthority(admin)));
            }
            for (RolePermissionDomain rolePermission:rolePermissions) {
                authorities.add(new GeneralUserAuthority(AuthorityCreater.getActionAuthority(rolePermission),rolePermission));
            }
            AnyUser anyUser = new AnyUser(user,authorities);
            anyUser.setAdmin(admin);
            anyUser.setOrganizations(organizations);
            anyUser.setRoles(roles);
            anyUser.setRolePermissions(rolePermissions);
            logger.debug("AnyUser:" + anyUser.toString());
            return anyUser;
        } catch (NoSuchFieldException | IllegalAccessException | InstantiationException e){
            logger.error("反射对象属性时发生异常",e);
            throw new RuntimeException("反射对象属性时发生异常");
        }
    }


}
