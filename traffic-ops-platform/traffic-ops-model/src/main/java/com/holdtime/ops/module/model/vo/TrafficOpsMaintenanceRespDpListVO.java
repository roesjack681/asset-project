package com.holdtime.ops.module.model.vo;

import cn.iocoder.yudao.framework.excel.core.annotations.DictFormat;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

@Schema(description = "管理后台 - 设备维保信息 Response VO")
@Data
@ExcelIgnoreUnannotated
public class TrafficOpsMaintenanceRespDpListVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "13818")
    private String id;

    @Schema(description = "所属单位：1-交警；2-公安", example = "11295")
    private String affiliatedUnit;

    @Schema(description = "工单号")
    private String orderNum;

    @Schema(description = "设备类型", example = "2")
    @DictFormat("equ_type") // TODO 代码优化：建议设置到对应的 DictTypeConstants 枚举类中
    private String equType;

    @Schema(description = "设备编号", example = "443")
    private String equId;

    @Schema(description = "存在问题")
    private String problem;

    @Schema(description = "故障分类", example = "1")
    private String faultType;

    @Schema(description = "故障时间")
    private Date faultDate;

    @Schema(description = "上报人员")
    private String reportPersonnel;

    @Schema(description = "上报人员编号", example = "24804")
    private String reportUserId;

    @Schema(description = "上报时间")
    private LocalDateTime reportTime;

    @Schema(description = "上报人员部门编号")
    private String reportDeptId;

    @Schema(description = "上报人员部门")
    private String reportDept;

    @Schema(description = "状态", example = "2")
    private String status;

}