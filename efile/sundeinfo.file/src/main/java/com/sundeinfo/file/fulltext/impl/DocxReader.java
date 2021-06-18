package com.sundeinfo.file.fulltext.impl;

import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

import java.io.File;
import java.io.FileInputStream;

public class DocxReader extends DefaultReader {

    public DocxReader(File file) {
        super(file);
    }

    @Override
    public boolean isSupport() {
        return true;
    }

    @Override
    public String read() {
        if (file == null){
            return "";
        }
        String context = "";
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            XWPFDocument docx = new XWPFDocument(fileInputStream);
            XWPFWordExtractor extractor = new XWPFWordExtractor(docx);
            context = extractor.getText();
            extractor.close();
            fileInputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return context;
    }
}
