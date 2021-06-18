package com.sundeinfo.sypglass.dto;

import com.sundeinfo.core.domain.Entity;
import com.sundeinfo.file.model.FilePreviewAttribute;
import com.sundeinfo.file.model.FileUrl;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Map;

@Data
@EqualsAndHashCode(callSuper = false)
public class FilePreviewDto extends Entity implements Serializable {

    private String name;

    private String suffix;

    private int seq;

    private byte status;

    private String comment;

    private String size;

    private String saveId;

    private String saveMD5;

    private long saveLength;

    private Map<String,Boolean> permissionMap;

    private String guid;

    //预览信息
    private FileUrl url;

}