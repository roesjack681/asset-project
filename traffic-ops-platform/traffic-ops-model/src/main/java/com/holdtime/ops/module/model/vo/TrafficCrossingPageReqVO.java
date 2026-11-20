package com.holdtime.ops.module.model.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.iocoder.yudao.framework.common.pojo.PageParam;

@Schema(description = "管理后台 - 路口信息分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class TrafficCrossingPageReqVO extends PageParam {

    @Schema(description = "路口id")
    private Long id;

    @Schema(description = "路口名称")
    private String lkmc;

    @Schema(description = "路口类型")
    private String lklx;

    @Schema(description = "路口坐标，需要在地图显示时使用")
    private String lkzb;

    @Schema(description = "区旗县")
    private String qu;

}