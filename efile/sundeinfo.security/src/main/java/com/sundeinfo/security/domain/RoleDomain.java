package com.sundeinfo.security.domain;

import com.sundeinfo.core.domain.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public abstract class RoleDomain extends Entity {

    private long userId;

    private long organizationId;

    private long positionId;

    private List<Long> rolePermissionIds;

}