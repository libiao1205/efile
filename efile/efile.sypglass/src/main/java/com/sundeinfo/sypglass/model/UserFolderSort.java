package com.sundeinfo.sypglass.model;

public class UserFolderSort {
    private Long id;

    private Long userid;

    private Long parentid;

    private String sortjson;

    public UserFolderSort(Long id, Long userid, Long parentid, String sortjson) {
        this.id = id;
        this.userid = userid;
        this.parentid = parentid;
        this.sortjson = sortjson;
    }

    public UserFolderSort() {
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

    public Long getParentid() {
        return parentid;
    }

    public void setParentid(Long parentid) {
        this.parentid = parentid;
    }

    public String getSortjson() {
        return sortjson;
    }

    public void setSortjson(String sortjson) {
        this.sortjson = sortjson == null ? null : sortjson.trim();
    }
}