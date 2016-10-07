package com.mx;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by shenghai on 15/4/19.
 */
public class DateUtil {
    private final static SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");

    public static String formatDate(Date date) {
        return format.format(date);
    }

    public static String formatDate(Date date, String pattern) {
        return new SimpleDateFormat(pattern).format(date);
    }

    public static Date parseToDate(String dateString, String pattern) {
        try {
            return new SimpleDateFormat(pattern).parse(dateString);
        } catch (ParseException e) {
            return null;
        }
    }



    public static Date parseToDate(String dateString) {
        try {
            return format.parse(dateString);
        } catch (ParseException e) {
            return null;
        }
    }

    public static long datesBetween(Date beginDate, Date endDate) {
        return (endDate.getTime()-beginDate.getTime())/(24*60*60*1000);
    }
}
