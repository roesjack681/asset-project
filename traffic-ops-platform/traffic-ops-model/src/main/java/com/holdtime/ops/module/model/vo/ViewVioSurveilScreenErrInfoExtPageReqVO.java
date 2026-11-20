package com.holdtime.ops.module.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;

@Schema(description = "管理后台 - 集指无效违法记录分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ViewVioSurveilScreenErrInfoExtPageReqVO extends ViewVioSurveilScreenErrInfoPageReqVO {

    @Schema(description = "是否一致")
    private String isConsistentAuditReason;

    @Schema(description = "操作类型：1-待审核；2-已审核")
    private String optType;

    @Schema(description = "行政区域")
    private List<String> unitList;

    @Schema(description = "是否复审")
    private String isReview;

}