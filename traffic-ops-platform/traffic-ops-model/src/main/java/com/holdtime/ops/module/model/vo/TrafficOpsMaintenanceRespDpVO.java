package com.holdtime.ops.module.model.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.util.List;

@Schema(description = "管理后台 - 设备维保信息 Response VO")
@Data
@ExcelIgnoreUnannotated
public class TrafficOpsMaintenanceRespDpVO {

    @Schema(description = "工单列表信息")
    private List<TrafficOpsMaintenanceRespDpListVO>  maintenanceList;

    @Schema(description = "待派单数")
    private String dpdCount;

    @Schema(description = "待接单数")
    private String djdCount;

}