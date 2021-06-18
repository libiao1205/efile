package com.sundeinfo.sypglass.dto;

public class ProcessSettingDto {
    private Long id;

    private Long workflowid;

    private String workflowname;

    private String groupname;

    private String classname;

    private String param;

    private String cron;

    private Byte status;

    public ProcessSettingDto(Long id, Long workflowid, String workflowname, String groupname, String classname, String param, String cron, Byte status) {
        this.id = id;
        this.workflowid = workflowid;
        this.workflowname = workflowname;
        this.groupname = groupname;
        this.classname = classname;
        this.param = param;
        this.cron = cron;
        this.status = status;
    }

    public ProcessSettingDto() {
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