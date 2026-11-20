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
 * 资产摄像设备信息 DO
 *
 * @author 管理员
 */
@TableName("public_ops_camera_info")
@KeySequence("public_ops_camera_info_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PublicOpsCameraInfoDO extends BaseDO {

    /**
     * 主键
     */
    @TableId(type = IdType.INPUT)
    private String id;
    /**
     * 摄像头名称
     */
    private String cameraName;
    /**
     * 摄像头编号
     */
    private String cameraBh;
    /**
     * 摄像头用户
     */
    private String cameraUser;
    /**
     * 摄像头密码
     */
    private String cameraPassword;
    /**
     * 摄像头组
     */
    private String cameraGroup;
    /**
     * 安装位置
     */
    private String installLocation;
    /**
     * 安装时间
     */
    private LocalDateTime installDate;
    /**
     * 删除时间
     */
    private LocalDateTime deletedTime;

}