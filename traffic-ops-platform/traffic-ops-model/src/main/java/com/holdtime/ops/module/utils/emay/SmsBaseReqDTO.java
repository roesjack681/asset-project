package com.holdtime.ops.module.utils.emay;

/**
 * 项目名称： hdtm-sms
 * 功能描述： SmsBaseReqDTO概述
 * 创 建 人： yanxl
 * 创建日期： 2022年10月08日 23:28:00
 * 修 改 人：
 * 修改时间：
 * 修改内容：
 * 版    本： 1.0.0
 */
public class SmsBaseReqDTO extends BaseReqDTO {
    private static final long serialVersionUID = 1L;

    /**
     * 定时时间
     * yyyy-MM-dd HH:mm:ss
     */
    private String timerTime;

    /**
     * 扩展码
     */
    private String extendedCode;

    public String getTimerTime() {
        return timerTime;
    }

    public void setTimerTime(String timerTime) {
        this.timerTime = timerTime;
    }

    public String getExtendedCode() {
        return extendedCode;
    }

    public void setExtendedCode(String extendedCode) {
        this.extendedCode = extendedCode;
    }
}
