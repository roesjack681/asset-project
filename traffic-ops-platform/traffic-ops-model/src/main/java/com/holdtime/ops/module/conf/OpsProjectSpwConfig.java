package com.holdtime.ops.module.conf;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@ConfigurationProperties(prefix = "hdtm.project.spw")
public @Data class OpsProjectSpwConfig {
    /**
     * 公安网地址
     */
    private String gawHost;

    /**
     * wvp视频接口地址
     */
    private String wvpHost;


}
