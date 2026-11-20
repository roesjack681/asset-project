package com.holdtime.ops.module.model.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Schema(description = "管理后台 - 交叉口信息 Response VO")
@Data
@ExcelIgnoreUnannotated
public class TrafficOpsPositionRespVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "28295")
    private String id;

//    @Schema(description = "设备编号")
//    @ExcelProperty("设备编号")
//    private String equNumber;

//    @Schema(description = "设备名称", example = "张三")
//    @ExcelProperty("设备名称")
//    private String equName;

    @Schema(description = "设备类型", example = "2")
    private String equType;

//    @Schema(description = "所在道路名称", example = "赵六")
//    @ExcelProperty("所在道路名称")
//    private String roadName;

    @Schema(description = "灯控路口名称", example = "芋艿")
    private String intersectionName;

    @Schema(description = "GPS定位-经度")
    private String lng;

    @Schema(description = "GPS定位-纬度")
    private String lat;

    @Schema(description = "管辖单位")
    private String dominion;

    @Schema(description = "设备状态")
    private String status;

    @Schema(description = "信号机-信号控制相位")
    private String phase;

    @Schema(description = "信号机-信号机型号")
    private String equModel;

    @Schema(description = "信号机-信号机编号")
    private String equNumber;

    @Schema(description = "IP地址")
    private String ipAddr;

    @Schema(description = "LED大屏-屏幕朝向")
    private String screenOrientation;

    @Schema(description = "LED大屏-大屏对应编号")
    private String dpbh;

    @Schema(description = "LED大屏-大小")
    private String size;

    @Schema(description = "电警-电警类型")
    private String djType;

    @Schema(description = "电警--方向")
    private String direction;


    @Schema(description = "杆件——设备图片")
    private String equImg;

    @Schema(description = "杆件——序列号")
    private Integer serialNum;

    @Schema(description = "杆件——安装位置")
    private String adress;


}