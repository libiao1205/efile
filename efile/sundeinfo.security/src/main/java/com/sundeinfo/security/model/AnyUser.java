package com.sundeinfo.security.model;

import com.sundeinfo.security.domain.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class AnyUser extends User implements Serializable {

    private long id;

    private String name;

    private UserDomain user;

    private AdminDomain admin;

    private List<OrganizationDomain> organizations;

    private List<RoleDomain> roles;

    private List<RolePermissionDomain> rolePermissions;

    public AnyUser(UserDomain user, Collection<? extends GrantedAuthority> authorities) {
        super(user.getUsername(), user.getPassword(), authorities);
        this.user = user;
        this.name = user.getName();
    }

    //    private Collection<GeneralUserAuthority> anyUserFuncAuthorities;
//
//    public Collection<GeneralUserAuthority> getAnyUserFuncAuthorities() {
//        return anyUserFuncAuthorities;
//    }
//
//    public void setAnyUserFuncAuthorities(Collection<GeneralUserAuthority> anyUserFuncAuthorities) {
//        this.anyUserFuncAuthorities = anyUserFuncAuthorities;
//    }
}
