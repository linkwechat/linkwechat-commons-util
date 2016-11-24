package com.linkwechat.commons.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Class工具类
 * 
 * @author linkwechat linkwechat@foxmail.com
 * @version 1.0
 */
public class ClassUtils {

    private static final Log logger = LogFactory.getLog(ClassUtils.class);

    /**
     * 获取调用的类名
     * 
     * @return String
     */
    public static String getClassName() {
        StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();
        StackTraceElement e = stacktrace[2];
        String className = e.getClassName();
        return className;
    }

    /**
     * 获取调用的方法名
     * 
     * @return String
     */
    public static String getMethodName() {
        StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();
        StackTraceElement e = stacktrace[2];
        String methodName = e.getMethodName();
        return methodName;
    }
}
