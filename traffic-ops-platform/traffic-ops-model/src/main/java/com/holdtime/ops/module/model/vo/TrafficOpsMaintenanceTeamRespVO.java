package com.holdtime.ops.module.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 设备维保组员信息 Response VO")
@Data
@ExcelIgnoreUnannotated
public class TrafficOpsMaintenanceTeamRespVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "14850")
    @ExcelProperty("主键")
    private String id;

    @Schema(description = "维保主键", example = "2423")
    @ExcelProperty("维保主键")
    private String maintenanceId;

    @Schema(description = "维保进度id", example = "10134")
    @ExcelProperty("维保进度id")
    private String progressId;

    @Schema(description = "组员人员编号", example = "13546")
    @ExcelProperty("组员人员编号")
    private String maintenanceUserId;

    @Schema(description = "组员姓名")
    @ExcelProperty("组员姓名")
    private String maintenancePersonnel;

    @Schema(description = "组员手机号")
    @ExcelProperty("组员手机号")
    private String maintenancePhone;

    @Schema(description = "创建时间")
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @Schema(description = "删除时间")
    @ExcelProperty("删除时间")
    private LocalDateTime deletedTime;

}