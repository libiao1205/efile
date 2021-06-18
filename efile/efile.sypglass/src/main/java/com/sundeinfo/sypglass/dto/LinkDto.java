package com.sundeinfo.sypglass.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sundeinfo.core.domain.Entity;
import com.sundeinfo.sypglass.define.LinkStatus;
import com.sundeinfo.sypglass.model.LinkResource;
import com.sundeinfo.sypglass.utility.DateUtils;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
public class LinkDto extends Entity implements Serializable {

    private String name;

    @NotEmpty(message = "外链编码不能为空")
    private String randomFolderName;

    private String fullUrl;

    private String password;

    private long userId;

    private UserDto user;

    private Date startDate;

    private Date endDate;

    private int days;

    private byte isDelete;

    private long deleteUser;

    private Date deleteDate;

    private LinkStatus status;

    private List<LinkResourceDto> resources;

    public LinkStatus getStatus(){
        if (getIsDelete() == 1) return LinkStatus.DELETE;
        if (getDays() == 0) return LinkStatus.NORMAL;
        if (DateUtils.getDateDiff(getStartDate(),DateUtils.getNow()) > getDays()) return LinkStatus.EXPIRE;
        return LinkStatus.NORMAL;
    }
}
