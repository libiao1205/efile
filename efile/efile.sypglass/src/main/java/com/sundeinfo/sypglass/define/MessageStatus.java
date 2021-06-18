package com.sundeinfo.sypglass.define;

public enum MessageStatus {

    UNKNOWN(0,"未知"),

    NEW(1,"新消息"),

    READ(2,"已读"),

    FILTER(3,"已过滤"),

    DELETE(4,"已删除");

    private final int code;

    private final String value;

    MessageStatus(int code, String value) {
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
