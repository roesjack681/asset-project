package com.holdtime.ops.module.model.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "资产设备 Response VO")
@Data
@ExcelIgnoreUnannotated
public class TrafficOpsEquipRespVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "27930")
    @ExcelProperty("主键")
    private String id;

    @Schema(description = "路口名称")
    @ExcelProperty("路口名称")
    private String intersectionName;

    @Schema(description = "方向")
    @ExcelProperty("方向")
    private String direction;

    @Schema(description = "电警类型")
    @ExcelProperty("电警类型")
    private String djType;

    @Schema(description = "IP地址")
    @ExcelProperty("IP地址")
    private String ipAddr;

    @Schema(description = "用户名")
    @ExcelProperty("用户名")
    private String userName;

    @Schema(description = "密码")
    @ExcelProperty("密码")
    private String password;
}