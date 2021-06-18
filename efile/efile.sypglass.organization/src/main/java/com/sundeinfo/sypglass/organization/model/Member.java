package com.sundeinfo.sypglass.organization.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class Member implements Serializable {

    private long id;

    private String kind;

    private String name;

    private byte type;

    private byte status;

    private long parenId;

    public Member(Long id, String kind, String name, Byte type, Byte status, Long parenId) {
        this.id = id;
        this.kind = kind;
        this.name = name;
        this.type = type;
        this.status = status;
        this.parenId = parenId;
    }

    public String getKey(){
        return kind + "-" + id;
    }
}