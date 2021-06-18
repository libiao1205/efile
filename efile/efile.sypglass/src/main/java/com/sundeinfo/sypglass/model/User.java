package com.sundeinfo.sypglass.model;

import java.util.Date;

public class User {
    private Long id;

    private String username;

    private String password;

    private String name;

    private Byte status;

    private Date lastpasswordresetdate;

    private Boolean remind;

    private Long psdmistakecount;

    public User(Long id, String username, String password, String name, Byte status, Date lastpasswordresetdate, Boolean remind, Long psdmistakecount) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.name = name;
        this.status = status;
        this.lastpasswordresetdate = lastpasswordresetdate;
        this.remind = remind;
        this.psdmistakecount = psdmistakecount;
    }

    public User() {
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
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

    public Date getLastpasswordresetdate() {
        return lastpasswordresetdate;
    }

    public void setLastpasswordresetdate(Date lastpasswordresetdate) {
        this.lastpasswordresetdate = lastpasswordresetdate;
    }

    public Boolean getRemind() {
        return remind;
    }

    public void setRemind(Boolean remind) {
        this.remind = remind;
    }

    public Long getPsdmistakecount() {
        return psdmistakecount;
    }

    public void setPsdmistakecount(Long psdmistakecount) {
        this.psdmistakecount = psdmistakecount;
    }
}