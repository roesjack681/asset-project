package com.holdtime.ops.module.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 信控交叉口信息 Response VO")
@Data
@ExcelIgnoreUnannotated
public class TrafficTslJunctionInfoRespVO {

    @Schema(description = "交叉口ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "66")
    @ExcelProperty("交叉口ID")
    private String id;

    @Schema(description = "所属区域ID", example = "30719")
    @ExcelProperty("所属区域ID")
    private String regionId;

    @Schema(description = "交叉口名称", example = "李四")
    @ExcelProperty("交叉口名称")
    private String junctionName;

    @Schema(description = "控制ID", example = "2313")
    @ExcelProperty("控制ID")
    private String controllerId;

    @Schema(description = "控制名称", example = "芋艿")
    @ExcelProperty("控制名称")
    private String controllerName;

    @Schema(description = "协议类型")
    @ExcelProperty("协议类型")
    private String tcpudp;

    @Schema(description = "协议编号")
    @ExcelProperty("协议编号")
    private String protocol;

    @Schema(description = "创建时间")
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @Schema(description = "删除时间")
    @ExcelProperty("删除时间")
    private LocalDateTime deletedTime;

}