package com.sundeinfo.file.component;


import com.sundeinfo.core.mvc.component.AbstractComponent;
import com.sundeinfo.file.model.FilePreviewAttribute;
import com.sundeinfo.file.normalizer.ExecutorFactory;
import com.sundeinfo.file.normalizer.FileNormalizerExecutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;

@Component("FilePreviewNormalizeExecutor")
public class FilePreviewNormalizeManager extends AbstractComponent<FilePreviewNormalizeManager> {

    @Autowired
    private ExecutorFactory executorFactory;

    public FilePreviewAttribute normalize(File file){
        FileNormalizerExecutor executor = executorFactory.get(file.getName());
        return executor.doNormalizer(file);
    }


}