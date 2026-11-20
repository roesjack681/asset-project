package com.holdtime.ops.module.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 违法抓拍有效记录新增/修改 Request VO")
@Data
public class TrafficVioSurveilScreenSaveReqVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "19124")
    private String id;

    @Schema(description = "设备id", example = "18256")
    private String equId;

    @Schema(description = "设备编号")
    private String sbbh;

    @Schema(description = "号牌号码")
    private String hphm;

    @Schema(description = "号牌种类")
    private String hpzl;

    @Schema(description = "违法时间")
    private LocalDateTime wfsj;

    @Schema(description = "违法行为")
    private String wfxw;

}