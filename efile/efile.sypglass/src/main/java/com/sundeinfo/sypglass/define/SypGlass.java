package com.sundeinfo.sypglass.define;

import com.sundeinfo.sypglass.utility.DateUtils;
import org.springframework.beans.factory.annotation.Value;

import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SypGlass {

    public static final long ROOT_DEPT_ID = 1;

    public static final long ROOT_FOLDER_ID = 1;

    public static final String RESOURCE_TYPE_FOLDER = "folder";

    public static final String RESOURCE_TYPE_FILE = "file";

    public static final String MEMBER_KIND_DEPT = "dept";

    public static final String MEMBER_KIND_USER = "user";

    public static final String START_DATE = "2000-01-01";

    public static final String END_DATE = "2099-12-31";

    public static final String INDEFINITE_DATE = "2099-12-31";

    public static final byte DEPT_TYPE_DEPT = 1;

    public static final byte DEPT_TYPE_GROUP = 2;

    /*定时任务正常执行中*/
    public static final Byte STATUS_RUNNING = 1;
    /*定时任务暂停中*/
    public static final Byte STATUS_NOT_RUNNING = 9;

    public static String getFileStatusName(byte status){
        switch (status){
            case 1:
                return FileStatus.NORMAL.value();
            case 2:
                return FileStatus.PUBLIC.value();
            case 3:
                return FileStatus.PRIVATE.value();
            case 4:
                return FileStatus.LINK.value();
            case 9:
                return FileStatus.DELETE.value();
        }
        return FileStatus.UNKNOWN.value();
    }

    public static String getFolderStatusName(byte status){
        switch (status){
            case 1:
                return FolderStatus.NORMAL.value();
            case 2:
                return FolderStatus.PUBLIC.value();
            case 3:
                return FolderStatus.PRIVATE.value();
            case 4:
                return FolderStatus.LINK.value();
            case 9:
                return FolderStatus.DELETE.value();
        }
        return FolderStatus.UNKNOWN.value();
    }

    public static String getLinkName(Date startDate) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
        return String.format("%s开始共享的文件",sdf.format(startDate));
    }

}
