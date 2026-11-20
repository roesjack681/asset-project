package com.holdtime.ops.module.web.controller.app;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import com.holdtime.ops.module.model.vo.TrafficCamdetectionlogSaveReqVO;
import com.holdtime.ops.module.service.TrafficCamdetectionlogExtService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import javax.validation.Valid;
import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

@Tag(name = "管理后台 - 设备状态检测")
//@RestController
@RequestMapping("/traffic/camdetectionlog")
@Validated
public class AppTrafficCamdetectionlogController {

    @Resource
    private TrafficCamdetectionlogExtService camdetectionlogExtService;

    @PostMapping("/syncHandleEPoliceFault")
    @Operation(summary = "同步设备状态检测")
    public CommonResult<Long> createCamdetectionlog(@Valid @RequestBody TrafficCamdetectionlogSaveReqVO createReqVO) {
        return success(camdetectionlogExtService.createCamdetectionlog(createReqVO));
    }

}