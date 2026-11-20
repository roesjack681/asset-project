package com.holdtime.ops.module.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Schema(description = "管理后台 - 警用装备报修信息新增/修改 Request VO")
@Data
public class TrafficOpsPoliceMaintenanceExtSaveReqVO extends TrafficOpsPoliceMaintenanceSaveReqVO {

    @Schema(description = "报修详情")
    private List<TrafficOpsPoliceMaintenanceDetailSaveReqVO> detailVOList;

}