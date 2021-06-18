package com.sundeinfo.sypglass.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Data
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

}