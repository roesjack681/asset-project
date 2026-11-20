package com.holdtime.ops.module.model.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Schema(description = "管理后台 - 日常巡检信息 Response VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ExcelIgnoreUnannotated
public class PoliceDailyRoundCheckExtRespVO extends PoliceDailyRoundCheckRespVO {

    @Schema(description = "巡检设备编号", example = "3125")
    @ExcelProperty("巡检设备编号")
    private OpsElectricPoliceRespVO electricPoliceRespVO;
}