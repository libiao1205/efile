package com.sundeinfo.sypglass.solr.model;

import java.util.Date;

public class FileMetadata {
    private Long id;

    private Long fileid;

    private Long metadataid;

    private String value;

    private Long createuserid;

    private String createusername;

    private Date createdate;

    private String lastupdateusername;

    private Long lastupdateuserid;

    private Date lastupdatedate;

    private Byte isdelete;

    private Long deleteuserid;

    private String deleteusername;

    private Date deletedate;

    private String comment;

    public FileMetadata(Long id, Long fileid, Long metadataid, String value, Long createuserid, String createusername, Date createdate, String lastupdateusername, Long lastupdateuserid, Date lastupdatedate, Byte isdelete, Long deleteuserid, String deleteusername, Date deletedate, String comment) {
        this.id = id;
        this.fileid = fileid;
        this.metadataid = metadataid;
        this.value = value;
        this.createuserid = createuserid;
        this.createusername = createusername;
        this.createdate = createdate;
        this.lastupdateusername = lastupdateusername;
        this.lastupdateuserid = lastupdateuserid;
        this.lastupdatedate = lastupdatedate;
        this.isdelete = isdelete;
        this.deleteuserid = deleteuserid;
        this.deleteusername = deleteusername;
        this.deletedate = deletedate;
        this.comment = comment;
    }

    public FileMetadata() {
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

    public String getLastupdateusername() {
        return lastupdateusername;
    }

    public void setLastupdateusername(String lastupdateusername) {
        this.lastupdateusername = lastupdateusername == null ? null : lastupdateusername.trim();
    }

    public Long getLastupdateuserid() {
        return lastupdateuserid;
    }

    public void setLastupdateuserid(Long lastupdateuserid) {
        this.lastupdateuserid = lastupdateuserid;
    }

    public Date getLastupdatedate() {
        return lastupdatedate;
    }

    public void setLastupdatedate(Date lastupdatedate) {
        this.lastupdatedate = lastupdatedate;
    }

    public Byte getIsdelete() {
        return isdelete;
    }

    public void setIsdelete(Byte isdelete) {
        this.isdelete = isdelete;
    }

    public Long getDeleteuserid() {
        return deleteuserid;
    }

    public void setDeleteuserid(Long deleteuserid) {
        this.deleteuserid = deleteuserid;
    }

    public String getDeleteusername() {
        return deleteusername;
    }

    public void setDeleteusername(String deleteusername) {
        this.deleteusername = deleteusername == null ? null : deleteusername.trim();
    }

    public Date getDeletedate() {
        return deletedate;
    }

    public void setDeletedate(Date deletedate) {
        this.deletedate = deletedate;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment == null ? null : comment.trim();
    }
}