package com.holdtime.ops.module.model.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Schema(description = "管理后台 - 公安监测通达详情 Response VO")
@Data
@ExcelIgnoreUnannotated
public class PoliceMonitorPingDetailExtRespVO extends PoliceMonitorPingDetailRespVO {

    @Schema(description = "设备名称", example = "6091")
    @ExcelProperty("设备名称")
    private String equName;
}