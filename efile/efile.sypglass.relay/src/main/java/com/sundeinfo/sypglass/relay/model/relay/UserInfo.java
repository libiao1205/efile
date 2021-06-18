package com.sundeinfo.sypglass.relay.model.relay;

import java.util.Date;

public class UserInfo {
    private Long id;

    private Integer oaid;

    private String username;

    private String name;

    private Integer companyid;

    private Integer departmentid;

    private String enddate;

    private String lastmoddate;

    private Date synchrodate;

    public UserInfo(Long id, Integer oaid, String username, String name, Integer companyid, Integer departmentid, String enddate, String lastmoddate, Date synchrodate) {
        this.id = id;
        this.oaid = oaid;
        this.username = username;
        this.name = name;
        this.companyid = companyid;
        this.departmentid = departmentid;
        this.enddate = enddate;
        this.lastmoddate = lastmoddate;
        this.synchrodate = synchrodate;
    }

    public UserInfo() {
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Integer getCompanyid() {
        return companyid;
    }

    public void setCompanyid(Integer companyid) {
        this.companyid = companyid;
    }

    public Integer getDepartmentid() {
        return departmentid;
    }

    public void setDepartmentid(Integer departmentid) {
        this.departmentid = departmentid;
    }

    public String getEnddate() {
        return enddate;
    }

    public void setEnddate(String enddate) {
        this.enddate = enddate == null ? null : enddate.trim();
    }

    public String getLastmoddate() {
        return lastmoddate;
    }

    public void setLastmoddate(String lastmoddate) {
        this.lastmoddate = lastmoddate == null ? null : lastmoddate.trim();
    }

    public Date getSynchrodate() {
        return synchrodate;
    }

    public void setSynchrodate(Date synchrodate) {
        this.synchrodate = synchrodate;
    }
}