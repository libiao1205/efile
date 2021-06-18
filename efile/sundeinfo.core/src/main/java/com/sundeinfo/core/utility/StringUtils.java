package com.sundeinfo.core.utility;

import java.math.BigDecimal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

public class StringUtils {
    /**
     * 是否为空
     *
     * @param str 验证字符串
     * @return boolean true 空, false 非空
     */
    public static boolean isBlank(String str) {
        if (null == str || "".equals(str)) {
            return true;
        }
        return false;
    }

    /**
     * 是否非空
     *
     * @param str 验证字符串
     * @return boolean true 空, false 非空
     */
    public static boolean isNotBlank(String str) {
        if (null == str || "".equals(str)) {
            return false;
        }
        return true;
    }

    /**
     * 是否相等
     *
     * @param str1 字符串
     * @param str2 字符串
     * @return boolean true 相等, false 不等
     */
    public static boolean isEqualsString(String str1, String str2) {
        if (null == str1 || "".equals(str1)) {
            if (null == str2 || "".equals(str2)) {
                return true;
            }
            return false;
        }
        if (null != str1 && str1.equals(str2)) {
            return true;
        }
        return false;
    }

    /**
     * 是否是数字
     *
     * @param str 字符串
     * @return boolean true 数字, false 非数字
     */
    public static boolean isNumeric(String str) {
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str);
        if (!isNum.matches()) {
            return false;
        }
        return true;
    }

    /**
     * 判断字符串是否包含空格
     * @param str
     * @return
     */
    public static boolean isStringHasSpace(String str){
        boolean res = false;
        char[] result = str.toCharArray();
        for(char cha : result){
            if( (int) cha == 32){
                res = true;
                break;
            }
        }
        return res;
    }

    /**
     * 判断字符串是否只包含英文,中文,数字
     */
    public static boolean regexEffectiveLetter(String str){
        String regex = "^[0-9a-zA-Z\u4e00-\u9fa5]+$";
        return str.matches(regex);
    }

    /**
     * 判断字符串是否全部为数字
     */
    public static boolean regexEffectiveNumber(String str){
        String regex = "^-?[0-9]+$";
        return str.matches(regex);
    }

    /**
     * 取出一个指定长度大小的随机正整数.
     *
     * @param length
     *            int 设定所取出随机数的长度。length小于11
     * @return int 返回生成的随机数。
     */
    public static int buildRandom(int length) {
        int num = 1;
        double random = Math.random();
        if (random < 0.1) {
            random = random + 0.1;
        }
        for (int i = 0; i < length; i++) {
            num = num * 10;
        }
        return (int) ((random * num));
    }

    /**
     * 杀空
     * @param str
     * @return
     */
    public static String killNull(String str){
        if(str==null || "null".equals(str)){
            return "";
        }
        return str.trim();
    }
    
    public static String killNullv1(String str){
        if(str==null || "null".equals(str)){
            return "　";
        }
        return str.trim();
    }

    /**
     * 杀空
     * @param str
     * @return
     */
    public static String killEmpty(String str){
        if(str!=null){
            if("".equals(str.trim())){
                str = null;
            }
        }
        return str.trim();
    }

    public static String  getAppAction(String activity) {
        String action;
        if("go_app".equals(activity)){
            action = "after_open";
        }else if(activity.indexOf("http")!=-1){
            action = "after_open";
        }else {
            action = "go_custom";
        }
        return  action;
    }


    /**
     * accountOff为空
     * @param accountOff
     * @return
     */
    public static boolean isHavingAccountOff(String accountOff){
        return StringUtils.killEmpty(accountOff)!= null && new BigDecimal(accountOff).doubleValue()!=0;
    }

    /**
     *商品库存出库参数检查
     * @param ids
     * @param productNums
     * @param stockNums
     * @param types
     * @param typeTexts
     * @return
     */
    public static boolean checkArgs(String ids, String productNums, String stockNums, String types, String typeTexts) {
        if("".equals(ids)||"".equals(productNums)||"".equals(stockNums)||"".equals(types)||"".equals(typeTexts)||ids==null||productNums==null||stockNums==null||types==null||typeTexts==null){
            return false;
        }
        return true;
    }
    
    public static boolean equalsIgnoreCase(String str1, String str2) {
        return str1 == null?str2 == null:str1.equalsIgnoreCase(str2);
    }
    
    public static int indexOf(String str, String searchStr) {
        return str != null && searchStr != null?str.indexOf(searchStr):-1;
    }
    
    public static boolean isEmpty(String str) {
        return str == null || str.length() == 0;
    }
    
    /**
	 * 获取登录用户IP地址
	 * 
	 * @param request
	 * @return
	 */
	public static String getClientIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("X-real-ip");
		if (isBlank(ip)) {
			ip = request.getHeader("X-Forward-For");
		}
		if (isBlank(ip) || equalsIgnoreCase("unknown", ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (isBlank(ip) || equalsIgnoreCase("unknown", ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (isBlank(ip)) {
			ip = request.getRemoteAddr();
		}
		if (indexOf(ip, "0:0") != -1) {
			ip = "127.0.0.1";
		}
		return ip;
	}
}
