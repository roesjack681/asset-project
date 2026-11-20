package com.holdtime.ops.module.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDate;
import java.util.*;
import javax.validation.constraints.*;
import java.util.*;
import java.math.BigDecimal;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 集指设备每日情况新增/修改 Request VO")
@Data
public class BtjczhVioEquipmentDailySituationSaveReqVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "6038")
    private String id;

    @Schema(description = "检测时间")
    private LocalDate occurrenceDay;

    @Schema(description = "卡口编号")
    private String kkbh;

    @Schema(description = "设备编号")
    private String sbbh;

    @Schema(description = "无违法次数")
    private Integer noSurveilCnt;

    @Schema(description = "有效违法数")
    private Integer validSurveilCnt;

    @Schema(description = "无效违法数")
    private Integer invalidSurveilCnt;

    @Schema(description = "识别准确率")
    private BigDecimal recognitionAccuracyRate;

    @Schema(description = "识别准确率采集时间")
    private LocalDateTime recognitionAccuracyPickDate;

    @Schema(description = "删除时间")
    private LocalDateTime deletedTime;

}