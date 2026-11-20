package com.holdtime.ops.module.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.util.*;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - VIEW Response VO")
@Data
@ExcelIgnoreUnannotated
public class ViewOpsMonitorSituationRespVO {

    @Schema(description = "设备ID", example = "13324")
    @ExcelProperty("设备ID")
    private String equId;

    @Schema(description = "状态", example = "1")
    @ExcelProperty("状态")
    private String latestStatus;

    @Schema(description = "状态", example = "2")
    @ExcelProperty("状态")
    private String prevStatus;

    @Schema(description = "状态变化描述")
    @ExcelProperty("状态变化描述")
    private String statusChangeDesc;

}