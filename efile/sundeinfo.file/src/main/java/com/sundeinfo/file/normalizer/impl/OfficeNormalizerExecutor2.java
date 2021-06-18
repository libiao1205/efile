package com.sundeinfo.file.normalizer.impl;

import com.sundeinfo.file.component.FileOperator;
import com.sundeinfo.file.model.FilePreviewAttribute;
import com.sundeinfo.file.normalizer.FileNormalizerExecutor;
import org.jodconverter.JodConverter;
import org.jodconverter.document.DefaultDocumentFormatRegistry;
import org.jodconverter.office.LocalOfficeManager;
import org.jodconverter.office.OfficeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;

import static java.util.UUID.randomUUID;

//@Service("officeNormalizerExecutor")
public class OfficeNormalizerExecutor2 extends AbstractNormalizerExecutor<OfficeNormalizerExecutor2> implements FileNormalizerExecutor {

    @Value("${file.folderTmpPath}")
    private String tmpPath;

    @Autowired
    private FileOperator fileOperator;

    @Override
    public FilePreviewAttribute doNormalizer(File file) {

        attribute.setUrl(createPreviewUrl(tmpAccessPath + file.getName()));
        return attribute;
    }

}
