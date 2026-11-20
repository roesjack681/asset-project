package com.holdtime.ops.module.model.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 卡口质量检测结果分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class TrafficDevTollgateQaResultPageReqVO extends PageParam {

    @Schema(description = "卡口编号")
    private String kkbh;

    @Schema(description = "方向类型")
    private String fxlx;

    @Schema(description = "检测时间")
    private LocalDateTime jcsj;

    @Schema(description = "号牌种类")
    private String hpzl;

    @Schema(description = "号牌号码")
    private String hphm;

    @Schema(description = "过车时间")
    private LocalDateTime gcsj;

    @Schema(description = "入队列时间")
    private LocalDateTime rdlsj;

    @Schema(description = "过车序号")
    private String gcxh;

    @Schema(description = "识别号牌种类")
    private String sbhpzl;

    @Schema(description = "识别号牌号码")
    private String sbhphm;

    @Schema(description = "核查标记 默认0：未核查；1：号牌一致；2：号牌不一致")
    private String hcbj;

    @Schema(description = "核查时间")
    private LocalDateTime hcsj;

    @Schema(description = "核查人")
    private String hcr;

    @Schema(description = "数据来源 默认0：自动检测；1：5级以上核对；3：预警签收；4：人工核对")
    private String sjly;

    @Schema(description = "传输标记")
    private String csbj;

    @Schema(description = "部局传输标记")
    private String bjcsbj;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}