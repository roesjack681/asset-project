package com.holdtime.ops.module.utils.emay;

/**
 * 项目名称： hdtm-sms
 * 功能描述： SmsSingleReqDTO概述
 * 创 建 人： yanxl
 * 创建日期： 2022年10月08日 23:30:00
 * 修 改 人：
 * 修改时间：
 * 修改内容：
 * 版    本： 1.0.0
 */
public class SmsSingleReqDTO extends SmsBaseReqDTO {
    private static final long serialVersionUID = 1L;

    /**
     * 电话号码
     */
    private String mobile;

    /**
     * 短信内容
     */
    private String content;

    /**
     * 自定义smsid
     */
    private String customSmsId;


    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCustomSmsId() {
        return customSmsId;
    }

    public void setCustomSmsId(String customSmsId) {
        this.customSmsId = customSmsId;
    }
}
