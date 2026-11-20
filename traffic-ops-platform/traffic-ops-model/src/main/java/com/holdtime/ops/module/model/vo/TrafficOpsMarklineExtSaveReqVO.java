package com.holdtime.ops.module.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Schema(description = "管理后台 - 标线新增/修改 Request VO")
@Data
public class TrafficOpsMarklineExtSaveReqVO extends TrafficOpsMarklineSaveReqVO {

    @Schema(description = "设备图片")
    private List<PublicOpsAttachInfoExtSaveReqVO> attachInfoList;

}