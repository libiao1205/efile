package com.sundeinfo.sypglass.model;

public class AdminFolder {
    private Long id;

    private Long adminid;

    private Long userid;

    private String username;

    private Long folderid;

    private String foldername;

    private Byte status;

    public AdminFolder(Long id, Long adminid, Long userid, String username, Long folderid, String foldername, Byte status) {
        this.id = id;
        this.adminid = adminid;
        this.userid = userid;
        this.username = username;
        this.folderid = folderid;
        this.foldername = foldername;
        this.status = status;
    }

    public AdminFolder() {
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAdminid() {
        return adminid;
    }

    public void setAdminid(Long adminid) {
        this.adminid = adminid;
    }

    public Long getUserid() {
        return userid;
    }

    public void setUserid(Long userid) {
        this.userid = userid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public Long getFolderid() {
        return folderid;
    }

    public void setFolderid(Long folderid) {
        this.folderid = folderid;
    }

    public String getFoldername() {
        return foldername;
    }

    public void setFoldername(String foldername) {
        this.foldername = foldername == null ? null : foldername.trim();
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }
}