package com.sundeinfo.file.fulltext.impl;

import org.apache.poi.hslf.extractor.PowerPointExtractor;
import org.apache.poi.hwpf.HWPFDocument;

import java.io.File;
import java.io.FileInputStream;

public class PPTReader extends DefaultReader {

    public PPTReader(File file) {
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
            PowerPointExtractor extractor=new PowerPointExtractor(fileInputStream);
            context = extractor.getText();
            extractor.close();
            fileInputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return context;
    }

}
