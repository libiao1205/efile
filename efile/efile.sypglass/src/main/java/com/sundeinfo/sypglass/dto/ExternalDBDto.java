package com.sundeinfo.sypglass.dto;

import com.sundeinfo.core.domain.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
public class ExternalDBDto extends Entity implements Serializable {

    private String dbName;

    private String aliasName;

    private String ip;

    private String username;

    private String password;

    private String driverName;

    private String port;

    private String mapperPath;

    private String mapperPackage;

    private String comment;

    private Date syncTime;

    private Integer status;

    private List<ExternalTableDto> tables;

}
