package com.linkwechat.commons.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;

/**
 * Json工具类
 * 
 * @author linkwechat linkwechat@foxmail.com
 * @version 1.0
 */
public class JsonUtils {

    private static final Log logger = LogFactory.getLog(JsonUtils.class);

    private final static String datePattern = "yyyy-MM-dd HH:mm:ss";
    private final static SerializerFeature[] features = { SerializerFeature.DisableCircularReferenceDetect };

    /**
     * 对象转标准json字符串
     * 
     * @param obj
     *            待转换对象
     * @return String
     */
    public static String getStandardJsonString(Object obj) {
        return JSON.toJSONStringWithDateFormat(obj, datePattern, features);
    }

    /**
     * 对象转可阅读json字符串
     * 
     * @param obj
     *            待转换对象
     * @return String
     */
    public static String getReadableJsonString(Object obj) {
        String jsonString = getStandardJsonString(obj);
        return FormatStandardJsonStringToReadable(jsonString);
    }

    /**
     * json字符串格式化为标准json字符串
     * 
     * @param jsonString
     *            json字符串
     * @return String
     */
    public static String FormatJsonStringToStandard(String jsonString) {
        JSONObject jsonObject = JSONObject.parseObject(jsonString);
        return getStandardJsonString(jsonObject);
    }

    /**
     * json字符串格式化为可阅读json字符串
     * 
     * @param jsonString
     *            json字符串
     * @return String
     */
    public static String FormatJsonStringToReadable(String jsonString) {
        return FormatStandardJsonStringToReadable(FormatJsonStringToStandard(jsonString));
    }

    /**
     * 标准json字符串格式化为可阅读json字符串
     * 
     * @param jsonString
     *            json字符串
     * @return String
     */
    public static String FormatStandardJsonStringToReadable(String jsonString) {
        StringBuilder sb = new StringBuilder();
        int depth = 0;
        int width = 4;
        int strMark = -1;
        char[] array = jsonString.toCharArray();
        for (int i = 0; i < array.length; i++) {
            char c = array[i];
            if (c == '"' && i > 0 && array[i - 1] != '\\') {
                sb.append(c);
                strMark = -strMark;
            } else if (strMark == 1) {
                sb.append(c);
            } else {
                switch (c) {
                case '{':
                case '[':
                    depth = depth + width;
                    sb.append(c).append("\n").append(StringUtils.fillBlankChar(depth));
                    break;
                case '}':
                case ']':
                    depth = depth - width;
                    sb.append("\n").append(StringUtils.fillBlankChar(depth)).append(c);
                    break;
                case ',':
                    sb.append(c).append("\n").append(StringUtils.fillBlankChar(depth));
                    break;
                case ':':
                    sb.append(" ").append(c).append(" ");
                    break;
                default:
                    sb.append(c);
                    break;
                }
            }
        }
        return sb.toString();
    }
}
