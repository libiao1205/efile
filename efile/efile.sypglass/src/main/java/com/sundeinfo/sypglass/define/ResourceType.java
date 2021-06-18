package com.sundeinfo.sypglass.define;

public enum ResourceType {

    UNKNOWN(0,"未知"),

    FOLDER(1,"文件夹"),

    FILE(2,"文件"),

    DOCUMENT(3,"文件夹/文件"),

    TREE_FOLDER(4,"文件夹树"),

    TREE_FILE(5,"文件树"),

    TREE_DOCUMENT(6,"文件夹/文件树"),

    LIST_FOLDER(7,"文件夹列表"),

    LIST_FILE(8,"文件列表"),

    LIST_DOCUMENT(9,"文件夹/文件列表"),

    PAGE_FOLDER(10,"文件夹分页"),

    PAGE_FILE(11,"文件分页"),

    PAGE_DOCUMENT(12,"文件夹/文件分页");

    private final int code;

    private final String value;

    ResourceType(int code, String value) {
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
