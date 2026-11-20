package com.holdtime.ops.module.model.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Schema(description = "管理后台 - 设备维保信息 Response VO")
@Data
@ExcelIgnoreUnannotated
public class TrafficOpsMaintenanceConfirmSaveReqVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED)
    private String id;

    @Schema(description = "状态", example = "2")
    private String status;

    @Schema(description = "备注", example = "你猜")
    private String remark;

}