package com.holdtime.ops.module.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;
import java.util.*;
import java.math.BigDecimal;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 电警通断检测管理新增/修改 Request VO")
@Data
public class PoliceMonitorPingAnalysisSaveReqVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "12274")
    private String id;

    @Schema(description = "设备id", example = "4715")
    private String equId;

    @Schema(description = "设备标签")
    private String equLable;

    @Schema(description = "自动ping正常数")
    private Integer autoPingNormalCnt;

    @Schema(description = "自动ping异常数")
    private Integer autoPingAbnormalCnt;

    @Schema(description = "自动ping健康率")
    private BigDecimal autoPingHealthRate;

    @Schema(description = "自动检测次数")
    private Integer autoMonitorCnt;

    @Schema(description = "ping正常数")
    private Integer pingNormalCnt;

    @Schema(description = "ping异常数")
    private Integer pingAbnormalCnt;

    @Schema(description = "ping健康率")
    private BigDecimal pingHealthRate;

    @Schema(description = "检测次数")
    private Integer monitorCnt;

    @Schema(description = "删除时间")
    private LocalDateTime deletedTime;

}