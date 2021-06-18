package com.sundeinfo.sypglass.log.file;

import com.sundeinfo.core.log.LogInfo;
import org.springframework.web.multipart.MultipartFile;

public class UpdateFileLog extends LogInfo {

    public String getLog(){

        return String.format("[%s] 用户[%s]更新了文件:[%s]，执行时间:%s ms",getDateString(),getUserName(),getFileName(),getExecTime());
    }

    private String getFileName(){
        StringBuilder result = new StringBuilder("未获得文件名");
        Object id = joinPoint.getArgs()[1];

        Object args = joinPoint.getArgs()[0];
        if (MultipartFile.class.isAssignableFrom(args.getClass())){
            MultipartFile file = (MultipartFile)joinPoint.getArgs()[0];
            result = new StringBuilder(file.getOriginalFilename());
        }
        return result.toString();
    }
}
