package com.sundeinfo.sypglass.solr.model;

import java.util.Date;

public class SolrHolder {
    private Long id;

    private Long fileid;

    private String name;

    private String saveid;

    private Date lastupdatedate;

    private Byte handle;

    private String path;

    public SolrHolder(Long id, Long fileid, String name, String saveid, Date lastupdatedate, Byte handle, String path) {
        this.id = id;
        this.fileid = fileid;
        this.name = name;
        this.saveid = saveid;
        this.lastupdatedate = lastupdatedate;
        this.handle = handle;
        this.path = path;
    }

    public SolrHolder() {
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getFileid() {
        return fileid;
    }

    public void setFileid(Long fileid) {
        this.fileid = fileid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getSaveid() {
        return saveid;
    }

    public void setSaveid(String saveid) {
        this.saveid = saveid == null ? null : saveid.trim();
    }

    public Date getLastupdatedate() {
        return lastupdatedate;
    }

    public void setLastupdatedate(Date lastupdatedate) {
        this.lastupdatedate = lastupdatedate;
    }

    public Byte getHandle() {
        return handle;
    }

    public void setHandle(Byte handle) {
        this.handle = handle;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path == null ? null : path.trim();
    }
}