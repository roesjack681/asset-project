package com.holdtime.ops.module.model.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 信号灯检测信息分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class TrafficLightsdetectionlogPageReqVO extends PageParam {

    @Schema(description = "路口ID", example = "27850")
    private Long id;

    @Schema(description = "相机ID", example = "24353")
    private Long sbid;

    @Schema(description = "检测结果")
    private String jcjg;

    @Schema(description = "检测结果扩展信息")
    private String jcjgex;

    @Schema(description = "检测图片PATH")
    private String jctp;

    @Schema(description = "检测时间")
    private LocalDateTime jcsj;

    @Schema(description = "判断结果")
    private String pdjg;

}