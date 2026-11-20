package com.holdtime.ops.module.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 集指设备错误原因分析新增/修改 Request VO")
@Data
public class TrafficVioEquipmentInvalidReasonAnalysisSaveReqVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "7191")
    private String id;

    @Schema(description = "设备编号")
    private String sbbh;

    @Schema(description = "检测时间")
    private LocalDateTime occurrenceTime;

    @Schema(description = "无效原因", example = "不对")
    private String invalidReason;

    @Schema(description = "无效数量")
    private Integer invalidCnt;

    @Schema(description = "删除时间")
    private LocalDateTime deletedTime;

}