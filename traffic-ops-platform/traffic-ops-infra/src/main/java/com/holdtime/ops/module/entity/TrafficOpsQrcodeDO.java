package com.holdtime.ops.module.entity;

import lombok.*;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;

/**
 * 资产二维码信息 DO
 *
 * @author yangli
 */
@TableName("traffic_ops_qrcode")
@KeySequence("traffic_ops_qrcode_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TrafficOpsQrcodeDO extends BaseDO {

    /**
     * 主键
     */
    @TableId(type = IdType.INPUT)
    private String id;
    /**
     * 所属单位：1-交警；2-公安
     */
    private String affiliatedUnit;
    /**
     * 道路ID
     */
    private String roadId;
    /**
     * 道路名称
     */
    private String raodName;
    /**
     * 方向
     */
    private String direction;
    /**
     * 生成记录ID
     */
    private String genRecordId;
    /**
     * 资产类型
     */
    private String opsType;
    /**
     * 序列号前缀
     */
    private String serialPrefix;
    /**
     * 序列号
     */
    private Integer serialNum;
    /**
     * 二维码内容
     */
    private String qrcodeContent;
    /**
     * 二维码文件
     */
    private String qrcodeFile;
    /**
     * 删除时间
     */
    private LocalDateTime deletedTime;

}