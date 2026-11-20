package com.holdtime.ops.module.web.controller.admin;

import com.holdtime.ops.module.service.TrafficOpsPoliceMaintenanceExtService;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Operation;
import javax.validation.*;
import javax.servlet.http.*;
import java.util.*;
import java.io.IOException;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;
import cn.iocoder.yudao.framework.excel.core.util.ExcelUtils;
import com.holdtime.ops.module.model.vo.*;
import com.holdtime.ops.module.entity.TrafficOpsPoliceMaintenanceDO;
import com.holdtime.ops.module.service.TrafficOpsPoliceMaintenanceService;

@Tag(name = "管理后台 - 警用装备报修信息")
@RestController
@RequestMapping("/traffic/ops-police-maintenance")
@Validated
public class TrafficOpsPoliceMaintenanceController {

    @Resource
    private TrafficOpsPoliceMaintenanceService opsPoliceMaintenanceService;
    @Resource
    private TrafficOpsPoliceMaintenanceExtService opsPoliceMaintenanceExtService;

    @PostMapping("/create")
    @Operation(summary = "创建警用装备报修信息")
    @PreAuthorize("@ss.hasPermission('traffic:ops-police-maintenance:create')")
    public CommonResult<String> createOpsPoliceMaintenance(@Valid @RequestBody TrafficOpsPoliceMaintenanceExtSaveReqVO createReqVO) {
        return success(opsPoliceMaintenanceExtService.saveOpsPoliceMaintenance(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新警用装备报修信息")
    @PreAuthorize("@ss.hasPermission('traffic:ops-police-maintenance:update')")
    public CommonResult<Boolean> updateOpsPoliceMaintenance(@Valid @RequestBody TrafficOpsPoliceMaintenanceExtSaveReqVO updateReqVO) {
        opsPoliceMaintenanceExtService.saveOpsPoliceMaintenance(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除警用装备报修信息")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('traffic:ops-police-maintenance:delete')")
    public CommonResult<Boolean> deleteOpsPoliceMaintenance(@RequestParam("id") String id) {
        opsPoliceMaintenanceExtService.deleteOpsPoliceMaintenance(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得警用装备报修信息")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('traffic:ops-police-maintenance:query')")
    public CommonResult<TrafficOpsPoliceMaintenanceExtRespVO> getOpsPoliceMaintenance(@RequestParam("id") String id) {
        return success(opsPoliceMaintenanceExtService.getOpsPoliceMaintenance(id));
    }

    @GetMapping("/page")
    @Operation(summary = "获得警用装备报修信息分页")
    @PreAuthorize("@ss.hasPermission('traffic:ops-police-maintenance:query')")
    public CommonResult<PageResult<TrafficOpsPoliceMaintenanceExtRespVO>> getOpsPoliceMaintenancePage(@Valid TrafficOpsPoliceMaintenancePageReqVO pageReqVO) {
        return success(opsPoliceMaintenanceExtService.getOpsPoliceMaintenancePage(pageReqVO));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出警用装备报修信息 Excel")
    @PreAuthorize("@ss.hasPermission('traffic:ops-police-maintenance:export')")
    public void exportOpsPoliceMaintenanceExcel(@Valid TrafficOpsPoliceMaintenancePageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<TrafficOpsPoliceMaintenanceDO> list = opsPoliceMaintenanceService.getOpsPoliceMaintenancePage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "警用装备报修信息.xls", "数据", TrafficOpsPoliceMaintenanceRespVO.class,
                        BeanUtils.toBean(list, TrafficOpsPoliceMaintenanceRespVO.class));
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