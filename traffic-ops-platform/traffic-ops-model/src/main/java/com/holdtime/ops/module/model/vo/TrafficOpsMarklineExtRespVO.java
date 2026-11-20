package com.holdtime.ops.module.model.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Schema(description = "管理后台 - 标线 Response VO")
@Data
@ExcelIgnoreUnannotated
public class TrafficOpsMarklineExtRespVO extends TrafficOpsMarklineRespVO {
    @Schema(description = "设备图片")
    private List<PublicOpsAttachInfoRespVO> attachInfoList;

}