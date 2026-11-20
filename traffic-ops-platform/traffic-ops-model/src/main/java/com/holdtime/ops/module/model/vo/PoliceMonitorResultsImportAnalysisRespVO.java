package com.holdtime.ops.module.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.util.*;
import java.math.BigDecimal;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 公安监测结果导入分析 Response VO")
@Data
@ExcelIgnoreUnannotated
public class PoliceMonitorResultsImportAnalysisRespVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "26747")
    private String id;

    @Schema(description = "设备id", example = "8624")
    private String equId;

    @Schema(description = "设备标签")
    @ExcelProperty(value = "设备标签", index = 2)
    private String equLable;

    @Schema(description = "检测事件", example = "1")
    private String eventType;

    @Schema(description = "视频正常数")
    @ExcelProperty(value = "视频正常数", index = 7)
    private Integer videoNormalCnt;

    @Schema(description = "视频异常数")
    @ExcelProperty(value = "视频异常数", index = 8)
    private Integer videoAbnormalCnt;

    @Schema(description = "视频健康率")
    @ExcelProperty(value = "视频健康率", index = 9)
    private BigDecimal videoHealthRate;

    @Schema(description = "ping正常数")
    @ExcelProperty(value = "ping正常数", index = 3)
    private Integer pingNormalCnt;

    @Schema(description = "ping异常数")
    @ExcelProperty(value = "ping异常数", index = 4)
    private Integer pingAbnormalCnt;

    @Schema(description = "ping健康率")
    @ExcelProperty(value = "ping健康率", index = 5)
    private BigDecimal pingHealthRate;

    @Schema(description = "检测次数")
    @ExcelProperty(value = "检测次数", index = 11)
    private Integer monitorCnt;

    @Schema(description = "上次修复时间")
    private LocalDateTime lastRepairTime;

    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    @Schema(description = "删除时间")
    private LocalDateTime deletedTime;

}