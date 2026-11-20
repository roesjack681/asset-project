package com.holdtime.ops.module.conf;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "hdtm.project.qrcode")
public @Data class OpsProjectQrCodeConfig {
    /**
     * 接口地址
     */
    private String apiHost;

    /**
     * 二维码宽
     */
    private Integer width;

    /**
     * 二维码高
     */
    private Integer height;
}
