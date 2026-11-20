package com.holdtime.ops.module.utils.emay;

/**
 * 项目名称： hdtm-sms
 * 功能描述： BaseReqDTO概述
 * 创 建 人： yanxl
 * 创建日期： 2022年10月08日 23:26:00
 * 修 改 人：
 * 修改时间：
 * 修改内容：
 * 版    本： 1.0.0
 */
public class BaseReqDTO {
    private static final long serialVersionUID = 1L;

    /**
     * 请求时间
     */
    private long requestTime = System.currentTimeMillis();

    /**
     * 请求有效时间(秒)<br/>
     * 服务器接受时间与请求时间对比，如果超过有效时间，拒绝此次请求<br/>
     * 防止被网络抓包不断发送同一条请求<br/>
     * 默认1分钟有效期
     */
    private int requestValidPeriod  = 60;

    public long getRequestTime() {
        return requestTime;
    }

    public void setRequestTime(long requestTime) {
        this.requestTime = requestTime;
    }

    public int getRequestValidPeriod() {
        return requestValidPeriod;
    }

    public void setRequestValidPeriod(int requestValidPeriod) {
        this.requestValidPeriod = requestValidPeriod;
    }
}
