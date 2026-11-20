package com.holdtime.ops.module.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;
import java.util.*;
import java.math.BigDecimal;

@Schema(description = "管理后台 - 检测结果每日分析视图新增/修改 Request VO")
@Data
public class ViewOpsMonitorResultsDailyAnalysisSaveReqVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "23518")
    private String equId;

    @Schema(description = "杆件编号")
    private String memberBh;

    @Schema(description = "所在道路名称", example = "赵六")
    private String roadName;

    @Schema(description = "灯控路口名称", example = "李四")
    private String intersectionName;

    @Schema(description = "方向")
    private String direction;

    @Schema(description = "IP地址")
    private String ipAddr;

    @Schema(description = "电警类型", example = "2")
    private String djType;

    @Schema(description = "设备标签")
    private String equLable;

    @Schema(description = "检测事件", example = "1")
    private String eventType;

    @Schema(description = "")
    private String occurrenceDay;

    @Schema(description = "")
    private BigDecimal normalCnt;

    @Schema(description = "")
    private BigDecimal abnormalCnt;

    @Schema(description = "", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "不能为空")
    private Long monitorSumDays;

    @Schema(description = "", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "不能为空")
    private Long monitorSumCnt;

    @Schema(description = "")
    private BigDecimal healthRate;

}