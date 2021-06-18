package com.sundeinfo.sypglass.model;

public class ActionLog {
    private Long id;

    private String httpMethod;

    private String classMethod;

    private String ip;

    private String url;

    private String parameter;

    private String result;

    private Long starttime;

    private Long endtime;

    private Long exectime;

    private Long userid;

    private String username;

    private String execinfo;

    public ActionLog(Long id, String httpMethod, String classMethod, String ip, String url, String parameter, String result, Long starttime, Long endtime, Long exectime, Long userid, String username, String execinfo) {
        this.id = id;
        this.httpMethod = httpMethod;
        this.classMethod = classMethod;
        this.ip = ip;
        this.url = url;
        this.parameter = parameter;
        this.result = result;
        this.starttime = starttime;
        this.endtime = endtime;
        this.exectime = exectime;
        this.userid = userid;
        this.username = username;
        this.execinfo = execinfo;
    }

    public ActionLog() {
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getHttpMethod() {
        return httpMethod;
    }

    public void setHttpMethod(String httpMethod) {
        this.httpMethod = httpMethod == null ? null : httpMethod.trim();
    }

    public String getClassMethod() {
        return classMethod;
    }

    public void setClassMethod(String classMethod) {
        this.classMethod = classMethod == null ? null : classMethod.trim();
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip == null ? null : ip.trim();
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    public String getParameter() {
        return parameter;
    }

    public void setParameter(String parameter) {
        this.parameter = parameter == null ? null : parameter.trim();
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result == null ? null : result.trim();
    }

    public Long getStarttime() {
        return starttime;
    }

    public void setStarttime(Long starttime) {
        this.starttime = starttime;
    }

    public Long getEndtime() {
        return endtime;
    }

    public void setEndtime(Long endtime) {
        this.endtime = endtime;
    }

    public Long getExectime() {
        return exectime;
    }

    public void setExectime(Long exectime) {
        this.exectime = exectime;
    }

    public Long getUserid() {
        return userid;
    }

    public void setUserid(Long userid) {
        this.userid = userid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public String getExecinfo() {
        return execinfo;
    }

    public void setExecinfo(String execinfo) {
        this.execinfo = execinfo == null ? null : execinfo.trim();
    }
}