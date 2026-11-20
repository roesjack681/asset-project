package com.holdtime.ops.module.model.vo;

import cn.iocoder.yudao.framework.excel.core.annotations.DictFormat;
import cn.iocoder.yudao.framework.excel.core.convert.DictConvert;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Schema(description = "管理后台 - 集指设备检测分析 Response VO")
@Data
@ExcelIgnoreUnannotated
public class TrafficVioEquipmentMonitorAnalysisExtRespVO extends TrafficVioEquipmentMonitorAnalysisRespVO {
    @Schema(description = "IP地址")
    private String ipAddr;

    @Schema(description = "杆件编号")
    private String memberName;

    @Schema(description = "是否在保")
    private String underWarranty;

    @Schema(description = "电警类型")
    @ExcelProperty(value = "电警类型", index = 3)
    private String djType;

    @ExcelProperty(value = "设备标签", index = 5)
    private String equLabel;

    @ExcelProperty(value = "所属区域", converter = DictConvert.class)
    @DictFormat("geo_info")
    private String unit;

}