package com.holdtime.ops.module.conf;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@ConfigurationProperties(prefix = "hdtm.project.emay")
public @Data class OpsProjectEmayConfig {
    /**
     * 接口地址
     */
    private String host;

    /**
     * 应用ID
     */
    private String appId;

    /**
     * 应用Key
     */
    private String secretKey;

    /**
     * 签名
     */
    private String signName;

    /**
     * 通知消息
     */
    private Map<String, String> noticeMsgMap;
}
