package com.holdtime.ops.module.model.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;


@Schema(description = "管理后台 - 电警 Response VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ExcelIgnoreUnannotated
public class PoliceOpsElectricPoliceExtRespVO extends PoliceOpsElectricPoliceRespVO {
    @Schema(description = "二维码信息", example = "1")
    private TrafficOpsQrcodeRespVO qrcodeRespVO;

    @Schema(description = "维修次数")
    @ExcelProperty("维修次数")
    private Long maintenanceCnt;


}