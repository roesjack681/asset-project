package com.holdtime.ops.module.model.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import java.math.BigDecimal;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 设备监测结果每日分析分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class TrafficMonitorResultsDailyAnalysisPageReqVO extends PageParam {

    @Schema(description = "分析时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] analysisDate;

    @Schema(description = "设备ID", example = "23882")
    private String equId;

    @Schema(description = "杆件编号")
    private String memberBh;

    @Schema(description = "道路名称", example = "赵六")
    private String roadName;

    @Schema(description = "交叉口名称", example = "芋艿")
    private String intersectionName;

    @Schema(description = "方向")
    private String direction;

    @Schema(description = "IP地址")
    private String ipAddr;

    @Schema(description = "电警类型", example = "1")
    private String djType;

    @Schema(description = "设备标签")
    private String equLable;

    @Schema(description = "检测事件", example = "2")
    private String eventType;

    @Schema(description = "正常数")
    private Integer normalCnt;

    @Schema(description = "异常数")
    private Integer abnormalCnt;

    @Schema(description = "检测天数")
    private Integer monitorSumDays;

    @Schema(description = "检测总次数")
    private Integer monitorSumCnt;

    @Schema(description = "健康率")
    private BigDecimal healthRate;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

    @Schema(description = "删除时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] deletedTime;

}