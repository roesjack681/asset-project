package com.holdtime.ops.module.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;


@Schema(description = "管理后台 - 维修排名")
@Data
public class TrafficOpsMemeberCameraDTO {
    @Schema(description = "杆件编号")
    private String memberBh;

    @Schema(description = "杆件名称")
    private String memberName;

    @Schema(description = "结果数量")
    private Integer monitorResultDTOCnt;

//    @Schema(description = "检测结果")
//    List<TrafficOpsMonitorResultDTO> monitorResultDTOList;
    TrafficOpsMonitorResultDTO monitorResultDTO;
}