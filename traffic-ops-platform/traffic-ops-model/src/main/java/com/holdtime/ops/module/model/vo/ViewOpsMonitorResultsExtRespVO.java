package com.holdtime.ops.module.model.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Schema(description = "管理后台 - VIEW Response VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ExcelIgnoreUnannotated
public class ViewOpsMonitorResultsExtRespVO extends ViewOpsMonitorResultsRespVO {

    @Schema(description = "杆件编号")
    @ExcelProperty(value = "道路名称", index = 1)
    private String memberName;

    @Schema(description = "状态")
    @ExcelProperty(value = "状态", index = 3)
    private String statusName;

    @Schema(description = "状态变化描述")
    private String statusChangeDesc;

}