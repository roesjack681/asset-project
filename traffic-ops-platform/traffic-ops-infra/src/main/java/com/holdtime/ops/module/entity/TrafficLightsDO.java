package com.holdtime.ops.module.entity;

import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;

/**
 * 信号灯信息 DO
 *
 * @author 芋道源码
 */
@TableName("lights")
@KeySequence("lights_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TrafficLightsDO extends BaseDO {

    /**
     * 主键
     */
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;
    /**
     * 路口ID
     */
    private Long lkid;
    /**
     * 信号灯方向
     */
    private String xhdfx;
    /**
     * 相机方向
     */
    private String xjfx;
    /**
     * 信号灯状态
     */
    private String xhdzt;
    /**
     * 上次检测时间
     */
    private LocalDateTime jcsj;
    /**
     * 相机地址
     */
    private String xjdz;
    /**
     * 处置结果
     */
    private String czjg;
    /**
     * 是否检测
     */
    private String sfjc;
    /**
     * 坐标
     */
    private String zb;
    /**
     * 信号灯型号
     */
    private String xhdxh;
    /**
     * 检测目标：例：GB1:3(此方向有3个GB1灯);FGB1:2;FGBF1:2(2个非国标机动车灯，2个非国标非机动车灯)
     */
    private String jcmb;
    /**
     * 常黑状态个数
     */
    private Integer ch;
    /**
     * 信号灯识别区域
     */
    private String xhdsbqy;
    /**
     * 拥堵识别区域
     */
    private String ydsbqy;

}