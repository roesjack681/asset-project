package com.holdtime.ops.module.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 公安设备监测结果新增/修改 Request VO")
@Data
public class PoliceMonitorResultsSaveReqVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "1366")
    private String id;

    @Schema(description = "设备类型", example = "2")
    private String equType;

    @Schema(description = "设备id", example = "22166")
    private String equId;

    @Schema(description = "IP地址")
    private String ipAddr;

    @Schema(description = "设备名称", example = "李四")
    private String equName;

    @Schema(description = "杆件编号")
    private String memberBh;

    @Schema(description = "方向")
    private String direction;

    @Schema(description = "检测事件", example = "1")
    private String eventType;

    @Schema(description = "检测时间")
    private LocalDateTime occurrenceTime;

    @Schema(description = "状态", example = "2")
    private String status;

    @Schema(description = "检测结果图片")
    private String resultImg;

    @Schema(description = "是否为工单")
    private String isRepairOrder;

    @Schema(description = "维保主键", example = "15594")
    private String maintenanceId;

    @Schema(description = "维护人员编号", example = "19675")
    private String maintenanceUserId;

    @Schema(description = "维护人员")
    private String maintenancePersonnel;

    @Schema(description = "维护人员手机号")
    private String maintenancePhone;

    @Schema(description = "来源：1-接口；2-导入")
    private String source;

    @Schema(description = "删除时间")
    private LocalDateTime deletedTime;

    @Schema(description = "设备情况")
    private List<String> equSituationList;

    @Schema(description = "设备情况描述")
    private String equDesc;

    @Schema(description = "设备情况证据")
    private String evidenceFileId;

    @Schema(description = "备注时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] remarkDate;
}