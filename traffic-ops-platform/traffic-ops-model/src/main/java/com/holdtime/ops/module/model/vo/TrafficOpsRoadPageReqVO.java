package com.holdtime.ops.module.model.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 道路信息分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class TrafficOpsRoadPageReqVO extends PageParam {

    @Schema(description = "管理部门")
    private String glbm;

    @Schema(description = "道路代码")
    private String dldm;

    @Schema(description = "道路名称")
    private String dlmc;

    @Schema(description = "道路类型")
    private String dllx;

    @Schema(description = "公路行政等级")
    private String glxzdj;

    @Schema(description = "起始")
    private String qs;

    @Schema(description = "结束")
    private String js;

    @Schema(description = "辖区里程")
    private String xqlc;

    @Schema(description = "起始名称")
    private String qsmc;

    @Schema(description = "结束名称")
    private String jsmc;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

    @Schema(description = "删除时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] deletedTime;

}