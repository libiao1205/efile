package com.sundeinfo.file.fulltext.impl;

import com.itextpdf.text.pdf.parser.PdfTextExtractor;
import java.io.File;

public class PdfReader extends DefaultReader {

    public PdfReader(File file) {
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
        try {
            com.itextpdf.text.pdf.PdfReader reader = new com.itextpdf.text.pdf.PdfReader(file.getPath());
            int pageNum = reader.getNumberOfPages();
            for(int i=1;i<=pageNum;i++){
                context.append(PdfTextExtractor.getTextFromPage(reader, i)).append("\n");//读取第i页的文档内容
            }
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return context.toString();
    }

    public static void main(String[] args) throws Exception {
        String path = "C:\\Users\\Jinni\\Desktop\\广州南峰填料报价20180112.pdf";
        File file = new File(path);
        PdfReader reader = new PdfReader(file);
        String value = reader.read();
    }

}
