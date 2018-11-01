package com.linkwechat.commons.crypto;

import org.apache.commons.codec.binary.Base64;

/**
 * Base64工具类
 * 
 * @author linkwechat linkwechat@foxmail.com
 * @version 1.0
 */
public class Base64Utils {

    /**
     * 二进制数据编码为Base64二进制数据
     * 
     * @param bytes
     *            二进制数据
     * @return byte[]
     */
    public static byte[] encode(final byte[] bytes) {
        return Base64.encodeBase64(bytes);
    }

    /**
     * Base64二进制数据解码为二进制数据
     * 
     * @param bytes
     *            Base64二进制数据
     * @return byte[]
     */
    public static byte[] decode(final byte[] bytes) {
        return Base64.decodeBase64(bytes);
    }

    /**
     * 二进制数据编码为Base64字符串
     * 
     * @param bytes
     *            二进制数据
     * @return String
     */
    public static String encodeString(final byte[] bytes) {
        return Base64.encodeBase64String(bytes);
    }

    /**
     * Base64字符串解码为二进制数据
     * 
     * @param base64String
     *            Base64字符串
     * @return byte[]
     */
    public static byte[] decodeString(final String base64String) {
        return Base64.decodeBase64(base64String);
    }
}
