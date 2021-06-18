package com.sundeinfo.sypglass.relay.model.relay;

import java.util.Date;

public class RelayRequestBase {
    private Long id;

    private Long requestid;

    private Long workflowid;

    private Integer lastnodeid;

    private String lastnodetype;

    private Integer currentnodeid;

    private String currentnodetype;

    private String status;

    private Integer passedgroups;

    private Integer totalgroups;

    private String requestname;

    private Integer creater;

    private String createdate;

    private String createtime;

    private Integer lastoperator;

    private String lastoperatedate;

    private String lastoperatetime;

    private Byte deleted;

    private Integer creatertype;

    private Integer lastoperatortype;

    private Date relaydate;

    private Byte relaystatus;

    private String relaymd5;

    public RelayRequestBase(Long id, Long requestid, Long workflowid, Integer lastnodeid, String lastnodetype, Integer currentnodeid, String currentnodetype, String status, Integer passedgroups, Integer totalgroups, String requestname, Integer creater, String createdate, String createtime, Integer lastoperator, String lastoperatedate, String lastoperatetime, Byte deleted, Integer creatertype, Integer lastoperatortype, Date relaydate, Byte relaystatus, String relaymd5) {
        this.id = id;
        this.requestid = requestid;
        this.workflowid = workflowid;
        this.lastnodeid = lastnodeid;
        this.lastnodetype = lastnodetype;
        this.currentnodeid = currentnodeid;
        this.currentnodetype = currentnodetype;
        this.status = status;
        this.passedgroups = passedgroups;
        this.totalgroups = totalgroups;
        this.requestname = requestname;
        this.creater = creater;
        this.createdate = createdate;
        this.createtime = createtime;
        this.lastoperator = lastoperator;
        this.lastoperatedate = lastoperatedate;
        this.lastoperatetime = lastoperatetime;
        this.deleted = deleted;
        this.creatertype = creatertype;
        this.lastoperatortype = lastoperatortype;
        this.relaydate = relaydate;
        this.relaystatus = relaystatus;
        this.relaymd5 = relaymd5;
    }

    public RelayRequestBase() {
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRequestid() {
        return requestid;
    }

    public void setRequestid(Long requestid) {
        this.requestid = requestid;
    }

    public Long getWorkflowid() {
        return workflowid;
    }

    public void setWorkflowid(Long workflowid) {
        this.workflowid = workflowid;
    }

    public Integer getLastnodeid() {
        return lastnodeid;
    }

    public void setLastnodeid(Integer lastnodeid) {
        this.lastnodeid = lastnodeid;
    }

    public String getLastnodetype() {
        return lastnodetype;
    }

    public void setLastnodetype(String lastnodetype) {
        this.lastnodetype = lastnodetype == null ? null : lastnodetype.trim();
    }

    public Integer getCurrentnodeid() {
        return currentnodeid;
    }

    public void setCurrentnodeid(Integer currentnodeid) {
        this.currentnodeid = currentnodeid;
    }

    public String getCurrentnodetype() {
        return currentnodetype;
    }

    public void setCurrentnodetype(String currentnodetype) {
        this.currentnodetype = currentnodetype == null ? null : currentnodetype.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public Integer getPassedgroups() {
        return passedgroups;
    }

    public void setPassedgroups(Integer passedgroups) {
        this.passedgroups = passedgroups;
    }

    public Integer getTotalgroups() {
        return totalgroups;
    }

    public void setTotalgroups(Integer totalgroups) {
        this.totalgroups = totalgroups;
    }

    public String getRequestname() {
        return requestname;
    }

    public void setRequestname(String requestname) {
        this.requestname = requestname == null ? null : requestname.trim();
    }

    public Integer getCreater() {
        return creater;
    }

    public void setCreater(Integer creater) {
        this.creater = creater;
    }

    public String getCreatedate() {
        return createdate;
    }

    public void setCreatedate(String createdate) {
        this.createdate = createdate == null ? null : createdate.trim();
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime == null ? null : createtime.trim();
    }

    public Integer getLastoperator() {
        return lastoperator;
    }

    public void setLastoperator(Integer lastoperator) {
        this.lastoperator = lastoperator;
    }

    public String getLastoperatedate() {
        return lastoperatedate;
    }

    public void setLastoperatedate(String lastoperatedate) {
        this.lastoperatedate = lastoperatedate == null ? null : lastoperatedate.trim();
    }

    public String getLastoperatetime() {
        return lastoperatetime;
    }

    public void setLastoperatetime(String lastoperatetime) {
        this.lastoperatetime = lastoperatetime == null ? null : lastoperatetime.trim();
    }

    public Byte getDeleted() {
        return deleted;
    }

    public void setDeleted(Byte deleted) {
        this.deleted = deleted;
    }

    public Integer getCreatertype() {
        return creatertype;
    }

    public void setCreatertype(Integer creatertype) {
        this.creatertype = creatertype;
    }

    public Integer getLastoperatortype() {
        return lastoperatortype;
    }

    public void setLastoperatortype(Integer lastoperatortype) {
        this.lastoperatortype = lastoperatortype;
    }

    public Date getRelaydate() {
        return relaydate;
    }

    public void setRelaydate(Date relaydate) {
        this.relaydate = relaydate;
    }

    public Byte getRelaystatus() {
        return relaystatus;
    }

    public void setRelaystatus(Byte relaystatus) {
        this.relaystatus = relaystatus;
    }

    public String getRelaymd5() {
        return relaymd5;
    }

    public void setRelaymd5(String relaymd5) {
        this.relaymd5 = relaymd5 == null ? null : relaymd5.trim();
    }
}