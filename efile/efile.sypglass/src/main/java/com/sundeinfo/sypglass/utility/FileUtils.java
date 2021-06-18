package com.sundeinfo.sypglass.utility;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.text.DecimalFormat;

public class FileUtils {

    private static Logger logger = LoggerFactory.getLogger(FileUtils.class);

    // 文件大小转换
    public static String getFileSize(MultipartFile file){
        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        long fileSize = file.getSize();
        if (fileSize < 1024) {
            return decimalFormat.format((double) fileSize) + "B";
        } else if (fileSize < 1048576) {
            return decimalFormat.format((double) fileSize / 1024) + "K";
        } else if (fileSize < 1073741824) {
            return decimalFormat.format((double) fileSize / 1048576) + "M";
        } else {
            return decimalFormat.format((double) fileSize / 1073741824) + "G";
        }
    }

    public static void writeFile(String filepath,String content){
        File file = new File(filepath);
        try {
            file.createNewFile();
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(new FileOutputStream(file), "utf-8"));
            writer.write(content);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static boolean deleteFile(String delpath) throws Exception {
        try {
            File file = new File(delpath);
            // 当且仅当此抽象路径名表示的文件存在且 是一个目录时，返回 true
            if (!file.isDirectory()) {
                file.delete();
            } else if (file.isDirectory()) {
                String[] filelist = file.list();
                for (int i = 0; i < filelist.length; i++) {
                    File delfile = new File(delpath + "\\" + filelist[i]);
                    if (!delfile.isDirectory()) {
                        delfile.delete();
                    } else if (delfile.isDirectory()) {
                        deleteFile(delpath + "\\" + filelist[i]);
                    }
                }
                file.delete();
            }
        } catch (FileNotFoundException e) {
            logger.error("文件删除时异常发生：" + e.getMessage());
            e.printStackTrace();
        }
        return true;
    }

    public static boolean moveTo(String filePath,String targetPath){
        try {
            File file = new File(filePath);
            if (file.renameTo(new File(targetPath + file.getName()))) {
                logger.debug("文件："+filePath+" 转移位置完成");
                return true;
            } else {
                logger.error("文件："+filePath+" 转移位置失败：原因未知。");
                return false;
            }
        } catch (Exception e) {
            logger.error("文件："+filePath+" 转移位置发生异常:" + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }


}
