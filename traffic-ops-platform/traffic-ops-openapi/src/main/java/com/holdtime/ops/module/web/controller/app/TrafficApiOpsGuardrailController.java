package com.holdtime.ops.module.web.controller.app;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import com.holdtime.ops.module.aop.annotation.ApiAuthCheck;
import com.holdtime.ops.module.entity.TrafficOpsGuardrailDO;
import com.holdtime.ops.module.model.vo.OpsApiGuardrailPageReqVO;
import com.holdtime.ops.module.model.vo.TrafficOpsGuardrailPageReqVO;
import com.holdtime.ops.module.model.vo.TrafficOpsGuardrailRespVO;
import com.holdtime.ops.module.service.TrafficOpsGuardrailService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import javax.validation.Valid;
import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

@Tag(name = "第三方 - 护栏")
@RestController
@RequestMapping("/traffic-api")
@Validated
public class TrafficApiOpsGuardrailController {

    @Resource
    private TrafficOpsGuardrailService opsGuardrailService;

    @ApiAuthCheck
    @GetMapping("/guardrail-page")
    @Operation(summary = "获得护栏分页")
    public CommonResult<PageResult<TrafficOpsGuardrailRespVO>> getOpsGuardrailPage(@Valid OpsApiGuardrailPageReqVO pageReqVO) {
        TrafficOpsGuardrailPageReqVO reqVO = BeanUtils.toBean(pageReqVO, TrafficOpsGuardrailPageReqVO.class);
        reqVO.setAdress(pageReqVO.getAddress());
        PageResult<TrafficOpsGuardrailDO> pageResult = opsGuardrailService.getOpsGuardrailPage(reqVO);
        return success(BeanUtils.toBean(pageResult, TrafficOpsGuardrailRespVO.class));
    }


}