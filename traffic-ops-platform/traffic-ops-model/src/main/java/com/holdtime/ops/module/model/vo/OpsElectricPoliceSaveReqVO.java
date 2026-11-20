package com.holdtime.ops.module.model.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "管理后台 - 电警 Response VO")
@Data
@ExcelIgnoreUnannotated
public class OpsElectricPoliceSaveReqVO {

    @Schema(description = "设备名称", example = "张三")
    @ExcelProperty("设备名称")
    private String equName;

    @Schema(description = "安装位置")
    @ExcelProperty("安装位置")
    private String adress;

    @Schema(description = "IP地址")
    @ExcelProperty("IP地址")
    private String ipAddr;

    @Schema(description = "方向")
    @ExcelProperty("方向")
    private String direction;

    @Schema(description = "电警类型", example = "1")
    @ExcelProperty("电警类型")
    private String djType;

}