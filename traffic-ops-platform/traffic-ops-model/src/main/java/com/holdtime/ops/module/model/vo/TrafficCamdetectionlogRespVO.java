package com.holdtime.ops.module.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 设备状态检测 Response VO")
@Data
@ExcelIgnoreUnannotated
public class TrafficCamdetectionlogRespVO {

    @Schema(description = "id", requiredMode = Schema.RequiredMode.REQUIRED, example = "29962")
    @ExcelProperty("id")
    private Long id;

    @Schema(description = "路口ID", example = "1015")
    @ExcelProperty("路口ID")
    private Long lkid;

    @Schema(description = "相机ID", example = "30922")
    @ExcelProperty("相机ID")
    private Long sbid;

    @Schema(description = "相机状态，是否能打开视频流")
    @ExcelProperty("相机状态，是否能打开视频流")
    private String xjzt;

    @Schema(description = "检测时间")
    @ExcelProperty("检测时间")
    private LocalDateTime jcsj;

}