package com.holdtime.ops.module.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Schema(description = "管理后台 - 领用记录新增/修改 Request VO")
@Data
public class TrafficOpsIssueRecordExtSaveReqVO extends TrafficOpsIssueRecordSaveReqVO{

    @Schema(description = "领用详情")
    private List<TrafficOpsIssueDetailSaveReqVO> issueDetailList;

}