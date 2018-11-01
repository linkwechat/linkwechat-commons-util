package com.linkwechat.commons.crypto;

import java.security.MessageDigest;

/**
 * Mac工具类
 * 
 * @author linkwechat linkwechat@foxmail.com
 * @version 1.0
 */
public class MacUtils {

    /**
     * Mac加密算法
     */
    public enum MacAlgorithm {
        MD2("MD2"), MD5("MD5"), SHA1("SHA-1"), SHA256("SHA-256"), SHA384("SHA-384"), SHA512("SHA-512");

        private String algorithm;

        private MacAlgorithm(String algorithm) {
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
     * 使用Mac方法进行签名
     * 
     * @param data
     *            二进制数据
     * @param algorithm
     *            算法
     * @return byte[]
     */
    public static byte[] macEncode(byte[] data, MacAlgorithm algorithm) throws Exception {
        // 构造消息摘要算法对象
        MessageDigest md = MessageDigest.getInstance(algorithm.toString());
        return md.digest(data);
    }
}
