package com.sundeinfo.file.model;

import com.sundeinfo.core.domain.Entity;
import com.sundeinfo.file.define.FileType;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = false)
public class FilePreviewAttribute extends FileUrl implements Serializable {

    private FileType type;

    private String fileName;

    private String decodeFileName;

}
