package com.sundeinfo.sypglass.relay.model.relay;

import java.util.Date;

public class RelayProcessPool {
    private Long id;

    private Integer requestid;

    private Integer workflowid;

    private String requestname;

    private String creater;

    private String createdate;

    private String createtime;

    private String lastoperator;

    private String lastoperatedate;

    private String lastoperatetime;

    private Byte deleted;

    private String formcode;

    private Long formid;

    private String requestdate;

    private Long comp;

    private Long dept;

    private Long leader;

    private String additional1;

    private String additional2;

    private String additional3;

    private Long docimageid;

    private Long docid;

    private Long imagefileid;

    private String imagefilename;

    private String imagefiletype;

    private Integer versionid;

    private String filerealpath;

    private String iszip;

    private String filesize;

    private Date relaydate;

    private Byte relaystatus;

    private String relayfilepath;

    private String relayinfo;

    public RelayProcessPool(Long id, Integer requestid, Integer workflowid, String requestname, String creater, String createdate, String createtime, String lastoperator, String lastoperatedate, String lastoperatetime, Byte deleted, String formcode, Long formid, String requestdate, Long comp, Long dept, Long leader, String additional1, String additional2, String additional3, Long docimageid, Long docid, Long imagefileid, String imagefilename, String imagefiletype, Integer versionid, String filerealpath, String iszip, String filesize, Date relaydate, Byte relaystatus, String relayfilepath, String relayinfo) {
        this.id = id;
        this.requestid = requestid;
        this.workflowid = workflowid;
        this.requestname = requestname;
        this.creater = creater;
        this.createdate = createdate;
        this.createtime = createtime;
        this.lastoperator = lastoperator;
        this.lastoperatedate = lastoperatedate;
        this.lastoperatetime = lastoperatetime;
        this.deleted = deleted;
        this.formcode = formcode;
        this.formid = formid;
        this.requestdate = requestdate;
        this.comp = comp;
        this.dept = dept;
        this.leader = leader;
        this.additional1 = additional1;
        this.additional2 = additional2;
        this.additional3 = additional3;
        this.docimageid = docimageid;
        this.docid = docid;
        this.imagefileid = imagefileid;
        this.imagefilename = imagefilename;
        this.imagefiletype = imagefiletype;
        this.versionid = versionid;
        this.filerealpath = filerealpath;
        this.iszip = iszip;
        this.filesize = filesize;
        this.relaydate = relaydate;
        this.relaystatus = relaystatus;
        this.relayfilepath = relayfilepath;
        this.relayinfo = relayinfo;
    }

    public RelayProcessPool() {
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getRequestid() {
        return requestid;
    }

    public void setRequestid(Integer requestid) {
        this.requestid = requestid;
    }

    public Integer getWorkflowid() {
        return workflowid;
    }

    public void setWorkflowid(Integer workflowid) {
        this.workflowid = workflowid;
    }

    public String getRequestname() {
        return requestname;
    }

    public void setRequestname(String requestname) {
        this.requestname = requestname == null ? null : requestname.trim();
    }

    public String getCreater() {
        return creater;
    }

    public void setCreater(String creater) {
        this.creater = creater == null ? null : creater.trim();
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

    public String getLastoperator() {
        return lastoperator;
    }

    public void setLastoperator(String lastoperator) {
        this.lastoperator = lastoperator == null ? null : lastoperator.trim();
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

    public String getFormcode() {
        return formcode;
    }

    public void setFormcode(String formcode) {
        this.formcode = formcode == null ? null : formcode.trim();
    }

    public Long getFormid() {
        return formid;
    }

    public void setFormid(Long formid) {
        this.formid = formid;
    }

    public String getRequestdate() {
        return requestdate;
    }

    public void setRequestdate(String requestdate) {
        this.requestdate = requestdate == null ? null : requestdate.trim();
    }

    public Long getComp() {
        return comp;
    }

    public void setComp(Long comp) {
        this.comp = comp;
    }

    public Long getDept() {
        return dept;
    }

    public void setDept(Long dept) {
        this.dept = dept;
    }

    public Long getLeader() {
        return leader;
    }

    public void setLeader(Long leader) {
        this.leader = leader;
    }

    public String getAdditional1() {
        return additional1;
    }

    public void setAdditional1(String additional1) {
        this.additional1 = additional1 == null ? null : additional1.trim();
    }

    public String getAdditional2() {
        return additional2;
    }

    public void setAdditional2(String additional2) {
        this.additional2 = additional2 == null ? null : additional2.trim();
    }

    public String getAdditional3() {
        return additional3;
    }

    public void setAdditional3(String additional3) {
        this.additional3 = additional3 == null ? null : additional3.trim();
    }

    public Long getDocimageid() {
        return docimageid;
    }

    public void setDocimageid(Long docimageid) {
        this.docimageid = docimageid;
    }

    public Long getDocid() {
        return docid;
    }

    public void setDocid(Long docid) {
        this.docid = docid;
    }

    public Long getImagefileid() {
        return imagefileid;
    }

    public void setImagefileid(Long imagefileid) {
        this.imagefileid = imagefileid;
    }

    public String getImagefilename() {
        return imagefilename;
    }

    public void setImagefilename(String imagefilename) {
        this.imagefilename = imagefilename == null ? null : imagefilename.trim();
    }

    public String getImagefiletype() {
        return imagefiletype;
    }

    public void setImagefiletype(String imagefiletype) {
        this.imagefiletype = imagefiletype == null ? null : imagefiletype.trim();
    }

    public Integer getVersionid() {
        return versionid;
    }

    public void setVersionid(Integer versionid) {
        this.versionid = versionid;
    }

    public String getFilerealpath() {
        return filerealpath;
    }

    public void setFilerealpath(String filerealpath) {
        this.filerealpath = filerealpath == null ? null : filerealpath.trim();
    }

    public String getIszip() {
        return iszip;
    }

    public void setIszip(String iszip) {
        this.iszip = iszip == null ? null : iszip.trim();
    }

    public String getFilesize() {
        return filesize;
    }

    public void setFilesize(String filesize) {
        this.filesize = filesize == null ? null : filesize.trim();
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

    public String getRelayfilepath() {
        return relayfilepath;
    }

    public void setRelayfilepath(String relayfilepath) {
        this.relayfilepath = relayfilepath == null ? null : relayfilepath.trim();
    }

    public String getRelayinfo() {
        return relayinfo;
    }

    public void setRelayinfo(String relayinfo) {
        this.relayinfo = relayinfo == null ? null : relayinfo.trim();
    }
}