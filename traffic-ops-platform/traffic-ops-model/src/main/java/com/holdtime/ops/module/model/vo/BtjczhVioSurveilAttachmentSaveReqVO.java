package com.holdtime.ops.module.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 违法抓拍附件新增/修改 Request VO")
@Data
public class BtjczhVioSurveilAttachmentSaveReqVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "19759")
    private String id;

    @Schema(description = "号牌种类")
    private String hpzl;

    @Schema(description = "号牌号码")
    private String hphm;

    @Schema(description = "图片1")
    private String url1;

    @Schema(description = "图片2")
    private String url2;

    @Schema(description = "图片3")
    private String url3;

    @Schema(description = "录入时间")
    private LocalDateTime lrsj;

}