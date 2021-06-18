package com.sundeinfo.sypglass.model;

import java.util.Date;

public class FileMetadataHistory {
    private Long id;

    private Long fileid;

    private Integer seq;

    private Long metadataid;

    private String value;

    private Long createuserid;

    private String createusername;

    private Date createdate;

    private String comment;

    public FileMetadataHistory(Long id, Long fileid, Integer seq, Long metadataid, String value, Long createuserid, String createusername, Date createdate, String comment) {
        this.id = id;
        this.fileid = fileid;
        this.seq = seq;
        this.metadataid = metadataid;
        this.value = value;
        this.createuserid = createuserid;
        this.createusername = createusername;
        this.createdate = createdate;
        this.comment = comment;
    }

    public FileMetadataHistory() {
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

    public Long getMetadataid() {
        return metadataid;
    }

    public void setMetadataid(Long metadataid) {
        this.metadataid = metadataid;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value == null ? null : value.trim();
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
}