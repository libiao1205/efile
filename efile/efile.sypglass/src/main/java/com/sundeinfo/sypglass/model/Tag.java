package com.sundeinfo.sypglass.model;

public class Tag {
    private Long id;

    private String name;

    private Byte type;

    public Tag(Long id, String name, Byte type) {
        this.id = id;
        this.name = name;
        this.type = type;
    }

    public Tag() {
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

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }
}