package com.sundeinfo.sypglass.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class ConfigDto implements Serializable {

    private String key;

    private String value;

    public ConfigDto(String key, String value) {
        this.key = key;
        this.value = value;
    }
}