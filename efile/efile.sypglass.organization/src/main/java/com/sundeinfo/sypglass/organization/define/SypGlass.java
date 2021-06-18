package com.sundeinfo.sypglass.organization.define;

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

}
