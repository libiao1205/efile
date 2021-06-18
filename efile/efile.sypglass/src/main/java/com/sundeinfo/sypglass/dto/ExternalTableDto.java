package com.sundeinfo.sypglass.dto;

import com.sundeinfo.core.domain.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
public class ExternalTableDto extends Entity implements Serializable {

    private String dbname;

    private String tablename;

    private Long datacount;

    private String comment;

    private Date synctime;

    private List<ExternalColumnDto> columns;

}
