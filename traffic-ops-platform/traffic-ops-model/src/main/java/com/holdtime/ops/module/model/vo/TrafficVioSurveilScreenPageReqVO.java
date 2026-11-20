package com.holdtime.ops.module.model.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 违法抓拍有效记录分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class TrafficVioSurveilScreenPageReqVO extends PageParam {

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

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}