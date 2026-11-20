package com.holdtime.ops.module.model.vo;

import lombok.*;

import java.math.BigDecimal;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.iocoder.yudao.framework.common.pojo.PageParam;

@Schema(description = "管理后台 - VIEW分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ViewOpsMonitorResultsPageReqVO extends PageParam {

    @Schema(description = "设备ID", example = "10502")
    private String equId;

    @Schema(description = "杆件编号")
    private String memberBh;

    @Schema(description = "所在道路名称", example = "芋艿")
    private String roadName;

    @Schema(description = "灯控路口名称", example = "李四")
    private String intersectionName;

    @Schema(description = "方向")
    private String direction;

    @Schema(description = "IP地址")
    private String ipAddr;

    @Schema(description = "检测事件")
    private String eventType;

    @Schema(description = "正常次数")
    private BigDecimal normalCnt;

    @Schema(description = "异常次数")
    private BigDecimal abnormalCnt;

    @Schema(description = "总检测天数")
    private BigDecimal monitorSumDays;

    @Schema(description = "总检测次数")
    private BigDecimal monitorSumCnt;

    @Schema(description = "健康率")
    private Double healthRate;

    @Schema(description = "健康率")
    private String status;

    @Schema(description = "卡口编号")
    private String kkbh;

    @Schema(description = "标签列表")
    private List<String> equLableList;

    @Schema(description = "电警类型")
    private String djType;

    @Schema(description = "状态变化描述")
    private String statusChangeDesc;

}