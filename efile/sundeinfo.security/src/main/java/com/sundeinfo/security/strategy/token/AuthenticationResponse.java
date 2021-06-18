package com.sundeinfo.security.strategy.token;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class AuthenticationResponse implements Serializable {
    private static final long serialVersionUID = 1250166508152483573L;

    private String captcha;

    private String name;

    private boolean exceed;

    private String token;

    private String operation;

    private int level;

    private String mistakeMsg;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }
}
