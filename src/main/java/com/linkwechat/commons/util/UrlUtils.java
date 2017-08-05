package com.linkwechat.commons.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

/**
 * Url工具类
 * 
 * @author linkwechat linkwechat@foxmail.com
 * @version 1.0
 */
public class UrlUtils {

    /**
     * http请求参数转换为Map
     * 
     * @param request
     *            http请求参数
     * @return http请求参数Map
     */
    public static Map<String, String> getParamMap(HttpServletRequest request) {
        Map<String, String> paramMap = new HashMap<String, String>();
        Enumeration<String> paramNames = request.getParameterNames();
        while (paramNames.hasMoreElements()) {
            String paramName = (String) paramNames.nextElement();
            paramMap.put(paramName, request.getParameter(paramName));
        }
        return paramMap;
    }

    /**
     * 按照字典序把http请求参数Map格式化为字符串
     * 
     * @param paramMap
     *            http请求参数Map
     * @return http请求参数字符串
     */
    public static String getFormatUrl(Map<String, String> paramMap) {
        List<String> list = new ArrayList<String>(paramMap.keySet());
        Collections.sort(list);
        StringBuilder sb = new StringBuilder();
        for (String key : list) {
            sb.append(key).append("=").append(paramMap.get(key)).append("&");
        }
        if (sb.length() >= 1) {
            sb.delete(sb.length() - 1, sb.length());
        }
        return sb.toString();
    }
}
