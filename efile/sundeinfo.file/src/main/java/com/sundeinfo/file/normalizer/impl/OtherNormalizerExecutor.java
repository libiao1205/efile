package com.sundeinfo.file.normalizer.impl;

import com.sundeinfo.file.model.FilePreviewAttribute;
import com.sundeinfo.file.normalizer.FileNormalizerExecutor;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
public class OtherNormalizerExecutor extends AbstractNormalizerExecutor<OtherNormalizerExecutor> implements FileNormalizerExecutor {



    @Override
    public FilePreviewAttribute doNormalizer(File file) {
        return attribute;
    }

    @Override
    public String createPreviewUrl(String fileName) {
        return null;
    }
}
