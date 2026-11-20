package com.holdtime.ops.module.model.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 交叉口信息分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class TrafficOpsIntersectionPageReqVO extends PageParam {

    @Schema(description = "管理部门")
    private String glbm;

    @Schema(description = "道路代码")
    private String dldm;

    @Schema(description = "行政区划")
    private String xzqh;

    @Schema(description = "路段代码")
    private String lddm;

    @Schema(description = "道路名称")
    private String dlmc;

    @Schema(description = "路段名称")
    private String ldmc;

    @Schema(description = "起始里程")
    private String qslc;

    @Schema(description = "结束里程")
    private String jslc;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

    @Schema(description = "删除时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] deletedTime;

}