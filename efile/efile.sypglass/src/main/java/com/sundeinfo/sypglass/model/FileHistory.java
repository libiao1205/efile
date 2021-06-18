package com.sundeinfo.sypglass.model;

import java.util.Date;

public class FileHistory {
    private Long id;

    private Long fileid;

    private Integer seq;

    private Long folderid;

    private String name;

    private String suffix;

    private String size;

    private Long userid;

    private String username;

    private Date updatedate;

    private String comment;

    private String saveid;

    private String savemd5;

    private Long savelength;

    public FileHistory(Long id, Long fileid, Integer seq, Long folderid, String name, String suffix, String size, Long userid, String username, Date updatedate, String comment, String saveid, String savemd5, Long savelength) {
        this.id = id;
        this.fileid = fileid;
        this.seq = seq;
        this.folderid = folderid;
        this.name = name;
        this.suffix = suffix;
        this.size = size;
        this.userid = userid;
        this.username = username;
        this.updatedate = updatedate;
        this.comment = comment;
        this.saveid = saveid;
        this.savemd5 = savemd5;
        this.savelength = savelength;
    }

    public FileHistory() {
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getFileid() {
        return fileid;
    }

    public void setFileid(Long fileid) {
        this.fileid = fileid;
    }

    public Integer getSeq() {
        return seq;
    }

    public void setSeq(Integer seq) {
        this.seq = seq;
    }

    public Long getFolderid() {
        return folderid;
    }

    public void setFolderid(Long folderid) {
        this.folderid = folderid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix == null ? null : suffix.trim();
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size == null ? null : size.trim();
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

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment == null ? null : comment.trim();
    }

    public String getSaveid() {
        return saveid;
    }

    public void setSaveid(String saveid) {
        this.saveid = saveid == null ? null : saveid.trim();
    }

    public String getSavemd5() {
        return savemd5;
    }

    public void setSavemd5(String savemd5) {
        this.savemd5 = savemd5 == null ? null : savemd5.trim();
    }

    public Long getSavelength() {
        return savelength;
    }

    public void setSavelength(Long savelength) {
        this.savelength = savelength;
    }
}