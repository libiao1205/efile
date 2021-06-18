package com.sundeinfo.sypglass.log.file;

import com.sundeinfo.core.log.LogInfo;
import org.springframework.web.multipart.MultipartFile;

public class AddFileLog extends LogInfo {

    public String getLog(){

        return String.format("[%s] 用户[%s]上传了新文件:[%s]，执行时间:%s ms",getDateString(),getUserName(),getFileName(),getExecTime());
    }

    private String getFileName(){
        StringBuilder result = new StringBuilder("未获得文件名");
        MultipartFile[] multipartFile ={};
        Object args = joinPoint.getArgs()[0];
        if (args.getClass().isAssignableFrom(multipartFile.getClass())){
            MultipartFile[] files = (MultipartFile[])joinPoint.getArgs()[0];
            result = new StringBuilder();
            for (MultipartFile file:files) {
                result.append(file.getOriginalFilename()).append("/");
            }
            return result.toString().substring(0,result.toString().lastIndexOf("/"));
        }
        return result.toString();
    }
}
