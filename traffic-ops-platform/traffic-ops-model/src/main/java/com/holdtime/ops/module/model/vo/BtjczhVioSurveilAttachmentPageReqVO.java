package com.holdtime.ops.module.model.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 违法抓拍附件分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class BtjczhVioSurveilAttachmentPageReqVO extends PageParam {

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

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}