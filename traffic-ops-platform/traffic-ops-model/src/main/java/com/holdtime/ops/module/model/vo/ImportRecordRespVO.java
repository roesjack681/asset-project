package com.holdtime.ops.module.model.vo;

import cn.hutool.core.date.DateTime;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 电动自行车导入 Response VO")
@Data
@ExcelIgnoreUnannotated
public class ImportRecordRespVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "1961")
    @ExcelProperty("主键")
    private String id;

    @Schema(description = "导入类型")
    @ExcelProperty("导入类型")
    private String importType;

    @Schema(description = "导入文件名")
    @ExcelProperty("导入文件名")
    private String fileName;

    @Schema(description = "导入文件")
    @ExcelProperty("导入文件")
    private String importFile;

    @Schema(description = "导入记录数")
    @ExcelProperty("导入记录数")
    private Integer importNum;

    @Schema(description = "成功记录数")
    @ExcelProperty("成功记录数")
    private Integer sucNum;

    @Schema(description = "失败记录数")
    @ExcelProperty("失败记录数")
    private Integer failNum;

    @Schema(description = "导入结果文件")
    @ExcelProperty("导入结果文件")
    private String failFile;

    @Schema(description = "同步成功数量")
    @ExcelProperty("同步成功数量")
    private Integer syncSucNum;

    @Schema(description = "同步失败数量")
    @ExcelProperty("同步失败数量")
    private Integer syncFailNum;

    @Schema(description = "创建时间")
    @ExcelProperty("创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private DateTime createTime;

}