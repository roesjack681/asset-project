package com.holdtime.ops.module.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 警用装备采集站新增/修改 Request VO")
@Data
public class TrafficOpsPoliceStationSaveReqVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "22320")
    private String id;

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

    @Schema(description = "删除时间")
    private LocalDateTime deletedTime;

}