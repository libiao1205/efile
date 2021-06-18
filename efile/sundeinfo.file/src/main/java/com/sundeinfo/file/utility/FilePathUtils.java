package com.sundeinfo.file.utility;

import com.sundeinfo.file.define.FileType;
import com.sundeinfo.file.define.FileTypeDefine;

public class FilePathUtils {

    /**
     * 查看文件类型(防止参数中存在.点号或者其他特殊字符，所以先抽取文件名，然后再获取文件类型)
     */
    public static FileType getFileType(String fileName) {
        String fileType = fileName.substring(fileName.lastIndexOf(".") + 1);
        if (FileTypeDefine.PictureTypes.contains(fileType.toLowerCase())) {
            return FileType.picture;
        }
        if (FileTypeDefine.ArchiveTypes.contains(fileType.toLowerCase())) {
            return FileType.compress;
        }
        if (FileTypeDefine.OfficeTypes.contains(fileType.toLowerCase())) {
            return FileType.office;
        }
        if (FileTypeDefine.TxtTypes.contains(fileType.toLowerCase())) {
            return FileType.simText;
        }
        if (FileTypeDefine.MediaTypes.contains(fileType.toLowerCase())) {
            return FileType.media;
        }
        if(FileTypeDefine.PdfTypes.contains(fileType.toLowerCase())){
            return FileType.pdf;
        }
        if(FileTypeDefine.cadTypes.contains(fileType.toLowerCase())){
            return FileType.cad;
        }
        return FileType.other;
    }

    public static String getFileName(String fileName){
        return fileName.substring(0, + fileName.lastIndexOf("."));
    }

    public static String getFileSuffix(String fileName) {
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }

}
