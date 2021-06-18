package com.sundeinfo.file.model;

import com.sundeinfo.core.domain.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = false)
public class FileUrl extends Entity implements Serializable {

    private String suffix;

    private String url;

    private String decodedUrl;

}
