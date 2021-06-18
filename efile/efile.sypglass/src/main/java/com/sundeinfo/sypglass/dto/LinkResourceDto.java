package com.sundeinfo.sypglass.dto;

import com.sundeinfo.core.domain.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
public class LinkResourceDto extends Entity implements Serializable {

    private long linkId;

    private long resourceId;

    private String kind;

    private String name;

    private String suffix;

    private Entity resource;

    //文献附件
    private List<FileAccessoryDto> fileAccessoryDto;
    //文献元数据
    private List<FileMetadataDto> fileMetadataDto;

}
