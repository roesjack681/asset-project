package com.holdtime.ops.module.model.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 集指无效违法记录分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ViewVioSurveilScreenErrInfoPageReqVO extends PageParam {

    @Schema(description = "设备编号")
    private String sbbh;

    @Schema(description = "号牌号码")
    private String hphm;

    @Schema(description = "号牌种类")
    private String hpzl;

    @Schema(description = "违法时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] wfsj;

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
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] lrsj;

    @Schema(description = "复审结果")
    private String reviewResult;

    @Schema(description = "复审描述")
    private String reviewDesc;

    @Schema(description = "复审时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] reviewDate;

    @Schema(description = "复审人")
    private String reviewer;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}