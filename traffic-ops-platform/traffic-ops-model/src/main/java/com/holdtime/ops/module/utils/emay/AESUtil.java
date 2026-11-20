package com.holdtime.ops.module.utils.emay;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * 项目名称： hdtm-sms
 * 功能描述： AESUtil概述
 * 创 建 人： yanxl
 * 创建日期： 2022年10月10日 11:25:00
 * 修 改 人：
 * 修改时间：
 * 修改内容：
 * 版    本： 1.0.0
 */
public class AESUtil {
    public final static String ALGORITHM_AEPP = "AES/ECB/PKCS5Padding";

    /**
     * AES加密
     * @param content 内容
     * @param password 密钥
     * @param algorithm 算法
     * @return 加密后数据
     */
    public static byte[] encrypt(byte[] content, byte[] password, String algorithm) {
        if (content == null || password == null) {
            return null;
        }
        try {
            Cipher cipher;
            if (algorithm.endsWith("PKCS7Padding")) {
                cipher = Cipher.getInstance(algorithm, "BC");
            } else {
                cipher = Cipher.getInstance(algorithm);
            }
            cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(password, "AES"));
            return cipher.doFinal(content);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * AES解密
     * @param content 加密内容
     * @param password 密钥
     * @param algorithm 算法
     * @return 解密后数据
     */
    public static byte[] decrypt(byte[] content, byte[] password, String algorithm) {
        if (content == null || password == null) {
            return null;
        }
        try {
            Cipher cipher;
            if (algorithm.endsWith("PKCS7Padding")) {
                cipher = Cipher.getInstance(algorithm, "BC");
            } else {
                cipher = Cipher.getInstance(algorithm);
            }
            cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(password, "AES"));
            return cipher.doFinal(content);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * AES加密
     * @param content 内容
     * @param password 密钥
     * @param ivStr 向量
     * @param algorithm 算法
     * @return 加密后数据
     */
    public static byte[] encrypt(byte[] content, byte[] password, byte[] ivStr, String algorithm){
        if(content==null||password==null) {
            return null;
        }
        try {
            Cipher cipher;
            if (algorithm.endsWith("PKCS7Padding")) {
                cipher = Cipher.getInstance(algorithm, "BC");
            } else {
                cipher = Cipher.getInstance(algorithm);
            }
            IvParameterSpec iv = new IvParameterSpec(ivStr);
            cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(password, "AES"), iv);
            return cipher.doFinal(content);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * AES解密
     * @param content 加密内容
     * @param password 密钥
     * @param ivStr 向量
     * @param algorithm 算法
     * @return 解密后数据
     */
    public static byte[] decrypt(byte[] content, byte[] password, byte[] ivStr, String algorithm){
        if(content==null||password==null) {
            return null;
        }
        try {
            Cipher cipher;
            if (algorithm.endsWith("PKCS7Padding")) {
                cipher = Cipher.getInstance(algorithm, "BC");
            } else {
                cipher = Cipher.getInstance(algorithm);
            }
            IvParameterSpec iv = new IvParameterSpec(ivStr);
            cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(password, "AES"), iv);
            return cipher.doFinal(content);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
