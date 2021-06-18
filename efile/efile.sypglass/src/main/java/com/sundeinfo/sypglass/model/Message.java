package com.sundeinfo.sypglass.model;

import java.util.Date;

public class Message {
    private Long id;

    private Long touser;

    private Date todate;

    private Byte status;

    private Byte type;

    private String name;

    private String info;

    private Long fromuser;

    private String fromusername;

    private Date fromdate;

    private Long resourceid;

    private String resourcetype;

    private String resourcename;

    private String resourcesuffix;

    public Message(Long id, Long touser, Date todate, Byte status, Byte type, String name, String info, Long fromuser, String fromusername, Date fromdate, Long resourceid, String resourcetype, String resourcename, String resourcesuffix) {
        this.id = id;
        this.touser = touser;
        this.todate = todate;
        this.status = status;
        this.type = type;
        this.name = name;
        this.info = info;
        this.fromuser = fromuser;
        this.fromusername = fromusername;
        this.fromdate = fromdate;
        this.resourceid = resourceid;
        this.resourcetype = resourcetype;
        this.resourcename = resourcename;
        this.resourcesuffix = resourcesuffix;
    }

    public Message() {
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTouser() {
        return touser;
    }

    public void setTouser(Long touser) {
        this.touser = touser;
    }

    public Date getTodate() {
        return todate;
    }

    public void setTodate(Date todate) {
        this.todate = todate;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info == null ? null : info.trim();
    }

    public Long getFromuser() {
        return fromuser;
    }

    public void setFromuser(Long fromuser) {
        this.fromuser = fromuser;
    }

    public String getFromusername() {
        return fromusername;
    }

    public void setFromusername(String fromusername) {
        this.fromusername = fromusername == null ? null : fromusername.trim();
    }

    public Date getFromdate() {
        return fromdate;
    }

    public void setFromdate(Date fromdate) {
        this.fromdate = fromdate;
    }

    public Long getResourceid() {
        return resourceid;
    }

    public void setResourceid(Long resourceid) {
        this.resourceid = resourceid;
    }

    public String getResourcetype() {
        return resourcetype;
    }

    public void setResourcetype(String resourcetype) {
        this.resourcetype = resourcetype == null ? null : resourcetype.trim();
    }

    public String getResourcename() {
        return resourcename;
    }

    public void setResourcename(String resourcename) {
        this.resourcename = resourcename == null ? null : resourcename.trim();
    }

    public String getResourcesuffix() {
        return resourcesuffix;
    }

    public void setResourcesuffix(String resourcesuffix) {
        this.resourcesuffix = resourcesuffix == null ? null : resourcesuffix.trim();
    }
}