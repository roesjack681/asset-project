package com.holdtime.ops.module.kafka.message;

import lombok.Data;

import java.io.Serializable;

@Data
public class VideoNetBoundaryMessage implements Serializable {
    public static final String VIDEO_TO_INTERNET_TOPIC = "boundary_video_to_internet";
    public static final String INTERNET_TO_VIDEO_TOPIC = "boundary_internet_to_video";

    public static final String VIDEO_TO_INTERNET_TOPIC_BATCH = "boundary_video_to_internet_batch";
    public static final String INTERNET_TO_VIDEO_TOPIC_BATCH = "boundary_internet_to_video_batch";

    /**
     * 消息类型
     */
    private String msgType;

    /**
     * 数据变动类型
     */
    private String msgDataType;

    /**
     * 消息数据
     */
    private String msgData;

    /**
     * 时间戳
     */
    private long timestamp;

    /**
     * 随机字符串
     */
    private String nonce;

    /**
     * 签名
     */
    private String sign;
}
