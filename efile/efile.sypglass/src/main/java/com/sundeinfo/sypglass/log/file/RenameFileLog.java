package com.sundeinfo.sypglass.log.file;

import com.sundeinfo.core.log.LogInfo;
import com.sundeinfo.sypglass.dto.DocumentEditDto;
import com.sundeinfo.sypglass.dto.FileDto;

public class RenameFileLog extends LogInfo {

    public String getLog(){

        return String.format("[%s] 用户[%s]重命名了文件:[%s]，执行时间:%s ms",getDateString(),getUserName(),getFileName(),getExecTime());
    }

    private String getFileName(){
        if(DocumentEditDto.class.isAssignableFrom(joinPoint.getArgs()[0].getClass())){
            DocumentEditDto documentEditDto = (DocumentEditDto)joinPoint.getArgs()[0];
            return documentEditDto.getOrgName() + "." + documentEditDto.getSuffix();
        }
        return "未获得文件名";
    }
}
