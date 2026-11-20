package com.holdtime.ops.module.model.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Schema(description = "管理后台 - 设备监测结果每日分析 Response VO")
@Data
@ExcelIgnoreUnannotated
public class TrafficMonitorResultsDailyAnalysisExtRespVO extends TrafficMonitorResultsDailyAnalysisRespVO {
    @Schema(description = "杆件编号")
    @ExcelProperty("杆件编号")
    private String memberName;

}