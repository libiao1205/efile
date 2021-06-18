package com.sundeinfo.sypglass.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class PasswordExceedDto implements Serializable {
    private String usableDay;
    private String aheadDay;
    private String state;
}
