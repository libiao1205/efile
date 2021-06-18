package com.sundeinfo.file.component;

import com.sundeinfo.core.mvc.component.AbstractComponent;
import org.springframework.stereotype.Component;

import java.io.File;

@Component("FileOperator")
public class FileOperator extends AbstractComponent<FileOperator> {


    public File create(String fileName){
        File file = new File(fileName);
        if(!file.getParentFile().exists()){ //判断文件父目录是否存在
            file.getParentFile().mkdir();
        }
        return file;
    }


}