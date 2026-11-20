package com.holdtime.ops.module.entity;

import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;

/**
 * 设备出入库存明细 DO
 *
 * @author 管理员
 */
@TableName("public_ops_inout_detail")
@KeySequence("public_ops_inout_detail_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PublicOpsInoutDetailDO extends BaseDO {

    /**
     * 主键
     */
    @TableId(type = IdType.INPUT)
    private String id;
    /**
     * 设备出入库id
     */
    private String inoutId;
    /**
     * 耗材种类
     */
    private String cmSort;
    /**
     * 耗材数量
     */
    private Integer cmNum;
    /**
     * 新旧标识
     */
    private String flag;
    /**
     * 删除时间
     */
    private LocalDateTime deletedTime;

}