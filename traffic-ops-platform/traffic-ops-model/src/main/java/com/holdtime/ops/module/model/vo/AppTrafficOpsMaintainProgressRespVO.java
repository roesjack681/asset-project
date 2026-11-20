package com.holdtime.ops.module.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "用户 APP - 设备维保进度 Response VO")
@Data
@ExcelIgnoreUnannotated
public class AppTrafficOpsMaintainProgressRespVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "23990")
    @ExcelProperty("主键")
    private String id;

    @Schema(description = "所属单位：1-交警；2-公安", example = "11295")
    private String affiliatedUnit;

    @Schema(description = "维保主键", example = "11295")
    @ExcelProperty("维保主键")
    private String maintenanceId;

    @Schema(description = "维护情况")
    @ExcelProperty("维护情况")
    private String maintenance;

    @Schema(description = "维护人员编号", example = "16913")
    @ExcelProperty("维护人员编号")
    private String maintenanceUserId;

    @Schema(description = "维护人员")
    @ExcelProperty("维护人员")
    private String maintenancePersonnel;

    @Schema(description = "维护人员手机号")
    private String maintenancePhone;

    @Schema(description = "维护时间")
    @ExcelProperty("维护时间")
    private LocalDateTime maintenanceDate;

    @Schema(description = "维护地址")
    @ExcelProperty("维护地址")
    private String maintenanceAdress;

    @Schema(description = "维护经度")
    @ExcelProperty("维护经度")
    private String maintenanceLng;

    @Schema(description = "维护纬度")
    @ExcelProperty("维护纬度")
    private String maintenanceLat;

    @Schema(description = "是否换新")
    private String isChange;

    @Schema(description = "创建时间")
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @Schema(description = "删除时间")
    @ExcelProperty("删除时间")
    private LocalDateTime deletedTime;

}