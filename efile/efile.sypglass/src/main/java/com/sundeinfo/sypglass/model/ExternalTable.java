package com.sundeinfo.sypglass.model;

import java.util.Date;

public class ExternalTable {
    private Long id;

    private Long dbid;

    private String dbname;

    private String tablename;

    private Long datacount;

    private String createtime;

    private String comment;

    private Date synctime;

    private Long syncuser;

    public ExternalTable(Long id, Long dbid, String dbname, String tablename, Long datacount, String createtime, String comment, Date synctime, Long syncuser) {
        this.id = id;
        this.dbid = dbid;
        this.dbname = dbname;
        this.tablename = tablename;
        this.datacount = datacount;
        this.createtime = createtime;
        this.comment = comment;
        this.synctime = synctime;
        this.syncuser = syncuser;
    }

    public ExternalTable() {
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getDbid() {
        return dbid;
    }

    public void setDbid(Long dbid) {
        this.dbid = dbid;
    }

    public String getDbname() {
        return dbname;
    }

    public void setDbname(String dbname) {
        this.dbname = dbname == null ? null : dbname.trim();
    }

    public String getTablename() {
        return tablename;
    }

    public void setTablename(String tablename) {
        this.tablename = tablename == null ? null : tablename.trim();
    }

    public Long getDatacount() {
        return datacount;
    }

    public void setDatacount(Long datacount) {
        this.datacount = datacount;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime == null ? null : createtime.trim();
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment == null ? null : comment.trim();
    }

    public Date getSynctime() {
        return synctime;
    }

    public void setSynctime(Date synctime) {
        this.synctime = synctime;
    }

    public Long getSyncuser() {
        return syncuser;
    }

    public void setSyncuser(Long syncuser) {
        this.syncuser = syncuser;
    }
}