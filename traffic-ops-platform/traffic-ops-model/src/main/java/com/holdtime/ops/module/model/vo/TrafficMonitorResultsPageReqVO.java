package com.holdtime.ops.module.model.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 设备监测结果分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class TrafficMonitorResultsPageReqVO extends PageParam {

    @Schema(description = "设备类型", example = "1")
    private String equType;

    @Schema(description = "IP地址")
    private String ipAddr;

    @Schema(description = "方向")
    private String direction;

    @Schema(description = "检测事件", example = "1")
    private String eventType;

    @Schema(description = "检测时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] occurrenceTime;

    @Schema(description = "状态")
    private String status;

    @Schema(description = "来源：1-接口；2-导入")
    private String source;

    @Schema(description = "设备ID")
    private String equId;

    @Schema(description = "所在道路名称")
    private String roadName;

    @Schema(description = "灯控路口名称")
    private String intersectionName;

    @Schema(description = "杆件编号")
    private String memberBh;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

    @Schema(description = "删除时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] deletedTime;

}