package com.holdtime.ops.module.entity;

import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;

/**
 * 信号灯检测信息 DO
 *
 * @author 芋道源码
 */
@TableName("lightsdetectionlog")
@KeySequence("lightsdetectionlog_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TrafficLightsdetectionlogDO extends BaseDO {

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
     * 检测结果
     */
    private String jcjg;
    /**
     * 检测结果扩展信息
     */
    private String jcjgex;
    /**
     * 检测图片PATH
     */
    private String jctp;
    /**
     * 检测时间
     */
    private LocalDateTime jcsj;
    /**
     * 判断结果
     */
    private String pdjg;

}