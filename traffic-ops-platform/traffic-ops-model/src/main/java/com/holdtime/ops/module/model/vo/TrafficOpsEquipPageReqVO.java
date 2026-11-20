package com.holdtime.ops.module.model.vo;

import cn.iocoder.yudao.framework.common.pojo.PageParamNoLimit;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Schema(description = "资产设备分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class TrafficOpsEquipPageReqVO extends PageParamNoLimit {

    @Schema(description = "设备类型:电子警察；球机；卡口；枪机", example = "电子警察")
    private String djType;

    @Schema(description = "方向")
    private String direction;
}