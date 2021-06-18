package com.sundeinfo.sypglass.organization.dto;

import com.sundeinfo.security.domain.PermissionDomain;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = false)
public class PermissionDto extends PermissionDomain implements Serializable {

    private String description;

}
