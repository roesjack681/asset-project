package com.holdtime.ops.module.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 杆件 Response VO")
@Data
@ExcelIgnoreUnannotated
public class PoliceOpsMemberRespVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "26836")
    @ExcelProperty("主键")
    private String id;

    @Schema(description = "设备编号")
    @ExcelProperty("设备编号")
    private String equNumber;

    @Schema(description = "设备名称", example = "张三")
    @ExcelProperty("设备名称")
    private String equName;

    @Schema(description = "设备类型", example = "1")
    @ExcelProperty("设备类型")
    private String equType;

    @Schema(description = "所在道路名称", example = "王五")
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

    @Schema(description = "厂家")
    @ExcelProperty("厂家")
    private String manufacturer;

    @Schema(description = "归属单位")
    @ExcelProperty("归属单位")
    private String unit;

    @Schema(description = "高度")
    @ExcelProperty("高度")
    private String hight;

    @Schema(description = "横杆长度")
    @ExcelProperty("横杆长度")
    private String barLength;

    @Schema(description = "副臂长度")
    @ExcelProperty("副臂长度")
    private String jibLength;

    @Schema(description = "杆件类型", example = "1")
    @ExcelProperty("杆件类型")
    private String memberType;

    @Schema(description = "管辖单位")
    @ExcelProperty("管辖单位")
    private String dominion;

    @Schema(description = "状态", example = "2")
    @ExcelProperty("状态")
    private String status;

    @Schema(description = "设备图片")
    @ExcelProperty("设备图片")
    private String equImg;

    @Schema(description = "创建时间")
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @Schema(description = "删除时间")
    @ExcelProperty("删除时间")
    private LocalDateTime deletedTime;

    @Schema(description = "序列号")
    @ExcelProperty("序列号")
    private Integer serialNum;

    @Schema(description = "设备图片2")
    @ExcelProperty("设备图片2")
    private String equImg2;

    @Schema(description = "设备图片3")
    @ExcelProperty("设备图片3")
    private String equImg3;

    @Schema(description = "是否核查")
    @ExcelProperty("是否核查")
    private String isCheck;

    @Schema(description = "核查备注说明", example = "你说的对")
    @ExcelProperty("核查备注说明")
    private String checkRemark;

    @Schema(description = "取电位置")
    private String powerSupplyLocation;

    @Schema(description = "图纸")
    private String drawing;

}