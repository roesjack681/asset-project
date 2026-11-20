package com.holdtime.ops.module.kafka.handler;

/**
 * 项目名称： traffic-ops-platform
 * 功能描述： KafkaMsgBaseHandlerService
 * 创 建 人： aisrch
 * 创建日期： 2026年01月27日 11:05:00
 * 修 改 人：
 * 修改时间：
 * 修改内容：
 * 版    本： 1.0.0
 */
public interface KafkaMsgBaseHandler {

    void handlerMsg(String kafkaMsg);
}
