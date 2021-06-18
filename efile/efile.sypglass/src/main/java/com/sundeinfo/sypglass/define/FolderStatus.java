package com.sundeinfo.sypglass.define;

public enum FolderStatus {

    UNKNOWN(0,"未知"),

    NORMAL(1,"普通"),

    PUBLIC(2,"开放"),

    PRIVATE(3,"私密"),

    LINK(4,"外链"),

    DELETE(9,"已删除");

    private final int code;

    private final String value;

    FolderStatus(int code, String value) {
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
