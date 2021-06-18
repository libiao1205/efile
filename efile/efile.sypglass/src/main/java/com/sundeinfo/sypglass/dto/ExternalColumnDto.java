package com.sundeinfo.sypglass.dto;

import com.sundeinfo.core.domain.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = false)
public class ExternalColumnDto extends Entity implements Serializable {

    private String dbname;

    private String tablename;

    private String columnname;

    private Integer seq;

    private String datatype;

    private String datatypevalue;

    private String comment;

    private Date synctime;

}
