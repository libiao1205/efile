package com.sundeinfo.sypglass.organization.model;

public class UserJoin {
    private Long id;

    private Long sysid;

    private Long oaid;

    private String name;

    public UserJoin(Long id, Long sysid, Long oaid, String name) {
        this.id = id;
        this.sysid = sysid;
        this.oaid = oaid;
        this.name = name;
    }

    public UserJoin() {
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSysid() {
        return sysid;
    }

    public void setSysid(Long sysid) {
        this.sysid = sysid;
    }

    public Long getOaid() {
        return oaid;
    }

    public void setOaid(Long oaid) {
        this.oaid = oaid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }
}