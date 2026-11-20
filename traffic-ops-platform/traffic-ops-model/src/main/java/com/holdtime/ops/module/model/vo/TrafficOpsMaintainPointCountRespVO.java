package com.holdtime.ops.module.model.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "管理后台 - 维修点位 Response VO")
@Data
@ExcelIgnoreUnannotated
public class TrafficOpsMaintainPointCountRespVO {


    @Schema(description = "类型", example = "2")
    private String type;

    @Schema(description = "数量", example = "2")
    private String count;


}