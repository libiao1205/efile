package com.sundeinfo.sypglass.relay.model.relay;

import java.util.Date;

public class ProcessLog {
    private Long id;

    private Date processdate;

    private String status;

    private String info;

    public ProcessLog(Long id, Date processdate, String status, String info) {
        this.id = id;
        this.processdate = processdate;
        this.status = status;
        this.info = info;
    }

    public ProcessLog() {
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getProcessdate() {
        return processdate;
    }

    public void setProcessdate(Date processdate) {
        this.processdate = processdate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info == null ? null : info.trim();
    }
}