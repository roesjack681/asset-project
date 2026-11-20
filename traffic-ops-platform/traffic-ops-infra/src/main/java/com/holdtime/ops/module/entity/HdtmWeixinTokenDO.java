package com.holdtime.ops.module.entity;

import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;

/**
 * 微信Token DO
 *
 * @author yangli
 */
@TableName("hdtm_weixin_token")
@KeySequence("hdtm_weixin_token_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HdtmWeixinTokenDO extends BaseDO {

    /**
     * 主键
     */
    @TableId(type = IdType.INPUT)
    private String id;
    /**
     * 微信接口调用票据
     */
    private String accessToken;
    /**
     * 获得票据时间
     */
    private Long getTokenMillis;
    /**
     * 微信JS接口调用临时票据
     */
    private String jsapiTicket;
    /**
     * 获得临时票据时间
     */
    private Long getTicketMillis;
    /**
     * 删除时间
     */
    private LocalDateTime deletedTime;

}