package com.sundeinfo.sypglass.model;

import java.util.Date;

public class BlockedFolder {
    private Long sequence;

    private Long id;

    private Long parentid;

    private String name;

    private Byte status;

    private Long createuserid;

    private String createusername;

    private Date createdate;

    private Byte isdelete;

    private Long deleteuserid;

    private String deleteusername;

    private Date deletedate;

    private String parentpath;

    private String parentpathname;

    public BlockedFolder(Long sequence, Long id, Long parentid, String name, Byte status, Long createuserid, String createusername, Date createdate, Byte isdelete, Long deleteuserid, String deleteusername, Date deletedate, String parentpath, String parentpathname) {
        this.sequence = sequence;
        this.id = id;
        this.parentid = parentid;
        this.name = name;
        this.status = status;
        this.createuserid = createuserid;
        this.createusername = createusername;
        this.createdate = createdate;
        this.isdelete = isdelete;
        this.deleteuserid = deleteuserid;
        this.deleteusername = deleteusername;
        this.deletedate = deletedate;
        this.parentpath = parentpath;
        this.parentpathname = parentpathname;
    }

    public BlockedFolder() {
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

    public Long getParentid() {
        return parentid;
    }

    public void setParentid(Long parentid) {
        this.parentid = parentid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
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