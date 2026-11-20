package com.holdtime.ops.module.entity;

import cn.iocoder.yudao.framework.mybatis.core.dataobject.PdBaseDO;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;

/**
 * 终端设备信息 DO
 *
 * @author 管理员
 */
@TableName("public_ops_device")
@KeySequence("public_ops_device_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PublicOpsDeviceDO extends PdBaseDO {

    /**
     * 主键
     */
    @TableId(type = IdType.INPUT)
    private String id;
    /**
     * model
     */
    private String model;
    /**
     * IMEI
     */
    private String imei;
    /**
     * 车牌号
     */
    private String plateNum;
    /**
     * 摄像头信息
     */
    private String cameraInfo;
    /**
     * 状态
     */
    private String status;
    /**
     * 备注
     */
    private String remark;

}