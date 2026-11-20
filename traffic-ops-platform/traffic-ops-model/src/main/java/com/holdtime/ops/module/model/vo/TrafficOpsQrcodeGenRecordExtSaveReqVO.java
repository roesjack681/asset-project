package com.holdtime.ops.module.model.vo;

import com.holdtime.ops.module.model.dto.QrcodeDirectionCntDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Schema(description = "管理后台 - 资产二维码生成记录新增/修改 Request VO")
@Data
public class TrafficOpsQrcodeGenRecordExtSaveReqVO extends TrafficOpsQrcodeGenRecordSaveReqVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "16474")
    private String id;

    @Schema(description = "生成日期")
    private LocalDateTime genDate;

    @Schema(description = "道路ID", example = "12198")
    private String roadId;

    @Schema(description = "道路名称", example = "李四")
    private String roadName;

    @Schema(description = "资产类型", example = "1")
    private String opsType;

    @Schema(description = "方向和数量")
    private List<QrcodeDirectionCntDTO> directionCntList;

    @Schema(description = "删除时间")
    private LocalDateTime deletedTime;

}