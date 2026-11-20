package com.holdtime.ops.module.model.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "大数据——基础设施 Response VO")
@Data
@ExcelIgnoreUnannotated
public class PoliceOpsBigDataBaseRespVO {
    @Schema(description = "电警")
    private String electricPoliceNum;

    @Schema(description = "电警-正常")
    private String electricPoliceZNum;

    @Schema(description = "电警-维修中")
    private String electricPoliceWNum;

    @Schema(description = "电警-故障")
    private String electricPoliceGNum;

    @Schema(description = "杆件")
    private String memberNum;

    @Schema(description = "杆件-正常")
    private String memberZNum;

    @Schema(description = "杆件-维修中")
    private String memberWNum;

    @Schema(description = "杆件-故障")
    private String memberGNum;

    @Schema(description = "设备总数")
    private String equNum;

    @Schema(description = "电警占比")
    private String electricPoliceProp;

    @Schema(description = "杆件占比")
    private String memberProp;
}