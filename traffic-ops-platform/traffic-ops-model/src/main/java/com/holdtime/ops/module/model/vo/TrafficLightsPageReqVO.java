package com.holdtime.ops.module.model.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 信号灯信息分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class TrafficLightsPageReqVO extends PageParam {

    @Schema(description = "路口ID", example = "1477")
    private Long id;

    @Schema(description = "信号灯方向")
    private String xhdfx;

    @Schema(description = "相机方向")
    private String xjfx;

    @Schema(description = "信号灯状态")
    private String xhdzt;

    @Schema(description = "上次检测时间")
    private LocalDateTime jcsj;

    @Schema(description = "相机地址")
    private String xjdz;

    @Schema(description = "处置结果")
    private String czjg;

    @Schema(description = "是否检测")
    private String sfjc;

    @Schema(description = "坐标")
    private String zb;

    @Schema(description = "信号灯型号")
    private String xhdxh;

    @Schema(description = "检测目标：例：GB1:3(此方向有3个GB1灯);FGB1:2;FGBF1:2(2个非国标机动车灯，2个非国标非机动车灯)")
    private String jcmb;

    @Schema(description = "常黑状态个数")
    private Integer ch;

    @Schema(description = "信号灯识别区域")
    private String xhdsbqy;

    @Schema(description = "拥堵识别区域")
    private String ydsbqy;

}