package com.holdtime.ops.module.utils;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.jwt.JWT;
import cn.hutool.jwt.JWTUtil;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.crypto.ECDSAVerifier;
import com.nimbusds.jose.crypto.RSASSAVerifier;
import com.nimbusds.jose.jwk.ECKey;
import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jwt.SignedJWT;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Base64;

/**
 * 统一用户中心JWT令牌工具类
 */
@Slf4j
public class IamTokenUtil {

    /**
     * 验证JWT令牌签名
     *
     * @param algorithm     签名算法(RS256或ES256)
     * @param publicKeyStr  公钥，Base64编码或JWKS字符串
     * @param token         JWT令牌
     * @return 验证结果
     */
    public static boolean verifyToken(String algorithm, String publicKeyStr, String token) {
        try {
            if (StrUtil.isBlank(token) || StrUtil.isBlank(publicKeyStr)) {
                return false;
            }

            SignedJWT jwt = SignedJWT.parse(token);
            String kid = jwt.getHeader().getKeyID();

            if (JWSAlgorithm.RS256.getName().equalsIgnoreCase(algorithm)) {
                RSAPublicKey publicKey = parseRSAPublicKeyFromStr(publicKeyStr, kid);
                return jwt.verify(new RSASSAVerifier(publicKey));
            } else if (JWSAlgorithm.ES256.getName().equalsIgnoreCase(algorithm)) {
                return jwt.verify(new ECDSAVerifier(parseEcPublicKeyFromStr(publicKeyStr, kid)));
            }

            return false;
        } catch (Exception e) {
            log.error("JWT令牌验证失败", e);
            return false;
        }
    }

    /**
     * 解析RSA公钥
     *
     * @param publicKeyStr 公钥字符串
     * @param kid          Key ID
     * @return RSA公钥
     */
    public static RSAPublicKey parseRSAPublicKeyFromStr(String publicKeyStr, String kid) throws Exception {
        try {
            // 尝试解析为JWKS格式
            if (publicKeyStr.trim().startsWith("{")) {
                JWKSet jwkSet = JWKSet.parse(publicKeyStr);
                JWK jwk = jwkSet.getKeyByKeyId(kid);
                if (jwk instanceof RSAKey) {
                    return ((RSAKey) jwk).toRSAPublicKey();
                }
            }
            // 解析为PEM格式或Base64格式
            return (RSAPublicKey) decodePublicKey(publicKeyStr);
        } catch (Exception e) {
            throw new Exception("解析RSA公钥失败: " + e.getMessage(), e);
        }
    }

    /**
     * 解析EC公钥
     *
     * @param publicKeyStr 公钥字符串
     * @param kid          Key ID
     * @return EC公钥
     */
    public static java.security.interfaces.ECPublicKey parseEcPublicKeyFromStr(String publicKeyStr, String kid) throws Exception {
        try {
            // 尝试解析为JWKS格式
            if (publicKeyStr.trim().startsWith("{")) {
                JWKSet jwkSet = JWKSet.parse(publicKeyStr);
                JWK jwk = jwkSet.getKeyByKeyId(kid);
                if (jwk instanceof ECKey) {
                    return ((ECKey) jwk).toECPublicKey();
                }
            }
            // 解析为PEM格式或Base64格式
            return decodeEcPublicKey(publicKeyStr);
        } catch (Exception e) {
            throw new Exception("解析EC公钥失败: " + e.getMessage(), e);
        }
    }

    /**
     * 解码公钥
     *
     * @param publicKeyStr 公钥字符串
     * @return 公钥对象
     */
    private static PublicKey decodePublicKey(String publicKeyStr) throws Exception {
        String publicKeyPem = publicKeyStr.trim();

        // 去除PEM头尾
        if (publicKeyPem.contains("-----BEGIN")) {
            publicKeyPem = publicKeyPem.replace("-----BEGIN PUBLIC KEY-----", "")
                    .replace("-----END PUBLIC KEY-----", "")
                    .replace("-----BEGIN RSA PUBLIC KEY-----", "")
                    .replace("-----END RSA PUBLIC KEY-----", "")
                    .replaceAll("\\s", "");
        }

        byte[] keyBytes = Base64.getDecoder().decode(publicKeyPem);

        java.security.spec.X509EncodedKeySpec spec = new java.security.spec.X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePublic(spec);
    }

    /**
     * 解码EC公钥
     *
     * @param publicKeyStr 公钥字符串
     * @return EC公钥对象
     */
    private static java.security.interfaces.ECPublicKey decodeEcPublicKey(String publicKeyStr) throws Exception {
        String publicKeyPem = publicKeyStr.trim();

        // 去除PEM头尾
        if (publicKeyPem.contains("-----BEGIN")) {
            publicKeyPem = publicKeyPem.replace("-----BEGIN PUBLIC KEY-----", "")
                    .replace("-----END PUBLIC KEY-----", "")
                    .replace("-----BEGIN EC PUBLIC KEY-----", "")
                    .replace("-----END EC PUBLIC KEY-----", "")
                    .replaceAll("\\s", "");
        }

        byte[] keyBytes = Base64.getDecoder().decode(publicKeyPem);

        java.security.spec.X509EncodedKeySpec spec = new java.security.spec.X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("EC");
        return (java.security.interfaces.ECPublicKey) keyFactory.generatePublic(spec);
    }

    /**
     * 从id_token中获取用户信息
     *
     * @param idToken id_token字符串
     * @return 用户信息对象
     */
    public static UserInfo getUserInfoFromIdToken(String idToken) {
        try {
            JWT jwt = JWTUtil.parseToken(idToken);
            JSONObject claimsJson = jwt.getPayloads();

            UserInfo userInfo = new UserInfo();
            userInfo.setUserId(claimsJson.getStr("sub"));
            if (claimsJson.containsKey("attachMap")) {
                userInfo.setStaffId(claimsJson.getJSONObject("attachMap").getStr("staffId"));
            }
            userInfo.setName(claimsJson.getStr("name"));
            userInfo.setAccount(claimsJson.getStr("account"));
            userInfo.setEmail(claimsJson.getStr("email"));
            userInfo.setPhone(claimsJson.getStr("phone"));
            userInfo.setPicture(claimsJson.getStr("picture"));
            userInfo.setOrg(claimsJson.getStr("org"));
            userInfo.setPosition(claimsJson.getStr("position"));

            return userInfo;
        } catch (Exception e) {
            log.error("从id_token解析用户信息失败", e);
            return null;
        }
    }

    /**
     * 验证JWT令牌过期时间
     *
     * @param token JWT令牌
     * @return 是否过期
     */
    public static boolean isTokenExpired(String token) {
        try {
            JWT jwt = JWTUtil.parseToken(token);
            Number expireTime = jwt.getPayloads().get("exp", Number.class);
            if (expireTime == null) {
                return true;
            }
            long currentTime = System.currentTimeMillis() / 1000;
            return currentTime >= expireTime.longValue();
        } catch (Exception e) {
            log.error("检查令牌过期失败", e);
            return true;
        }
    }

    /**
     * 用户信息实体类
     */
    @Setter
    @Getter
    public static class UserInfo {
        private String userId;
        private String staffId;
        private String name;
        private String account;
        private String email;
        private String phone;
        private String picture;
        private String org;
        private String position;

    }
}
