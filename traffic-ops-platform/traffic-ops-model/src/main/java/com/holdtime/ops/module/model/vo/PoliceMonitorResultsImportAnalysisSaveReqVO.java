package com.holdtime.ops.module.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;
import java.util.*;
import java.math.BigDecimal;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 公安监测结果导入分析新增/修改 Request VO")
@Data
public class PoliceMonitorResultsImportAnalysisSaveReqVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "26747")
    private String id;

    @Schema(description = "设备id", example = "8624")
    private String equId;

    @Schema(description = "设备标签")
    private String equLable;

    @Schema(description = "检测事件", example = "1")
    private String eventType;

    @Schema(description = "视频正常数")
    private Integer videoNormalCnt;

    @Schema(description = "视频异常数")
    private Integer videoAbnormalCnt;

    @Schema(description = "视频健康率")
    private BigDecimal videoHealthRate;

    @Schema(description = "ping正常数")
    private Integer pingNormalCnt;

    @Schema(description = "ping异常数")
    private Integer pingAbnormalCnt;

    @Schema(description = "ping健康率")
    private BigDecimal pingHealthRate;

    @Schema(description = "检测次数")
    private Integer monitorCnt;

    @Schema(description = "上次修复时间")
    private LocalDateTime lastRepairTime;

    @Schema(description = "删除时间")
    private LocalDateTime deletedTime;

}