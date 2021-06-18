package com.sundeinfo.sypglass.organization.dto;

import com.sundeinfo.security.domain.RoleDomain;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
public class RoleDto extends RoleDomain implements Serializable {

    private String name;

    private long deptId;

    private byte status;

    private byte type;

    private List<RolePermissionDto> rolePermissions;

    public void setOrganizationId(long organizationId) {
        super.setOrganizationId(organizationId);
        this.deptId = organizationId;
    }

    public void setDeptId(long deptId) {
        this.setOrganizationId(deptId);
        this.deptId = deptId;
    }

    public void addRolePermission(RolePermissionDto rolePermission){
        this.rolePermissions.add(rolePermission);
    }

}
