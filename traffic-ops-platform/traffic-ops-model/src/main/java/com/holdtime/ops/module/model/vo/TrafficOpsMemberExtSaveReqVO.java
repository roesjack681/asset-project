package com.holdtime.ops.module.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Schema(description = "管理后台 - 杆件新增/修改 Request VO")
@Data
public class TrafficOpsMemberExtSaveReqVO extends TrafficOpsMemberSaveReqVO {
    @Schema(description = "附属设备", example = "1")
    private List<TrafficOpsAccessoryEquipmentSaveReqVO> accessoryEquipmentList;

    @Schema(description = "创建人", example = "1")
    private String creator;

    @Schema(description = "电警信息", example = "1")
    private List<TrafficOpsElectricPoliceRespVO> electricPoliceVOList;

}