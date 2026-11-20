package com.holdtime.ops.module.model.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 信控交叉口日方案详细信息分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class TrafficTslJunctionDayPlanDetailPageReqVO extends PageParam {

    @Schema(description = "详细ID", example = "28146")
    private String segmentId;

    @Schema(description = "日方案ID", example = "4771")
    private String dayPlansId;

    @Schema(description = "交叉口ID", example = "30129")
    private String junctionId;

    @Schema(description = "小时")
    private Integer planHour;

    @Schema(description = "分钟")
    private Integer planMinute;

    @Schema(description = "配时方案号", example = "19694")
    private String planId;

    @Schema(description = "感应方案号", example = "17914")
    private String actuId;

    @Schema(description = "相序方案号", example = "29009")
    private String stageListId;

    @Schema(description = "相位差")
    private Integer phaseDifference;

    @Schema(description = "周期")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private Integer[] cycleTime;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

    @Schema(description = "删除时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] deletedTime;

}