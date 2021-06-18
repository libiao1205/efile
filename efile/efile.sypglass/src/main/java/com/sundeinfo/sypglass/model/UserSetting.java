package com.sundeinfo.sypglass.model;

public class UserSetting {
    private Long id;

    private Long userid;

    private String kind;

    private String name;

    private Byte status;

    private String setting;

    public UserSetting(Long id, Long userid, String kind, String name, Byte status, String setting) {
        this.id = id;
        this.userid = userid;
        this.kind = kind;
        this.name = name;
        this.status = status;
        this.setting = setting;
    }

    public UserSetting() {
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserid() {
        return userid;
    }

    public void setUserid(Long userid) {
        this.userid = userid;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind == null ? null : kind.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public String getSetting() {
        return setting;
    }

    public void setSetting(String setting) {
        this.setting = setting == null ? null : setting.trim();
    }
}