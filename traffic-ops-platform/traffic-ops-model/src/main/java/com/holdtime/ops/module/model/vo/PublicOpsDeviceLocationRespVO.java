package com.holdtime.ops.module.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.util.*;
import java.math.BigDecimal;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 终端最新位置 Response VO")
@Data
@ExcelIgnoreUnannotated
public class PublicOpsDeviceLocationRespVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "12870")
    @ExcelProperty("主键")
    private String id;

    @Schema(description = "终端编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "2921")
    @ExcelProperty("终端编号")
    private String deviceId;

    @Schema(description = "GPS纬度", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("GPS纬度")
    private BigDecimal latitude;

    @Schema(description = "GPS经度", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("GPS经度")
    private BigDecimal longitude;

    @Schema(description = "GPS速度(km/h)")
    @ExcelProperty("GPS速度(km/h)")
    private BigDecimal speed;

    @Schema(description = "方向(0-359度)")
    @ExcelProperty("方向(0-359度)")
    private Integer direction;

    @Schema(description = "位置上报时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("位置上报时间")
    private LocalDateTime reportTime;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}