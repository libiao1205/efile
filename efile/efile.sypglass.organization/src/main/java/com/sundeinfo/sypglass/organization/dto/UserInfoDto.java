package com.sundeinfo.sypglass.organization.dto;

import com.sundeinfo.core.domain.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = false)
public class UserInfoDto extends Entity implements Serializable {

    private Long userid;

    private String tel;

    private String mobile;

    private String mail;

    private String info;

    public boolean isEmpty(){
        return userid == null || userid < 1;
    }

}
