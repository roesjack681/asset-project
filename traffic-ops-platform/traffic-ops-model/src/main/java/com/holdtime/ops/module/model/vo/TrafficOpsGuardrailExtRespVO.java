package com.holdtime.ops.module.model.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Schema(description = "管理后台 - 护栏 Response VO")
@Data
@ExcelIgnoreUnannotated
public class TrafficOpsGuardrailExtRespVO extends TrafficOpsGuardrailRespVO {

    @Schema(description = "设备图片")
    private List<PublicOpsAttachInfoRespVO> attachInfoList;

    @Schema(description = "护栏产品信息")
    private List<TrafficOpsGuardrailRProductRespVO> rproductList;

    @Schema(description = "辅助设备信息")
    private List<TrafficOpsGuardrailAuxiliaryRespVO> auxiliaryList;

    @Schema(description = "开口信息")
    private List<TrafficOpsGuardrailGapRespVO> gapList;

}