package com.sundeinfo.file.fulltext.impl;

import org.apache.poi.hslf.extractor.PowerPointExtractor;
import org.apache.poi.ooxml.POIXMLDocument;
import org.apache.poi.xslf.extractor.XSLFPowerPointExtractor;

import java.io.File;
import java.io.FileInputStream;

public class PPTxReader extends DefaultReader {

    public PPTxReader(File file) {
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
            XSLFPowerPointExtractor xslfPowerPointExtractor = new XSLFPowerPointExtractor(POIXMLDocument.openPackage(file.getPath()));
            context = xslfPowerPointExtractor.getText();
            xslfPowerPointExtractor.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return context;
    }

}
