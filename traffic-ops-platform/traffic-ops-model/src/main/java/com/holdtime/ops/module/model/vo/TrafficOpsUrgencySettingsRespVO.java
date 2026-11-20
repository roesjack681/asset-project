package com.holdtime.ops.module.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 维保紧急程度配置 Response VO")
@Data
@ExcelIgnoreUnannotated
public class TrafficOpsUrgencySettingsRespVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "9267")
    @ExcelProperty("主键")
    private String id;

    @Schema(description = "所属单位：1-交警；2-公安", example = "11295")
    private String affiliatedUnit;

    @Schema(description = "紧急程度")
    @ExcelProperty("紧急程度")
    private String urgencyLevel;

    @Schema(description = "处理时限小时")
    @ExcelProperty("处理时限小时")
    private String deadline;

    @Schema(description = "通知人员")
    @ExcelProperty("通知人员")
    private String notifyPersonnel;

    @Schema(description = "通知人员openid")
    @ExcelProperty("通知人员openid")
    private String notifyOpenid;


    @Schema(description = "创建时间")
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @Schema(description = "删除时间")
    @ExcelProperty("删除时间")
    private LocalDateTime deletedTime;

}