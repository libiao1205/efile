package com.sundeinfo.file.normalizer.impl;

import com.sundeinfo.file.model.FilePreviewAttribute;
import com.sundeinfo.file.normalizer.FileNormalizerExecutor;
import org.springframework.stereotype.Service;

import java.io.File;

@Service("pictureNormalizerExecutor")
public class PictureNormalizerExecutor extends AbstractNormalizerExecutor<PictureNormalizerExecutor> implements FileNormalizerExecutor {

    @Override
    public FilePreviewAttribute doNormalizer(File file) {
        attribute.setUrl(createPreviewUrl(tmpAccessPath + file.getName()));
        return attribute;
    }

}