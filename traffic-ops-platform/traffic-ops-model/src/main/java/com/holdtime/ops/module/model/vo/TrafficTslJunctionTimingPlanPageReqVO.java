package com.holdtime.ops.module.model.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 信控交叉口配时方案信息分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class TrafficTslJunctionTimingPlanPageReqVO extends PageParam {

    @Schema(description = "配时方案ID", example = "8326")
    private String planId;

    @Schema(description = "交叉口ID", example = "28901")
    private String junctionId;

    @Schema(description = "交叉口控制id", example = "16731")
    private String controllerId;

    @Schema(description = "标识")
    private String flag;

    @Schema(description = "相序方案号", example = "7479")
    private String stageListId;

    @Schema(description = "方案数量", example = "21723")
    private Integer stageCount;

    @Schema(description = "方向")
    private String direct;

    @Schema(description = "周期")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private Integer[] cycleTime;

    @Schema(description = "相位差")
    private Integer phaseDifference;

    @Schema(description = "是否使用")
    private String isUse;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

    @Schema(description = "删除时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] deletedTime;

}