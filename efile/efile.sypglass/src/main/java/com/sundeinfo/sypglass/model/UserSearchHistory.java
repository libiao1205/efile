package com.sundeinfo.sypglass.model;

import java.util.Date;

public class UserSearchHistory {
    private Long id;

    private Long userid;

    private String searchkey;

    private Date serachdate;

    public UserSearchHistory(Long id, Long userid, String searchkey, Date serachdate) {
        this.id = id;
        this.userid = userid;
        this.searchkey = searchkey;
        this.serachdate = serachdate;
    }

    public UserSearchHistory() {
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

    public String getSearchkey() {
        return searchkey;
    }

    public void setSearchkey(String searchkey) {
        this.searchkey = searchkey == null ? null : searchkey.trim();
    }

    public Date getSerachdate() {
        return serachdate;
    }

    public void setSerachdate(Date serachdate) {
        this.serachdate = serachdate;
    }
}