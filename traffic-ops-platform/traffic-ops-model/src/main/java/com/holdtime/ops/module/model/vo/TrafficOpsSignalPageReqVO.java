package com.holdtime.ops.module.model.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 信号灯分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class TrafficOpsSignalPageReqVO extends PageParam {

    @Schema(description = "设备名称", example = "芋艿")
    private String equName;

    @Schema(description = "设备类型", example = "1")
    private String equType;

    @Schema(description = "所在道路名称", example = "赵六")
    private String roadName;

    @Schema(description = "灯控路口名称", example = "李四")
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

    @Schema(description = "联网方式", example = "2")
    private String networkType;

    @Schema(description = "联网位置")
    private String networkAddr;

    @Schema(description = "信号控制相位")
    private String phase;

    @Schema(description = "联网状态", example = "1")
    private String networkStatus;

    @Schema(description = "信号机厂家")
    private String manufacturer;

    @Schema(description = "信号机型号")
    private String equModel;

    @Schema(description = "信号机编号")
    private String equNumber;

    @Schema(description = "IP地址")
    private String ipAddr;

    @Schema(description = "子网掩码")
    private String mask;

    @Schema(description = "网关")
    private String gateway;

    @Schema(description = "所属环路")
    private String belongLoop;

    @Schema(description = "联网协议")
    private String networkProtocol;

    @Schema(description = "服务器IP地址")
    private String serverIp;

    @Schema(description = "路口全景图")
    private String panorama;

    @Schema(description = "设备箱门禁")
    private String accessControl;

    @Schema(description = "归属单位")
    private String unit;

    @Schema(description = "所属管辖单位")
    private String dominion;

    @Schema(description = "状态")
    private String status;

    @Schema(description = "设备图片")
    private String equImg;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

    @Schema(description = "删除时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] deletedTime;

}