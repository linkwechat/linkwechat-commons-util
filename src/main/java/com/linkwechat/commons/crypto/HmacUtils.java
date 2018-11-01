package com.linkwechat.commons.crypto;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * Hmac工具类
 * 
 * @author linkwechat linkwechat@foxmail.com
 * @version 1.0
 */
public class HmacUtils {

    /**
     * Hmac加密算法
     */
    public enum HmacAlgorithm {
        HmacMD5("HmacMD5"),
        HmacSHA1("HmacSHA1"),
        HmacSHA256("HmacSHA256"),
        HmacSHA384("HmacSHA384"),
        HmacSHA512("HmacSHA512");

        private String algorithm;

        private HmacAlgorithm(String algorithm) {
            this.algorithm = algorithm;
        }

        public String getAlgorithm() {
            return algorithm;
        }

        @Override
        public String toString() {
            return algorithm;
        }
    }

    /**
     * 使用Hmac方法进行签名
     * 
     * @param data
     *            二进制数据
     * @param key
     *            密钥
     * @param algorithm
     *            算法
     * @return byte[]
     */
    public static byte[] hmacEncode(byte[] data, byte[] key, HmacAlgorithm algorithm) throws Exception {
        // 构造密钥对象
        SecretKey secretKey = new SecretKeySpec(key, algorithm.toString());
        // 构造Mac算法对象
        Mac mac = Mac.getInstance(algorithm.toString());
        // 用密钥对象初始化Mac对象
        mac.init(secretKey);
        return mac.doFinal(data);
    }
}
