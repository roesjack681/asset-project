package com.holdtime.ops.module.web.controller.app;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.holdtime.ops.module.aop.annotation.ApiAuthCheck;
import com.holdtime.ops.module.entity.TrafficOpsMaintenanceDO;
import com.holdtime.ops.module.entity.mapper.TrafficOpsMaintenanceMapper;
import com.holdtime.ops.module.model.vo.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import javax.validation.Valid;
import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

@Tag(name = "第三方 - 设备维保信息")
@RestController
@RequestMapping("/traffic-api")
@Validated
public class TrafficApiOpsMaintenanceController {
    @Resource
    private TrafficOpsMaintenanceMapper opsMaintenanceMapper;

    @ApiAuthCheck
    @GetMapping("/maintenance-page")
    @Operation(summary = "根据设备主键获取设备维修信息")
    public CommonResult<PageResult<TrafficOpsMaintenanceRespVO>> getOpsMaintenancePage(@Valid ApiEquPageReqVO pageReqVO) {
        LambdaQueryWrapperX<TrafficOpsMaintenanceDO> lambdaQueryWrapperX = new LambdaQueryWrapperX<TrafficOpsMaintenanceDO>()
                .eqIfPresent(TrafficOpsMaintenanceDO::getEquId, pageReqVO.getEquId())
                .orderByDesc(TrafficOpsMaintenanceDO::getCreateTime);

        PageResult<TrafficOpsMaintenanceRespVO> pageResult = BeanUtils.toBean( opsMaintenanceMapper.selectPage(pageReqVO, lambdaQueryWrapperX), TrafficOpsMaintenanceRespVO.class);
        return success(pageResult);
    }



}