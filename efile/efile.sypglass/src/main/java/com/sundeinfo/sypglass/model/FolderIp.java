package com.sundeinfo.sypglass.model;

public class FolderIp {
    private Long id;

    private Long folderid;

    private String ipinfo;

    private Byte status;

    public FolderIp(Long id, Long folderid, String ipinfo, Byte status) {
        this.id = id;
        this.folderid = folderid;
        this.ipinfo = ipinfo;
        this.status = status;
    }

    public FolderIp() {
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getFolderid() {
        return folderid;
    }

    public void setFolderid(Long folderid) {
        this.folderid = folderid;
    }

    public String getIpinfo() {
        return ipinfo;
    }

    public void setIpinfo(String ipinfo) {
        this.ipinfo = ipinfo == null ? null : ipinfo.trim();
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }
}