package com.holdtime.ops.module.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDate;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 违法抓拍设备每日情况新增/修改 Request VO")
@Data
public class ViewVioEquipmentDailySituationSaveReqVO {

    @Schema(description = "设备编号")
    private String sbbh;

    @Schema(description = "卡口编号")
    private String kkbh;

    @Schema(description = "IP地址")
    private String ipAddr;

    @Schema(description = "违法时间")
    private LocalDate wfsj;

    @Schema(description = "待审核违法数量")
    private BigDecimal dswfCnt;

    @Schema(description = "有效违法数量")
    private BigDecimal yxwfCnt;

    @Schema(description = "无效违法数量")
    private BigDecimal wxwfCnt;

    @Schema(description = "检测时间")
    private LocalDateTime jcsj;

    @Schema(description = "数据可用率")
    private BigDecimal kyl;

}