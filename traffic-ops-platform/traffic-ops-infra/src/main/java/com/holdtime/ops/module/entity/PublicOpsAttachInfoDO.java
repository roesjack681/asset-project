package com.holdtime.ops.module.entity;

import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;

/**
 * 资产附件信息 DO
 *
 * @author 管理员
 */
@TableName("public_ops_attach_info")
@KeySequence("public_ops_attach_info_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PublicOpsAttachInfoDO extends BaseDO {

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
     * 业务模块
     */
    private String businessModel;
    /**
     * 业务id
     */
    private String businessId;
    /**
     * 文件类型:1-图片；2-音频；3-视频
     */
    private String fileType;
    /**
     * 文件名称
     */
    private String fileName;
    /**
     * 文件路径
     */
    private String filePath;
    /**
     * 文件大小
     */
    private Integer fileSize;
    /**
     * 删除时间
     */
    private LocalDateTime deletedTime;

}