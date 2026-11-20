package com.holdtime.ops.module.model.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Schema(description = "管理后台 - 设备维保无法修复信息 Response VO")
@Data
@ExcelIgnoreUnannotated
public class TrafficOpsMaintenanceUnfixableInfoExtRespVO extends TrafficOpsMaintenanceUnfixableInfoRespVO {

    @ExcelProperty("跟进记录")
    private List<TrafficOpsMaintenanceUnfixableFollowRespVO> followRespVOList;

}