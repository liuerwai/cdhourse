package com.cdhouse.utils;

import java.text.SimpleDateFormat;

public class DateUtils {

    public static ThreadLocal DATE_FORMAT_YMD = new ThreadLocal(){
         protected Object initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd");
        }
    };

    public static SimpleDateFormat getDateFormat(){
        return (SimpleDateFormat) DATE_FORMAT_YMD.get();
    }

}
