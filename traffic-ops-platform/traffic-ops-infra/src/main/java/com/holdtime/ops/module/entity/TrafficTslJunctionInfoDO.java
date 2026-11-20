package com.holdtime.ops.module.entity;

import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;

/**
 * 信控交叉口信息 DO
 *
 * @author yangli
 */
@TableName("traffic_tsl_junction_info")
@KeySequence("traffic_tsl_junction_info_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TrafficTslJunctionInfoDO extends BaseDO {

    /**
     * 交叉口ID
     */
    @TableId(type = IdType.INPUT)
    private String id;
    /**
     * 所属区域ID
     */
    private String regionId;
    /**
     * 交叉口名称
     */
    private String junctionName;
    /**
     * 控制ID
     */
    private String controllerId;
    /**
     * 控制名称
     */
    private String controllerName;
    /**
     * 协议类型
     */
    private String tcpudp;
    /**
     * 协议编号
     */
    private String protocol;
    /**
     * 删除时间
     */
    private LocalDateTime deletedTime;

}