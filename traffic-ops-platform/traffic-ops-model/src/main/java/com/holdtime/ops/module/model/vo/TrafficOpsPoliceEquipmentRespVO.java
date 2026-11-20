package com.holdtime.ops.module.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 警用装备资产信息 Response VO")
@Data
@ExcelIgnoreUnannotated
public class TrafficOpsPoliceEquipmentRespVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "10555")
    @ExcelProperty("主键")
    private String id;

    @Schema(description = "设备名称", example = "张三")
    @ExcelProperty("设备名称")
    private String equName;

    @Schema(description = "类型", example = "2")
    @ExcelProperty("类型")
    private String equType;

    @Schema(description = "加密卡")
    @ExcelProperty("加密卡")
    private String encryptionCard;

    @Schema(description = "PSN")
    @ExcelProperty("PSN")
    private String psn;

    @Schema(description = "品牌")
    @ExcelProperty("品牌")
    private String brand;

    @Schema(description = "型号")
    @ExcelProperty("型号")
    private String model;

    @Schema(description = "生产厂家")
    @ExcelProperty("生产厂家")
    private String manufacturer;

    @Schema(description = "电池")
    @ExcelProperty("电池")
    private String battery;

    @Schema(description = "呼号")
    @ExcelProperty("呼号")
    private String callSign;

    @Schema(description = "供货厂家")
    @ExcelProperty("供货厂家")
    private String supplier;

    @Schema(description = "版本号")
    @ExcelProperty("版本号")
    private String version;

    @Schema(description = "备注", example = "你说的对")
    @ExcelProperty("备注")
    private String remark;

    @Schema(description = "录入人编号")
    @ExcelProperty("录入人编号")
    private String recordBh;

    @Schema(description = "录入人")
    @ExcelProperty("录入人")
    private String recorder;

    @Schema(description = "录入部门编号")
    @ExcelProperty("录入部门编号")
    private String recordDeptBh;

    @Schema(description = "录入部门")
    @ExcelProperty("录入部门")
    private String recordDept;

    @Schema(description = "使用部门编号")
    @ExcelProperty("使用部门编号")
    private String useDeptBh;

    @Schema(description = "使用部门")
    @ExcelProperty("使用部门")
    private String useDept;

    @Schema(description = "使用人编号")
    @ExcelProperty("使用人编号")
    private String userBh;

    @Schema(description = "使用人", example = "李四")
    @ExcelProperty("使用人")
    private String userName;

    @Schema(description = "创建时间")
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @Schema(description = "删除时间")
    @ExcelProperty("删除时间")
    private LocalDateTime deletedTime;

}