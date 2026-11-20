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
 * 资产二维码生成记录 DO
 *
 * @author yangli
 */
@TableName("traffic_ops_qrcode_gen_record")
@KeySequence("traffic_ops_qrcode_gen_record_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TrafficOpsQrcodeGenRecordDO extends BaseDO {

    /**
     * 主键
     */
    @TableId(type = IdType.ASSIGN_ID)
    private String id;
    /**
     * 所属单位：1-交警；2-公安
     */
    private String affiliatedUnit;
    /**
     * 区域编码
     */
    private String geoCode;
    /**
     * 生成日期
     */
    private LocalDateTime genDate;
    /**
     * 道路ID
     */
    private String roadId;
    /**
     * 道路名称
     */
    private String roadName;
    /**
     * 资产类型
     */
    private String opsType;
    /**
     * 方向和数量(json存储)
     */
    private String directionCnt;
    /**
     * 删除时间
     */
    private LocalDateTime deletedTime;

}