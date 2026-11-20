package com.holdtime.ops.module.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;
import java.util.*;
import java.math.BigDecimal;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 公安监测通达详情新增/修改 Request VO")
@Data
public class PoliceMonitorPingDetailSaveReqVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "21341")
    private String id;

    @Schema(description = "设备id", example = "6091")
    private String equId;

    @Schema(description = "设备IP")
    private String ipAddr;

    @Schema(description = "设备标签")
    private String equLable;

    @Schema(description = "检测时间")
    private LocalDateTime occurrenceTime;

    @Schema(description = "ping状态", example = "2")
    private String pingStatus;

    @Schema(description = "ping次数")
    private Integer pingCnt;

    @Schema(description = "丢包率")
    private BigDecimal lossRate;

    @Schema(description = "来源：1-自动；2-手动")
    private String source;

    @Schema(description = "删除时间")
    private LocalDateTime deletedTime;

}