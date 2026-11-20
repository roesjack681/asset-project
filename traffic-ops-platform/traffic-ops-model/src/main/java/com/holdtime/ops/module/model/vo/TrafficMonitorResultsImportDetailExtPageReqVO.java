package com.holdtime.ops.module.model.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;


@Schema(description = "管理后台 - 设备监测结果导入信息分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class TrafficMonitorResultsImportDetailExtPageReqVO extends TrafficMonitorResultsImportDetailPageReqVO {
    @Schema(description = "卡口编号")
    private String kkbh;

    @Schema(description = "标签列表")
    private List<String> equLableList;

    @Schema(description = "电警类型", example = "2")
    private String djType;

    @Schema(description = "检测情况枚举")
    @ExcelProperty("检测情况枚举")
    private List<String> monitorSituationList;

    @Schema(description = "是否在保")
    private String underWarranty;
}