package com.sundeinfo.security.domain;

import com.sundeinfo.core.domain.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public abstract class PermissionDomain extends Entity {

    String code;

    String function;

    String action;

    String kind;

    int value;
}