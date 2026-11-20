package com.holdtime.ops.module.service;

import com.holdtime.ops.module.utils.emay.SmsSingleReqDTO;

public interface Sms4JService {
    /**
     * 发送通知短信
     * @param phone 手机号
     * @param noticeMsgKey 短信通知模板key
     */
    SmsSingleReqDTO sendNoticeMsg(String phone, String noticeMsgKey);

    /**
     * 发送自定义通知短信
     * @param phone 手机号
     * @param noticeMsg 自定义短信内容
     */
    SmsSingleReqDTO sendCustomNoticeMsg(String phone, String noticeMsg);
}
