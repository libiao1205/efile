package com.sundeinfo.sypglass.organization.dto;

import com.sundeinfo.security.strategy.token.AuthenticationResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class AuthenticationResponseDto extends AuthenticationResponse {

    private Long userId;

    private String username;

}
