package com.sundeinfo.sypglass.organization.dto;

import com.sundeinfo.core.domain.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;
import java.io.Serializable;


@Data
@EqualsAndHashCode(callSuper = false)
public class TagDto extends Entity implements Serializable {

    @NotNull
    private String name;

    private byte type;

}