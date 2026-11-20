package com.holdtime.ops.module.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - Led屏 Response VO")
@Data
@ExcelIgnoreUnannotated
public class TrafficOpsLedRespVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "20928")
    @ExcelProperty("主键")
    private String id;

    @Schema(description = "设备编号")
    @ExcelProperty("设备编号")
    private String equNumber;

    @Schema(description = "设备名称", example = "王五")
    @ExcelProperty("设备名称")
    private String equName;

    @Schema(description = "设备类型", example = "2")
    @ExcelProperty("设备类型")
    private String equType;

    @Schema(description = "所在道路名称", example = "王五")
    @ExcelProperty("所在道路名称")
    private String roadName;

    @Schema(description = "灯控路口名称", example = "王五")
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

    @Schema(description = "尺寸")
    @ExcelProperty("尺寸")
    private String size;

    @Schema(description = "显示用途")
    @ExcelProperty("显示用途")
    private String displayPurpose;

    @Schema(description = "创建时间")
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @Schema(description = "删除时间")
    @ExcelProperty("删除时间")
    private LocalDateTime deletedTime;

}