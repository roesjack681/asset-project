package com.holdtime.ops.module.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 护栏辅助设备维护详细 Response VO")
@Data
@ExcelIgnoreUnannotated
public class TrafficOpsMaintenanceAuxiliaryRespVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "4138")
    @ExcelProperty("主键")
    private String id;

    @Schema(description = "维保主键", example = "1463")
    @ExcelProperty("维保主键")
    private String maintenanceId;

    @Schema(description = "护栏主键", example = "21345")
    @ExcelProperty("护栏主键")
    private String guardrailId;

    @Schema(description = "辅助设备ID", example = "23263")
    @ExcelProperty("辅助设备ID")
    private String auxiliaryId;

    @Schema(description = "辅助设备名称", example = "赵六")
    @ExcelProperty("辅助设备名称")
    private String auxiliaryName;

    @Schema(description = "数量")
    @ExcelProperty("数量")
    private Integer auxiliaryNum;

    @Schema(description = "创建时间")
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @Schema(description = "删除时间")
    @ExcelProperty("删除时间")
    private LocalDateTime deletedTime;

}