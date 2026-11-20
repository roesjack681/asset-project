package com.holdtime.ops.module.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.*;
import java.time.LocalDateTime;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 设备监测结果新增/修改 Request VO")
@Data
public class TrafficMonitorResultsSaveReqVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "4491")
    private String id;

    @NotBlank
    @Schema(description = "设备类型：1-红绿灯;2-电警", example = "1")
    private String equType;

    @NotBlank
    @Schema(description = "IP地址")
    private String ipAddr;

    @NotBlank
    @Schema(description = "方向")
    private String direction;

    @NotBlank
    @Schema(description = "检测事件", example = "1")
    private String eventType;

    @NotNull
    @Schema(description = "检测时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime occurrenceTime;

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

}