package com.sundeinfo.security.domain;

import com.sundeinfo.core.domain.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public abstract class RolePermissionDomain extends Entity {

    long roleId;

    long memberId;

    byte type;

    String code;

    long resourceId;

    int value;

}