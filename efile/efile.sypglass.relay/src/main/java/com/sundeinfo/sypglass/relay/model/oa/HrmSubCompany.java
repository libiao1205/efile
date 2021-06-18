package com.sundeinfo.sypglass.relay.model.oa;

import lombok.Data;

@Data
public class HrmSubCompany {

    private int id;

    private String subcompanyname;

    private String subcompanydesc;

    private int companyid;

    private int supsubcomid;

    private String canceled;

    private String subcompanycode;

    public HrmSubCompany(Integer id, String subcompanyname, String subcompanydesc, Integer companyid, Integer supsubcomid, String canceled, String subcompanycode) {
        this.id = id;
        this.subcompanyname = subcompanyname;
        this.subcompanydesc = subcompanydesc;
        this.companyid = companyid;
        this.supsubcomid = supsubcomid;
        this.canceled = canceled;
        this.subcompanycode = subcompanycode;
    }
}
