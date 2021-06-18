package com.sundeinfo.security.domain;

import com.sundeinfo.core.domain.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = true)
public abstract class UserDomain extends Entity {

    private String name;

    private String username;

    private String password;

    private Boolean remind;

    private Long psdMistakeCount;

    //TODO:必要性确认
    private Date lastPasswordResetDate;

}