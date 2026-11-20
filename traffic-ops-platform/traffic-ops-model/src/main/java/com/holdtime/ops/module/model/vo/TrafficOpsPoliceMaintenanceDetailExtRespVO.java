package com.holdtime.ops.module.model.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Schema(description = "管理后台 - 警用装备报修详情信息 Response VO")
@Data
@ExcelIgnoreUnannotated
public class TrafficOpsPoliceMaintenanceDetailExtRespVO extends TrafficOpsPoliceMaintenanceDetailRespVO {
    @Schema(description = "PSN")
    @ExcelProperty("PSN")
    private String psn;

    @Schema(description = "类型", example = "2")
    @ExcelProperty("类型")
    private String equType;

    @Schema(description = "加密卡")
    @ExcelProperty("加密卡")
    private String encryptionCard;
}