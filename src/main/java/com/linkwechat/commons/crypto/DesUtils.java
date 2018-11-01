package com.linkwechat.commons.crypto;

import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * DES工具类
 * 
 * @author linkwechat 2017年3月1日 上午11:46:42
 * @version 1.0
 */
public class DesUtils {

    /**
     * DES加密算法
     */
    public enum DesAlgorithm {
        DES("DES"), DESede("DESede"), AES("AES");

        private String algorithm;

        private DesAlgorithm(String algorithm) {
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
     * DES工作模式（共有五种工作模式：ECB-电子密码本模式；CBC-加密分组链接模式；CFB-加密反馈模式；OFB-输出反馈模式；CTR-计数模式）
     */
    public enum DesMode {
        ECB("ECB"), CBC("CBC"), OFB("OFB"), CFB("CFB"), CTR("CTR");

        private String mode;

        private DesMode(String mode) {
            this.mode = mode;
        }

        public String getMode() {
            return mode;
        }

        @Override
        public String toString() {
            return mode;
        }
    }

    /**
     * DES填充方式
     */
    public enum DesPadding {
        NoPadding("NoPadding"), PKCS5Padding("PKCS5Padding");

        private String padding;

        private DesPadding(String padding) {
            this.padding = padding;
        }

        public String getAlgorithm() {
            return padding;
        }

        @Override
        public String toString() {
            return padding;
        }
    }

    /**
     * 格式化密钥
     * 
     * @param key
     *            密钥
     * @param algorithm
     *            DES加密算法
     * @return byte[]
     */
    private static byte[] keyFormat(byte[] key, DesAlgorithm algorithm) throws Exception {
        byte[] effectivekey;
        if (algorithm == DesAlgorithm.DES) {
            effectivekey = new byte[8];
        } else if (algorithm == DesAlgorithm.DESede) {
            effectivekey = new byte[24];
        } else if (algorithm == DesAlgorithm.AES) {
            effectivekey = new byte[16];
        } else {
            return key;
        }
        System.arraycopy(effectivekey, 0, key, 0, effectivekey.length < key.length ? effectivekey.length : key.length);
        return effectivekey;
    }

    /**
     * 格式化密钥初始化向量
     * 
     * @param keyiv
     *            密钥初始化向量
     * @return byte[]
     */
    private static byte[] keyivFormat(byte[] keyiv, DesAlgorithm algorithm) throws Exception {
        byte[] effectivekeyiv;
        if (algorithm == DesAlgorithm.DES || algorithm == DesAlgorithm.DESede) {
            effectivekeyiv = new byte[8];
        } else if (algorithm == DesAlgorithm.AES) {
            effectivekeyiv = new byte[16];
        } else {
            return keyiv;
        }
        System.arraycopy(effectivekeyiv, 0, keyiv, 0,
                effectivekeyiv.length < keyiv.length ? effectivekeyiv.length : keyiv.length);
        return effectivekeyiv;
    }

    /**
     * 生成密钥key对象
     * 
     * @param key
     *            密钥数据
     * @param algorithm
     *            DES加密算法
     * @return Key
     */
    private static Key keyGenerator(byte[] key, DesAlgorithm algorithm) throws Exception {
        SecretKey secretKey = new SecretKeySpec(keyFormat(key, algorithm), algorithm.toString());
        return secretKey;
    }

    /**
     * 生成转换信息
     * 
     * @param algorithm
     *            DES加密算法
     * @param mode
     *            DES工作模式
     * @param padding
     *            DES填充方式
     * @return String
     */
    private static String transformationGenerator(DesAlgorithm algorithm, DesMode mode, DesPadding padding)
            throws Exception {
        String transformation = String.format("%s/%s/%s", algorithm.toString(), mode.toString(), padding.toString());
        return transformation;
    }

    /**
     * DES加密
     * 
     * @param data
     *            二进制数据
     * @param key
     *            密钥
     * @param algorithm
     *            DES加密算法
     * @param mode
     *            DES工作模式
     * @param padding
     *            DES填充方式
     * @return byte[]
     */
    public static byte[] desEncrypt(byte[] data, byte[] key, DesAlgorithm algorithm, DesMode mode, DesPadding padding)
            throws Exception {
        return desEncrypt(data, key, key, algorithm, mode, padding);
    }

    /**
     * DES加密
     * 
     * @param data
     *            二进制数据
     * @param key
     *            密钥
     * @param keyiv
     *            密钥初始化向量
     * @param algorithm
     *            DES加密算法
     * @param mode
     *            DES工作模式
     * @param padding
     *            DES填充方式
     * @return byte[]
     */
    public static byte[] desEncrypt(byte[] data, byte[] key, byte[] keyiv, DesAlgorithm algorithm, DesMode mode,
            DesPadding padding) throws Exception {
        Key k = keyGenerator(key, algorithm);
        String transformation = transformationGenerator(algorithm, mode, padding);
        Cipher cipher = Cipher.getInstance(transformation);
        if (mode == DesMode.ECB) {
            cipher.init(Cipher.ENCRYPT_MODE, k);
        } else {
            cipher.init(Cipher.ENCRYPT_MODE, k, new IvParameterSpec(keyivFormat(keyiv, algorithm)));
        }
        return cipher.doFinal(data);
    }

    /**
     * DES解密
     * 
     * @param data
     *            二进制数据
     * @param key
     *            密钥
     * @param algorithm
     *            DES加密算法
     * @param mode
     *            DES工作模式
     * @param padding
     *            DES填充方式
     * @return byte[]
     */
    public static byte[] desDecrypt(byte[] data, byte[] key, DesAlgorithm algorithm, DesMode mode, DesPadding padding)
            throws Exception {
        return desDecrypt(data, key, key, algorithm, mode, padding);
    }

    /**
     * DES解密
     * 
     * @param data
     *            二进制数据
     * @param key
     *            密钥
     * @param keyiv
     *            密钥初始化向量
     * @param algorithm
     *            DES加密算法
     * @param mode
     *            DES工作模式
     * @param padding
     *            DES填充方式
     * @return byte[]
     */
    public static byte[] desDecrypt(byte[] data, byte[] key, byte[] keyiv, DesAlgorithm algorithm, DesMode mode,
            DesPadding padding) throws Exception {
        Key k = keyGenerator(key, algorithm);
        String transformation = transformationGenerator(algorithm, mode, padding);
        Cipher cipher = Cipher.getInstance(transformation);
        if (mode == DesMode.ECB) {
            cipher.init(Cipher.DECRYPT_MODE, k);
        } else {
            cipher.init(Cipher.DECRYPT_MODE, k, new IvParameterSpec(keyivFormat(keyiv, algorithm)));
        }
        return cipher.doFinal(data);
    }
}
