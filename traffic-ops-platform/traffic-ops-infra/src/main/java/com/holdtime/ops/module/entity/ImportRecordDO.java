package com.holdtime.ops.module.entity;

import cn.iocoder.yudao.framework.mybatis.core.dataobject.PdBaseDO;
import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

/**
 * Excle导入记录 DO
 *
 * @author 超级管理员
 */
@TableName("excel_import_record")
@KeySequence("excel_import_record_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ImportRecordDO extends PdBaseDO {

    /**
     * 主键
     */
    @TableId(type = IdType.ASSIGN_ID)
    private String id;
    /**
     * 导入类型
     */
    private String importType;
    /**
     * 导入文件名
     */
    private String fileName;
    /**
     * 导入文件
     */
    private String importFile;
    /**
     * 导入记录数
     */
    private Integer importNum;
    /**
     * 成功记录数
     */
    private Integer sucNum;
    /**
     * 失败记录数
     */
    private Integer failNum;
    /**
     * 同步成功数量
     */
    private Integer syncSucNum;
    /**
     * 同步失败数量
     */
    private Integer syncFailNum;
    /**
     * 失败文件
     */
    private String failFile;

    /**
     * 操作用户部门
     */
    private String deptId;

}