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
public class ViewPoliceMonitorResultsImportSituationPageReqVO extends PageParam {

    @Schema(description = "设备id", example = "10752")
    private String equId;

    @Schema(description = "检测时间")
    private LocalDateTime latestOccurrenceDay;

    @Schema(description = "检测时间")
    private LocalDateTime previousOccurrenceDay;

    @Schema(description = "ping状态", example = "1")
    private String latestPingStatus;

    @Schema(description = "ping状态", example = "2")
    private String previousPingStatus;

    @Schema(description = "", example = "2")
    private String finalPingStatus;

    @Schema(description = "实时视频状态", example = "2")
    private String latestVideoStatus;

    @Schema(description = "实时视频状态", example = "1")
    private String previousVideoStatus;

    @Schema(description = "", example = "1")
    private String finalVideoStatus;

}