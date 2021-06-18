package com.sundeinfo.security.model;

import com.sundeinfo.security.domain.PermissionDomain;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.security.access.SecurityConfig;
import org.springframework.util.Assert;

@Data
@EqualsAndHashCode(callSuper = true)
public class GeneralConfigAttribute extends SecurityConfig {

    private PermissionDomain permission;

    private GeneralConfigAttribute(String config) {
        super(config);
    }

    public GeneralConfigAttribute(String config, PermissionDomain permission) {
        this(config);
        Assert.notNull(permission,"---------------------resource permission is null----------------------");
        this.permission = permission;
    }
}