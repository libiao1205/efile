package com.sundeinfo.sypglass.relay.utility;

import com.sundeinfo.sypglass.relay.Application;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

public class FileUtils {
    private static Logger logger = LoggerFactory.getLogger(Application.class);

    public static void rename(File file, String newName){
        String filename = file.getParent() + "/" + newName;
        file.renameTo(new File(filename));
    }

    public static MultipartFile getMulFileByPath(String path) {
        FileItem fileItem = createFileItem(path);
        return new CommonsMultipartFile(fileItem);
    }

    private static FileItem createFileItem(String filePath)
    {
        FileItemFactory factory = new DiskFileItemFactory(16, null);
        String textFieldName = "textField";
        int num = filePath.lastIndexOf(".");
        String extFile = filePath.substring(num);
        FileItem item = factory.createItem(textFieldName, "text/plain", true,
                "fileName" + extFile);
        File file = new File(filePath);
        int bytesRead = 0;
        byte[] buffer = new byte[8192];
        try
        {
            FileInputStream fis = new FileInputStream(file);
            OutputStream os = item.getOutputStream();
            while ((bytesRead = fis.read(buffer, 0, 8192)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
            os.close();
            fis.close();
            logger.debug("文件："+filePath+" 转换成MultipartFile完成");
        }
        catch (IOException e)
        {
            logger.error("文件："+filePath+" 转换成MultipartFile发生异常:" + e.getMessage());
            e.printStackTrace();
        }
        return item;
    }
}
