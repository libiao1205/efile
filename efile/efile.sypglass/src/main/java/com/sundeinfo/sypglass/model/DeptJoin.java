package com.sundeinfo.sypglass.model;

public class DeptJoin {
    private Long id;

    private Long sysid;

    private Long oacompid;

    private Long oadeptid;

    public DeptJoin(Long id, Long sysid, Long oacompid, Long oadeptid) {
        this.id = id;
        this.sysid = sysid;
        this.oacompid = oacompid;
        this.oadeptid = oadeptid;
    }

    public DeptJoin() {
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

    public Long getOacompid() {
        return oacompid;
    }

    public void setOacompid(Long oacompid) {
        this.oacompid = oacompid;
    }

    public Long getOadeptid() {
        return oadeptid;
    }

    public void setOadeptid(Long oadeptid) {
        this.oadeptid = oadeptid;
    }
}