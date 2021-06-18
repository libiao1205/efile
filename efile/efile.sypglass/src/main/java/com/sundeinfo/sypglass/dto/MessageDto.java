package com.sundeinfo.sypglass.dto;

import com.sundeinfo.core.domain.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = false)
public class MessageDto extends Entity implements Serializable {

    private long toUser;

    private Date toDate;

    private byte status;

    private String statusName;

    private byte type;

    private String typeName;

    private String name;

    private String info;

    private long fromUser;

    private String fromUserName;

    private Date fromDate;

    private long resourceId;

    private String resourceType;

    private String resourceName;

    private String resourceSuffix;

    private UserDto User;

    private FolderDto  folder;

    private FileDto file;
}
