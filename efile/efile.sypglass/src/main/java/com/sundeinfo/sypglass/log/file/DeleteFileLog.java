package com.sundeinfo.sypglass.log.file;

import com.sundeinfo.core.log.LogInfo;
import com.sundeinfo.sypglass.dto.FileDto;

public class DeleteFileLog extends LogInfo {

    public String getLog(){

        return String.format("[%s] 用户[%s]删除了文件:[%s]，执行时间:%s ms",getDateString(),getUserName(),getFileName(),getExecTime());
    }

    private String getFileName(){
        if(FileDto.class.isAssignableFrom(joinPoint.getArgs()[0].getClass())){
            FileDto fileDto = (FileDto)joinPoint.getArgs()[0];
            return fileDto.getName() + "." + fileDto.getSuffix();
        }
        return "未获得文件名";
    }
}
