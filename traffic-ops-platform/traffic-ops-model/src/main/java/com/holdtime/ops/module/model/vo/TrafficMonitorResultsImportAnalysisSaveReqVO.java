package com.holdtime.ops.module.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;
import java.util.*;
import java.math.BigDecimal;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 设备监测结果导入分析新增/修改 Request VO")
@Data
public class TrafficMonitorResultsImportAnalysisSaveReqVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "21332")
    private String id;

    @Schema(description = "分析时间")
    private LocalDateTime analysisDate;

    @Schema(description = "正常数")
    private Integer normalCnt;

    @Schema(description = "异常数")
    private Integer abnormalCnt;

    @Schema(description = "健康率")
    private BigDecimal healthRate;

    @Schema(description = "删除时间")
    private LocalDateTime deletedTime;

}