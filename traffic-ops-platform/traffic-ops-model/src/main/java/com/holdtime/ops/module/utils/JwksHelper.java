package com.holdtime.ops.module.utils;

import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.crypto.ECDSAVerifier;
import com.nimbusds.jose.crypto.RSASSAVerifier;
import com.nimbusds.jose.jwk.ECKey;
import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jwt.SignedJWT;
import lombok.extern.slf4j.Slf4j;

import java.security.PublicKey;
import java.security.interfaces.ECPublicKey;
import java.security.interfaces.RSAPublicKey;

/**
 * JWKS工具类
 * 提供静态方法用于JWKS公钥的获取和JWT验证
 */
@Slf4j
public class JwksHelper {

    /**
     * 从JWKS端点获取JWKSet
     *
     * @param jwksUrl JWKS端点URL
     * @return JWKSet对象
     */
    public static JWKSet fetchJwks(String jwksUrl) {
        try {
            HttpResponse response = HttpRequest.get(jwksUrl)
                    .timeout(10000)
                    .execute();

            String body = response.body();
            return JWKSet.parse(body);
        } catch (Exception e) {
            log.error("获取JWKS失败, URL: {}", jwksUrl, e);
            throw new RuntimeException("获取JWKS失败: " + e.getMessage(), e);
        }
    }

    /**
     * 从JWKS端点获取指定Key ID的公钥
     *
     * @param jwksUrl JWKS端点URL
     * @param kid      Key ID
     * @return 公钥对象
     */
    public static PublicKey fetchPublicKey(String jwksUrl, String kid) {
        try {
            JWKSet jwkSet = fetchJwks(jwksUrl);
            JWK jwk = jwkSet.getKeyByKeyId(kid);

            if (jwk == null) {
                throw new RuntimeException("未找到匹配的公钥, kid: " + kid);
            }

            if (jwk instanceof RSAKey) {
                return ((RSAKey) jwk).toRSAPublicKey();
            } else if (jwk instanceof ECKey) {
                return ((ECKey) jwk).toECPublicKey();
            }

            throw new RuntimeException("不支持的JWK类型: " + jwk.getClass().getName());
        } catch (Exception e) {
            log.error("获取公钥失败, URL: {}, kid: {}", jwksUrl, kid, e);
            throw new RuntimeException("获取公钥失败: " + e.getMessage(), e);
        }
    }

    /**
     * 从JWKS端点获取RSA公钥
     *
     * @param jwksUrl JWKS端点URL
     * @param kid      Key ID
     * @return RSA公钥
     */
    public static RSAPublicKey fetchRSAPublicKey(String jwksUrl, String kid) {
        try {
            return (RSAPublicKey) fetchPublicKey(jwksUrl, kid);
        } catch (Exception e) {
            log.error("获取RSA公钥失败, URL: {}, kid: {}", jwksUrl, kid, e);
            throw new RuntimeException("获取RSA公钥失败: " + e.getMessage(), e);
        }
    }

    /**
     * 从JWKS端点获取EC公钥
     *
     * @param jwksUrl JWKS端点URL
     * @param kid      Key ID
     * @return EC公钥
     */
    public static ECPublicKey fetchECPublicKey(String jwksUrl, String kid) {
        try {
            return (ECPublicKey) fetchPublicKey(jwksUrl, kid);
        } catch (Exception e) {
            log.error("获取EC公钥失败, URL: {}, kid: {}", jwksUrl, kid, e);
            throw new RuntimeException("获取EC公钥失败: " + e.getMessage(), e);
        }
    }

    /**
     * 使用JWKS端点验证JWT令牌
     *
     * @param token    JWT令牌
     * @param jwksUrl  JWKS端点URL
     * @param algorithm 签名算法
     * @return 验证结果
     */
    public static boolean verifyTokenWithJwks(String token, String jwksUrl, String algorithm) {
        try {
            SignedJWT jwt = SignedJWT.parse(token);
            String kid = jwt.getHeader().getKeyID();

            if (StrUtil.isBlank(kid)) {
                log.warn("JWT令牌缺少kid参数");
                return false;
            }

            // 从JWKS端点获取公钥
            PublicKey publicKey = fetchPublicKey(jwksUrl, kid);

            // 验证令牌
            if (JWSAlgorithm.RS256.getName().equalsIgnoreCase(algorithm)) {
                if (publicKey instanceof RSAPublicKey) {
                    return jwt.verify(new RSASSAVerifier((RSAPublicKey) publicKey));
                }
            } else if (JWSAlgorithm.ES256.getName().equalsIgnoreCase(algorithm)) {
                if (publicKey instanceof ECPublicKey) {
                    return jwt.verify(new ECDSAVerifier((ECPublicKey) publicKey));
                }
            }

            log.warn("签名算法或公钥类型不匹配");
            return false;
        } catch (Exception e) {
            log.error("JWT令牌验证失败", e);
            return false;
        }
    }

    /**
     * 解析JWKS端点URL
     * 支持从OpenID Connect发现文档中获取JWKS端点
     *
     * @param issuer Issuer地址，例如: https://iamapi.xiaonuo.vip
     * @return JWKS端点URL
     */
    public static String discoverJwksUrl(String issuer) {
        try {
            String discoveryUrl = issuer + "/.well-known/openid-configuration";
            HttpResponse response = HttpRequest.get(discoveryUrl)
                    .timeout(10000)
                    .execute();

            String body = response.body();
            JSONObject json = JSONUtil.parseObj(body);
            return json.getStr("jwks_uri");
        } catch (Exception e) {
            log.error("发现JWKS端点失败, Issuer: {}", issuer, e);
            throw new RuntimeException("发现JWKS端点失败: " + e.getMessage(), e);
        }
    }

    /**
     * 创建JWKSet缓存
     * 用于缓存JWKS，避免频繁请求
     *
     * @param jwksUrl JWKS端点URL
     * @param ttl      缓存时间(秒)
     * @return JWKSet对象
     */
    public static JWKSet fetchAndCacheJwks(String jwksUrl, long ttl) {
        // TODO: 实现缓存逻辑，可以使用Caffeine或其他缓存库
        return fetchJwks(jwksUrl);
    }
}
