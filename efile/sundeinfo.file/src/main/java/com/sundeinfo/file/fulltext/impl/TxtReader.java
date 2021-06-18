package com.sundeinfo.file.fulltext.impl;

import com.sundeinfo.core.utility.StringUtils;
import com.sundeinfo.file.utility.EncodeUtils;
import org.apache.poi.ooxml.POIXMLDocument;
import org.apache.poi.xslf.extractor.XSLFPowerPointExtractor;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

public class TxtReader extends DefaultReader {

    public TxtReader(File file) {
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
        StringBuilder context = new StringBuilder();
        String line = null;
        try {
            //获取txt文本的编码类型
            String charsetName = EncodeUtils.getFileEncode(file.getPath());
            if (StringUtils.isBlank(charsetName)){
                charsetName = "GBK";
            }
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), charsetName));// 构造一个BufferedReader类来读取文件

            while ((line = br.readLine()) != null) {// 使用readLine方法，一次读一行
                context.append(line).append("\n");
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return context.toString();
    }

}
