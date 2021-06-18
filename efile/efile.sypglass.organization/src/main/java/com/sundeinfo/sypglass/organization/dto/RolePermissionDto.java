package com.sundeinfo.sypglass.organization.dto;

import com.sundeinfo.security.domain.RolePermissionDomain;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Map;

@Data
@EqualsAndHashCode(callSuper = false)
public class RolePermissionDto extends RolePermissionDomain implements Serializable {

    private int inherit = 0;

    private String inheritName;

    public void reckonInheritType(RoleDto role){
        if (role == null ){
            this.inherit = 2;
            this.inheritName = "继承自上级部门";
        }else if(role.getId() != this.getRoleId()){
            this.inherit = 2;
            this.inheritName = "继承自上级部门";
        }else{
            this.inherit = 1;
            this.inheritName = "已设定";
        }
    }

    private String memberName;

    private String resourceName;

    private String resourceSuffix;

    public String fullPath;

    public String fullName;

    private Map<String,Boolean> explication;

}