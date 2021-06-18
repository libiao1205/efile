package com.sundeinfo.sypglass.relay.model.relay;

import java.util.Date;

public class ProcessStatus {
    private Long id;

    private String code;

    private Date presuccessdate;

    private Date predate;

    private Byte status;

    public ProcessStatus(Long id, String code, Date presuccessdate, Date predate, Byte status) {
        this.id = id;
        this.code = code;
        this.presuccessdate = presuccessdate;
        this.predate = predate;
        this.status = status;
    }

    public ProcessStatus() {
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    public Date getPresuccessdate() {
        return presuccessdate;
    }

    public void setPresuccessdate(Date presuccessdate) {
        this.presuccessdate = presuccessdate;
    }

    public Date getPredate() {
        return predate;
    }

    public void setPredate(Date predate) {
        this.predate = predate;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }
}