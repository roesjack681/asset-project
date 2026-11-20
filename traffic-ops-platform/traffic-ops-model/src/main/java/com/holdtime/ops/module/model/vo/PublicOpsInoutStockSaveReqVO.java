package com.holdtime.ops.module.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 设备出入库管理新增/修改 Request VO")
@Data
public class PublicOpsInoutStockSaveReqVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "4243")
    private String id;

    @Schema(description = "出入库类型", example = "2")
    private String stockType;

    @Schema(description = "操作人")
    private String stockPerson;

    @Schema(description = "备注", example = "随便")
    private String remark;

    @Schema(description = "删除时间")
    private LocalDateTime deletedTime;

    @Schema(description = "出入库详细", example = "随便")
    private List<PublicOpsInoutDetailSaveReqVO> inoutDetailList;

}