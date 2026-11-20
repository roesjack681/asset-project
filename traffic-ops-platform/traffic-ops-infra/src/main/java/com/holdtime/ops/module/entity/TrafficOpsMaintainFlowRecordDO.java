package com.holdtime.ops.module.entity;

import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;

/**
 * 设备维保全流程 DO
 *
 * @author 管理员
 */
@TableName("traffic_ops_maintain_flow_record")
@KeySequence("traffic_ops_maintain_flow_record_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TrafficOpsMaintainFlowRecordDO extends BaseDO {

    /**
     * 主键
     */
    @TableId(type = IdType.ASSIGN_ID)
    private String id;
    /**
     * 维保主键
     */
    private String maintenanceId;
    /**
     * 流程类型：1-上报；2-派单；3-接单；4-维修；5-确认；6-驳回；7-取消
     */
    private String flowType;
    /**
     * 注册编号
     */
    private String registerId;
    /**
     * 用户编号
     */
    private Long userId;
    /**
     * 用户姓名
     */
    private String userName;
    /**
     * 用户手机号
     */
    private String userPhone;
    /**
     * 地址
     */
    private String operateAddress;
    /**
     * 经度
     */
    private String operateLng;
    /**
     * 纬度
     */
    private String operateLat;
    /**
     * 操作状态
     */
    private String operateStatus;
    /**
     * 操作结果
     */
    private String operateResult;
    /**
     * 备注
     */
    private String remark;
    /**
     * 删除时间
     */
    private LocalDateTime deletedTime;

}