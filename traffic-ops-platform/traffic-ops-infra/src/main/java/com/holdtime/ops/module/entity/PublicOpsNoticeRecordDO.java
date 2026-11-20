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
 * 消息通知记录 DO
 *
 * @author 管理员
 */
@TableName("public_ops_notice_record")
@KeySequence("public_ops_notice_record_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PublicOpsNoticeRecordDO extends BaseDO {

    /**
     * 主键
     */
    @TableId(type = IdType.ASSIGN_ID)
    private String id;
    /**
     * 业务类型
     */
    private String businessType;
    /**
     * 业务主键
     */
    private String businessId;
    /**
     * 消息类型:1-SMS;2-IM;3-mail
     */
    private String noticeType;
    /**
     * 接收者类型
     */
    private String receiveUserType;
    /**
     * 接收者ID
     */
    private String receiveUserId;
    /**
     * 接收者姓名
     */
    private String receiveUserName;
    /**
     * 接收者电话
     */
    private String receiveUserPhone;
    /**
     * 通知消息
     */
    private String noticeMsg;
    /**
     * 发送时间
     */
    private LocalDateTime sendTime;
    /**
     * 删除时间
     */
    private LocalDateTime deletedTime;

}