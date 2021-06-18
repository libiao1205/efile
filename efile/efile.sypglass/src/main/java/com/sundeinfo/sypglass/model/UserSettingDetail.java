package com.sundeinfo.sypglass.model;

public class UserSettingDetail {
    private Long id;

    private Long settingid;

    private Integer seq;

    private String code;

    private String value;

    private String comment;

    public UserSettingDetail(Long id, Long settingid, Integer seq, String code, String value, String comment) {
        this.id = id;
        this.settingid = settingid;
        this.seq = seq;
        this.code = code;
        this.value = value;
        this.comment = comment;
    }

    public UserSettingDetail() {
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSettingid() {
        return settingid;
    }

    public void setSettingid(Long settingid) {
        this.settingid = settingid;
    }

    public Integer getSeq() {
        return seq;
    }

    public void setSeq(Integer seq) {
        this.seq = seq;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value == null ? null : value.trim();
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment == null ? null : comment.trim();
    }
}