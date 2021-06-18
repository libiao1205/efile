package com.sundeinfo.sypglass.dto;

import com.sundeinfo.core.domain.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
public class UserSettingDto extends Entity implements Serializable {

    private long userId;

    private String kind;

    private String name;

    private byte status;

    private String setting;

    private List<UserSettingDetailDto> details;

}
