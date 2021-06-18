package com.sundeinfo.sypglass.relay.utility;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {

    public static String getShortDateString(Date value){
        return (new SimpleDateFormat("yyyy-MM-dd")).format(value);
    }

    public static Date StringToDate(String dateString) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = sdf.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static Date getTodayShort(){
        String today = getShortDateString(new Date());
        return  StringToDate(today);
    }
}
