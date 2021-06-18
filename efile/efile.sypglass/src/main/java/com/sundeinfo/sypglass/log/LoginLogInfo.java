package com.sundeinfo.sypglass.log;

import com.sundeinfo.core.log.LogInfo;
import com.sundeinfo.core.request.result.Result;
import com.sundeinfo.security.strategy.token.AuthenticationResponse;
import com.sundeinfo.sypglass.dto.FolderDto;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.ResponseEntity;

@Data
@EqualsAndHashCode(callSuper = false)
public class LoginLogInfo extends LogInfo {

    public String getLog(){

        return String.format("[%s] 用户[%s]进行了登录，执行时间(%s)ms",getDateString(),getName(getReturnValue()),getExecTime());
    }

    private String getName(Object result){
        try {
            Object ctt = getResultCttObject(getReturnValue());
            if(ctt instanceof AuthenticationResponse) {
                return ((AuthenticationResponse)ctt).getName();
            }
            return "";
        }catch (Exception ex){
            return "";
        }
    }

    private Object getResultCttObject(Object result){
        if (((ResponseEntity)result).getBody() instanceof Result) {
            return ((Result)((ResponseEntity)result).getBody()).getCtt();
        }
        return null;
    }
}
