package com.holdtime.ops.module.model.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Schema(description = "管理后台 - 警用装备报修信息 Response VO")
@Data
@ExcelIgnoreUnannotated
public class TrafficOpsPoliceMaintenanceExtRespVO extends TrafficOpsPoliceMaintenanceRespVO {

    @Schema(description = "报修详情")
    private List<TrafficOpsPoliceMaintenanceDetailExtRespVO> detailVOList;

}