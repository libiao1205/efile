package com.sundeinfo.sypglass.dto;

import com.sundeinfo.security.domain.UserDomain;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @author chihai
 * @ClassName PasswordDto
 * @Description TODO
 * @CreateTime 2018/12/5 17:57
 * @Version V1.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class PasswordDto extends UserDomain implements Serializable {
private String checkPass1;
}

