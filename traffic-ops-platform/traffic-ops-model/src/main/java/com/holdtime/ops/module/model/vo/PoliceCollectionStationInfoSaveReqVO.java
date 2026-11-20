package com.holdtime.ops.module.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;
import java.util.*;

@Schema(description = "管理后台 - 警用设备采集站信息新增/修改 Request VO")
@Data
public class PoliceCollectionStationInfoSaveReqVO {

    @Schema(description = "主键ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "26118")
    private Long id;

    @Schema(description = "设备编号/资产编号", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "设备编号/资产编号不能为空")
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

}