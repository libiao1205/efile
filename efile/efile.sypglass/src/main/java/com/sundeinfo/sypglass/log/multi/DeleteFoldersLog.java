package com.sundeinfo.sypglass.log.multi;

import com.sundeinfo.core.log.LogInfo;
import com.sundeinfo.sypglass.dto.FolderDto;

import java.util.ArrayList;
import java.util.List;

public class DeleteFoldersLog extends LogInfo {

    public String getLog(){

        return String.format("[%s] 用户[%s]删除了文件夹:[%s]，执行时间:%s ms",getDateString(),getUserName(),getFileName(),getExecTime());
    }

    private String getFileName(){
        StringBuilder result = new StringBuilder();
        List<FolderDto> folderDtos = new ArrayList<>();
        if (joinPoint.getArgs()[0].getClass().isAssignableFrom(folderDtos.getClass())){
            folderDtos = (List<FolderDto>)joinPoint.getArgs()[0];
            for (FolderDto folderDto:folderDtos) {
                result.append(folderDto.getName()).append("/");
            }
            return result.toString().substring(0,result.toString().lastIndexOf("/"));
        }
        return "未获得文件夹名";
    }
}
