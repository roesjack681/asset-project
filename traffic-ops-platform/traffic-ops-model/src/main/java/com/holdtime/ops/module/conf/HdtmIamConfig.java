package com.holdtime.ops.module.conf;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 统一用户中心配置属性
 */
@Data
@Component
@ConfigurationProperties(prefix = "hdtm.iam")
public class HdtmIamConfig {

    /**
     * 是否启用统一用户中心
     */
    private Boolean enabled = false;

    /**
     * 认证中心地址
     */
    private String baseUrl = "https://iamapi.xiaonuo.vip";

    /**
     * 客户端ID
     */
    private String clientId;

    /**
     * 签名密钥
     */
    private String secretKey;


    /**
     * 签名算法: RS256, ES256
     */
    private String algorithm = "RS256";

    /**
     * 验签公钥
     */
    private String publicKey;

    /**
     * 是否使用JWKS端点
     */
    private Boolean useJwks = false;

    /**
     * JWKS端点URL
     */
    private String jwksUrl;

    /**
     * 消息推送端点
     */
    private String pushServerUrl;

    /**
     * 撤销令牌端点
     */
    public String getRevokeTokenUrl() {
        return baseUrl + "/iam/auth/protocol/oauth/revoke";
    }

    /**
     * 获取JWKS端点URL
     */
    public String getJwksUrl() {
        if (jwksUrl != null && !jwksUrl.trim().isEmpty()) {
            return jwksUrl;
        }
        // 如果没有配置，则使用默认位置
        return baseUrl + "/.well-known/jwks.json";
    }
}
