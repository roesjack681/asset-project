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
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Component
@Slf4j
@Profile({"prod"})
public class VideoToInternetMsgConsumer {
    @Resource
    private KafkaMsgBaseHandler kafkaMsgBaseHandler;

    // 消费端并行处理线程池
    private final ExecutorService consumerExecutor = new ThreadPoolExecutor(
        Runtime.getRuntime().availableProcessors() * 2,
        50,
        60L,
        TimeUnit.SECONDS,
        new LinkedBlockingQueue<>(2000),
        new ThreadPoolExecutor.CallerRunsPolicy()
    );

    @KafkaListener(topics = VideoNetBoundaryMessage.VIDEO_TO_INTERNET_TOPIC, groupId = VideoNetBoundaryMessage.VIDEO_TO_INTERNET_TOPIC + "_CONSUMER", containerFactory = "batchFactory")
    public void onMessage(@Payload List<String> messages, Acknowledgment acknowledgment,
                         @Header(KafkaHeaders.RECEIVED_PARTITION_ID) List<Integer> partitions,
                         @Header(KafkaHeaders.OFFSET) List<Long> offsets) {
        if (messages == null || messages.isEmpty()) {
            return;
        }

        long startTime = System.currentTimeMillis();
        log.info("开始处理Kafka消息批次, 数量: {}", messages.size());

        List<CompletableFuture<Void>> futures = new ArrayList<>();

        // 并行处理每条消息
        for (int i = 0; i < messages.size(); i++) {
            final int index = i;
            final String message = messages.get(i);
            final Integer partition = partitions.get(i);
            final Long offset = offsets.get(i);

            CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
                try {
                    kafkaMsgBaseHandler.handlerMsg(message);
                    log.debug("消息处理成功 partition: {}, offset: {}", partition, offset);
                } catch (Exception e) {
                    log.error("处理消息失败 partition: {}, offset: {}, message: {}",
                        partition, offset, message.substring(0, Math.min(100, message.length())), e);
                    // 记录失败消息，但不影响其他消息
                }
            }, consumerExecutor);

            futures.add(future);
        }

        // 等待所有消息处理完成
        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();

        // 确认所有消息（无论成功失败，避免重复消费）
        acknowledgment.acknowledge();

        long elapsed = System.currentTimeMillis() - startTime;
        log.info("Kafka消息批次处理完成, 数量: {}, 耗时: {}ms", messages.size(), elapsed);
    }
}
