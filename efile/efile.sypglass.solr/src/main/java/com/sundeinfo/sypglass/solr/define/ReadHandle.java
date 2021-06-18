package com.sundeinfo.sypglass.solr.define;

public enum ReadHandle {

    HANDLE_ERROR(-1,"SOLR导入执行异常"),

    HANDLE_SUCCESS(0,"SOLR导入正常结束"),

    HANDLE_NO_PROCESS(9, "没有处理全文");

    private final int code;

    private final String value;

    ReadHandle(int code, String value) {
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
