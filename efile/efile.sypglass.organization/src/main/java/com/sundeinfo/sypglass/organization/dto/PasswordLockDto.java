package com.sundeinfo.sypglass.organization.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class PasswordLockDto implements Serializable {
    private String psdMistakeCount;
    private String state;
}
