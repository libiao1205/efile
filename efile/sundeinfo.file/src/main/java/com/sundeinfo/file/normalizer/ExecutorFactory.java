package com.sundeinfo.file.normalizer;

import com.sundeinfo.core.mvc.service.AbstractService;
import com.sundeinfo.file.define.FileType;
import com.sundeinfo.file.define.FileTypeDefine;
import com.sundeinfo.file.model.FilePreviewAttribute;
import com.sundeinfo.file.utility.FilePathUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Map;


@Service
public class ExecutorFactory extends AbstractService<ExecutorFactory,FilePreviewAttribute> {

    @Autowired
    ApplicationContext context;

    public FileNormalizerExecutor get(String fileName){
        Map<String, FileNormalizerExecutor> executorMap = context.getBeansOfType(FileNormalizerExecutor.class);
        FilePreviewAttribute attribute = getFileAttribute(fileName);
        FileNormalizerExecutor executor = executorMap.get(attribute.getType().getInstanceName());
        if (executor == null){
            logger.warn("没有找到文件的对应处理逻辑：" +fileName);
            executor = executorMap.get(FileType.other.getInstanceName());
        }
        executor.setAttribute(attribute);
        return executor;
    }

    public FilePreviewAttribute getFileAttribute(String fileName) {
        FilePreviewAttribute filePreviewAttribute = new FilePreviewAttribute();
        try {
            String decodedFileName = URLDecoder.decode(fileName, "utf-8");
            filePreviewAttribute.setDecodeFileName(decodedFileName);
        }catch (UnsupportedEncodingException e){
            logger.debug("解码失败");
        }
        filePreviewAttribute.setFileName(FilePathUtils.getFileName(fileName));
        filePreviewAttribute.setType(FilePathUtils.getFileType(fileName));
        filePreviewAttribute.setSuffix(FilePathUtils.getFileSuffix(fileName));

        return filePreviewAttribute;
    }


}
