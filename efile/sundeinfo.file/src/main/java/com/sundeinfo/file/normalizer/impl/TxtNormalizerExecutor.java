package com.sundeinfo.file.normalizer.impl;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;
import com.sundeinfo.file.component.FileOperator;
import com.sundeinfo.file.model.FilePreviewAttribute;
import com.sundeinfo.file.normalizer.ExecutorFactory;
import com.sundeinfo.file.normalizer.FileNormalizerExecutor;
import com.sundeinfo.file.utility.EncodeUtils;
import info.monitorenter.cpdetector.io.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.charset.Charset;

import static java.util.UUID.randomUUID;

@Service("txtNormalizerExecutor")
public class TxtNormalizerExecutor extends AbstractNormalizerExecutor<TxtNormalizerExecutor> implements FileNormalizerExecutor {

    @Value("${file.folderTmpPath}")
    private String tmpPath;

    @Autowired
    private FileOperator fileOperator;

    @Override
    public FilePreviewAttribute doNormalizer(File file) {
        String fileName = tmpPath + randomUUID().toString() + ".pdf";
        File newFile = fileOperator.create(fileName);
        try {
            //获取txt文本的编码类型
            String charsetName = EncodeUtils.getFileEncode(file.getPath());
            //直接把字体放到项目的resources中，因为服务器是在linux系统
            //String fontPath = ExecutorFactory.class.getClassLoader().getResource("font/simhei.ttf").getPath();
            //BaseFont baseFont = BaseFont.createFont(fontPath, BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
            BaseFont baseFont = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
            Font FontChinese = new Font(baseFont, 12, Font.NORMAL);
            FileOutputStream out = new FileOutputStream(fileName);
            Rectangle rect = new Rectangle(PageSize.A4.rotate());
            Document document = new Document(rect);
            PdfWriter writer = PdfWriter.getInstance(document, out);
            document.open();
            Paragraph p = new Paragraph();
            p.setFont(FontChinese);
            FileInputStream fileInstream = new FileInputStream(new File(file.getPath()));
            InputStreamReader isr = new InputStreamReader(fileInstream, charsetName);  //根据txt文本的编码获取流
            BufferedReader read = new BufferedReader(isr);

            String line = read.readLine();
            while (line != null) {
                System.out.println(line);
                p.add(line + "\n");
                line = read.readLine();
            }
            read.close();
            document.add(p);
            document.close();
            if (newFile != null) {
                attribute.setUrl(createPreviewUrl(tmpAccessPath + newFile.getName()));
            }
            return attribute;
        } catch (DocumentException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }



}