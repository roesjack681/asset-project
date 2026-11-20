package com.holdtime.ops.module.model.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import java.math.BigDecimal;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 终端最新位置分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class PublicOpsDeviceLocationPageReqVO extends PageParam {

    @Schema(description = "终端编号", example = "2921")
    private String deviceId;

    @Schema(description = "GPS纬度")
    private BigDecimal latitude;

    @Schema(description = "GPS经度")
    private BigDecimal longitude;

    @Schema(description = "GPS速度(km/h)")
    private BigDecimal speed;

    @Schema(description = "方向(0-359度)")
    private Integer direction;

    @Schema(description = "位置上报时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] reportTime;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}