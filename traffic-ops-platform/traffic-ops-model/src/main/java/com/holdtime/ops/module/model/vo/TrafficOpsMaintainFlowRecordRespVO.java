package com.holdtime.ops.module.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 设备维保全流程 Response VO")
@Data
@ExcelIgnoreUnannotated
public class TrafficOpsMaintainFlowRecordRespVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "22969")
    @ExcelProperty("主键")
    private String id;

    @Schema(description = "维保主键", example = "25983")
    @ExcelProperty("维保主键")
    private String maintenanceId;

    @Schema(description = "流程类型：1-上报；2-派单；3-接单；4-维修；5-确认；6-驳回；7-取消", example = "2")
    @ExcelProperty("流程类型：1-上报；2-派单；3-接单；4-维修；5-确认；6-驳回；7-取消")
    private String flowType;

    @Schema(description = "注册编号", example = "20265")
    @ExcelProperty("注册编号")
    private String registerId;

    @Schema(description = "用户编号", example = "20736")
    @ExcelProperty("用户编号")
    private Long userId;

    @Schema(description = "用户姓名", example = "芋艿")
    @ExcelProperty("用户姓名")
    private String userName;

    @Schema(description = "用户手机号")
    @ExcelProperty("用户手机号")
    private String userPhone;

    @Schema(description = "地址")
    @ExcelProperty("地址")
    private String operateAddress;

    @Schema(description = "经度")
    @ExcelProperty("经度")
    private String operateLng;

    @Schema(description = "纬度")
    @ExcelProperty("纬度")
    private String operateLat;

    @Schema(description = "操作状态")
    private String operateStatus;

    @Schema(description = "操作结果")
    private String operateResult;

    @Schema(description = "备注", example = "你猜")
    @ExcelProperty("备注")
    private String remark;

    @Schema(description = "创建时间")
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @Schema(description = "删除时间")
    @ExcelProperty("删除时间")
    private LocalDateTime deletedTime;

}