package com.sundeinfo.sypglass.define;

public enum LinkStatus {
    UNKNOWN(0,"未知"),

    NORMAL(1,"正常"),

    EXPIRE(2,"已过期"),

    DELETE(9,"已删除");

    private final int code;

    private final String value;

    LinkStatus(int code, String value) {
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
