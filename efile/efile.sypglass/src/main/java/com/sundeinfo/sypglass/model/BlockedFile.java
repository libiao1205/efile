package com.sundeinfo.sypglass.model;

import java.util.Date;

public class BlockedFile {
    private Long sequence;

    private Long id;

    private Long folderid;

    private String name;

    private String suffix;

    private Byte status;

    private String size;

    private Long createuserid;

    private String createusername;

    private Date createdate;

    private Long lastupdateuserid;

    private String lastupdateusername;

    private Date lastupdatedate;

    private Byte isdelete;

    private Long deleteuserid;

    private String deleteusername;

    private Date deletedate;

    private String comment;

    private String saveid;

    private String savemd5;

    private Long savelength;

    private String parentpath;

    private String parentpathname;

    public BlockedFile(Long sequence, Long id, Long folderid, String name, String suffix, Byte status, String size, Long createuserid, String createusername, Date createdate, Long lastupdateuserid, String lastupdateusername, Date lastupdatedate, Byte isdelete, Long deleteuserid, String deleteusername, Date deletedate, String comment, String saveid, String savemd5, Long savelength, String parentpath, String parentpathname) {
        this.sequence = sequence;
        this.id = id;
        this.folderid = folderid;
        this.name = name;
        this.suffix = suffix;
        this.status = status;
        this.size = size;
        this.createuserid = createuserid;
        this.createusername = createusername;
        this.createdate = createdate;
        this.lastupdateuserid = lastupdateuserid;
        this.lastupdateusername = lastupdateusername;
        this.lastupdatedate = lastupdatedate;
        this.isdelete = isdelete;
        this.deleteuserid = deleteuserid;
        this.deleteusername = deleteusername;
        this.deletedate = deletedate;
        this.comment = comment;
        this.saveid = saveid;
        this.savemd5 = savemd5;
        this.savelength = savelength;
        this.parentpath = parentpath;
        this.parentpathname = parentpathname;
    }

    public BlockedFile() {
        super();
    }

    public Long getSequence() {
        return sequence;
    }

    public void setSequence(Long sequence) {
        this.sequence = sequence;
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

    public Long getLastupdateuserid() {
        return lastupdateuserid;
    }

    public void setLastupdateuserid(Long lastupdateuserid) {
        this.lastupdateuserid = lastupdateuserid;
    }

    public String getLastupdateusername() {
        return lastupdateusername;
    }

    public void setLastupdateusername(String lastupdateusername) {
        this.lastupdateusername = lastupdateusername == null ? null : lastupdateusername.trim();
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

    public String getParentpath() {
        return parentpath;
    }

    public void setParentpath(String parentpath) {
        this.parentpath = parentpath == null ? null : parentpath.trim();
    }

    public String getParentpathname() {
        return parentpathname;
    }

    public void setParentpathname(String parentpathname) {
        this.parentpathname = parentpathname == null ? null : parentpathname.trim();
    }
}