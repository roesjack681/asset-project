package com.holdtime.ops.module.model.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 警用装备资产信息分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class TrafficOpsPoliceEquipmentPageReqVO extends PageParam {

    @Schema(description = "设备名称", example = "张三")
    private String equName;

    @Schema(description = "类型", example = "2")
    private String equType;

    @Schema(description = "加密卡")
    private String encryptionCard;

    @Schema(description = "PSN")
    private String psn;

    @Schema(description = "品牌")
    private String brand;

    @Schema(description = "型号")
    private String model;

    @Schema(description = "生产厂家")
    private String manufacturer;

    @Schema(description = "电池")
    private String battery;

    @Schema(description = "呼号")
    private String callSign;

    @Schema(description = "供货厂家")
    private String supplier;

    @Schema(description = "版本号")
    private String version;

    @Schema(description = "备注", example = "你说的对")
    private String remark;

    @Schema(description = "录入人编号")
    private String recordBh;

    @Schema(description = "录入人")
    private String recorder;

    @Schema(description = "录入部门编号")
    private String recordDeptBh;

    @Schema(description = "录入部门")
    private String recordDept;

    @Schema(description = "使用部门编号")
    private String useDeptBh;

    @Schema(description = "使用部门")
    private String useDept;

    @Schema(description = "使用人编号")
    private String userBh;

    @Schema(description = "使用人", example = "李四")
    private String userName;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

    @Schema(description = "删除时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] deletedTime;



}