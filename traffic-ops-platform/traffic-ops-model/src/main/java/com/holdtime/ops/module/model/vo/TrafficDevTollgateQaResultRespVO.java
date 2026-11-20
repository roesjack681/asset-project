package com.holdtime.ops.module.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 卡口质量检测结果 Response VO")
@Data
@ExcelIgnoreUnannotated
public class TrafficDevTollgateQaResultRespVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "6041")
    @ExcelProperty("主键")
    private String id;

    @Schema(description = "卡口编号")
    @ExcelProperty("卡口编号")
    private String kkbh;

    @Schema(description = "方向类型")
    @ExcelProperty("方向类型")
    private String fxlx;

    @Schema(description = "检测时间")
    @ExcelProperty("检测时间")
    private LocalDateTime jcsj;

    @Schema(description = "号牌种类")
    @ExcelProperty("号牌种类")
    private String hpzl;

    @Schema(description = "号牌号码")
    @ExcelProperty("号牌号码")
    private String hphm;

    @Schema(description = "过车时间")
    @ExcelProperty("过车时间")
    private LocalDateTime gcsj;

    @Schema(description = "入队列时间")
    @ExcelProperty("入队列时间")
    private LocalDateTime rdlsj;

    @Schema(description = "过车序号")
    @ExcelProperty("过车序号")
    private String gcxh;

    @Schema(description = "识别号牌种类")
    @ExcelProperty("识别号牌种类")
    private String sbhpzl;

    @Schema(description = "识别号牌号码")
    @ExcelProperty("识别号牌号码")
    private String sbhphm;

    @Schema(description = "核查标记 默认0：未核查；1：号牌一致；2：号牌不一致")
    @ExcelProperty("核查标记 默认0：未核查；1：号牌一致；2：号牌不一致")
    private String hcbj;

    @Schema(description = "核查时间")
    @ExcelProperty("核查时间")
    private LocalDateTime hcsj;

    @Schema(description = "核查人")
    @ExcelProperty("核查人")
    private String hcr;

    @Schema(description = "数据来源 默认0：自动检测；1：5级以上核对；3：预警签收；4：人工核对")
    @ExcelProperty("数据来源 默认0：自动检测；1：5级以上核对；3：预警签收；4：人工核对")
    private String sjly;

    @Schema(description = "传输标记")
    @ExcelProperty("传输标记")
    private String csbj;

    @Schema(description = "部局传输标记")
    @ExcelProperty("部局传输标记")
    private String bjcsbj;

    @Schema(description = "创建时间")
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}