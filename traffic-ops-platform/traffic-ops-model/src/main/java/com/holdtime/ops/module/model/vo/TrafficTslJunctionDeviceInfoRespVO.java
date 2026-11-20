package com.holdtime.ops.module.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 信控交叉口设备信息 Response VO")
@Data
@ExcelIgnoreUnannotated
public class TrafficTslJunctionDeviceInfoRespVO {

    @Schema(description = "信号机", requiredMode = Schema.RequiredMode.REQUIRED, example = "22195")
    @ExcelProperty("信号机")
    private String id;

    @Schema(description = "信号机ID")
    private String signalId;

    @Schema(description = "信号机名称", example = "王五")
    @ExcelProperty("信号机名称")
    private String controllerName;

    @Schema(description = "交叉口ID", example = "2759")
    @ExcelProperty("交叉口ID")
    private String junctionId;

    @Schema(description = "交叉口名称", example = "王五")
    @ExcelProperty("交叉口名称")
    private String junctionName;

    @Schema(description = "设备IP")
    @ExcelProperty("设备IP")
    private String deviceIp;

    @Schema(description = "在线状态")
    @ExcelProperty("在线状态")
    private String online;

    @Schema(description = "状态")
    @ExcelProperty("状态")
    private String state;

    @Schema(description = "模式")
    @ExcelProperty("模式")
    private String mode;

    @Schema(description = "协议")
    @ExcelProperty("协议")
    private String protocol;

    @Schema(description = "能否ping")
    @ExcelProperty("能否ping")
    private String canPing;

    @Schema(description = "创建时间")
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @Schema(description = "删除时间")
    @ExcelProperty("删除时间")
    private LocalDateTime deletedTime;

}