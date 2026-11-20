package com.holdtime.ops.module.model.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - VIEW分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ViewPoliceMonitorPingDetailSituationPageReqVO extends PageParam {

    @Schema(description = "设备id", example = "9630")
    private String equId;

    @Schema(description = "检测时间")
    private LocalDateTime latestOccurrenceDay;

    @Schema(description = "检测时间")
    private LocalDateTime previousOccurrenceDay;

    @Schema(description = "ping状态", example = "2")
    private String latestPingStatus;

    @Schema(description = "ping状态", example = "2")
    private String previousPingStatus;

    @Schema(description = "", example = "2")
    private String finalPingStatus;

}