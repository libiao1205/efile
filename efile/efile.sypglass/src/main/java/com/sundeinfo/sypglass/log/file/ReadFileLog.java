package com.sundeinfo.sypglass.log.file;

import com.sundeinfo.core.log.LogInfo;
import com.sundeinfo.core.request.RequestState;
import com.sundeinfo.core.request.result.Result;
import com.sundeinfo.sypglass.dto.FileDto;
import org.springframework.http.ResponseEntity;

public class ReadFileLog extends LogInfo {

    public String getLog(){

        return String.format("[%s] 用户[%s]查看了文件:[%s]，执行时间:%s ms",getDateString(),getUserName(),getFileName(),getExecTime());
    }

    private String getFileName(){

        if (returnValue != null){
            if (returnValue.getClass().isAssignableFrom(ResponseEntity.class)){
                if (((ResponseEntity) returnValue).getBody() != null) {
                    if (((Result) ((ResponseEntity) returnValue).getBody()).getC() == 0){
                        Object object = ((Result) ((ResponseEntity) returnValue).getBody()).getCtt();
                        if (FileDto.class.isAssignableFrom(object.getClass())){
                            FileDto fileDto = (FileDto)object;
                            return fileDto.getName() + "." + fileDto.getSuffix();
                        }
                    }else if (((Result) ((ResponseEntity) returnValue).getBody()).getC() == RequestState.FILE_STATUS_DELETE.id()){
                        return "已删除数据,无法查询";
                    }
                }
            }
        }
        return "未获得文件名";
    }
}
