package com.holdtime.ops.module.model.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 设备状态检测分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class TrafficCamdetectionlogPageReqVO extends PageParam {

    @Schema(description = "路口ID", example = "1015")
    private Long id;

    @Schema(description = "相机ID", example = "30922")
    private Long sbid;

    @Schema(description = "相机状态，是否能打开视频流")
    private String xjzt;

    @Schema(description = "检测时间")
    private LocalDateTime jcsj;

}