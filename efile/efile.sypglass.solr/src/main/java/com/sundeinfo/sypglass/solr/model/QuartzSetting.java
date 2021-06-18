package com.sundeinfo.sypglass.solr.model;

public class QuartzSetting {
    private Long id;

    private String name;

    private String groupname;

    private String classname;

    private String param;

    private String cron;

    private Byte status;

    public QuartzSetting(Long id, String name, String groupname, String classname, String param, String cron, Byte status) {
        this.id = id;
        this.name = name;
        this.groupname = groupname;
        this.classname = classname;
        this.param = param;
        this.cron = cron;
        this.status = status;
    }

    public QuartzSetting() {
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getGroupname() {
        return groupname;
    }

    public void setGroupname(String groupname) {
        this.groupname = groupname == null ? null : groupname.trim();
    }

    public String getClassname() {
        return classname;
    }

    public void setClassname(String classname) {
        this.classname = classname == null ? null : classname.trim();
    }

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param == null ? null : param.trim();
    }

    public String getCron() {
        return cron;
    }

    public void setCron(String cron) {
        this.cron = cron == null ? null : cron.trim();
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }
}