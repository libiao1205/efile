package com.sundeinfo.sypglass.relay.define;

public enum RelayStatus {

    RELAY_INITIALISE(0,"OA数据导入准备完毕"),

    RELAY_SUCCESS(1, "导入正常结束"),

    RELAY_ERROR(-1,"导入异常结束"),

    FILE_EXTRACTED(3,"文件提取完毕"),

    FILE_NOT_FOUND(4,"没有找到文件"),

    FILE_UNZIP_ERROR(5,"文件解压失败"),

    FILE_RESTORE_ERROR(6,"文件恢复原始名称失败"),

    FILE_SEND_ERROR(7,"文件上传失败");

    private final int code;

    private final String value;

    RelayStatus(int code, String value) {
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
