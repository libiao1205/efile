package com.sundeinfo.sypglass.model;

public class RolePermission {
    private Long id;

    private Long roleid;

    private Byte type;

    private Long memberid;

    private String membername;

    private String code;

    private Long resourceid;

    private String resourcename;

    private String resourcesuffix;

    private Integer value;

    private String fullpath;

    private String fullname;

    public RolePermission(Long id, Long roleid, Byte type, Long memberid, String membername, String code, Long resourceid, String resourcename, String resourcesuffix, Integer value, String fullpath, String fullname) {
        this.id = id;
        this.roleid = roleid;
        this.type = type;
        this.memberid = memberid;
        this.membername = membername;
        this.code = code;
        this.resourceid = resourceid;
        this.resourcename = resourcename;
        this.resourcesuffix = resourcesuffix;
        this.value = value;
        this.fullpath = fullpath;
        this.fullname = fullname;
    }

    public RolePermission() {
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRoleid() {
        return roleid;
    }

    public void setRoleid(Long roleid) {
        this.roleid = roleid;
    }

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }

    public Long getMemberid() {
        return memberid;
    }

    public void setMemberid(Long memberid) {
        this.memberid = memberid;
    }

    public String getMembername() {
        return membername;
    }

    public void setMembername(String membername) {
        this.membername = membername == null ? null : membername.trim();
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    public Long getResourceid() {
        return resourceid;
    }

    public void setResourceid(Long resourceid) {
        this.resourceid = resourceid;
    }

    public String getResourcename() {
        return resourcename;
    }

    public void setResourcename(String resourcename) {
        this.resourcename = resourcename == null ? null : resourcename.trim();
    }

    public String getResourcesuffix() {
        return resourcesuffix;
    }

    public void setResourcesuffix(String resourcesuffix) {
        this.resourcesuffix = resourcesuffix == null ? null : resourcesuffix.trim();
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public String getFullpath() {
        return fullpath;
    }

    public void setFullpath(String fullpath) {
        this.fullpath = fullpath == null ? null : fullpath.trim();
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname == null ? null : fullname.trim();
    }
}