package com.holdtime.ops.module.model.vo;

import cn.iocoder.yudao.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Schema(description = "接口管理 - 护栏分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class OpsApiGuardrailPageReqVO extends PageParam {
    @Schema(description = "所在道路名称", example = "赵六")
    private String roadName;

    @Schema(description = "安装位置")
    private String address;
}