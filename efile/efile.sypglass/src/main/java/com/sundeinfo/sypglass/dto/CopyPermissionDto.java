package com.sundeinfo.sypglass.dto;

import com.sundeinfo.core.domain.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
public class CopyPermissionDto extends Entity implements Serializable {

    private int isMove;

    private List<UserDto> from;

    private List<UserDto> to;

}
