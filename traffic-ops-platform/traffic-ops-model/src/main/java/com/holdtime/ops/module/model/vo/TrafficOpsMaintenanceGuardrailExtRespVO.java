package com.holdtime.ops.module.model.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Schema(description = "管理后台 - 护栏维护详细 Response VO")
@Data
@ExcelIgnoreUnannotated
public class TrafficOpsMaintenanceGuardrailExtRespVO extends TrafficOpsMaintenanceGuardrailRespVO {

    @Schema(description = "设备图片")
    private List<PublicOpsAttachInfoRespVO> attachInfoList;

    @Schema(description = "保险信息")
    private TrafficOpsGuardrailInsuranceRespVO insuranceInfoVO;

    @Schema(description = "护栏开口信息")
    private List<TrafficOpsGuardrailGapRespVO> guardrailGapVOList;

}