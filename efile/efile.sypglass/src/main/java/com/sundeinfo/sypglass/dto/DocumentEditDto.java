package com.sundeinfo.sypglass.dto;

import com.sundeinfo.core.domain.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = false)
public class DocumentEditDto extends Entity implements Serializable {


    private String kind;

    private String orgName;

    private String name;

    private String suffix;

    private long orgParentId;

    private String orgParentName;

    private long parentId;

    private String parentName;

}
