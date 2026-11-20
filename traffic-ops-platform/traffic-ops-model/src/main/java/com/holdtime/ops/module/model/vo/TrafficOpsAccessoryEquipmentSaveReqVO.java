package com.holdtime.ops.module.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 杆件附属设备新增/修改 Request VO")
@Data
public class TrafficOpsAccessoryEquipmentSaveReqVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "29797")
    private String id;

    @Schema(description = "杆件编号", example = "23096")
    private String memberId;

    @Schema(description = "设备类型名称", example = "1")
    private String equType;

    @Schema(description = "厂家")
    private String manufacturer;

    @Schema(description = "设备型号")
    private String equModel;

    @Schema(description = "数量")
    private String equNum;

    @Schema(description = "备注", example = "随便")
    private String remark;

    @Schema(description = "删除时间")
    private LocalDateTime deletedTime;

}