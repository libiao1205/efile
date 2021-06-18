package com.sundeinfo.sypglass.dto;

import com.sundeinfo.core.domain.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = false)
public class FileAccessoryHistoryDto extends Entity implements Serializable {

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

    private String comment;

    private String saveId;

    private String saveMD5;

    private Long saveLength;


}
