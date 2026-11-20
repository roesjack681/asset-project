package com.holdtime.ops.module.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 信号灯检测信息 Response VO")
@Data
@ExcelIgnoreUnannotated
public class TrafficLightsdetectionlogRespVO {

    @Schema(description = "id", requiredMode = Schema.RequiredMode.REQUIRED, example = "9085")
    @ExcelProperty("id")
    private Long id;

    @Schema(description = "路口ID", example = "27850")
    @ExcelProperty("路口ID")
    private Long lkid;

    @Schema(description = "相机ID", example = "24353")
    @ExcelProperty("相机ID")
    private Long sbid;

    @Schema(description = "检测结果")
    @ExcelProperty("检测结果")
    private String jcjg;

    @Schema(description = "检测结果扩展信息")
    @ExcelProperty("检测结果扩展信息")
    private String jcjgex;

    @Schema(description = "检测图片PATH")
    @ExcelProperty("检测图片PATH")
    private String jctp;

    @Schema(description = "检测时间")
    @ExcelProperty("检测时间")
    private LocalDateTime jcsj;

    @Schema(description = "判断结果")
    @ExcelProperty("判断结果")
    private String pdjg;

}