package com.holdtime.ops.module.model.vo;

import lombok.*;

import java.time.LocalDate;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY;


@Schema(description = "管理后台 - 违法抓拍设备每日情况分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ViewVioEquipmentDailySituationPageReqVO extends PageParam {

    @Schema(description = "设备编号")
    private String sbbh;

    @Schema(description = "卡口编号")
    private String kkbh;

    @Schema(description = "IP地址")
    private String ipAddr;

    @Schema(description = "违法时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY)
    private LocalDate wfsj;

    @Schema(description = "违法时间")
    private LocalDateTime[] wfsjs;

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