package com.sundeinfo.sypglass.log.folder;

import com.sundeinfo.core.log.LogInfo;
import com.sundeinfo.sypglass.dto.FolderDto;

public class AddFolderLog extends LogInfo {

    public String getLog(){

        return String.format("[%s] 用户[%s]新建了文件夹:[%s]，执行时间:%s ms",getDateString(),getUserName(),getFileName(),getExecTime());
    }

    private String getFileName(){
        if(FolderDto.class.isAssignableFrom(joinPoint.getArgs()[0].getClass())){
            FolderDto folderDto = (FolderDto)joinPoint.getArgs()[0];
            return folderDto.getName();
        }
        return "未获得文件夹名";
    }
}
