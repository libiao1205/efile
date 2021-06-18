package com.sundeinfo.sypglass.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class RoleException extends Exception {

    private long member;

    public RoleException(String message, long member) {
        super(message);
        this.member = member;
    }
}