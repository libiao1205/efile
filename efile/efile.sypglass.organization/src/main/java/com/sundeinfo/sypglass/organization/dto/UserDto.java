package com.sundeinfo.sypglass.organization.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sundeinfo.security.domain.UserDomain;
import com.sundeinfo.sypglass.organization.model.UserInfo;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
public class UserDto extends UserDomain implements Serializable {

    private byte status;

    private AdminDto admin;

    private List<DepartmentDto> departments;

    private UserInfoDto info;

    private List<TagDto> tags;

    private List<UserSettingDto> settings;

    public UserDto() {
        departments = new ArrayList<>();
        info = new UserInfoDto();
        tags = new ArrayList<>();
        settings = new ArrayList<>();
    }

    public void addDepartment(DepartmentDto department){
        this.departments.add(department);
    }

    public void addTag(TagDto tag){
        this.tags.add(tag);
    }

    @JsonIgnore
    public String getPassword() {
        return super.getPassword();
    }

    public int getAdminStatus(){
        if (admin == null) return 0;
        return admin.getLevel();
    }

    public boolean isEmptyInfo(){
        return info == null || info.isEmpty();
    }
}
