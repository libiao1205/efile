package com.sundeinfo.sypglass.relay.domain;

import com.sundeinfo.core.domain.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = false)
public class RelayRequest extends Entity implements Serializable {

    private long requestId;

    private long workflowId;

    private int lastnodeId;

    private String lastNodeType;

    private int currentNodeId;

    private String currentNodeType;

    private String status;

    private int passedGroups;

    private int totalGroups;

    private String requestName;

    private int creater;

    private String createDate;

    private String createTime;

    private int lastOperator;

    private String lastOperateDate;

    private String lastOperateTime;

    private byte deleted;

    private int createrType;

    private int lastOperatorType;

    private Date relayDate;

    private byte relayStatus;

    private String relayMd5;

}
