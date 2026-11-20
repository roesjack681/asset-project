package com.holdtime.ops.module.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 集指无效违法记录新增/修改 Request VO")
@Data
public class ViewVioSurveilScreenErrInfoSaveReqVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "10529")
    private String id;

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

    @Schema(description = "错误原因")
    private String cwyy;

    @Schema(description = "图片1")
    private String url1;

    @Schema(description = "图片2")
    private String url2;

    @Schema(description = "图片3")
    private String url3;

    @Schema(description = "录入时间")
    private LocalDateTime lrsj;

    @Schema(description = "复审结果")
    private String reviewResult;

    @Schema(description = "复审描述")
    private String reviewDesc;

    @Schema(description = "复审时间")
    private LocalDateTime reviewDate;

    @Schema(description = "复审人")
    private String reviewer;

}