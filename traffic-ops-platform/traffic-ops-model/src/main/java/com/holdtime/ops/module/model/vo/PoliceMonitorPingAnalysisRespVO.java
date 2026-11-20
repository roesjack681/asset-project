package com.holdtime.ops.module.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.util.*;
import java.math.BigDecimal;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 电警通断检测管理 Response VO")
@Data
@ExcelIgnoreUnannotated
public class PoliceMonitorPingAnalysisRespVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "12274")
    @ExcelProperty("主键")
    private String id;

    @Schema(description = "设备id", example = "4715")
    @ExcelProperty("设备id")
    private String equId;

    @Schema(description = "设备标签")
    @ExcelProperty("设备标签")
    private String equLable;

    @Schema(description = "自动ping正常数")
    @ExcelProperty("自动ping正常数")
    private Integer autoPingNormalCnt;

    @Schema(description = "自动ping异常数")
    @ExcelProperty("自动ping异常数")
    private Integer autoPingAbnormalCnt;

    @Schema(description = "自动ping健康率")
    @ExcelProperty("自动ping健康率")
    private BigDecimal autoPingHealthRate;

    @Schema(description = "自动检测次数")
    @ExcelProperty("自动检测次数")
    private Integer autoMonitorCnt;

    @Schema(description = "ping正常数")
    @ExcelProperty("ping正常数")
    private Integer pingNormalCnt;

    @Schema(description = "ping异常数")
    @ExcelProperty("ping异常数")
    private Integer pingAbnormalCnt;

    @Schema(description = "ping健康率")
    @ExcelProperty("ping健康率")
    private BigDecimal pingHealthRate;

    @Schema(description = "检测次数")
    @ExcelProperty("检测次数")
    private Integer monitorCnt;

    @Schema(description = "创建时间")
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @Schema(description = "删除时间")
    @ExcelProperty("删除时间")
    private LocalDateTime deletedTime;

}