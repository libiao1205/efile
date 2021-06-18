package com.sundeinfo.sypglass.model;

public class Department {
    private Long id;

    private Long parentid;

    private String name;

    private Byte status;

    private Byte type;

    public Department(Long id, Long parentid, String name, Byte status, Byte type) {
        this.id = id;
        this.parentid = parentid;
        this.name = name;
        this.status = status;
        this.type = type;
    }

    public Department() {
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getParentid() {
        return parentid;
    }

    public void setParentid(Long parentid) {
        this.parentid = parentid;
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

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }
}