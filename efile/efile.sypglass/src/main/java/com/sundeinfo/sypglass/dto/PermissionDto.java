package com.sundeinfo.sypglass.dto;

import com.sundeinfo.security.domain.PermissionDomain;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
public class PermissionDto extends PermissionDomain implements Serializable {

    private String description;

}
