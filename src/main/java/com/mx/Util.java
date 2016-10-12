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

    public static long toLong(byte[] b)

    {
        long l = 0;

        l = b[0];

        l |= ((long) b[1] << 8);

        l |= ((long) b[2] << 16);

        l |= ((long) b[3] << 24);

        l |= ((long) b[4] << 32);

        l |= ((long) b[5] << 40);

        l |= ((long) b[6] << 48);

        l |= ((long) b[7] << 56);

        return l;
    }

    public static byte[] toByteArray(int number) {

        int temp = number;

        byte[] b = new byte[8];

        for (int i = b.length - 1; i > -1; i--)
        {

            b[i] = new Integer(temp & 0xff).byteValue();

            temp = temp >> 8;

        }

        return b;
    }

    public static byte[] intToBytes( int value )
    {
        byte[] src = new byte[4];
        src[3] =  (byte) ((value>>24) & 0xFF);
        src[2] =  (byte) ((value>>16) & 0xFF);
        src[1] =  (byte) ((value>>8) & 0xFF);
        src[0] =  (byte) (value & 0xFF);
        return src;
    }
    /**
     * 将int数值转换为占四个字节的byte数组，本方法适用于(高位在前，低位在后)的顺序。  和bytesToInt2（）配套使用
     */
    public static byte[] intToBytes2(int value)
    {
        byte[] src = new byte[4];
        src[0] = (byte) ((value>>24) & 0xFF);
        src[1] = (byte) ((value>>16)& 0xFF);
        src[2] = (byte) ((value>>8)&0xFF);
        src[3] = (byte) (value & 0xFF);
        return src;
    }

    /**
     * byte数组中取int数值，本方法适用于(低位在前，高位在后)的顺序，和和intToBytes（）配套使用
     *
     * @param src
     *            byte数组
     * @param offset
     *            从数组的第offset位开始
     * @return int数值
     */
    public static int bytesToInt(byte[] src, int offset) {
        int value;
        value = (int) ((src[offset] & 0xFF)
                | ((src[offset+1] & 0xFF)<<8)
                | ((src[offset+2] & 0xFF)<<16)
                | ((src[offset+3] & 0xFF)<<24));
        return value;
    }

    /**
     * byte数组中取int数值，本方法适用于(低位在后，高位在前)的顺序。和intToBytes2（）配套使用
     */
    public static int bytesToInt2(byte[] src, int offset) {
        int value;
        value = (int) ( ((src[offset] & 0xFF)<<24)
                |((src[offset+1] & 0xFF)<<16)
                |((src[offset+2] & 0xFF)<<8)
                |(src[offset+3] & 0xFF));
        return value;
    }
}
