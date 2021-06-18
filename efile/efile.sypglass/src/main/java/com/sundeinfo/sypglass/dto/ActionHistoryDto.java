package com.sundeinfo.sypglass.dto;

import com.sundeinfo.core.domain.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = false)
public class ActionHistoryDto extends Entity implements Serializable {

    private long resourceId;

    private String kind;

    private int seq;

    private String action;

    private String actionName;

    private long userId;

    private String userName;

    private Date updateDate;
}
