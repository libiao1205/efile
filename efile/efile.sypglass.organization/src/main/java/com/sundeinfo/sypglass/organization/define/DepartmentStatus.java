package com.sundeinfo.sypglass.organization.define;

public enum DepartmentStatus {

    UNKNOWN(0,"未知"),

    NORMAL(1,"普通"),

    DELETE(9,"封存");

    private final int status;

    private final String value;

    DepartmentStatus(int status, String value) {
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
