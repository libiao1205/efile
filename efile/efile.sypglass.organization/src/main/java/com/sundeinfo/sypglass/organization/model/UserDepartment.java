package com.sundeinfo.sypglass.organization.model;

public class UserDepartment {
    private Long id;

    private Long userid;

    private Long deptid;

    private Long positionid;

    public UserDepartment(Long id, Long userid, Long deptid, Long positionid) {
        this.id = id;
        this.userid = userid;
        this.deptid = deptid;
        this.positionid = positionid;
    }

    public UserDepartment() {
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

    public Long getPositionid() {
        return positionid;
    }

    public void setPositionid(Long positionid) {
        this.positionid = positionid;
    }
}