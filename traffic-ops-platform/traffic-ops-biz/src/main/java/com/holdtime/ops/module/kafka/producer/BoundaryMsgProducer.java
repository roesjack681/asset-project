package com.holdtime.ops.module.kafka.producer;

import cn.hutool.core.lang.UUID;
import com.holdtime.ops.module.kafka.message.VideoNetBoundaryMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Slf4j
@Component
public class BoundaryMsgProducer {
    @Resource
    private KafkaTemplate<Object, Object> kafkaTemplate;

    /**
     * 视频网到互联网
     * @param msgType 消息类型
     * @param msgDataType 数据变动类型
     * @param msgData 消息体
     */
    public void sendMessageFromVideoToInternet(String msgType, String msgDataType, String msgData) {
        sendKafkaMessage(VideoNetBoundaryMessage.VIDEO_TO_INTERNET_TOPIC, msgType, msgDataType, msgData);
    }

    /**
     * 互联网到视频网
     * @param msgType 消息类型
     * @param msgDataType 数据变动类型
     * @param msgData 消息体
     */
    public void sendMessageFromInternetToVideo(String msgType, String msgDataType, String msgData) {
        sendKafkaMessage(VideoNetBoundaryMessage.INTERNET_TO_VIDEO_TOPIC, msgType, msgDataType, msgData);
    }

    /**
     * 视频网到互联网
     * @param msgType 消息类型
     * @param msgDataType 数据变动类型
     * @param msgData 消息体
     */
    public void batchSendMessageFromVideoToInternet(String msgType, String msgDataType, String msgData) {
        sendKafkaMessage(VideoNetBoundaryMessage.VIDEO_TO_INTERNET_TOPIC_BATCH, msgType, msgDataType, msgData);
    }

    /**
     * 互联网到视频网
     * @param msgType 消息类型
     * @param msgDataType 数据变动类型
     * @param msgData 消息体
     */
    public void batchSendMessageFromInternetToVideo(String msgType, String msgDataType, String msgData) {
        sendKafkaMessage(VideoNetBoundaryMessage.INTERNET_TO_VIDEO_TOPIC_BATCH, msgType, msgDataType, msgData);
    }

    /**
     * 发送kafka消息
     * @param topic 主题
     * @param msgType 消息类型
     * @param msgDataType 数据变动类型
     * @param msgData 消息体
     */
    public void sendKafkaMessage(String topic, String msgType, String msgDataType, String msgData) {
        VideoNetBoundaryMessage kafkaMessage = new VideoNetBoundaryMessage();
        kafkaMessage.setMsgType(msgType);
        kafkaMessage.setMsgDataType(msgDataType);
        kafkaMessage.setMsgData(msgData);
        kafkaMessage.setTimestamp(System.currentTimeMillis());
        kafkaMessage.setNonce(UUID.fastUUID().toString());

        // 异步发送，不阻塞调用线程
        kafkaTemplate.send(topic, kafkaMessage).addCallback(
            result -> log.debug("[sendKafkaMessage][消息发送成功] topic: {}, msgType: {}, msgDataType: {}",
                topic, msgType, msgDataType),
            failure -> log.error("[sendKafkaMessage][消息发送失败] topic: {}, msgType: {}, msgDataType: {}",
                topic, msgType, msgDataType, failure)
        );
    }

}
