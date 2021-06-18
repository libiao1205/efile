package com.sundeinfo.sypglass.dto;

import com.sundeinfo.security.domain.PositionDomain;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = false)
public class PositionDto extends PositionDomain implements Serializable {

}
