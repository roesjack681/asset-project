package com.holdtime.ops.module.web.controller.admin;

import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Operation;

import javax.validation.constraints.*;
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
import com.holdtime.ops.module.entity.TrafficOpsMaintenanceGuardrailDO;
import com.holdtime.ops.module.service.TrafficOpsMaintenanceGuardrailService;

@Tag(name = "管理后台 - 护栏维护详细")
@RestController
@RequestMapping("/traffic/ops-maintenance-guardrail")
@Validated
public class TrafficOpsMaintenanceGuardrailController {

    @Resource
    private TrafficOpsMaintenanceGuardrailService opsMaintenanceGuardrailService;

    @PostMapping("/create")
    @Operation(summary = "创建护栏维护详细")
    @PreAuthorize("@ss.hasPermission('traffic:ops-maintenance-guardrail:create')")
    public CommonResult<String> createOpsMaintenanceGuardrail(@Valid @RequestBody TrafficOpsMaintenanceGuardrailSaveReqVO createReqVO) {
        return success(opsMaintenanceGuardrailService.createOpsMaintenanceGuardrail(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新护栏维护详细")
    @PreAuthorize("@ss.hasPermission('traffic:ops-maintenance-guardrail:update')")
    public CommonResult<Boolean> updateOpsMaintenanceGuardrail(@Valid @RequestBody TrafficOpsMaintenanceGuardrailSaveReqVO updateReqVO) {
        opsMaintenanceGuardrailService.updateOpsMaintenanceGuardrail(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除护栏维护详细")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('traffic:ops-maintenance-guardrail:delete')")
    public CommonResult<Boolean> deleteOpsMaintenanceGuardrail(@RequestParam("id") String id) {
        opsMaintenanceGuardrailService.deleteOpsMaintenanceGuardrail(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得护栏维护详细")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('traffic:ops-maintenance-guardrail:query')")
    public CommonResult<TrafficOpsMaintenanceGuardrailRespVO> getOpsMaintenanceGuardrail(@RequestParam("id") String id) {
        TrafficOpsMaintenanceGuardrailDO opsMaintenanceGuardrail = opsMaintenanceGuardrailService.getOpsMaintenanceGuardrail(id);
        return success(BeanUtils.toBean(opsMaintenanceGuardrail, TrafficOpsMaintenanceGuardrailRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得护栏维护详细分页")
    @PreAuthorize("@ss.hasPermission('traffic:ops-maintenance-guardrail:query')")
    public CommonResult<PageResult<TrafficOpsMaintenanceGuardrailRespVO>> getOpsMaintenanceGuardrailPage(@Valid TrafficOpsMaintenanceGuardrailPageReqVO pageReqVO) {
        PageResult<TrafficOpsMaintenanceGuardrailDO> pageResult = opsMaintenanceGuardrailService.getOpsMaintenanceGuardrailPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, TrafficOpsMaintenanceGuardrailRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出护栏维护详细 Excel")
    @PreAuthorize("@ss.hasPermission('traffic:ops-maintenance-guardrail:export')")
    public void exportOpsMaintenanceGuardrailExcel(@Valid TrafficOpsMaintenanceGuardrailPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<TrafficOpsMaintenanceGuardrailDO> list = opsMaintenanceGuardrailService.getOpsMaintenanceGuardrailPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "护栏维护详细.xls", "数据", TrafficOpsMaintenanceGuardrailRespVO.class,
                        BeanUtils.toBean(list, TrafficOpsMaintenanceGuardrailRespVO.class));
    }

}