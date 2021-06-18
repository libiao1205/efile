package com.sundeinfo.sypglass.define;

public class SypGlassConfig {

    //设置部门导入标识位
    public static final String[] INPUT_DEPT_SETTING = {"input","dept"};

    //设置用户导入标识位
    public static final String[] INPUT_USER_SETTING = {"input","user"};

    //密码超期提醒
    public static final String PASSWORD_EXCEED ="password_exceed";

    //强制密码修改天数
    public static final String PASSWORD_USABLE_DAY ="passwordUsableDay";

    //提前几天提醒修改密码
    public static final String PASSWORD_AHEAD_DAY ="passwordAheadDay";

    //密码锁定
    public static final String PSD_LOCK ="psd_lock";

    //锁定密码错误次数
    public static final String PSD_MISTAKE_COUNT ="psdMistakeCount";

    //密码等级设置
    public static final String PASSWORD_COMPLEXITY ="password_complexity";

    //密码等级校验
    public static final String PSD_CHECK = "psdCheck";

    //密码规定长度
    public static final String PSD_LENGTH ="psdLength";

    //大写字母、小写字母、数字
    public static final String PSD_CHECK_TOP1 = "^(?![A-Za-z]+$)(?![A-Z0-9]+$)(?![a-z0-9]+$)(?![A-Za-z\\W]+$)(?![0-9\\W]+$)[a-zA-Z0-9\\W]{3,}$";

    //字母、数字、特殊字符
    public static final String PSD_CHECK_TOP2 = "^(?![A-Za-z0-9]+$)(?![a-z0-9\\w]+$)(?![0-9\\W]+$)(?![A-Za-z\\W]+$)(?![A-Z0-9\\w]+$)[a-zA-Z0-9\\W]{3,}$";

    //备份删除
    public static final String DELETE_BACKUP_PERIOD_NAME = "delete_backup_period";

    public static final String DELETE_BACKUP_PERIOD_CODE = "deleteBackupPeriod";

    //文档根目录logo
    public static final String FOLDER_ROOT_LOGO_NAME= "folder_root_logo";
    public static final String FOLDER_ROOT_LOGO_CODE = "folderRootLogo";

    //支持结构化数据开关
    public static final String CONSTRUCTION_DATA_SWITCH_NAME = "construction_data_switch";
    public static final String CONSTRUCTION_DATA_SWITCH_CODE = "constructionDataSwitch";

    //getSolrSynchronizationSwitch
    public static final String SOLR_SYNCHRONIZATION_SWITCH_NAME = "solr_synchronization_switch";
    public static final String SOLR_SYNCHRONIZATION_SWITCH_CODE = "solrSynchronizationSwitch";

    //solt同步开关getSoltSynchronizationSwitch
    public static final String SOLR_QUERY_TEMPLATE_NAME = "solr.query.Template";
    public static final String SOLR_QUERY_TEMPLATE_CODE = "solr.query.Template";

    //系统管理员登陆名
    public static final String ADMIN_USER_NAME = "root";

    //元数据分类
    public static final String DOC_TYPE_NAME = "doc_type";
    public static final String DOC_TYPE_CODE = "docType";

    //数据库驱动
    public static final String DB_DRIVER_NAME = "db_driver";
    public static final String DB_DRIVER_CODE = "dbDriver";

    public static void main(String arg[]){
//        System.out.println(check("Syp@1210*Ef"));
//        System.out.println(check("aaaaaaaaaa"));
//        System.out.println(check("11111"));
//        System.out.println(check("@@@$##"));
//        System.out.println(check("11@@"));
//        System.out.println(check("aaaaaaaaaa11111111"));
//        System.out.println(check("AA11"));
//        System.out.println(check("AAaa"));
//        System.out.println(check("AAaa11"));
//        System.out.println(check("AAA^^^"));
//        System.out.println(check("aaaA^^^"));
//        System.out.println(check("aaa11"));
//        System.out.println(check("aaa^^^"));
//        System.out.println(check("111^^^"));
//        System.out.println(check("AAA111$$a"));
//        System.out.println(check("aaa111$$$"));
//        System.out.println(check("$$$111AAAaaa"));
//        System.out.println(check("AAAaaa111"));
//        System.out.println(check("AAAaaa111"));
//        System.out.println(check("A1aA1a1A1"));
//        System.out.println(check("aaa111"));
//        System.out.println(check("AAA111"));
//        System.out.println(check("AAAA"));
//        System.out.println(check("aaaa"));
//        System.out.println(check("1111"));
//        System.out.println(check("$$$$$$"));
//        System.out.println(check("AAAaaa111@1Aa"));
//        System.out.println(check("AAAaaa$$"));
//        System.out.println(check("111$$$"));
        System.out.println(1&3);
        System.out.println(2&3);
        System.out.println(4&3);
        System.out.println(8&3);
        System.out.println(16&3);
        System.out.println(32&3);
        System.out.println(64&3);
        System.out.println(128&3);
    }

    private static String check(String password){

        String rule = PSD_CHECK_TOP1;

        if (password.matches(rule)){
            return password+"----->OK";
        }else{
            if (rule.equals(PSD_CHECK_TOP1)){
                return password+"----->密码必须是大写字母、小写字母与数字的组合";
            }else{
                return password+"----->密码必须是字母、数字、特殊字符的组合";
            }
        }
    }
}