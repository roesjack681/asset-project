package com.holdtime.ops.module.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 设备监测结果历史新增/修改 Request VO")
@Data
public class TrafficMonitorResultsHistorySaveReqVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "30873")
    private String id;

    @Schema(description = "设备类型", example = "1")
    private String equType;

    @Schema(description = "IP地址")
    private String ipAddr;

    @Schema(description = "方向")
    private String direction;

    @Schema(description = "检测事件", example = "2")
    private String eventType;

    @Schema(description = "检测时间")
    private LocalDateTime occurrenceTime;

    @Schema(description = "状态", example = "2")
    private String status;

    @Schema(description = "删除时间")
    private LocalDateTime deletedTime;

    @Schema(description = "来源：1-接口；2-导入")
    private String source;

    @Schema(description = "设备ID", example = "12040")
    private String equId;

    @Schema(description = "所在道路名称", example = "王五")
    private String roadName;

    @Schema(description = "灯控路口名称", example = "王五")
    private String intersectionName;

    @Schema(description = "杆件编号")
    private String memberBh;

}