package com.sundeinfo.sypglass.model;

import java.util.Date;

public class Folder {
    private Long id;

    private Long parentid;

    private Integer sortno;

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

    private Integer filesize;

    private Integer maxsize;

    private Long oaworkflowid;

    private String oaworkflowinfo;

    public Folder(Long id, Long parentid, Integer sortno ,String name, Byte status, Byte isdelete, String parentpath, String parentpathname) {
        this.id = id;
        this.parentid = parentid;
        this.sortno = sortno;
        this.name = name;
        this.status = status;
        this.isdelete = isdelete;
        this.parentpath = parentpath;
        this.parentpathname = parentpathname;
    }

    public Folder(Long id, Long parentid, Integer sortno, String name, Byte status, Long createuserid, String createusername, Date createdate, Byte isdelete, Long deleteuserid, String deleteusername, Date deletedate, String parentpath, String parentpathname, Integer filesize, Integer maxsize, Long oaworkflowid, String oaworkflowinfo) {
        this.id = id;
        this.parentid = parentid;
        this.sortno = sortno;
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
        this.filesize = filesize;
        this.maxsize = maxsize;
        this.oaworkflowid = oaworkflowid;
        this.oaworkflowinfo = oaworkflowinfo;
    }

    public Folder() {
        super();
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

    public Integer getSortno() {
        return sortno;
    }

    public void setSortno(Integer sortno) {
        this.sortno = sortno;
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

    public Integer getFilesize() {
        return filesize;
    }

    public void setFilesize(Integer filesize) {
        this.filesize = filesize;
    }

    public Integer getMaxsize() {
        return maxsize;
    }

    public void setMaxsize(Integer maxsize) {
        this.maxsize = maxsize;
    }

    public Long getOaworkflowid() {
        return oaworkflowid;
    }

    public void setOaworkflowid(Long oaworkflowid) {
        this.oaworkflowid = oaworkflowid;
    }

    public String getOaworkflowinfo() {
        return oaworkflowinfo;
    }

    public void setOaworkflowinfo(String oaworkflowinfo) {
        this.oaworkflowinfo = oaworkflowinfo == null ? null : oaworkflowinfo.trim();
    }
}