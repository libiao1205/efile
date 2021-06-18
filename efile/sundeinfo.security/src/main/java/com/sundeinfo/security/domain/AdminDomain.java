package com.sundeinfo.security.domain;

import com.sundeinfo.core.domain.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = true)
public abstract class AdminDomain extends Entity {

    private long userId;

    private byte level;

}