package com.sundeinfo.sypglass.dto;

import com.sundeinfo.security.domain.AdminDomain;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
public class AdminDto extends AdminDomain implements Serializable {

    private long deptId;

    private byte status;

    private List<AdminFolderDto> adminFolders;

    public String getAdminFoldersName(){
        String value = "";
        if (this.adminFolders == null || this.adminFolders.size() == 0) return value;
        for (AdminFolderDto adminFolder:this.adminFolders) {
            value = value + adminFolder.getFolderName() + "、";
        }
        return value.substring(0,value.length() - 1);
    }
}
