package com.cdhouse.utils;

import org.apache.commons.lang.StringUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DateUtils {

    public static ThreadLocal DATE_FORMAT_YMD = new ThreadLocal() {
        protected Object initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd");
        }
    };

    public static SimpleDateFormat getDateFormat() {
        return (SimpleDateFormat) DATE_FORMAT_YMD.get();
    }

    /**
     * 查询日期转换
     *
     * @param startTime
     * @param endTime
     * @return
     */
    public static List<Date> transQueryStartEndDate(String startTime, String endTime) {

        List<Date> list = new ArrayList<>();
        Date startDate = new Date();
        Date endDate = new Date();
        try {
            if (StringUtils.isNotBlank(startTime)) {
                startDate = DateUtils.getDateFormat().parse(startTime);
            } else {
                startDate = DateUtils.getDateFormat().parse("2017-01-01");
            }
            if (StringUtils.isNotBlank(endTime)) {
                endDate = DateUtils.getDateFormat().parse(endTime);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        list.add(startDate);
        list.add(endDate);
        return list;
    }

}
