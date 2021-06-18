package com.sundeinfo.sypglass.define;

public enum RoleType {


    UNKNOWN(0,"未知"),

    ORGANIZATION(1,"组织部门"),

    USER(2,"用户"),

    MANAGER(3,"部门管理员"),;

    private final int code;

    private final String value;

    RoleType(int code, String value) {
        this.code = code;
        this.value = value;
    }

    public int code() {
        return this.code;
    }


    public String value() {
        return this.value;
    }

}
