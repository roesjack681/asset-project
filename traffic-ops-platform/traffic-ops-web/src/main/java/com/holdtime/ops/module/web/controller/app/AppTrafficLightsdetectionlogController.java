package com.holdtime.ops.module.web.controller.app;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import com.holdtime.ops.module.model.vo.TrafficLightsdetectionlogSaveReqVO;
import com.holdtime.ops.module.service.TrafficLightsdetectionlogExtService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import javax.validation.Valid;
import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

@Tag(name = "管理后台 - 信号灯检测信息")
//@RestController
@RequestMapping("/traffic/lightsdetectionlog")
@Validated
public class AppTrafficLightsdetectionlogController {

    @Resource
    private TrafficLightsdetectionlogExtService lightsdetectionlogExtService;

    @PostMapping("/syncAbnormalSignalLight")
    @Operation(summary = "同步信号灯检测信息")
    public CommonResult<Long> createLightsdetectionlog(@Valid @RequestBody TrafficLightsdetectionlogSaveReqVO createReqVO) {
        return success(lightsdetectionlogExtService.createLightsdetectionlog(createReqVO));
    }


}