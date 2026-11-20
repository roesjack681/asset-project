package com.holdtime.ops.module.model.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Schema(description = "管理后台 - 杆件 Response VO")
@Data
@ExcelIgnoreUnannotated
public class PoliceOpsMemberExtRespVO extends PoliceOpsMemberRespVO {
    @Schema(description = "二维码路径", example = "1")
    private String qrCodeUrl;

    @Schema(description = "电警信息", example = "1")
    private List<PoliceOpsElectricPoliceRespVO> electricPoliceVOList;

    @Schema(description = "创建人", example = "1")
    private String creator;

    @Schema(description = "电警数量")
    private Long ectricPoliceCnt;

    @Schema(description = "维修次数")
    private Long maintenanceCnt;
}