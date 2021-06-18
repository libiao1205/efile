package com.sundeinfo.sypglass.model;

import java.util.Date;

public class ExternalDB {
    private Long id;

    private String dbname;

    private String aliasname;

    private String ip;

    private String port;

    private String username;

    private String password;

    private String drivername;

    private String mapperpath;

    private String mapperpackage;

    private String comment;

    private Date synctime;

    private Long syncuser;

    private Integer status;

    public ExternalDB(Long id, String dbname, String aliasname, String ip, String port, String username, String password, String drivername, String mapperpath, String mapperpackage, String comment, Date synctime, Long syncuser, Integer status) {
        this.id = id;
        this.dbname = dbname;
        this.aliasname = aliasname;
        this.ip = ip;
        this.port = port;
        this.username = username;
        this.password = password;
        this.drivername = drivername;
        this.mapperpath = mapperpath;
        this.mapperpackage = mapperpackage;
        this.comment = comment;
        this.synctime = synctime;
        this.syncuser = syncuser;
        this.status = status;
    }

    public ExternalDB() {
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDbname() {
        return dbname;
    }

    public void setDbname(String dbname) {
        this.dbname = dbname == null ? null : dbname.trim();
    }

    public String getAliasname() {
        return aliasname;
    }

    public void setAliasname(String aliasname) {
        this.aliasname = aliasname == null ? null : aliasname.trim();
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip == null ? null : ip.trim();
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port == null ? null : port.trim();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public String getDrivername() {
        return drivername;
    }

    public void setDrivername(String drivername) {
        this.drivername = drivername == null ? null : drivername.trim();
    }

    public String getMapperpath() {
        return mapperpath;
    }

    public void setMapperpath(String mapperpath) {
        this.mapperpath = mapperpath == null ? null : mapperpath.trim();
    }

    public String getMapperpackage() {
        return mapperpackage;
    }

    public void setMapperpackage(String mapperpackage) {
        this.mapperpackage = mapperpackage == null ? null : mapperpackage.trim();
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}