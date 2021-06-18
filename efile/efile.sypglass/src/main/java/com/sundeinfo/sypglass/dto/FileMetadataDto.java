package com.sundeinfo.sypglass.dto;

import com.sundeinfo.core.domain.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = false)
public class FileMetadataDto extends Entity implements Serializable {

    private Long fileId;

    private Long metadataId;

    private Integer docType;

    private Integer dataType;

    private String code;

    private String name;

    private Integer seq;

    private String value;

}
