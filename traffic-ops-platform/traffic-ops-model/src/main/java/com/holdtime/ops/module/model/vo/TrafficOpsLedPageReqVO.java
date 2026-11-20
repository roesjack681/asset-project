package com.holdtime.ops.module.model.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - Led屏分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class TrafficOpsLedPageReqVO extends PageParam {

    @Schema(description = "设备编号")
    private String equNumber;

    @Schema(description = "设备名称", example = "王五")
    private String equName;

    @Schema(description = "设备类型", example = "2")
    private String equType;

    @Schema(description = "所在道路名称", example = "王五")
    private String roadName;

    @Schema(description = "灯控路口名称", example = "王五")
    private String intersectionName;

    @Schema(description = "GPS定位-经度")
    private String lng;

    @Schema(description = "GPS定位-纬度")
    private String lat;

    @Schema(description = "安装位置")
    private String adress;

    @Schema(description = "安装时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] installDate;

    @Schema(description = "厂家")
    private String manufacturer;

    @Schema(description = "型号")
    private String equModel;

    @Schema(description = "归属单位")
    private String unit;

    @Schema(description = "尺寸")
    private String size;

    @Schema(description = "显示用途")
    private String displayPurpose;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

    @Schema(description = "删除时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] deletedTime;

}