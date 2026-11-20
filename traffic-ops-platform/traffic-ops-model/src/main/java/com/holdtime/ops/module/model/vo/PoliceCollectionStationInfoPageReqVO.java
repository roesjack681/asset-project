package com.holdtime.ops.module.model.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 警用设备采集站信息分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class PoliceCollectionStationInfoPageReqVO extends PageParam {

    @Schema(description = "设备编号/资产编号")
    private String deviceCode;

    @Schema(description = "机器名", example = "芋艿")
    private String machineName;

    @Schema(description = "部门", example = "22553")
    private String deptId;

    @Schema(description = "设备厂商")
    private String vendor;

    @Schema(description = "CPU型号")
    private String cpuModel;

    @Schema(description = "内存信息")
    private String memoryInfo;

    @Schema(description = "硬盘信息")
    private String diskInfo;

    @Schema(description = "操作系统")
    private String osVersion;

    @Schema(description = "软件版本")
    private String softwareVersion;

    @Schema(description = "MAC地址")
    private String macAddr;

    @Schema(description = "IP地址")
    private String ipAddr;

    @Schema(description = "设备位置")
    private String location;

    @Schema(description = "设备状态 1正常 2维修 3停用 4报废", example = "1")
    private String status;

    @Schema(description = "备注", example = "你说的对")
    private String remark;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}