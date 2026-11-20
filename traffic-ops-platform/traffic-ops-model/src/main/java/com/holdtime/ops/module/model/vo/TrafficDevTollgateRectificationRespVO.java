package com.holdtime.ops.module.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.util.*;
import java.math.BigDecimal;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 卡口质量评价 Response VO")
@Data
@ExcelIgnoreUnannotated
public class TrafficDevTollgateRectificationRespVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "12567")
    @ExcelProperty("主键")
    private String id;

    @Schema(description = "序号")
    @ExcelProperty("序号")
    private String xh;

    @Schema(description = "卡口编号")
    @ExcelProperty("卡口编号")
    private String kkbh;

    @Schema(description = "方向类型")
    @ExcelProperty("方向类型")
    private String fxlx;

    @Schema(description = "检测时间")
    @ExcelProperty("检测时间")
    private LocalDateTime jcsj;

    @Schema(description = "检测不达标原因 1：传输不及时；2：号牌识别准确率低，多个用逗号分隔")
    @ExcelProperty("检测不达标原因 1：传输不及时；2：号牌识别准确率低，多个用逗号分隔")
    private String bdbyy;

    @Schema(description = "数据传输及时率")
    @ExcelProperty("数据传输及时率")
    private BigDecimal jsl;

    @Schema(description = "号牌识别准确率")
    @ExcelProperty("号牌识别准确率")
    private BigDecimal sbhpzl;

    @Schema(description = "传输标记")
    @ExcelProperty("传输标记")
    private String csbj;

    @Schema(description = "部局传输标记")
    @ExcelProperty("部局传输标记")
    private String bjcsbj;

    @Schema(description = "数据可用率")
    @ExcelProperty("数据可用率")
    private BigDecimal kyl;

    @Schema(description = "创建时间")
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}