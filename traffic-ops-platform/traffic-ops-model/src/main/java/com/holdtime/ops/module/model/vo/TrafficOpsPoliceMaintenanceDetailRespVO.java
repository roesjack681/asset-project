package com.holdtime.ops.module.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 警用装备报修详情信息 Response VO")
@Data
@ExcelIgnoreUnannotated
public class TrafficOpsPoliceMaintenanceDetailRespVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "32277")
    @ExcelProperty("主键")
    private String id;

    @Schema(description = "报修编号", example = "10158")
    @ExcelProperty("报修编号")
    private String maintenanceId;

    @Schema(description = "装备编号", example = "29215")
    @ExcelProperty("装备编号")
    private String equId;

    @Schema(description = "问题描述")
    @ExcelProperty("问题描述")
    private String problem;

    @Schema(description = "创建时间")
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @Schema(description = "删除时间")
    @ExcelProperty("删除时间")
    private LocalDateTime deletedTime;

}