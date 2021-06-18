package com.sundeinfo.security.domain;

import com.sundeinfo.core.domain.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.NotEmpty;


import javax.validation.constraints.Min;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public abstract class OrganizationDomain extends Entity {

    @Min(value = 1,message = "不能建立根目录")
    private long parentId;

    @NotEmpty(message = "组织名称不能为空")
    private String name;

    private List<Long> childrenId;

    private long positionId;

}