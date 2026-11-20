package com.holdtime.ops.module.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Schema(description = "用户 APP - 设备维保进度新增/修改 Request VO")
@Data
public class TrafficOpsMaintainProgressSaveReqVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "23990")
    private String id;

    @Schema(description = "所属单位：1-交警；2-公安", example = "11295")
    private String affiliatedUnit;

    @Schema(description = "维保主键", example = "11295")
    private String maintenanceId;

    @Schema(description = "设备类型", example = "11295")
    private String equType;

    @Schema(description = "设备ID", example = "11295")
    private String equId;

    @Schema(description = "故障分类", example = "11295")
    private String faultType;

    @Schema(description = "维护情况")
    private String maintenance;

    @Schema(description = "维护人员编号", example = "16913")
    private String maintenanceUserId;

    @Schema(description = "维护人员")
    private String maintenancePersonnel;

    @Schema(description = "维护人员手机号")
    private String maintenancePhone;

    @Schema(description = "维护时间")
    private LocalDateTime maintenanceDate;

    @Schema(description = "维护地址")
    private String maintenanceAdress;

    @Schema(description = "维护经度")
    private String maintenanceLng;

    @Schema(description = "维护纬度")
    private String maintenanceLat;

    @Schema(description = "是否完成")
    private String isFinish;

    @Schema(description = "是否换新")
    private String isChange;

    @Schema(description = "取电位置")
    private String powerSupplyLocation;

    @Schema(description = "预计修复难度")
    private String projectedRepair;

    @Schema(description = "实际修复难度")
    private String actualRepair;

    private List<TrafficOpsMaintenanceFileDTO> fileDTOList;

    @Schema(description = "设备维保耗材新增")
    private List<AppTrafficOpsMaintainCmSaveReqVO> opsMaintainCmSaveReqVOList;

    @Schema(description = "维修成员信息")
    private List<TrafficOpsMaintenanceTeamSaveReqVO> opsMaintainTeamSaveReqVOList;

    @Schema(description = "护栏维修信息")
    private TrafficOpsMaintenanceGuardrailExtSaveReqVO opsMaintenanceGuardrailVO;

}