package com.sundeinfo.sypglass.define;

public enum AdminFolderStatus {

    NORMAL(0,"正常"),

    DELETE(9,"文件夹已被删除");

    private final int code;

    private final String value;

    AdminFolderStatus(int code, String value) {
        this.code = code;
        this.value = value;
    }

    public int getCode() {
        return this.code;
    }


    public String value() {
        return this.value;
    }
}
