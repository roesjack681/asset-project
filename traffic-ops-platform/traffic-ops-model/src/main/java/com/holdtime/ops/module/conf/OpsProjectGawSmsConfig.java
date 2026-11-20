package com.holdtime.ops.module.conf;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "hdtm.project.gaw.sms")
public @Data class OpsProjectGawSmsConfig {
    /**
     * 短信地址
     */
    private String apiUrl;

    /**
     * 序列号
     */
    private String serialNum;

    /**
     * mac地址
     */
    private String mac;

    /**
     * 系统名称
     */
    private String systemName;

    /**
     * 发送人姓名
     */
    private String sendUserName;


    /**
     * 发送部门
     */
    private String sendUserDept;

}
