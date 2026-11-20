package com.holdtime.ops.module.entity;

import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;

/**
 * 道路信息 DO
 *
 * @author 芋道源码
 */
@TableName("traffic_ops_road")
@KeySequence("traffic_ops_road_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TrafficOpsRoadDO extends BaseDO {

    /**
     * 主键
     */
    @TableId(type = IdType.ASSIGN_ID)
    private String id;
    /**
     * 管理部门
     */
    private String glbm;
    /**
     * 道路代码
     */
    private String dldm;
    /**
     * 道路名称
     */
    private String dlmc;
    /**
     * 道路类型
     */
    private String dllx;
    /**
     * 公路行政等级
     */
    private String glxzdj;
    /**
     * 起始
     */
    private String qs;
    /**
     * 结束
     */
    private String js;
    /**
     * 辖区里程
     */
    private String xqlc;
    /**
     * 起始名称
     */
    private String qsmc;
    /**
     * 结束名称
     */
    private String jsmc;
    /**
     * 删除时间
     */
    private LocalDateTime deletedTime;

}