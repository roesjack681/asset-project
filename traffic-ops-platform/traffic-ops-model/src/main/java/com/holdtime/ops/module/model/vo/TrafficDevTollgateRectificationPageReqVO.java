package com.holdtime.ops.module.model.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import java.math.BigDecimal;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 卡口质量评价分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class TrafficDevTollgateRectificationPageReqVO extends PageParam {

    @Schema(description = "序号")
    private String xh;

    @Schema(description = "卡口编号")
    private String kkbh;

    @Schema(description = "方向类型")
    private String fxlx;

    @Schema(description = "检测时间")
    private LocalDateTime jcsj;

    @Schema(description = "检测不达标原因 1：传输不及时；2：号牌识别准确率低，多个用逗号分隔")
    private String bdbyy;

    @Schema(description = "数据传输及时率")
    private BigDecimal jsl;

    @Schema(description = "号牌识别准确率")
    private BigDecimal sbhpzl;

    @Schema(description = "传输标记")
    private String csbj;

    @Schema(description = "部局传输标记")
    private String bjcsbj;

    @Schema(description = "数据可用率")
    private BigDecimal kyl;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}