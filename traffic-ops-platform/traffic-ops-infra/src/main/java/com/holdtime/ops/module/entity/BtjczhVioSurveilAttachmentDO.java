package com.holdtime.ops.module.entity;

import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;

/**
 * 违法抓拍附件 DO
 *
 * @author 管理员
 */
@TableName("btjczh_vio_surveil_attachment")
@KeySequence("btjczh_vio_surveil_attachment_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BtjczhVioSurveilAttachmentDO extends BaseDO {

    /**
     * 主键
     */
    @TableId(type = IdType.INPUT)
    private String id;
    /**
     * 号牌种类
     */
    private String hpzl;
    /**
     * 号牌号码
     */
    private String hphm;
    /**
     * 图片1
     */
    private String url1;
    /**
     * 图片2
     */
    private String url2;
    /**
     * 图片3
     */
    private String url3;
    /**
     * 录入时间
     */
    private LocalDateTime lrsj;

}