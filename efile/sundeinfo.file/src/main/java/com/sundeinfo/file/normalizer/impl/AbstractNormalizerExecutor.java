package com.sundeinfo.file.normalizer.impl;

import com.sundeinfo.core.mvc.service.AbstractService;
import com.sundeinfo.file.model.FilePreviewAttribute;
import com.sundeinfo.file.normalizer.FileNormalizerExecutor;
import org.springframework.beans.factory.annotation.Value;

import java.io.File;

public abstract class AbstractNormalizerExecutor<T> extends AbstractService<AbstractNormalizerExecutor,FilePreviewAttribute> implements FileNormalizerExecutor {

    @Value("${file.tmpAccessPath}")
    protected String tmpAccessPath;

    @Value("${file.urlPath}")
    protected String urlPath;

    protected FilePreviewAttribute attribute;

    @Override
    public void setAttribute(FilePreviewAttribute attribute) {
        this.attribute = attribute;
    }

    @Override
    public abstract FilePreviewAttribute doNormalizer(File file);

    @Override
    public String createPreviewUrl(String fileName) {
        return String.format(urlPath,fileName);
    }
}
