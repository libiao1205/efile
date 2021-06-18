package com.sundeinfo.sypglass.log.action;

import com.sundeinfo.core.log.LogInfo;

public class SearchLog extends LogInfo {
    public String getLog(){

        return String.format("[%s] 用户[%s]检索了关键字:[%s]，执行时间:%s ms",getDateString(),getUserName(),getKey(),getExecTime());
    }

    private String getKey(){
        if(joinPoint.getArgs()[0].equals(0) && joinPoint.getArgs()[2] != null){
            return joinPoint.getArgs()[2].toString();
        }else{
            super.setNeedful(false);
        }
        return "未获得关键字";
    }
}
