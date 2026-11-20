package com.holdtime.ops.module.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 警用装备采集站 Response VO")
@Data
@ExcelIgnoreUnannotated
public class TrafficOpsPoliceStationRespVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "22320")
    @ExcelProperty("主键")
    private String id;

    @Schema(description = "使用部门编号")
    @ExcelProperty("使用部门编号")
    private String useDeptBh;

    @Schema(description = "使用部门")
    @ExcelProperty("使用部门")
    private String useDept;

    @Schema(description = "IP地址")
    @ExcelProperty("IP地址")
    private String ipAddr;

    @Schema(description = "存放地址")
    @ExcelProperty("存放地址")
    private String address;

    @Schema(description = "设备编号")
    @ExcelProperty("设备编号")
    private String equCode;

    @Schema(description = "设备厂商")
    @ExcelProperty("设备厂商")
    private String manufacturer;

    @Schema(description = "机器名", example = "李四")
    @ExcelProperty("机器名")
    private String pcName;

    @Schema(description = "硬盘")
    @ExcelProperty("硬盘")
    private String hardDisk;

    @Schema(description = "处理器")
    @ExcelProperty("处理器")
    private String processor;

    @Schema(description = "操作系统")
    @ExcelProperty("操作系统")
    private String operatingSystem;

    @Schema(description = "MAC地址")
    @ExcelProperty("MAC地址")
    private String mac;

    @Schema(description = "内存")
    @ExcelProperty("内存")
    private String memory;

    @Schema(description = "软件版本")
    @ExcelProperty("软件版本")
    private String softVersion;

    @Schema(description = "创建时间")
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @Schema(description = "删除时间")
    @ExcelProperty("删除时间")
    private LocalDateTime deletedTime;

}