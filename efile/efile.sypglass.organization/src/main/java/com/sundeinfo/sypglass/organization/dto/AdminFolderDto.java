package com.sundeinfo.sypglass.organization.dto;

import com.sundeinfo.core.domain.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = false)
public class AdminFolderDto extends Entity implements Serializable {

    private long adminId;

    private long userId;

    private long deptId;

    private String userName;

    private long folderId;

    private String folderName;

    private byte status;
}