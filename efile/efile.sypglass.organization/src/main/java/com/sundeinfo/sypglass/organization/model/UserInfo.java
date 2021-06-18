package com.sundeinfo.sypglass.organization.model;

public class UserInfo {
    private Long id;

    private Long userid;

    private String tel;

    private String mobile;

    private String mail;

    private String info;

    public UserInfo(Long id, Long userid, String tel, String mobile, String mail, String info) {
        this.id = id;
        this.userid = userid;
        this.tel = tel;
        this.mobile = mobile;
        this.mail = mail;
        this.info = info;
    }

    public UserInfo() {
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserid() {
        return userid;
    }

    public void setUserid(Long userid) {
        this.userid = userid;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel == null ? null : tel.trim();
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail == null ? null : mail.trim();
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info == null ? null : info.trim();
    }
}