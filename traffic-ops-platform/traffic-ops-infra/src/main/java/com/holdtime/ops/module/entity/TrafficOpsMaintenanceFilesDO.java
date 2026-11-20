package com.holdtime.ops.module.entity;

import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;

/**
 * 维保材料信息 DO
 *
 * @author 芋道源码
 */
@TableName("traffic_ops_maintenance_files")
@KeySequence("traffic_ops_maintenance_files_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TrafficOpsMaintenanceFilesDO extends BaseDO {

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
     * 维保进度id
     */
    private String progressId;
    /**
     * 报修类型：上报；维修
     */
    private String repairType;
    /**
     * 材料类型：图片；音频；视频
     */
    private String fileType;
    /**
     * 材料url
     */
    private String fileUrl;
    /**
     * 删除时间
     */
    private LocalDateTime deletedTime;

}