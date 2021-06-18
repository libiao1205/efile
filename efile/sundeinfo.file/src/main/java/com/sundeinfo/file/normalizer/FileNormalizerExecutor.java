package com.sundeinfo.file.normalizer;

import com.sundeinfo.file.model.FilePreviewAttribute;

import java.io.File;

public interface FileNormalizerExecutor {

    void setAttribute(FilePreviewAttribute attribute);

    FilePreviewAttribute doNormalizer(File file);

    String createPreviewUrl(String fileName);

}
