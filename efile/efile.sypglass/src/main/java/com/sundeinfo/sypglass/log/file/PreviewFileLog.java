package com.sundeinfo.sypglass.log.file;

import com.sundeinfo.core.log.LogInfo;
import com.sundeinfo.core.request.RequestState;
import com.sundeinfo.core.request.result.Result;
import com.sundeinfo.sypglass.dto.FileDto;
import com.sundeinfo.sypglass.dto.FilePreviewDto;
import org.springframework.http.ResponseEntity;

public class PreviewFileLog extends LogInfo {

    public String getLog(){

        return String.format("[%s] 用户[%s]打开预览了文件:[%s]，执行时间:%s ms",getDateString(),getUserName(),getFileName(),getExecTime());
    }

    private String getFileName(){
        if (returnValue != null){
            if (returnValue.getClass().isAssignableFrom(ResponseEntity.class)){
                if (((ResponseEntity) returnValue).getBody() != null) {
                    if (((Result) ((ResponseEntity) returnValue).getBody()).getC() == 0){
                        Object object = ((Result) ((ResponseEntity) returnValue).getBody()).getCtt();
                        if (FilePreviewDto.class.isAssignableFrom(object.getClass())){
                            FilePreviewDto filePreviewDto = (FilePreviewDto)object;
                            return filePreviewDto.getName() + "." + filePreviewDto.getSuffix();
                        }
                    }else if (((Result) ((ResponseEntity) returnValue).getBody()).getC() == RequestState.FILE_STATUS_DELETE.id()){
                        return "已删除数据,预览无效";
                    }
                }
            }
        }
        return "未获得文件名";
    }
}
