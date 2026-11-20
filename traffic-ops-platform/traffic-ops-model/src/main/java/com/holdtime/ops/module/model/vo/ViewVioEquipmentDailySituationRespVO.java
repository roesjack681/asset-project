package com.holdtime.ops.module.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDate;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 违法抓拍设备每日情况 Response VO")
@Data
@ExcelIgnoreUnannotated
public class ViewVioEquipmentDailySituationRespVO {

    @Schema(description = "设备编号")
    @ExcelProperty("设备编号")
    private String sbbh;

    @Schema(description = "卡口编号")
    @ExcelProperty("卡口编号")
    private String kkbh;

    @Schema(description = "IP地址")
    @ExcelProperty("IP地址")
    private String ipAddr;

    @Schema(description = "违法时间")
    @ExcelProperty("违法时间")
    private LocalDate wfsj;

    @Schema(description = "待审核违法数量")
    @ExcelProperty("待审核违法数量")
    private BigDecimal dswfCnt;

    @Schema(description = "有效违法数量")
    @ExcelProperty("有效违法数量")
    private BigDecimal yxwfCnt;

    @Schema(description = "无效违法数量")
    @ExcelProperty("无效违法数量")
    private BigDecimal wxwfCnt;

    @Schema(description = "检测时间")
    @ExcelProperty("检测时间")
    private LocalDateTime jcsj;

    @Schema(description = "数据可用率")
    @ExcelProperty("数据可用率")
    private BigDecimal kyl;

}