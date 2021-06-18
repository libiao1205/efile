package com.sundeinfo.sypglass.dto;

import com.sundeinfo.core.domain.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = false)
public class SolrStructureDto extends Entity implements Serializable {

    private String docType;

    private String code;

    private String name;

    private Integer dataType;

    private Integer multivalued;

    private Integer indexed;

    private Integer notnull;

    private Integer isKey;

    private Integer clustering;

    private String comment;

    private Integer status;

}
