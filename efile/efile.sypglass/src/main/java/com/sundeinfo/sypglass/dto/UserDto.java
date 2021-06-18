package com.sundeinfo.sypglass.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sundeinfo.security.domain.UserDomain;
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

    private List<TagDto> tags;

    private List<UserSettingDto> settings;

    public UserDto() {
        departments = new ArrayList<>();
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
}
