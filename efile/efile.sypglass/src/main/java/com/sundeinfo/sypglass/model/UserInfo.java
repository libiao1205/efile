package com.sundeinfo.sypglass.model;

import lombok.Data;

import java.util.Date;

@Data
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

}