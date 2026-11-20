package com.holdtime.ops.module.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 设备监测结果导入信息 Response VO")
@Data
@ExcelIgnoreUnannotated
public class TrafficMonitorResultsImportDetailRespVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "884")
    private String id;

    @Schema(description = "设备id", example = "152")
    private String equId;

    @Schema(description = "IP地址")
    @ExcelProperty(value = "IP地址", index = 0)
    private String ipAddr;

    @Schema(description = "道路名称", example = "张三")
    @ExcelProperty(value = "所在道路名称", index = 1)
    private String roadName;

    @Schema(description = "路口名称", example = "王五")
    private String intersectionName;

    @Schema(description = "杆件编号")
    private String memberBh;

    @Schema(description = "方向")
    private String direction;

    @Schema(description = "检测时间")
    private LocalDateTime occurrenceTime;

    @Schema(description = "状态", example = "2")
    @ExcelProperty(value = "状态", index = 6)
    private String status;

    @Schema(description = "检测结果图片")
    private String resultImg;

    @Schema(description = "检测情况")
    @ExcelProperty(value = "检测情况", index = 8)
    private String monitorSituation;

    @Schema(description = "设备情况")
    private String equSituation;

    @Schema(description = "设备情况描述")
    private String equDesc;

    @Schema(description = "设备情况证据", example = "3271")
    private String evidenceFileId;

    @Schema(description = "备注时间")
    private LocalDateTime remarkDate;

    @Schema(description = "备注人ID")
    private Long remarkId;

    @Schema(description = "备注人姓名")
    private String remarkName;

    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    @Schema(description = "删除时间")
    private LocalDateTime deletedTime;

}