package com.sundeinfo.sypglass.relay.model.oa;

import lombok.Data;

@Data
public class HrmDepartment {

    private int id;

    private String departmentmark;

    private String departmentname;

    private int subcompanyid1;

    private int supdepid;

    private String canceled;

    private String departmentcode;

    public HrmDepartment(Integer id, String departmentmark, String departmentname, Integer subcompanyid1, Integer supdepid, String canceled, String departmentcode) {
        this.id = id;
        this.departmentmark = departmentmark;
        this.departmentname = departmentname;
        this.subcompanyid1 = subcompanyid1;
        this.supdepid = supdepid;
        this.canceled = canceled;
        this.departmentcode = departmentcode;
    }
}
