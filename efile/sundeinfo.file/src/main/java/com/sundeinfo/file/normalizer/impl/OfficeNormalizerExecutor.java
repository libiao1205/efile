package com.sundeinfo.file.normalizer.impl;

import com.sundeinfo.core.mvc.service.AbstractService;

import com.sundeinfo.core.utility.OSinfo;
import com.sundeinfo.file.component.FileOperator;
import com.sundeinfo.file.model.FilePreviewAttribute;
import com.sundeinfo.file.normalizer.FileNormalizerExecutor;
import com.sundeinfo.file.utility.FilePathUtils;
import org.jodconverter.JodConverter;
import org.jodconverter.document.DefaultDocumentFormatRegistry;
import org.jodconverter.office.LocalOfficeManager;
import org.jodconverter.office.OfficeException;
import org.jodconverter.office.OfficeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


import java.io.File;

import static java.util.UUID.randomUUID;

@Service("officeNormalizerExecutor")
public class OfficeNormalizerExecutor extends AbstractNormalizerExecutor<OfficeNormalizerExecutor> implements FileNormalizerExecutor {

    @Value("${file.folderTmpPath}")
    private String tmpPath;

    @Autowired
    private FileOperator fileOperator;

    @Override
    public FilePreviewAttribute doNormalizer(File file) {

        final LocalOfficeManager officeManager = OSinfo.isWindows() ?
                LocalOfficeManager.install() : LocalOfficeManager.builder().officeHome("/opt/openoffice4").install().build();

        File newFile = null;
        try {

            // Start an office process and connect to the started instance (on port 8100).
            officeManager.start();

            if (DefaultDocumentFormatRegistry.XLS.getExtension().equals(attribute.getSuffix())){
                String fileName = tmpPath + randomUUID().toString() + ".pdf";
                newFile = fileOperator.create(fileName);
                JodConverter.convert(file)
                        .as(DefaultDocumentFormatRegistry.XLS).to(newFile)
                        .as(DefaultDocumentFormatRegistry.PDF).execute();

            }else if(DefaultDocumentFormatRegistry.XLSX.getExtension().equals(attribute.getSuffix())) {
                String fileName = tmpPath + randomUUID().toString() + ".pdf";
                newFile = fileOperator.create(fileName);
                JodConverter.convert(file)
                        .as(DefaultDocumentFormatRegistry.XLSX).to(newFile)
                        .as(DefaultDocumentFormatRegistry.PDF).execute();

            }else if(DefaultDocumentFormatRegistry.DOC.getExtension().equals(attribute.getSuffix())) {
                String fileName = tmpPath + randomUUID().toString() + ".pdf";
                newFile = fileOperator.create(fileName);
                JodConverter.convert(file)
                        .as(DefaultDocumentFormatRegistry.DOC).to(newFile)
                        .as(DefaultDocumentFormatRegistry.PDF).execute();

            }else if(DefaultDocumentFormatRegistry.DOCX.getExtension().equals(attribute.getSuffix())) {
                String fileName = tmpPath + randomUUID().toString() + ".pdf";
                newFile = fileOperator.create(fileName);
                JodConverter.convert(file)
                        .as(DefaultDocumentFormatRegistry.DOCX).to(newFile)
                        .as(DefaultDocumentFormatRegistry.PDF).execute();

            }else if(DefaultDocumentFormatRegistry.PPT.getExtension().equals(attribute.getSuffix())) {
                String fileName = tmpPath + randomUUID().toString() + ".pdf";
                newFile = fileOperator.create(fileName);
                JodConverter.convert(file)
                        .as(DefaultDocumentFormatRegistry.PPT).to(newFile)
                        .as(DefaultDocumentFormatRegistry.PDF).execute();

            }else if(DefaultDocumentFormatRegistry.PPTX.getExtension().equals(attribute.getSuffix())) {
                String fileName = tmpPath + randomUUID().toString() + ".pdf";
                newFile = fileOperator.create(fileName);
                JodConverter.convert(file)
                        .as(DefaultDocumentFormatRegistry.PPTX).to(newFile)
                        .as(DefaultDocumentFormatRegistry.PDF).execute();
            } else{
                newFile = null;
            }
        }catch (Exception e){
            e.printStackTrace();
        } finally {
            OfficeUtils.stopQuietly(officeManager);
        }
        if (newFile != null) {
            attribute.setUrl(createPreviewUrl(tmpAccessPath + newFile.getName()));
        }
        return attribute;
    }

}
