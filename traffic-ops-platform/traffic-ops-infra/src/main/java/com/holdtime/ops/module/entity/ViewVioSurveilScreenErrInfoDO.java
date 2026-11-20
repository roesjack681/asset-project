package com.holdtime.ops.module.entity;

import cn.iocoder.yudao.framework.mybatis.core.dataobject.PdBaseDO;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;

/**
 * 集指无效违法记录 DO
 *
 * @author 管理员
 */
@TableName("view_vio_surveil_screen_err_info")
@KeySequence("view_vio_surveil_screen_err_info_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ViewVioSurveilScreenErrInfoDO extends PdBaseDO {

    /**
     * 主键
     */
    @TableId(type = IdType.INPUT)
    private String id;
    /**
     * 设备编号
     */
    private String sbbh;
    /**
     * 号牌号码
     */
    private String hphm;
    /**
     * 号牌种类
     */
    private String hpzl;
    /**
     * 违法时间
     */
    private LocalDateTime wfsj;
    /**
     * 违法行为
     */
    private String wfxw;
    /**
     * 错误原因
     */
    private String cwyy;
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
    /**
     * 复审结果
     */
    private String reviewResult;

    /**
     * 复审描述
     */
    private String reviewDesc;

    /**
     * 复审时间
     */
    private LocalDateTime reviewDate;

    /**
     * 复审人
     */
    private String reviewer;

}