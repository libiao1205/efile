package com.sundeinfo.sypglass.model;

public class FolderSetting {
    private Long id;

    private Long workflowid;

    private String workflowname;

    private String firstdictcode;

    private String firstdictname;

    private String seconddictcode;

    private String seconddictname;

    private String thirddictcode;

    private String thirddictname;

    private String fourthdictcode;

    private String fourthdictname;

    private Long folderid;

    private String foldername;

    private String fullpath;

    private String fullname;

    public FolderSetting(Long id, Long workflowid, String workflowname, String firstdictcode, String firstdictname, String seconddictcode, String seconddictname, String thirddictcode, String thirddictname, String fourthdictcode, String fourthdictname, Long folderid, String foldername, String fullpath, String fullname) {
        this.id = id;
        this.workflowid = workflowid;
        this.workflowname = workflowname;
        this.firstdictcode = firstdictcode;
        this.firstdictname = firstdictname;
        this.seconddictcode = seconddictcode;
        this.seconddictname = seconddictname;
        this.thirddictcode = thirddictcode;
        this.thirddictname = thirddictname;
        this.fourthdictcode = fourthdictcode;
        this.fourthdictname = fourthdictname;
        this.folderid = folderid;
        this.foldername = foldername;
        this.fullpath = fullpath;
        this.fullname = fullname;
    }

    public FolderSetting() {
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getWorkflowid() {
        return workflowid;
    }

    public void setWorkflowid(Long workflowid) {
        this.workflowid = workflowid;
    }

    public String getWorkflowname() {
        return workflowname;
    }

    public void setWorkflowname(String workflowname) {
        this.workflowname = workflowname == null ? null : workflowname.trim();
    }

    public String getFirstdictcode() {
        return firstdictcode;
    }

    public void setFirstdictcode(String firstdictcode) {
        this.firstdictcode = firstdictcode == null ? null : firstdictcode.trim();
    }

    public String getFirstdictname() {
        return firstdictname;
    }

    public void setFirstdictname(String firstdictname) {
        this.firstdictname = firstdictname == null ? null : firstdictname.trim();
    }

    public String getSeconddictcode() {
        return seconddictcode;
    }

    public void setSeconddictcode(String seconddictcode) {
        this.seconddictcode = seconddictcode == null ? null : seconddictcode.trim();
    }

    public String getSeconddictname() {
        return seconddictname;
    }

    public void setSeconddictname(String seconddictname) {
        this.seconddictname = seconddictname == null ? null : seconddictname.trim();
    }

    public String getThirddictcode() {
        return thirddictcode;
    }

    public void setThirddictcode(String thirddictcode) {
        this.thirddictcode = thirddictcode == null ? null : thirddictcode.trim();
    }

    public String getThirddictname() {
        return thirddictname;
    }

    public void setThirddictname(String thirddictname) {
        this.thirddictname = thirddictname == null ? null : thirddictname.trim();
    }

    public String getFourthdictcode() {
        return fourthdictcode;
    }

    public void setFourthdictcode(String fourthdictcode) {
        this.fourthdictcode = fourthdictcode == null ? null : fourthdictcode.trim();
    }

    public String getFourthdictname() {
        return fourthdictname;
    }

    public void setFourthdictname(String fourthdictname) {
        this.fourthdictname = fourthdictname == null ? null : fourthdictname.trim();
    }

    public Long getFolderid() {
        return folderid;
    }

    public void setFolderid(Long folderid) {
        this.folderid = folderid;
    }

    public String getFoldername() {
        return foldername;
    }

    public void setFoldername(String foldername) {
        this.foldername = foldername == null ? null : foldername.trim();
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