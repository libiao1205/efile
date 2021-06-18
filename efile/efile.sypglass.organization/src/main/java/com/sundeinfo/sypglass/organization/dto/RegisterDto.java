package com.sundeinfo.sypglass.organization.dto;

import com.sundeinfo.core.domain.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
public class RegisterDto extends Entity implements Serializable {

    private String name;

    private String username;

    private String password;

    private byte status;

    private long oldDeptId;

    private long deptId;

    private String deptName;

    private byte level;

    private List<DepartmentDto> departments;

    private AdminDto admin;

    public void addDepartment(DepartmentDto department){
        this.departments.add(department);
    }

    public RegisterDto() {
        departments = new ArrayList<>();
    }

}
