package com.sundeinfo.sypglass.dto;

import com.sundeinfo.core.domain.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
public class UserGroupDto extends Entity implements Serializable {

    private String name;

    private byte status;

    private byte type;

    private List<UserDto> users;
}