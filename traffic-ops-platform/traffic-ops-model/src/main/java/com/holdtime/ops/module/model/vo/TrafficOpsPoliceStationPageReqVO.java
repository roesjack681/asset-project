package com.holdtime.ops.module.model.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 警用装备采集站分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class TrafficOpsPoliceStationPageReqVO extends PageParam {

    @Schema(description = "使用部门编号")
    private String useDeptBh;

    @Schema(description = "使用部门")
    private String useDept;

    @Schema(description = "IP地址")
    private String ipAddr;

    @Schema(description = "存放地址")
    private String address;

    @Schema(description = "设备编号")
    private String equCode;

    @Schema(description = "设备厂商")
    private String manufacturer;

    @Schema(description = "机器名", example = "李四")
    private String pcName;

    @Schema(description = "硬盘")
    private String hardDisk;

    @Schema(description = "处理器")
    private String processor;

    @Schema(description = "操作系统")
    private String operatingSystem;

    @Schema(description = "MAC地址")
    private String mac;

    @Schema(description = "内存")
    private String memory;

    @Schema(description = "软件版本")
    private String softVersion;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

    @Schema(description = "删除时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] deletedTime;

}