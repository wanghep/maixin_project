package com.mx;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by shenghai on 15/5/15.
 */
public class StringUtil {

    public static List<String> findListByPattern(String from, String to, String content) {
        List<String> result = new ArrayList<>();
        String temp = findByPattern(from, to, content);
        while (temp != null) {
            result.add(temp);
            String next = content.substring(content.indexOf(temp));
            temp = findByPattern(from, to, next);
        }
        return result;
    }

    public static String findByPattern(String from, String to, String str) {
        if (str == null) {
            return null;
        }
        String startString = findByPattern(from, str);
        if (startString == null) {
            return null;
        }
        int start = str.indexOf(startString) + startString.length();
        String nextString = str.substring(start);
        String endString = findByPattern(to, nextString);
        int end = nextString.indexOf(endString) + start;

        return str.substring(start, end);
    }

    public static String findByPattern(String pattern, String str) {
        Pattern compile = Pattern.compile(pattern);
        Matcher matcher = compile.matcher(str);
        if (matcher.find()) {
            return matcher.group();
        } else {
            return null;
        }
    }
}
