package com.holdtime.ops.module.kafka.consumer;

import com.holdtime.ops.module.kafka.handler.KafkaMsgBaseHandler;
import com.holdtime.ops.module.kafka.message.VideoNetBoundaryMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;


@Component
@Slf4j
@Profile({"spw"})
public class InternetToVideoMsgConsumer {

    @Resource
    KafkaMsgBaseHandler kafkaMsgBaseHandler;

    @KafkaListener(topics = VideoNetBoundaryMessage.INTERNET_TO_VIDEO_TOPIC, 
                   groupId = VideoNetBoundaryMessage.INTERNET_TO_VIDEO_TOPIC + "_CONSUMER",
                   containerFactory = "batchFactory")
    public void onMessage(@Payload List<String> messages, 
                         Acknowledgment acknowledgment, 
                         @Header(KafkaHeaders.RECEIVED_PARTITION_ID) List<Integer> partitions,
                         @Header(KafkaHeaders.OFFSET) List<Long> offsets) {
        if (messages == null || messages.isEmpty()) {
            return;
        }

        List<Integer> successIndexes = new ArrayList<>();

        try {
            for (int i = 0; i < messages.size(); i++) {
                String message = messages.get(i);
                Integer partition = partitions.get(i);
                Long offset = offsets.get(i);
                
                log.debug("boundary_internet_to_video onMessage partition: {}, offset: {}, message: {}", 
                    partition, offset, message.substring(0, Math.min(100, message.length())));
                
                try {
                    kafkaMsgBaseHandler.handlerMsg(message);
                    successIndexes.add(i);
                } catch (Exception e) {
                    log.error("处理消息失败 partition: {}, offset: {}, message: {}", 
                        partition, offset, message.substring(0, Math.min(100, message.length())), e);
                    // 单条消息失败不影响其他消息
                }
            }
            
            if (!successIndexes.isEmpty()) {
                // 批量确认成功处理的消息
                acknowledgment.acknowledge();
            } else {
                log.warn("批次中所有消息处理失败，将重新消费");
            }
        } catch (Exception e) {
            log.error("批量处理消息异常，将重新消费", e);
            // 不确认，让 Kafka 重新投递
        }
    }
}
