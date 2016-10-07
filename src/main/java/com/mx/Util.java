package com.mx;

import org.apache.commons.lang3.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * Created by shenghai on 15/5/13.
 */
public class Util {
    private static SimpleDateFormat dateFormater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static Long toLongValue(Object value) {
        if (value == null) {
            return null;
        }
        if (value instanceof Number) {
            Number temp = (Number) value;
            return temp.longValue();
        } else if (value instanceof String) {
            if (StringUtils.isEmpty((String) value)) {
                return null;
            } else {
                return Long.valueOf((String) value);
            }
        }

        throw new RuntimeException("error parse long value by " + value.getClass().getName());
    }

    public static Integer toIntegerValue(Object value) {
        if (value == null) {
            return null;
        }
        if (value instanceof Number) {
            Number temp = (Number) value;
            return temp.intValue();
        } else if (value instanceof String) {
            if (StringUtils.isEmpty((String) value)) {
                return null;
            } else {
                return Integer.valueOf((String) value);
            }
        }
        throw new RuntimeException("error parse long value by " + value.getClass().getName());
    }


    public static String toStringValue(Object value) {
        if (value == null) {
            return "";
        } else {
            return String.valueOf(value);
        }
    }

    public static Float toFloatValue(Object value) {
        if (value == null) {
            return null;
        }
        if (value instanceof Number) {
            Number temp = (Number) value;
            return temp.floatValue();
        } else if (value instanceof String) {
            if (StringUtils.isEmpty((String) value)) {
                return null;
            } else {
                return Float.valueOf((String) value);
            }
        }
        throw new RuntimeException("error parse float value by " + value.getClass().getName());
    }

    public static String getUUID() {
        return UUID.randomUUID().toString().replaceAll("-", "").toUpperCase();
    }

    public static String toDateString(Date date) {
        return dateFormater.format(date);
    }

    public static Date toDate(String dateString) {
        try {
            return dateFormater.parse(dateString);
        } catch (ParseException e) {
            return null;
        }
    }
}
