package com.sundeinfo.sypglass.dto;

import com.sundeinfo.core.domain.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Data
@EqualsAndHashCode(callSuper = false)
public class SearchResultDto extends Entity implements Serializable {

    private Integer docType;

    private Long parentId;

    private String name;

    private String suffix;

    private String kind;

    private byte status;

    private String size;

    private String lastUpdateUserName;

    private Date lastUpdateDate;

    private String parentPath;

    private String parentPathName;

    private Serializable resource;

    private String highLight;

    private List<FileAccessoryDto> fileAccessoryDto;

    private List<FileMetadataDto> fileMetadataDto;

    private Map<String,Boolean> permissionMap;
}
