package com.sundeinfo.sypglass.model;

import java.util.Date;

public class Link {
    private Long id;

    private String name;

    private String randomfoldername;

    private String fullurl;

    private String password;

    private Long userid;

    private Date startdate;

    private Date enddate;

    private Integer days;

    private Byte isdelete;

    private Long deleteuser;

    private Date deletedate;

    public Link(Long id, String name, String randomfoldername, String fullurl, String password, Long userid, Date startdate, Date enddate, Integer days, Byte isdelete, Long deleteuser, Date deletedate) {
        this.id = id;
        this.name = name;
        this.randomfoldername = randomfoldername;
        this.fullurl = fullurl;
        this.password = password;
        this.userid = userid;
        this.startdate = startdate;
        this.enddate = enddate;
        this.days = days;
        this.isdelete = isdelete;
        this.deleteuser = deleteuser;
        this.deletedate = deletedate;
    }

    public Link() {
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

    public String getRandomfoldername() {
        return randomfoldername;
    }

    public void setRandomfoldername(String randomfoldername) {
        this.randomfoldername = randomfoldername == null ? null : randomfoldername.trim();
    }

    public String getFullurl() {
        return fullurl;
    }

    public void setFullurl(String fullurl) {
        this.fullurl = fullurl == null ? null : fullurl.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public Long getUserid() {
        return userid;
    }

    public void setUserid(Long userid) {
        this.userid = userid;
    }

    public Date getStartdate() {
        return startdate;
    }

    public void setStartdate(Date startdate) {
        this.startdate = startdate;
    }

    public Date getEnddate() {
        return enddate;
    }

    public void setEnddate(Date enddate) {
        this.enddate = enddate;
    }

    public Integer getDays() {
        return days;
    }

    public void setDays(Integer days) {
        this.days = days;
    }

    public Byte getIsdelete() {
        return isdelete;
    }

    public void setIsdelete(Byte isdelete) {
        this.isdelete = isdelete;
    }

    public Long getDeleteuser() {
        return deleteuser;
    }

    public void setDeleteuser(Long deleteuser) {
        this.deleteuser = deleteuser;
    }

    public Date getDeletedate() {
        return deletedate;
    }

    public void setDeletedate(Date deletedate) {
        this.deletedate = deletedate;
    }
}