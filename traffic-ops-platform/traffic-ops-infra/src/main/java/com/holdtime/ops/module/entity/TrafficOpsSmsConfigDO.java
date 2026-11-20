package com.holdtime.ops.module.entity;

import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;

/**
 * 资产短信发送配置 DO
 *
 * @author 杨丽
 */
@TableName("traffic_ops_sms_config")
@KeySequence("traffic_ops_sms_config_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TrafficOpsSmsConfigDO extends BaseDO {

    /**
     * 主键
     */
    @TableId(type = IdType.ASSIGN_ID)
    private String id;
    /**
     * 发送类型：1重点考核视频；2重点考核卡口
     */
    private String sendType;
    /**
     * 接收人姓名
     */
    private String receiveName;
    /**
     * 接收人手机号
     */
    private String receivePhone;
    /**
     * 用户编号
     */
    private String userBh;
    /**
     * 接收状态:on;off
     */
    private String receiveStatus;
    /**
     * 删除时间
     */
    private LocalDateTime deletedTime;

}