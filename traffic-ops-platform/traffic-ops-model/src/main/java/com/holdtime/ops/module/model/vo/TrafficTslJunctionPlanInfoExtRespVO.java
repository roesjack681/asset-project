package com.holdtime.ops.module.model.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@Schema(description = "管理后台 - 信控交叉口方案信息 Response VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ExcelIgnoreUnannotated
public class TrafficTslJunctionPlanInfoExtRespVO extends TrafficTslJunctionPlanInfoRespVO {

    @Schema(description = "星期")
    @ExcelProperty("星期")
    private Integer week;
}