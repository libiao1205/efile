package com.sundeinfo.sypglass.dto;

import com.sundeinfo.core.domain.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = false)
public class MessageSettingDto extends Entity implements Serializable {

    private Long userid;

    private Boolean updatenotice;

    private Boolean deletenotice;

    private Boolean downloadnotice;

    private Boolean notice1;

    private Boolean notice2;

    private Boolean notice3;

    private Boolean notice4;
}
