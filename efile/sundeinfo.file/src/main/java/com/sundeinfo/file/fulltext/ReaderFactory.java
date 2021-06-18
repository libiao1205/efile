package com.sundeinfo.file.fulltext;

import com.sundeinfo.core.mvc.service.AbstractService;
import com.sundeinfo.file.fulltext.impl.*;
import com.sundeinfo.file.model.FilePreviewAttribute;
import org.springframework.stereotype.Service;

import java.io.File;


@Service
public class ReaderFactory extends AbstractService<ReaderFactory,FilePreviewAttribute> {

    public FullTextReader get(File file){
        String fileName = file.getName();
        String suffix = fileName.substring(fileName.lastIndexOf(".") + 1);

        switch (suffix.toLowerCase()){
            case "doc":
                return new DocReader(file);
            case "docx":
                return new DocxReader(file);
            case "xls":
                return new ExcelReader(file);
            case "xlsx":
                return new ExcelReader(file);
            case "ppt":
                return new PPTReader(file);
            case "pptx":
                return new PPTxReader(file);
            case "pdf":
                return new PdfReader(file);
            case "txt":
                return new TxtReader(file);
            default:
                return new DefaultReader(file);
        }
    }
}
