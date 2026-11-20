package com.holdtime.ops.module.web.controller.app;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.holdtime.ops.module.entity.TrafficOpsElectricPoliceDO;
import com.holdtime.ops.module.entity.mapper.TrafficOpsElectricPoliceMapper;
import com.holdtime.ops.module.model.vo.*;
import com.holdtime.ops.module.service.TrafficMonitorResultsExtService;
import com.holdtime.ops.module.service.TrafficDjMonitorStatisticsExtService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

import static cn.iocoder.yudao.framework.common.pojo.CommonResult.error;
import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

@Tag(name = "设备监测结果")
//@RestController
@RequestMapping("/traffic/monitor-results")
@Validated
public class AppTrafficMonitorResultsController {

    @Resource
    private TrafficMonitorResultsExtService monitorResultsExtService;
    @Resource
    private TrafficDjMonitorStatisticsExtService monitorStatisticsExtService;
    @Resource
    private TrafficOpsElectricPoliceMapper opsElectricPoliceMapper;

    @PostMapping("/syncHandleMonitorResults")
    @Operation(summary = "同步设备监测结果")
    public CommonResult<String> createMonitorResults(@Valid @RequestBody TrafficMonitorResultsExtSaveReqVO createReqVO) {
        return success(monitorResultsExtService.createMonitorResults(createReqVO));
    }

    @PostMapping("/syncElectricPoliceMonitorStatistics")
    @Operation(summary = "同步电警监测统计结果")
    public CommonResult<String> createMonitorResults(@Valid @RequestBody TrafficDjMonitorStatisticsExtSaveReqVO createReqVO) {
        return success(monitorStatisticsExtService.syncElectricPoliceMonitorStatistics(createReqVO));
    }

    @GetMapping("/pullEquipInfo")
    @Operation(summary = "分页拉取资产设备信息")
    public CommonResult<PageResult<TrafficOpsEquipRespVO>> pullEquipInfo(@Valid TrafficOpsEquipPageReqVO pageReqVO) {
        LambdaQueryWrapperX<TrafficOpsElectricPoliceDO> opsDOLambdaQueryWrapperX = (LambdaQueryWrapperX<TrafficOpsElectricPoliceDO>) new LambdaQueryWrapperX<TrafficOpsElectricPoliceDO>()
                .eqIfPresent(TrafficOpsElectricPoliceDO::getDjType, pageReqVO.getDjType())
                .eqIfPresent(TrafficOpsElectricPoliceDO::getDirection, pageReqVO.getDirection())
                .like(TrafficOpsElectricPoliceDO::getEquLable,"交警")
                .isNotNull(TrafficOpsElectricPoliceDO::getMemberBh)
                .orderByAsc(TrafficOpsElectricPoliceDO::getId);
        PageResult<TrafficOpsElectricPoliceDO> pageResult = opsElectricPoliceMapper.selectPage(pageReqVO, opsDOLambdaQueryWrapperX);
        return success(BeanUtils.toBean(pageResult, TrafficOpsEquipRespVO.class));
    }



}