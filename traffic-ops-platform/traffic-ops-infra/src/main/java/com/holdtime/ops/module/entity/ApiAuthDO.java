package com.holdtime.ops.module.entity;

import cn.iocoder.yudao.framework.mybatis.core.dataobject.PdBaseDO;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.time.LocalDateTime;

/**
 * 接口授权 DO
 *
 * @author 超级管理员
 */
@TableName("hdtm_api_auth")
@KeySequence("hdtm_api_auth_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApiAuthDO extends PdBaseDO {

    /**
     * 主键
     */
    @TableId(type = IdType.ASSIGN_ID)
    private String id;
    /**
     * 客户端序列号
     */
    private String clientSn;
    /**
     * 授权码
     */
    private String authCode;
    /**
     * 开始时间
     */
    private LocalDateTime startDate;
    /**
     * 到期时间
     */
    private LocalDateTime expireDate;
    /**
     * 备注
     */
    private String remark;

}