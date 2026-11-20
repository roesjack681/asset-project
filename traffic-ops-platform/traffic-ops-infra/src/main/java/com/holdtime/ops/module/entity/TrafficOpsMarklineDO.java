package com.holdtime.ops.module.entity;

import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;

/**
 * 标线 DO
 *
 * @author 芋道源码
 */
@TableName("traffic_ops_markline")
@KeySequence("traffic_ops_markline_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TrafficOpsMarklineDO extends BaseDO {

    /**
     * 主键
     */
    @TableId(type = IdType.ASSIGN_ID)
    private String id;
    /**
     * 道路名称
     */
    private String roadName;
    /**
     * 路段
     */
    private String roadParts;
    /**
     * 施画种类
     */
    private String marklineSort;
    /**
     * 数量
     */
    private String marklineNum;
    /**
     * 线宽
     */
    private String lineWidth;
    /**
     * 规格
     */
    private String specifications;
    /**
     * 比例
     */
    private String proportion;
    /**
     * 面积
     */
    private String area;
    /**
     * 施画单位
     */
    private String paintingUnit;
    /**
     * 施划工艺
     */
    private String drawingProcess;
    /**
     * 施划时间
     */
    private LocalDateTime applicationTime;
    /**
     * 删除时间
     */
    private LocalDateTime deletedTime;
    /**
     * 所属管辖单位
     */
    private String dominion;
    /**
     * 状态
     */
    private String status;
    /**
     * 设备图片
     */
    private String equImg;

}