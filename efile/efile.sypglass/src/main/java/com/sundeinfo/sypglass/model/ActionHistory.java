package com.sundeinfo.sypglass.model;

import java.util.Date;

public class ActionHistory {
    private Long id;

    private Long resourceid;

    private String kind;

    private Integer seq;

    private String action;

    private Long userid;

    private String username;

    private Date updatedate;

    public ActionHistory(Long id, Long resourceid, String kind, Integer seq, String action, Long userid, String username, Date updatedate) {
        this.id = id;
        this.resourceid = resourceid;
        this.kind = kind;
        this.seq = seq;
        this.action = action;
        this.userid = userid;
        this.username = username;
        this.updatedate = updatedate;
    }

    public ActionHistory() {
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getResourceid() {
        return resourceid;
    }

    public void setResourceid(Long resourceid) {
        this.resourceid = resourceid;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind == null ? null : kind.trim();
    }

    public Integer getSeq() {
        return seq;
    }

    public void setSeq(Integer seq) {
        this.seq = seq;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action == null ? null : action.trim();
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

    public Date getUpdatedate() {
        return updatedate;
    }

    public void setUpdatedate(Date updatedate) {
        this.updatedate = updatedate;
    }
}