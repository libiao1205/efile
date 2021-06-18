package com.sundeinfo.sypglass.define;

public enum SortType {

    UP(-1,"向上一位"),

    DOWN(1,"向下一位");

    private final int code;

    private final String value;

    SortType(int code, String value) {
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
