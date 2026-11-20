package com.holdtime.ops.module.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 信号灯 Response VO")
@Data
@ExcelIgnoreUnannotated
public class TrafficOpsSignalRespVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "29831")
    @ExcelProperty("主键")
    private String id;

    @Schema(description = "设备名称", example = "芋艿")
    @ExcelProperty("设备名称")
    private String equName;

    @Schema(description = "设备类型", example = "1")
    @ExcelProperty("设备类型")
    private String equType;

    @Schema(description = "所在道路名称", example = "赵六")
    @ExcelProperty("所在道路名称")
    private String roadName;

    @Schema(description = "灯控路口名称", example = "李四")
    @ExcelProperty("灯控路口名称")
    private String intersectionName;

    @Schema(description = "GPS定位-经度")
    @ExcelProperty("GPS定位-经度")
    private String lng;

    @Schema(description = "GPS定位-纬度")
    @ExcelProperty("GPS定位-纬度")
    private String lat;

    @Schema(description = "安装位置")
    @ExcelProperty("安装位置")
    private String adress;

    @Schema(description = "安装时间")
    @ExcelProperty("安装时间")
    private LocalDateTime installDate;

    @Schema(description = "联网方式", example = "2")
    @ExcelProperty("联网方式")
    private String networkType;

    @Schema(description = "联网位置")
    @ExcelProperty("联网位置")
    private String networkAddr;

    @Schema(description = "信号控制相位")
    @ExcelProperty("信号控制相位")
    private String phase;

    @Schema(description = "联网状态", example = "1")
    @ExcelProperty("联网状态")
    private String networkStatus;

    @Schema(description = "信号机厂家")
    @ExcelProperty("信号机厂家")
    private String manufacturer;

    @Schema(description = "信号机型号")
    @ExcelProperty("信号机型号")
    private String equModel;

    @Schema(description = "信号机编号")
    @ExcelProperty("信号机编号")
    private String equNumber;

    @Schema(description = "IP地址")
    @ExcelProperty("IP地址")
    private String ipAddr;

    @Schema(description = "子网掩码")
    @ExcelProperty("子网掩码")
    private String mask;

    @Schema(description = "网关")
    @ExcelProperty("网关")
    private String gateway;

    @Schema(description = "所属环路")
    @ExcelProperty("所属环路")
    private String belongLoop;

    @Schema(description = "联网协议")
    @ExcelProperty("联网协议")
    private String networkProtocol;

    @Schema(description = "服务器IP地址")
    @ExcelProperty("服务器IP地址")
    private String serverIp;

    @Schema(description = "路口全景图")
    @ExcelProperty("路口全景图")
    private String panorama;

    @Schema(description = "设备箱门禁")
    @ExcelProperty("设备箱门禁")
    private String accessControl;

    @Schema(description = "归属单位")
    @ExcelProperty("归属单位")
    private String unit;

    @Schema(description = "所属管辖单位")
    @ExcelProperty("所属管辖单位")
    private String dominion;

    @Schema(description = "状态")
    @ExcelProperty("状态")
    private String status;

    @Schema(description = "设备图片")
    private String equImg;

    @Schema(description = "创建时间")
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @Schema(description = "删除时间")
    @ExcelProperty("删除时间")
    private LocalDateTime deletedTime;

}