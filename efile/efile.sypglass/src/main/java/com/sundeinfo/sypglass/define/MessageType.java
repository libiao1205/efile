package com.sundeinfo.sypglass.define;

public enum MessageType {

    UNKNOWN(0,"未知","有新消息"),

    MODIFY(1,"更新","文件有更新"),

    UPDATE(2, "新版本","文件上传新版本"),

    DELETE(3,"删除", "文件被删除"),

    LINK(4,"外链", "文件发布了外链"),

    DOWNLOAD(5,"下载", "文件被下载了"),

    NEW(6,"新增", "新增文件");

    private final int type;

    private final String value;

    private final String info;

    MessageType(int type, String value,String info) {
        this.type = type;
        this.value = value;
        this.info = info;
    }

    public int type() {
        return this.type;
    }


    public String value() {
        return this.value;
    }

    public String info() {
        return this.info;
    }


}
