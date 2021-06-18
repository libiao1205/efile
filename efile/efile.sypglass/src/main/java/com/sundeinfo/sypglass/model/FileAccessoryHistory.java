package com.sundeinfo.sypglass.model;

import java.util.Date;

public class FileAccessoryHistory {
    private Long id;

    private Integer type;

    private Long fileid;

    private Integer seq;

    private Long folderid;

    private String name;

    private String suffix;

    private Byte status;

    private String size;

    private Long createuserid;

    private String createusername;

    private Date createdate;

    private String comment;

    private String saveid;

    private String savemd5;

    private Long savelength;

    public FileAccessoryHistory(Long id, Integer type, Long fileid, Integer seq, Long folderid, String name, String suffix, Byte status, String size, Long createuserid, String createusername, Date createdate, String comment, String saveid, String savemd5, Long savelength) {
        this.id = id;
        this.type = type;
        this.fileid = fileid;
        this.seq = seq;
        this.folderid = folderid;
        this.name = name;
        this.suffix = suffix;
        this.status = status;
        this.size = size;
        this.createuserid = createuserid;
        this.createusername = createusername;
        this.createdate = createdate;
        this.comment = comment;
        this.saveid = saveid;
        this.savemd5 = savemd5;
        this.savelength = savelength;
    }

    public FileAccessoryHistory() {
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
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

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size == null ? null : size.trim();
    }

    public Long getCreateuserid() {
        return createuserid;
    }

    public void setCreateuserid(Long createuserid) {
        this.createuserid = createuserid;
    }

    public String getCreateusername() {
        return createusername;
    }

    public void setCreateusername(String createusername) {
        this.createusername = createusername == null ? null : createusername.trim();
    }

    public Date getCreatedate() {
        return createdate;
    }

    public void setCreatedate(Date createdate) {
        this.createdate = createdate;
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