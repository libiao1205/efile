package com.sundeinfo.sypglass.model;

public class Role {
    private Long id;

    private String name;

    private Long userid;

    private Long deptid;

    private Long positionid;

    private Byte status;

    private Byte type;

    public Role(Long id, String name, Long userid, Long deptid, Long positionid, Byte status, Byte type) {
        this.id = id;
        this.name = name;
        this.userid = userid;
        this.deptid = deptid;
        this.positionid = positionid;
        this.status = status;
        this.type = type;
    }

    public Role() {
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
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

    public Long getPositionid() {
        return positionid;
    }

    public void setPositionid(Long positionid) {
        this.positionid = positionid;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }
}