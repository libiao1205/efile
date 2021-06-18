package com.sundeinfo.sypglass.model;

public class Admin {
    private Long id;

    private Long userid;

    private Long deptid;

    private Byte level;

    private Byte status;

    public Admin(Long id, Long userid, Long deptid, Byte level, Byte status) {
        this.id = id;
        this.userid = userid;
        this.deptid = deptid;
        this.level = level;
        this.status = status;
    }

    public Admin() {
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

    public Long getDeptid() {
        return deptid;
    }

    public void setDeptid(Long deptid) {
        this.deptid = deptid;
    }

    public Byte getLevel() {
        return level;
    }

    public void setLevel(Byte level) {
        this.level = level;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }
}