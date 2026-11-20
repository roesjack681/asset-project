package com.holdtime.ops.module.kafka.handler.impl;

import cn.hutool.json.JSONUtil;
import com.holdtime.ops.module.kafka.handler.KafkaMsgBaseHandler;
import com.holdtime.ops.module.kafka.message.VideoNetBoundaryMessage;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * 项目名称： traffic-ops-platform
 * 功能描述： KafkaMsgBaseHandlerServiceImpl概述
 * 创 建 人： aisrch
 * 创建日期： 2026年01月27日 16:38:00
 * 修 改 人：
 * 修改时间：
 * 修改内容：
 * 版    本： 1.0.0
 */
@Service
@Slf4j
@Profile({"prod"})
public class InternetKafkaMsgBaseHandlerImpl implements KafkaMsgBaseHandler {
    @Resource
    private InternalPoliceMsgHandler internalPoliceMsgHandler;

    private final Map<String, Function<VideoNetBoundaryMessage, String>> msgNotifyHandlerMap = new HashMap<>();

    @PostConstruct
    public void dispatcherInit(){
        msgNotifyHandlerMap.put("POLICE_EQU_PING_MONITOR", message -> {
            internalPoliceMsgHandler.handlerPingPoliceOpsElectricPoliceMsg(message);
            return "success";
        });

        msgNotifyHandlerMap.put("POLICE_EQU_CONFIRM_RESULT", message -> {
            internalPoliceMsgHandler.handlerPoliceEquConfirmResultMsg(message);
            return "success";
        });

    }

    @Override
    public void handlerMsg(String kafkaMsg) {
        if (StringUtils.isBlank(kafkaMsg)) {
            log.warn("收到空消息");
            return;
        }
        
        VideoNetBoundaryMessage boundaryMessage;
        try {
            boundaryMessage = JSONUtil.toBean(kafkaMsg, VideoNetBoundaryMessage.class);
        } catch (Exception e) {
            log.error("解析Kafka消息失败: {}", kafkaMsg, e);
            throw new RuntimeException("消息格式错误", e);
        }
        
        String msgType = boundaryMessage.getMsgType();
        if (StringUtils.isBlank(msgType)) {
            log.error("消息类型为空: {}", kafkaMsg);
            throw new RuntimeException("消息类型为空");
        }
        
        Function<VideoNetBoundaryMessage, String> msgFunction = msgNotifyHandlerMap.get(msgType);
        if (msgFunction == null) {
            log.error("未找到消息处理类！msgType: {}", msgType);
            // 未找到处理类的消息不抛异常，直接跳过
            return;
        }
        
        try {
            msgFunction.apply(boundaryMessage);
        } catch (Exception e) {
            log.error("处理Kafka消息失败 msgType: {}, msgDataType: {}",
                msgType, boundaryMessage.getMsgDataType(), e);
            // 业务异常抛出，让消息重新消费
            throw new RuntimeException("消息处理失败: " + msgType, e);
        }
    }
}
