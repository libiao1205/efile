package com.sundeinfo.sypglass.dto;

import com.sundeinfo.core.domain.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = false)
public class FolderIpDto extends Entity implements Serializable {

    private long folderId;

    private String ipInfo;

    private byte status;


}