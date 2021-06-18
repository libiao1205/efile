package com.sundeinfo.sypglass.organization.dto;

import com.sundeinfo.security.domain.OrganizationDomain;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
public class DepartmentDto extends OrganizationDomain implements Serializable {

    private byte status;

    private byte type;

    private String compName;

    private List<RoleDto> roles;

    private List<DepartmentDto> children;

    private List<Long> parents;

    public DepartmentDto() {
        roles = new ArrayList<>();
        children = new ArrayList<>();
        parents = new ArrayList<>();
    }

    public void setParent(DepartmentDto parent){
        parents.addAll(parent.getParents());
        parents.add(parent.getId());
    }

    public void addChild(DepartmentDto department){
        this.children.add(department);
    }

    public void addRole(RoleDto role){
        this.roles.add(role);
    }
}
