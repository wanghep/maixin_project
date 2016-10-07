package com.mx;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Created by shenghai on 15/4/1.
 */
public class LogUtil {

    public static void warn(Class clazz, Object o) {
        Log log = LogFactory.getLog(clazz);
        log.info(o);
    }
    public static void info(Class clazz, Object o) {
        Log log = LogFactory.getLog(clazz);
        log.info(o);
    }

    public static void debug(Class clazz, Object o) {
        Log log = LogFactory.getLog(clazz);
        log.debug(o);
    }

    public static void error(Class clazz, Object o) {
        Log log = LogFactory.getLog(clazz);
        log.error(o);
    }

}
