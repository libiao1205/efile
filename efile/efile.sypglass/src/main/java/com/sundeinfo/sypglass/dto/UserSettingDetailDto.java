package com.sundeinfo.sypglass.dto;

import com.sundeinfo.core.domain.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = false)
public class UserSettingDetailDto  extends Entity implements Serializable {

    private long settingid;

    private int seq;

    private String code;

    private String value;

    private String comment;

}