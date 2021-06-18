package com.sundeinfo.sypglass.model;

public class UserTag {
    private Long id;

    private Long userid;

    private Long tagid;

    public UserTag(Long id, Long userid, Long tagid) {
        this.id = id;
        this.userid = userid;
        this.tagid = tagid;
    }

    public UserTag() {
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

    public Long getTagid() {
        return tagid;
    }

    public void setTagid(Long tagid) {
        this.tagid = tagid;
    }
}