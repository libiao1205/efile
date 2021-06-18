package com.sundeinfo.sypglass.relay.domain;

import com.sundeinfo.core.domain.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = false)
public class RelayProcess extends Entity implements Serializable {

    private int requestId;

    private int workflowId;

    private String requestName;

    private String creater;

    private String createDate;

    private String createTime;

    private String lastOperator;

    private String lastOperateDate;

    private String lastOperateTime;

    private byte deleted;

    private String formCode;

    private long formId;

    private String requestDate;//日期

    private long comp;//基地

    private long dept;//部门

    private long leader;//责任人

    private String additional1;//附件1

    private String additional2;//附件2

    private String additional3;//附件3

    private long docImageId;

    private long docId;

    private long imageFileId;

    private String imageFileName;

    private String imageFileType;

    private int versionId;

    private String fileRealPath;

    private String isZip;

    private String fileSize;

    private Date relayDate;

    private byte relayStatus;

    private String relayFilePath;

    private String relayInfo;
}
