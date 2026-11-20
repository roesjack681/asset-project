package com.holdtime.ops.module.model.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 警用装备资产信息新增/修改 Request VO")
@Data
public class TrafficOpsPoliceEquipmentSaveReqVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "10555")
    private String id;

    @Schema(description = "设备名称", example = "张三")
    private String equName;

    @Schema(description = "类型", example = "2")
    private String equType;

    @Schema(description = "加密卡")
    private String encryptionCard;

    @Schema(description = "PSN")
    private String psn;

    @Schema(description = "品牌")
    private String brand;

    @Schema(description = "型号")
    private String model;

    @Schema(description = "厂家")
    private String manufacturer;

    @Schema(description = "电池")
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
    private String remark;

    @Schema(description = "录入人编号")
    private String recordBh;

    @Schema(description = "录入人")
    private String recorder;

    @Schema(description = "录入部门编号")
    private String recordDeptBh;

    @Schema(description = "录入部门")
    private String recordDept;

    @Schema(description = "使用部门编号")
    private String useDeptBh;

    @Schema(description = "使用部门")
    private String useDept;

    @Schema(description = "使用人编号")
    private String userBh;

    @Schema(description = "使用人", example = "李四")
    private String userName;

    @Schema(description = "删除时间")
    private LocalDateTime deletedTime;

}