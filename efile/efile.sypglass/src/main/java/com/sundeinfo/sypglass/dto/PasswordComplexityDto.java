package com.sundeinfo.sypglass.dto;

import lombok.Data;

import java.io.Serializable;
@Data
public class PasswordComplexityDto implements Serializable {
    private String psdCheck;
    private String psdLength;
}
