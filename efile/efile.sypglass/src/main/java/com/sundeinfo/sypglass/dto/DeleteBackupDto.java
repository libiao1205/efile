package com.sundeinfo.sypglass.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class DeleteBackupDto implements Serializable {
    private String month;
    private String state;
}
