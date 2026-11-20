package com.holdtime.ops.module.model.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 设备维保全流程分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class TrafficOpsMaintainFlowRecordPageReqVO extends PageParam {

    @Schema(description = "维保主键", example = "25983")
    private String maintenanceId;

    @Schema(description = "流程类型：1-上报；2-派单；3-接单；4-维修；5-确认；6-驳回；7-取消", example = "2")
    private String flowType;

    @Schema(description = "注册编号", example = "20265")
    private String registerId;

    @Schema(description = "用户编号", example = "20736")
    private Long userId;

    @Schema(description = "用户姓名", example = "芋艿")
    private String userName;

    @Schema(description = "用户手机号")
    private String userPhone;

    @Schema(description = "地址")
    private String operateAddress;

    @Schema(description = "经度")
    private String operateLng;

    @Schema(description = "纬度")
    private String operateLat;

    @Schema(description = "操作状态")
    private String operateStatus;

    @Schema(description = "操作结果")
    private String operateResult;

    @Schema(description = "备注", example = "你猜")
    private String remark;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

    @Schema(description = "删除时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] deletedTime;

}