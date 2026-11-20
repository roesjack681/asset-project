package com.holdtime.ops.module.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 违法抓拍附件 Response VO")
@Data
@ExcelIgnoreUnannotated
public class BtjczhVioSurveilAttachmentRespVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "19759")
    @ExcelProperty("主键")
    private String id;

    @Schema(description = "号牌种类")
    @ExcelProperty("号牌种类")
    private String hpzl;

    @Schema(description = "号牌号码")
    @ExcelProperty("号牌号码")
    private String hphm;

    @Schema(description = "图片1")
    @ExcelProperty("图片1")
    private String url1;

    @Schema(description = "图片2")
    @ExcelProperty("图片2")
    private String url2;

    @Schema(description = "图片3")
    @ExcelProperty("图片3")
    private String url3;

    @Schema(description = "录入时间")
    @ExcelProperty("录入时间")
    private LocalDateTime lrsj;

    @Schema(description = "创建时间")
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}