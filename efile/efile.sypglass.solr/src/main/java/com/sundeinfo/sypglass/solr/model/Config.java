package com.sundeinfo.sypglass.solr.model;

public class Config {
    private Long id;

    private String name;

    private String code;

    private Byte seq;

    private String value;

    private Byte status;

    private String comment;

    public Config(Long id, String name, String code, Byte seq, String value, Byte status, String comment) {
        this.id = id;
        this.name = name;
        this.code = code;
        this.seq = seq;
        this.value = value;
        this.status = status;
        this.comment = comment;
    }

    public Config() {
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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    public Byte getSeq() {
        return seq;
    }

    public void setSeq(Byte seq) {
        this.seq = seq;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value == null ? null : value.trim();
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment == null ? null : comment.trim();
    }
}