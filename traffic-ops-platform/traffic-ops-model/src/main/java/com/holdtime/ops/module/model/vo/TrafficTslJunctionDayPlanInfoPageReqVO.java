package com.holdtime.ops.module.model.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 信控交叉口日方案信息分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class TrafficTslJunctionDayPlanInfoPageReqVO extends PageParam {

    @Schema(description = "日方案ID", example = "24482")
    private String dayPlansId;

    @Schema(description = "交叉口ID", example = "29441")
    private String junctionId;

    @Schema(description = "交叉口控制id", example = "27881")
    private String controllerId;

    @Schema(description = "标识")
    private String flag;

    @Schema(description = "假期方案是否使用")
    private String holidayPlanUse;

    @Schema(description = "方案数量", example = "28258")
    private Integer segmentCount;

    @Schema(description = "是否使用")
    private String isUse;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

    @Schema(description = "删除时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] deletedTime;

}