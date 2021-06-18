package com.sundeinfo.sypglass.model;

import java.util.Date;

public class ExternalColumn {
    private Long id;

    private Long dbid;

    private String dbname;

    private Long tableid;

    private String tablename;

    private String columnname;

    private Integer seq;

    private String datatype;

    private String datatypevalue;

    private String comment;

    private Date synctime;

    private Long syncuser;

    public ExternalColumn(Long id, Long dbid, String dbname, Long tableid, String tablename, String columnname, Integer seq, String datatype, String datatypevalue, String comment, Date synctime, Long syncuser) {
        this.id = id;
        this.dbid = dbid;
        this.dbname = dbname;
        this.tableid = tableid;
        this.tablename = tablename;
        this.columnname = columnname;
        this.seq = seq;
        this.datatype = datatype;
        this.datatypevalue = datatypevalue;
        this.comment = comment;
        this.synctime = synctime;
        this.syncuser = syncuser;
    }

    public ExternalColumn() {
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

    public Long getTableid() {
        return tableid;
    }

    public void setTableid(Long tableid) {
        this.tableid = tableid;
    }

    public String getTablename() {
        return tablename;
    }

    public void setTablename(String tablename) {
        this.tablename = tablename == null ? null : tablename.trim();
    }

    public String getColumnname() {
        return columnname;
    }

    public void setColumnname(String columnname) {
        this.columnname = columnname == null ? null : columnname.trim();
    }

    public Integer getSeq() {
        return seq;
    }

    public void setSeq(Integer seq) {
        this.seq = seq;
    }

    public String getDatatype() {
        return datatype;
    }

    public void setDatatype(String datatype) {
        this.datatype = datatype == null ? null : datatype.trim();
    }

    public String getDatatypevalue() {
        return datatypevalue;
    }

    public void setDatatypevalue(String datatypevalue) {
        this.datatypevalue = datatypevalue == null ? null : datatypevalue.trim();
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