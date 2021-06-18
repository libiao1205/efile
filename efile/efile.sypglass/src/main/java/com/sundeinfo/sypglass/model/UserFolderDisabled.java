package com.sundeinfo.sypglass.model;

public class UserFolderDisabled {
    private Long id;

    private Long userid;

    private Long folderid;

    private Boolean disabled;

    public UserFolderDisabled(Long id, Long userid, Long folderid, Boolean disabled) {
        this.id = id;
        this.userid = userid;
        this.folderid = folderid;
        this.disabled = disabled;
    }

    public UserFolderDisabled() {
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserid() {
        return userid;
    }

    public void setUserid(Long userid) {
        this.userid = userid;
    }

    public Long getFolderid() {
        return folderid;
    }

    public void setFolderid(Long folderid) {
        this.folderid = folderid;
    }

    public Boolean getDisabled() {
        return disabled;
    }

    public void setDisabled(Boolean disabled) {
        this.disabled = disabled;
    }
}