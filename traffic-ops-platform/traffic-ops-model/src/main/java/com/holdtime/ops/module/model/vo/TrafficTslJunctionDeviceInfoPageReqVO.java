package com.holdtime.ops.module.model.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 信控交叉口设备信息分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class TrafficTslJunctionDeviceInfoPageReqVO extends PageParam {

    @Schema(description = "信号机ID")
    private String signalId;

    @Schema(description = "信号机名称", example = "王五")
    private String controllerName;

    @Schema(description = "交叉口ID", example = "2759")
    private String junctionId;

    @Schema(description = "交叉口名称", example = "王五")
    private String junctionName;

    @Schema(description = "设备IP")
    private String deviceIp;

    @Schema(description = "在线状态")
    private String online;

    @Schema(description = "状态")
    private String state;

    @Schema(description = "模式")
    private String mode;

    @Schema(description = "协议")
    private String protocol;

    @Schema(description = "能否ping")
    private String canPing;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

    @Schema(description = "删除时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] deletedTime;

}