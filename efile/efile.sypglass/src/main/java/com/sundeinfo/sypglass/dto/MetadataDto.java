package com.sundeinfo.sypglass.dto;

import com.sundeinfo.core.domain.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = false)
public class MetadataDto extends Entity implements Serializable {

    private Integer docType;

    private Integer dataProperty;

    private Integer dataType;

    private String code;

    private String name;

    private Integer seq;

    private Integer size;

    private Integer notnull;

    private Long solrId;

    private String solrName;

    private Integer status;

    private String comment;

    private SolrStructureDto solrStructure;

}
