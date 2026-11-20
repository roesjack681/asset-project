package com.holdtime.ops.module.utils;

import cn.hutool.http.HttpUtil;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.KeySourceException;
import com.nimbusds.jose.jwk.*;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.JWSVerificationKeySelector;
import com.nimbusds.jose.proc.SecurityContext;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import com.nimbusds.jwt.proc.ConfigurableJWTProcessor;
import com.nimbusds.jwt.proc.DefaultJWTClaimsVerifier;
import com.nimbusds.jwt.proc.DefaultJWTProcessor;
import com.nimbusds.jwt.proc.JWTClaimsSetVerifier;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * JWKS公钥提供者
 * 用于从JWKS端点动态获取公钥，支持公钥轮换
 */
@Slf4j
public class JwksPublicKeyProvider {

    private final String jwksUrl;
    private final JWSAlgorithm jwsAlgorithm;
    private final long cacheTtl;

    private JWKSet cachedJwkSet;
    private long lastRefreshTime;

    /**
     * 构造函数
     *
     * @param jwksUrl JWKS端点URL，例如: https://iamapi.xiaonuo.vip/.well-known/jwks.json
     * @param algorithm 签名算法，例如: RS256, ES256
     */
    public JwksPublicKeyProvider(String jwksUrl, String algorithm) {
        this(jwksUrl, algorithm, 5 * 60 * 1000); // 默认缓存5分钟
    }

    /**
     * 构造函数
     *
     * @param jwksUrl JWKS端点URL，例如: https://iamapi.xiaonuo.vip/.well-known/jwks.json
     * @param algorithm 签名算法，例如: RS256, ES256
     * @param cacheTtl 缓存时间(毫秒)
     */
    public JwksPublicKeyProvider(String jwksUrl, String algorithm, long cacheTtl) {
        try {
            this.jwksUrl = jwksUrl;
            this.jwsAlgorithm = JWSAlgorithm.parse(algorithm);
            this.cacheTtl = cacheTtl;
            this.lastRefreshTime = 0;
            this.cachedJwkSet = null;

            // 预加载JWKS
            refreshJwkSet();
            log.info("JWKS公钥提供者初始化成功, URL: {}, Algorithm: {}", jwksUrl, algorithm);
        } catch (Exception e) {
            log.error("JWKS公钥提供者初始化失败", e);
            throw new RuntimeException("JWKS公钥提供者初始化失败: " + e.getMessage(), e);
        }
    }

    /**
     * 从JWKS端点获取JWKSet
     */
    private JWKSet fetchJwkSet() throws Exception {
        String json = HttpUtil.get(jwksUrl);
        return JWKSet.parse(json);
    }

    /**
     * 刷新JWKSet缓存
     */
    private void refreshJwkSet() {
        try {
            JWKSet newJwkSet = fetchJwkSet();
            this.cachedJwkSet = newJwkSet;
            this.lastRefreshTime = System.currentTimeMillis();
            log.debug("JWKS缓存刷新成功, keys count: {}", newJwkSet.getKeys().size());
        } catch (Exception e) {
            log.error("刷新JWKS失败", e);
            if (cachedJwkSet == null) {
                throw new RuntimeException("获取JWKS失败: " + e.getMessage(), e);
            }
        }
    }

    /**
     * 获取当前JWKSet(可能使用缓存)
     */
    private JWKSet getJwkSet() {
        long now = System.currentTimeMillis();
        if (cachedJwkSet == null || (now - lastRefreshTime) > cacheTtl) {
            refreshJwkSet();
        }
        return cachedJwkSet;
    }

    /**
     * 创建JWT处理器
     */
    private ConfigurableJWTProcessor<SecurityContext> createJwtProcessor() {
        ConfigurableJWTProcessor<SecurityContext> jwtProcessor = new DefaultJWTProcessor<>();
        jwtProcessor.setJWSKeySelector(new JWSVerificationKeySelector<>(
                jwsAlgorithm,
                new JWKSource<SecurityContext>() {
                    @Override
                    public List<JWK> get(JWKSelector jwkSelector, SecurityContext context)
                            throws KeySourceException {
                        try {
                            return jwkSelector.select(getJwkSet());
                        } catch (Exception e) {
                            throw new KeySourceException("从JWKS获取公钥失败", e);
                        }
                    }
                }
        ));

        JWTClaimsSetVerifier<SecurityContext> verifier = new DefaultJWTClaimsVerifier<>(
                new JWTClaimsSet.Builder().build(),
                null
        );
        jwtProcessor.setJWTClaimsSetVerifier(verifier);

        return jwtProcessor;
    }

    /**
     * 使用JWKS端点验证JWT令牌
     *
     * @param token JWT令牌
     * @return 验证结果
     */
    public boolean verifyToken(String token) {
        try {
            SignedJWT signedJWT = SignedJWT.parse(token);
            ConfigurableJWTProcessor<SecurityContext> jwtProcessor = createJwtProcessor();
            jwtProcessor.process(signedJWT, null);
            log.debug("JWT令牌验证成功");
            return true;
        } catch (Exception e) {
            log.error("JWT令牌验证失败", e);
            return false;
        }
    }

    /**
     * 验证JWT令牌并返回声明
     *
     * @param token JWT令牌
     * @return JWT声明集合
     */
    public JWTClaimsSet verifyAndGetClaims(String token) {
        try {
            SignedJWT signedJWT = SignedJWT.parse(token);
            ConfigurableJWTProcessor<SecurityContext> jwtProcessor = createJwtProcessor();
            return jwtProcessor.process(signedJWT, null);
        } catch (Exception e) {
            log.error("JWT令牌验证失败", e);
            throw new RuntimeException("JWT令牌验证失败: " + e.getMessage(), e);
        }
    }

    /**
     * 从JWKS端点获取公钥
     *
     * @param kid Key ID
     * @return JWK对象
     */
    public JWK getJwk(String kid) {
        JWKSet jwkSet = getJwkSet();
        for (JWK jwk : jwkSet.getKeys()) {
            if (kid.equals(jwk.getKeyID())) {
                return jwk;
            }
        }
        log.warn("未找到匹配的JWK, kid: {}", kid);
        throw new RuntimeException("未找到匹配的JWK: " + kid);
    }

    /**
     * 获取RSA公钥
     *
     * @param kid Key ID
     * @return RSA公钥
     */
    public java.security.interfaces.RSAPublicKey getRSAPublicKey(String kid) {
        try {
            JWK jwk = getJwk(kid);
            if (jwk instanceof RSAKey) {
                return ((RSAKey) jwk).toRSAPublicKey();
            }
            throw new RuntimeException("JWK不是RSA类型的公钥: " + kid);
        } catch (JOSEException e) {
            log.error("获取RSA公钥失败, kid: {}", kid, e);
            throw new RuntimeException("获取RSA公钥失败: " + e.getMessage(), e);
        }
    }

    /**
     * 获取EC公钥
     *
     * @param kid Key ID
     * @return EC公钥
     */
    public java.security.interfaces.ECPublicKey getECPublicKey(String kid) {
        try {
            JWK jwk = getJwk(kid);
            if (jwk instanceof ECKey) {
                return ((ECKey) jwk).toECPublicKey();
            }
            throw new RuntimeException("JWK不是EC类型的公钥: " + kid);
        } catch (JOSEException e) {
            log.error("获取EC公钥失败, kid: {}", kid, e);
            throw new RuntimeException("获取EC公钥失败: " + e.getMessage(), e);
        }
    }

    /**
     * 刷新JWKS缓存
     * 当公钥轮换时，可以调用此方法强制刷新缓存
     */
    public void refresh() {
        refreshJwkSet();
    }
}
