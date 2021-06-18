package com.sundeinfo.sypglass.utility;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class EncodeUtils {

    /**
     * 处理中文文件名乱码
     * */
    public static String encoder(HttpServletRequest request,String name) throws UnsupportedEncodingException {
        if (request.getHeader("User-Agent").toUpperCase().contains("MSIE") ||
                request.getHeader("User-Agent").toUpperCase().contains("TRIDENT")
                || request.getHeader("User-Agent").toUpperCase().contains("EDGE")) {
            return URLEncoder.encode(name, "UTF-8");
        } else {
            //非IE浏览器的处理：
            return new String(name.getBytes("UTF-8"), "ISO-8859-1");
        }
    }

}
