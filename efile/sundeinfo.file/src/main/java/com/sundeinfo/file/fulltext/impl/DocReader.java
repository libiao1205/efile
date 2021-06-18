package com.sundeinfo.file.fulltext.impl;

import org.apache.poi.hwpf.HWPFDocument;

import java.io.File;
import java.io.FileInputStream;

public class DocReader extends DefaultReader {

    public DocReader(File file) {
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
            HWPFDocument doc = new HWPFDocument(fileInputStream);
            context = doc.getDocumentText();
            doc.close();
            fileInputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return context;
    }

}
