package com.holdtime.ops.module.entity;

import lombok.*;
import java.util.*;
import com.baomidou.mybatisplus.annotation.*;
import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;

/**
 * 路口信息 DO
 *
 * @author 芋道源码
 */
@TableName("crossing")
@KeySequence("crossing_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TrafficCrossingDO extends BaseDO {

    /**
     * 主键
     */
    @TableId(type = IdType.INPUT)
    private Long id;
    /**
     * 路口名称
     */
    private String lkmc;
    /**
     * 路口类型
     */
    private String lklx;
    /**
     * 路口坐标，需要在地图显示时使用
     */
    private String lkzb;
    /**
     * 区旗县
     */
    private String qu;

}