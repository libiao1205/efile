package com.sundeinfo.sypglass.define;

public enum  UserStatus {

    UNKNOWN(0,"未知"),

    NORMAL(1,"正常普通用户"),

    LOCK(4,"封禁"),

    LEAVE(8,"已离职"),

    DELETE(9,"已删除");

    private final int code;

    private final String value;

    UserStatus(int code, String value) {
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
