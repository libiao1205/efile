package com.sundeinfo.sypglass.relay.model.relay;

import java.util.Date;

public class DepartmentInfo {
    private Long id;

    private Integer oaid;

    private String oakind;

    private String name;

    private String mark;

    private Integer companyid;

    private Integer parentid;

    private Byte canceled;

    private String code;

    private Date synchrodate;

    public DepartmentInfo(Long id, Integer oaid, String oakind, String name, String mark, Integer companyid, Integer parentid, Byte canceled, String code, Date synchrodate) {
        this.id = id;
        this.oaid = oaid;
        this.oakind = oakind;
        this.name = name;
        this.mark = mark;
        this.companyid = companyid;
        this.parentid = parentid;
        this.canceled = canceled;
        this.code = code;
        this.synchrodate = synchrodate;
    }

    public DepartmentInfo() {
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getOaid() {
        return oaid;
    }

    public void setOaid(Integer oaid) {
        this.oaid = oaid;
    }

    public String getOakind() {
        return oakind;
    }

    public void setOakind(String oakind) {
        this.oakind = oakind == null ? null : oakind.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark == null ? null : mark.trim();
    }

    public Integer getCompanyid() {
        return companyid;
    }

    public void setCompanyid(Integer companyid) {
        this.companyid = companyid;
    }

    public Integer getParentid() {
        return parentid;
    }

    public void setParentid(Integer parentid) {
        this.parentid = parentid;
    }

    public Byte getCanceled() {
        return canceled;
    }

    public void setCanceled(Byte canceled) {
        this.canceled = canceled;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    public Date getSynchrodate() {
        return synchrodate;
    }

    public void setSynchrodate(Date synchrodate) {
        this.synchrodate = synchrodate;
    }
}