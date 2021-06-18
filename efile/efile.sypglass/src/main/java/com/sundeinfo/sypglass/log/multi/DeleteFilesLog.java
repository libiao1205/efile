package com.sundeinfo.sypglass.log.multi;

import com.sundeinfo.core.log.LogInfo;
import com.sundeinfo.sypglass.dto.FileDto;

import java.util.ArrayList;
import java.util.List;

public class DeleteFilesLog extends LogInfo {

    public String getLog(){

        return String.format("[%s] 用户[%s]删除了文件:[%s]，执行时间:%s ms",getDateString(),getUserName(),getFileName(),getExecTime());
    }

    private String getFileName(){
        StringBuilder result = new StringBuilder();
        List<FileDto> fileDtos = new ArrayList<>();
        if (joinPoint.getArgs()[0].getClass().isAssignableFrom(fileDtos.getClass())){
            fileDtos = (List<FileDto>)joinPoint.getArgs()[0];
            for (FileDto fileDto:fileDtos) {
                result.append(fileDto.getName()).append(".").append(fileDto.getSuffix()).append("/");
            }
            return result.toString().substring(0,result.toString().lastIndexOf("/"));
        }
        return "未获得文件夹名";
    }
}
