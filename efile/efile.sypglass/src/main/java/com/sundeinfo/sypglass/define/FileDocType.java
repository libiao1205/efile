package com.sundeinfo.sypglass.define;

/**
 * 文献类型
 */
public enum FileDocType {
    UNKNOWN(0,"未知"),

    FILE(10,"文件"),

    LITERATURE(20,"文献");

    private final int status;

    private final String value;

    FileDocType(int status, String value) {
        this.status = status;
        this.value = value;
    }

    public int getStatus() {
        return this.status;
    }


    public String value() {
        return this.value;
    }

}
