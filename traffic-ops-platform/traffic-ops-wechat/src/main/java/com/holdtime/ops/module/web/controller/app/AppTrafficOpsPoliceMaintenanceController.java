package com.holdtime.ops.module.web.controller.app;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import com.holdtime.ops.module.model.vo.*;
import com.holdtime.ops.module.service.TrafficOpsPoliceMaintenanceExtService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

@Tag(name = "用户 APP - 警用装备报修信息")
@RestController
@RequestMapping("/traffic/ops-police-maintenance")
@Validated
public class AppTrafficOpsPoliceMaintenanceController {
    @Resource
    private TrafficOpsPoliceMaintenanceExtService opsPoliceMaintenanceExtService;

    @PostMapping("/save")
    @Operation(summary = "保存、修改警用装备报修信息")
    public CommonResult<String> saveOpsPoliceMaintenance(@Valid @RequestBody TrafficOpsPoliceMaintenanceExtSaveReqVO saveReqVO) {
        return success(opsPoliceMaintenanceExtService.saveOpsPoliceMaintenance(saveReqVO));
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除警用装备报修信息")
    @Parameter(name = "id", description = "编号", required = true)
    public CommonResult<Boolean> deleteOpsPoliceMaintenance(@RequestParam("id") String id) {
        opsPoliceMaintenanceExtService.deleteOpsPoliceMaintenance(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得警用装备报修信息")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    public CommonResult<TrafficOpsPoliceMaintenanceExtRespVO> getOpsPoliceMaintenance(@RequestParam("id") String id) {
        return success(opsPoliceMaintenanceExtService.getOpsPoliceMaintenance(id));
    }

    @PostMapping("/page")
    @Operation(summary = "获得警用装备报修信息分页（根据状态区分不同tab：8-待审核，4-维修中，5-已完成，9-驳回）")
    public CommonResult<PageResult<TrafficOpsPoliceMaintenanceExtRespVO>> getOpsPoliceMaintenancePage(@Valid TrafficOpsPoliceMaintenancePageReqVO pageReqVO) {
        return success(opsPoliceMaintenanceExtService.getOpsPoliceMaintenancePage(pageReqVO));
    }

    @PostMapping("/audit")
    @Operation(summary = "审核警用装备报修信息")
    public CommonResult<Boolean> auditOpsPoliceMaintenance(@Valid @RequestBody TrafficOpsPoliceMaintenanceSaveReqVO saveReqVO) {
        opsPoliceMaintenanceExtService.auditOpsPoliceMaintenance(saveReqVO);
        return success(true);
    }

    @PostMapping("/saveRepariResult")
    @Operation(summary = "保存报修结果")
    public CommonResult<Boolean> saveOpsPoliceMaintenance(@Valid @RequestBody TrafficOpsPoliceMaintenanceSaveReqVO saveReqVO) {
        opsPoliceMaintenanceExtService.saveOpsPoliceMaintenanceResult(saveReqVO);
        return success(true);
    }
}