package com.holdtime.ops.module.entity;

import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;

/**
 * 设备状态检测 DO
 *
 * @author 芋道源码
 */
@TableName("camdetectionlog")
@KeySequence("camdetectionlog_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TrafficCamdetectionlogDO extends BaseDO {

    /**
     * 主键
     */
    @TableId(type = IdType.INPUT)
    private Long id;
    /**
     * 路口ID
     */
    private Long lkid;
    /**
     * 相机ID
     */
    private Long sbid;
    /**
     * 相机状态，是否能打开视频流
     */
    private String xjzt;
    /**
     * 检测时间
     */
    private LocalDateTime jcsj;

}