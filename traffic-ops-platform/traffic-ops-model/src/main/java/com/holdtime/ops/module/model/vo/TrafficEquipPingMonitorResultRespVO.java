package com.holdtime.ops.module.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.util.*;
import java.math.BigDecimal;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 重点考核设备通断检测 Response VO")
@Data
@ExcelIgnoreUnannotated
public class TrafficEquipPingMonitorResultRespVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "21346")
    @ExcelProperty("主键")
    private String id;

    @Schema(description = "设备id", example = "22487")
    @ExcelProperty("设备id")
    private String equId;

    @Schema(description = "设备IP")
    @ExcelProperty("设备IP")
    private String ipAddr;

    @Schema(description = "检测时间")
    @ExcelProperty("检测时间")
    private LocalDateTime occurrenceTime;

    @Schema(description = "ping状态", example = "2")
    @ExcelProperty("ping状态")
    private String pingStatus;

    @Schema(description = "ping次数")
    @ExcelProperty("ping次数")
    private Integer pingCnt;

    @Schema(description = "丢包率")
    @ExcelProperty("丢包率")
    private BigDecimal lossRate;

    @Schema(description = "来源：1-自动；2-手动")
    @ExcelProperty("来源：1-自动；2-手动")
    private String source;

    @Schema(description = "执行批次号")
    @ExcelProperty("执行批次号")
    private String execBatchNum;

    @Schema(description = "创建时间")
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @Schema(description = "删除时间")
    @ExcelProperty("删除时间")
    private LocalDateTime deletedTime;

}