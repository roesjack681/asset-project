package com.holdtime.ops.module.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Schema(description = "管理后台 - 护栏维护详细新增/修改 Request VO")
@Data
public class TrafficOpsMaintenanceGuardrailExtSaveReqVO extends TrafficOpsMaintenanceGuardrailSaveReqVO {

    @Schema(description = "设备图片")
    private List<PublicOpsAttachInfoExtSaveReqVO> attachInfoList;

    @Schema(description = "保险信息")
    private TrafficOpsGuardrailInsuranceSaveReqVO insuranceInfoVO;

    @Schema(description = "护栏开口信息")
    private List<TrafficOpsGuardrailGapSaveReqVO> guardrailGapVOList;

    @Schema(description = "护栏产品信息")
    private List<TrafficOpsGuardrailRProductSaveReqVO> rproductList;

    @Schema(description = "辅助设备信息")
    private List<TrafficOpsGuardrailAuxiliarySaveReqVO> auxiliaryList;

}