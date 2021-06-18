package com.sundeinfo.sypglass.relay.model.oa;


import lombok.Data;

@Data
public class HrmResource {
    private int id;

    private String loginid;

    private String lastname;

    private int subcompanyid1;

    private int departmentid;

    private String enddate;

    private String lastmoddate;

    public HrmResource(Integer id, String loginid, String lastname, Integer subcompanyid1, Integer departmentid, String enddate, String lastmoddate) {
        this.id = id;
        this.loginid = loginid;
        this.lastname = lastname;
        this.subcompanyid1 = subcompanyid1;
        this.departmentid = departmentid;
        this.enddate = enddate;
        this.lastmoddate = lastmoddate;
    }
}