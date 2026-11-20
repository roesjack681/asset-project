package com.holdtime.ops.module.model.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import java.math.BigDecimal;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 护栏保险信息分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class TrafficOpsGuardrailInsurancePageReqVO extends PageParam {

    @Schema(description = "护栏维护主键", example = "15916")
    private String manceGuardrailId;

    @Schema(description = "车牌号")
    private String vehicleNum;

    @Schema(description = "保险公司名称")
    private String insuranceCompany;

    @Schema(description = "保单编号")
    private String insurancePolicy;

    @Schema(description = "赔付金额")
    private BigDecimal payMoney;

    @Schema(description = "赔付护栏产品编号", example = "19053")
    private String productId;

    @Schema(description = "赔付片数")
    private Integer pieces;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

    @Schema(description = "删除时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] deletedTime;

}