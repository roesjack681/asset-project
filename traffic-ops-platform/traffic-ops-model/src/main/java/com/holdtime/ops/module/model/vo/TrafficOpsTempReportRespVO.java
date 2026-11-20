package com.holdtime.ops.module.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 交警运维临时报 Response VO")
@Data
@ExcelIgnoreUnannotated
public class TrafficOpsTempReportRespVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "30198")
    @ExcelProperty("主键")
    private String id;

    @Schema(description = "设备id", example = "12481")
    @ExcelProperty("设备id")
    private String equId;

    @Schema(description = "IP地址")
    @ExcelProperty("IP地址")
    private String ipAddr;

    @Schema(description = "来源：1-接口；2-导入")
    @ExcelProperty("来源：1-接口；2-导入")
    private String source;

    @Schema(description = "创建时间")
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @Schema(description = "删除时间")
    @ExcelProperty("删除时间")
    private LocalDateTime deletedTime;

}