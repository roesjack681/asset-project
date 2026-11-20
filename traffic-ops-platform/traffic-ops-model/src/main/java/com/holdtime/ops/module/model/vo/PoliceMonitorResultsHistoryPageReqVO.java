package com.holdtime.ops.module.model.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 公安设备监测结果历史分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class PoliceMonitorResultsHistoryPageReqVO extends PageParam {

    @Schema(description = "检测日期")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] monitorDate;

    @Schema(description = "设备类型", example = "2")
    private String equType;

    @Schema(description = "设备id", example = "27457")
    private String equId;

    @Schema(description = "IP地址")
    private String ipAddr;

    @Schema(description = "设备名称", example = "赵六")
    private String equName;

    @Schema(description = "杆件编号")
    private String memberBh;

    @Schema(description = "方向")
    private String direction;

    @Schema(description = "检测事件", example = "1")
    private String eventType;

    @Schema(description = "检测时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] occurrenceTime;

    @Schema(description = "状态", example = "1")
    private String status;

    @Schema(description = "检测结果图片")
    private String resultImg;

    @Schema(description = "是否为工单")
    private String isRepairOrder;

    @Schema(description = "维保主键", example = "25527")
    private String maintenanceId;

    @Schema(description = "维护人员编号", example = "3039")
    private String maintenanceUserId;

    @Schema(description = "维护人员")
    private String maintenancePersonnel;

    @Schema(description = "维护人员手机号")
    private String maintenancePhone;

    @Schema(description = "设备情况")
    private String equSituation;

    @Schema(description = "设备情况描述")
    private String equDesc;

    @Schema(description = "设备情况证据", example = "31329")
    private String evidenceFileId;

    @Schema(description = "备注时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] remarkDate;

    @Schema(description = "变更情况：0-未变化；1-已修复；2-未修复")
    private String changeSituation;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

    @Schema(description = "删除时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] deletedTime;

    @Schema(description = "更新时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] updateTime;

    @Schema(description = "标签列表")
    private List<String> equLableList;

    @Schema(description = "设备情况")
    private List<String> equSituationList;

    @Schema(description = "所属行政区域")
    private List<String> unitList;

}