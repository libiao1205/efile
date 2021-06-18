package com.sundeinfo.sypglass.log.multi;

import com.sundeinfo.core.log.LogInfo;
import com.sundeinfo.sypglass.define.SypGlass;
import com.sundeinfo.sypglass.dto.FileDto;
import com.sundeinfo.sypglass.model.Document;

import java.util.ArrayList;
import java.util.List;

public class DeleteDocumentsLog extends LogInfo {

    public String getLog(){

        return String.format("[%s] 用户[%s]删除了多个资源:[%s]，执行时间:%s ms",getDateString(),getUserName(),getFileName(),getExecTime());
    }

    private String getFileName(){
        StringBuilder result = new StringBuilder();
        List<Document> documents = new ArrayList<>();
        if (joinPoint.getArgs()[0].getClass().isAssignableFrom(documents.getClass())){
            documents = (List<Document>)joinPoint.getArgs()[0];
            for (Document document:documents) {
                if (SypGlass.RESOURCE_TYPE_FOLDER.equals(document.getKind())){
                    result.append(document.getName()).append("/");
                }else {
                    result.append(document.getName()).append(".").append(document.getSuffix()).append("/");
                }
            }
            return result.toString().substring(0,result.toString().lastIndexOf("/"));
        }
        return "未获得资源列表名";
    }
}
