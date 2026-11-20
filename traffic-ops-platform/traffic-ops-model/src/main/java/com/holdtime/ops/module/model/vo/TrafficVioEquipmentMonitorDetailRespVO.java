package com.holdtime.ops.module.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 集指设备检测记录 Response VO")
@Data
@ExcelIgnoreUnannotated
public class TrafficVioEquipmentMonitorDetailRespVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "4844")
    @ExcelProperty("主键")
    private String id;

    @Schema(description = "检测时间")
    @ExcelProperty("检测时间")
    private LocalDateTime occurrenceTime;

    @Schema(description = "设备id", example = "3839")
    @ExcelProperty("设备id")
    private String equId;

    @Schema(description = "卡口编号")
    @ExcelProperty("卡口编号")
    private String kkbh;

    @Schema(description = "设备编号")
    @ExcelProperty("设备编号")
    private String sbbh;

    @Schema(description = "设备IP")
    @ExcelProperty("设备IP")
    private String ipAddr;

    @Schema(description = "ping状态", example = "2")
    @ExcelProperty("ping状态")
    private String pingStatus;

    @Schema(description = "实时视频状态", example = "1")
    @ExcelProperty("实时视频状态")
    private String videoStatus;

    @Schema(description = "无违法次数")
    @ExcelProperty("无违法次数")
    private Integer noSurveilCnt;

    @Schema(description = "有效违法数")
    @ExcelProperty("有效违法数")
    private Integer validSurveilCnt;

    @Schema(description = "无效违法数")
    @ExcelProperty("无效违法数")
    private Integer invalidSurveilCnt;

    @Schema(description = "操作记录")
    private String operateMark;

    @Schema(description = "操作描述")
    private String operateDesc;

    @Schema(description = "操作时间")
    private LocalDateTime operateDate;

    @Schema(description = "创建时间")
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @Schema(description = "删除时间")
    @ExcelProperty("删除时间")
    private LocalDateTime deletedTime;

}