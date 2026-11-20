package com.holdtime.ops.module.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 集指设备违法原因分析新增/修改 Request VO")
@Data
public class TrafficVioEquipmentIllegalAnalysisSaveReqVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "8318")
    private String id;

    @Schema(description = "设备编号")
    private String sbbh;

    @Schema(description = "检测时间")
    private LocalDateTime occurrenceTime;

    @Schema(description = "违法原因", example = "不好")
    private String illegalReason;

    @Schema(description = "违法数量")
    private Integer illegalCnt;

}