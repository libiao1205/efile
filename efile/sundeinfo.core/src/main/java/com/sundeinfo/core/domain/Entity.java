package com.sundeinfo.core.domain;

import lombok.Data;

import java.io.Serializable;

@Data
public abstract class Entity implements Serializable {

    /*数据主键*/
    private long id;

}
