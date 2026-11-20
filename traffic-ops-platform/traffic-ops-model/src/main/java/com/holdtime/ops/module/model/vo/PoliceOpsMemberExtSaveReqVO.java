package com.holdtime.ops.module.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Schema(description = "管理后台 - 杆件新增/修改 Request VO")
@Data
public class PoliceOpsMemberExtSaveReqVO extends PoliceOpsMemberSaveReqVO {

    @Schema(description = "创建人", example = "1")
    private String creator;

    @Schema(description = "电警绑定信息", example = "1")
    private List<String> electricPoliceIdList;

    @Schema(description = "新增电警信息", example = "1")
    private List<OpsElectricPoliceSaveReqVO> instElectricPoliceInfoList;

    @Schema(description = "拆除电警Id", example = "1")
    private List<String> removeElectricPoliceIdList;

}