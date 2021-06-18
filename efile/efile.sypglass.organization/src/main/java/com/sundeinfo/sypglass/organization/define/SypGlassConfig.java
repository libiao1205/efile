package com.sundeinfo.sypglass.organization.define;

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
    public static final String PSD_CHECK_TOP1 = "^(?![A-Za-z]+$)(?![A-Z0-9]+$)(?![a-z0-9]+$)[a-zA-Z0-9]{3,}$";

    //字母、数字、特殊字符
    public static final String PSD_CHECK_TOP2 = "([0-9].*([a-zA-Z].*[!@#$%^&*()_+=/|-]|[!@#$%^&*()_+=/|-].*[a-zA-Z])|[a-zA-Z].*([0-9].*[!@#$%^&*()_+=/|-]|[!@#$%^&*()_+=/|-].*[0-9])|[!@#$%^&*()_+=/|-].*([0-9].*[a-zA-Z]|[a-zA-Z].*[0-9]))";

    //备份删除
    public static final String DELETE_BACKUP_PERIOD_NAME = "delete_backup_period";

    public static final String DELETE_BACKUP_PERIOD_CODE = "deleteBackupPeriod";


    public static void main(String arg[]){
        System.out.println(check("1111111111111111"));
        System.out.println(check("aaaaaaaaaa"));
        System.out.println(check("aaaaaaaaaa11111111"));
        System.out.println(check("AA11"));
        System.out.println(check("AAaa"));
        System.out.println(check("AAaa11"));
        System.out.println(check("AAA^^^"));
        System.out.println(check("aaa^^^"));
        System.out.println(check("111^^^"));
        System.out.println(check("AAA111$$$"));
        System.out.println(check("aaa111$$$"));
        System.out.println(check("AAAaaa111$$$"));
        System.out.println(check("AAAaaa111"));
    }

    private static String check(String password){

        String rule = PSD_CHECK_TOP2;

        if (password.matches(rule)){
            return "OK";
        }else{
            if (rule.equals(PSD_CHECK_TOP1)){
                return "密码必须是大写字母、小写字母与数字的组合";
            }else{
                return "密码必须是字母、数字、特殊字符的组合";
            }
        }
    }
}