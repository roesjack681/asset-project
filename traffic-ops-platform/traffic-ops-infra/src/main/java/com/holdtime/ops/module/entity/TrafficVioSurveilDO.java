package com.holdtime.ops.module.entity;

import cn.iocoder.yudao.framework.mybatis.core.dataobject.PdBaseDO;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;

/**
 * 违法抓拍待审记录 DO
 *
 * @author 管理员
 */
@TableName("btjczh_vio_surveil")
@KeySequence("btjczh_vio_surveil_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TrafficVioSurveilDO extends PdBaseDO {

    /**
     * 主键
     */
    @TableId(type = IdType.ASSIGN_ID)
    private String id;
    /**
     * 设备id
     */
    private String equId;
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

}