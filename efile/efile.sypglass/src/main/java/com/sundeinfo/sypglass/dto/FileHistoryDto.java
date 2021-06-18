package com.sundeinfo.sypglass.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sundeinfo.core.domain.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = false)
public class FileHistoryDto extends Entity implements Serializable {

    private long fileId;

    private int seq;

    private String action;

    private long folderId;

    private String name;

    private String suffix;

    private String size;

    private long userId;

    private String username;

    private Date updateDate;

    private String comment;

    private String saveId;

    private String saveMD5;

    private long saveLength;


    @JsonIgnore
    public String getSaveId() {
        return saveId;
    }

    @JsonIgnore
    public String getSaveMD5() {
        return saveMD5;
    }

    @JsonIgnore
    public long getSaveLength() {
        return saveLength;
    }


}
