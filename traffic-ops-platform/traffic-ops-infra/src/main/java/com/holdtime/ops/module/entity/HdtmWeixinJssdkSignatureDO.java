package com.holdtime.ops.module.entity;

import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;

/**
 * 微信JS SDK签名信息 DO
 *
 * @author yangli
 */
@TableName("hdtm_weixin_jssdk_signature")
@KeySequence("hdtm_weixin_jssdk_signature_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HdtmWeixinJssdkSignatureDO extends BaseDO {

    /**
     * 主键
     */
    @TableId(type = IdType.INPUT)
    private String id;
    /**
     * 签名
     */
    private String signature;
    /**
     * 时间戳
     */
    private String timessamp;
    /**
     * 随机字符串
     */
    private String nonceStr;
    /**
     * 请求url
     */
    private String url;
    /**
     * JS接口调用临时票据
     */
    private String jsapiTicket;
    /**
     * 删除时间
     */
    private LocalDateTime deletedTime;

}