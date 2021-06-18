package com.sundeinfo.sypglass.model;

public class MessageSetting {
    private Long id;

    private Long userid;

    private Boolean updatenotice;

    private Boolean deletenotice;

    private Boolean downloadnotice;

    private Boolean notice1;

    private Boolean notice2;

    private Boolean notice3;

    private Boolean notice4;

    public MessageSetting(Long id, Long userid, Boolean updatenotice, Boolean deletenotice, Boolean downloadnotice, Boolean notice1, Boolean notice2, Boolean notice3, Boolean notice4) {
        this.id = id;
        this.userid = userid;
        this.updatenotice = updatenotice;
        this.deletenotice = deletenotice;
        this.downloadnotice = downloadnotice;
        this.notice1 = notice1;
        this.notice2 = notice2;
        this.notice3 = notice3;
        this.notice4 = notice4;
    }

    public MessageSetting() {
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

    public Boolean getUpdatenotice() {
        return updatenotice;
    }

    public void setUpdatenotice(Boolean updatenotice) {
        this.updatenotice = updatenotice;
    }

    public Boolean getDeletenotice() {
        return deletenotice;
    }

    public void setDeletenotice(Boolean deletenotice) {
        this.deletenotice = deletenotice;
    }

    public Boolean getDownloadnotice() {
        return downloadnotice;
    }

    public void setDownloadnotice(Boolean downloadnotice) {
        this.downloadnotice = downloadnotice;
    }

    public Boolean getNotice1() {
        return notice1;
    }

    public void setNotice1(Boolean notice1) {
        this.notice1 = notice1;
    }

    public Boolean getNotice2() {
        return notice2;
    }

    public void setNotice2(Boolean notice2) {
        this.notice2 = notice2;
    }

    public Boolean getNotice3() {
        return notice3;
    }

    public void setNotice3(Boolean notice3) {
        this.notice3 = notice3;
    }

    public Boolean getNotice4() {
        return notice4;
    }

    public void setNotice4(Boolean notice4) {
        this.notice4 = notice4;
    }
}