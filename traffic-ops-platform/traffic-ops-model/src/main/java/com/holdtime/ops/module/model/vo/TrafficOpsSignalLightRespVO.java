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
public class TrafficOpsSignalLightRespVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "18773")
    @ExcelProperty("主键")
    private String id;

    @Schema(description = "设备编号")
    @ExcelProperty("设备编号")
    private String equNumber;

    @Schema(description = "设备名称", example = "张三")
    @ExcelProperty("设备名称")
    private String equName;

    @Schema(description = "设备类型", example = "2")
    @ExcelProperty("设备类型")
    private String equType;

    @Schema(description = "所在道路名称", example = "赵六")
    @ExcelProperty("所在道路名称")
    private String roadName;

    @Schema(description = "灯控路口名称", example = "芋艿")
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

    @Schema(description = "厂家")
    @ExcelProperty("厂家")
    private String manufacturer;

    @Schema(description = "型号")
    @ExcelProperty("型号")
    private String equModel;

    @Schema(description = "归属单位")
    @ExcelProperty("归属单位")
    private String unit;

    @Schema(description = "信号灯图案")
    @ExcelProperty("信号灯图案")
    private String ledPattern;

    @Schema(description = "灯具类型", example = "2")
    @ExcelProperty("灯具类型")
    private String ledType;

    @Schema(description = "尺寸数据")
    @ExcelProperty("尺寸数据")
    private String ledSize;

    @Schema(description = "灯头数量")
    @ExcelProperty("灯头数量")
    private String ledHeadNum;

    @Schema(description = "排列方式")
    @ExcelProperty("排列方式")
    private String arrangement;

    @Schema(description = "信号显示方式")
    @ExcelProperty("信号显示方式")
    private String signalDisplayMode;

    @Schema(description = "创建时间")
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @Schema(description = "删除时间")
    @ExcelProperty("删除时间")
    private LocalDateTime deletedTime;

    @Schema(description = "所属管辖单位")
    @ExcelProperty("所属管辖单位")
    private String dominion;

    @Schema(description = "状态")
    @ExcelProperty("状态")
    private String status;

    @Schema(description = "设备图片")
    private String equImg;

}