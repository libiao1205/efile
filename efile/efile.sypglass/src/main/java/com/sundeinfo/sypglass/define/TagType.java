package com.sundeinfo.sypglass.define;

public enum TagType {

    UNKNOWN(0,"未知"),

    SYSTEM(1,"系统标签"),

    USER(2,"用户标签");

    private final int code;

    private final String value;

    TagType(int code, String value) {
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
