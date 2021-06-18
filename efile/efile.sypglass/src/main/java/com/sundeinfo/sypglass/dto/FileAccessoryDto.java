package com.sundeinfo.sypglass.dto;

import com.sundeinfo.core.domain.Entity;
import com.sundeinfo.security.permission.PermissionAware;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = false)
public class FileAccessoryDto extends Entity implements Serializable {

    private Integer seq;

    private Integer type;

    private Long fileId;

    private Long folderId;

    private String name;

    private String suffix;

    private Byte status;

    private String size;

    private Long createUserId;

    private String createUsername;

    private Date createDate;

    private Long lastUpdateUserId;

    private String lastUpdateUsername;

    private Date lastUpdateDate;

    private Byte isDelete;

    private Long deleteUserId;

    private String deleteUsername;

    private Date deleteDate;

    private String comment;

    private String saveId;

    private String saveMD5;

    private Long saveLength;


}
