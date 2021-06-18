package com.sundeinfo.security.utility;

import com.sundeinfo.security.domain.AdminDomain;
import com.sundeinfo.security.domain.RolePermissionDomain;
import com.sundeinfo.security.domain.PermissionDomain;

public class AuthorityCreater {

    public static String getAdminAuthority(AdminDomain admin)
    {
        if (admin != null){
            return "ROLE_ADMIN";
        }
        else{
            return "ROLE_USER";
        }

    }

    public static int getAdminLevel(AdminDomain admin){
        return admin == null? 0 : admin.getLevel();
    }

    public static String getActionAuthority(){
        //权限标识
        return "code:null^action:null^kind:null^function:null^value:null";
    }

    public static String getActionAuthority(PermissionDomain permission)
    {
        //权限标识
        return String.format("code:%s^action:%s^kind:%s^function:%s^value:%s",
                permission.getCode(),
                permission.getAction(),
                permission.getKind(),
                permission.getFunction(),
                permission.getValue());
    }

    public static String getActionAuthority(RolePermissionDomain rolePermission)
    {
        //用户权限标识
        return String.format("code:%s^rid:%s^value:%s",
                rolePermission.getCode(),
                rolePermission.getResourceId(),
                rolePermission.getValue());
    }
}
