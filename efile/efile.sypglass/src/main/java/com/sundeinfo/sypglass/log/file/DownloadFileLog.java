package com.sundeinfo.sypglass.log.file;

import com.sundeinfo.core.log.LogInfo;
import com.sundeinfo.core.request.result.Result;
import com.sundeinfo.sypglass.dto.FilePreviewDto;
import org.springframework.http.ResponseEntity;

public class DownloadFileLog extends LogInfo {

    public String getLog(){

        return String.format("[%s] 用户[%s]下载了文件:[%s]，执行时间:%s ms",getDateString(),getUserName(),getFileName(),getExecTime());
    }

    private String getFileName(){
        if (returnValue != null){
            if (FilePreviewDto.class.isAssignableFrom(returnValue.getClass())){
                FilePreviewDto filePreviewDto = (FilePreviewDto)returnValue;
                return filePreviewDto.getName() + "." + filePreviewDto.getSuffix();
            }
        }
        return "未获得文件名";
    }
}
