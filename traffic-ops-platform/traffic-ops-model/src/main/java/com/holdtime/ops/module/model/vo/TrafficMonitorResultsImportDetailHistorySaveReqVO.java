package com.holdtime.ops.module.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 设备监测结果导入信息历史新增/修改 Request VO")
@Data
public class TrafficMonitorResultsImportDetailHistorySaveReqVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "28804")
    private String id;

    @Schema(description = "设备id", example = "11955")
    private String equId;

    @Schema(description = "IP地址")
    private String ipAddr;

    @Schema(description = "道路名称", example = "张三")
    private String roadName;

    @Schema(description = "路口名称", example = "李四")
    private String intersectionName;

    @Schema(description = "杆件编号")
    private String memberBh;

    @Schema(description = "方向")
    private String direction;

    @Schema(description = "检测时间")
    private LocalDateTime occurrenceTime;

    @Schema(description = "状态", example = "1")
    private String status;

    @Schema(description = "检测情况")
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

    @Schema(description = "删除时间")
    private LocalDateTime deletedTime;

    @Schema(description = " 详细主键")
    private String detailId;

}