package com.holdtime.ops.module.entity;

import lombok.*;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;

/**
 * 护栏维护详细 DO
 *
 * @author 管理员
 */
@TableName("traffic_ops_maintenance_guardrail")
@KeySequence("traffic_ops_maintenance_guardrail_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TrafficOpsMaintenanceGuardrailDO extends BaseDO {

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
     * 护栏主键
     */
    private String guardrailId;
    /**
     * 护栏维护类型：清洗；更换；拆除
     */
    private String maintenanceType;
    /**
     * 更换类型：损毁；换新
     */
    private String changeType;
    /**
     * 起始位置名称
     */
    private String startName;
    /**
     * 起始位置经度
     */
    private String startLng;
    /**
     * 起始位置纬度
     */
    private String startLat;
    /**
     * 截止位置名称
     */
    private String endName;
    /**
     * 截止位置经度
     */
    private String endLng;
    /**
     * 截止位置纬度
     */
    private String endLat;
    /**
     * 是否增加开口
     */
    private String isGap;
    /**
     * 变更前产品编号
     */
    private String moditfyBeProduct;
    /**
     * 变更前片数
     */
    private Integer moditfyBePieces;
    /**
     * 变更后产品编号
     */
    private String moditfyAfProduct;
    /**
     * 变更后片数
     */
    private Integer moditfyAfPieces;
    /**
     * 变更前图片
     */
    private String moditfyBeImg;
    /**
     * 变更后图片
     */
    private String moditfyAfImg;
    /**
     * 变更内容描述
     */
    private String moditfyDesc;
    /**
     * 备注
     */
    private String remark;
    /**
     * 赔付类型：保险；运维
     */
    private String claimType;
    /**
     * 拆除类型：拆首段;拆尾端;拆中段;拆辅助设备
     */
    private String removalType;
    /**
     * 是否首位置变更
     */
    private String isHeadModitfy;
    /**
     * 是否尾位置变更
     */
    private String isTailModitfy;
    /**
     * 删除时间
     */
    private LocalDateTime deletedTime;

}