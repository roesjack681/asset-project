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
import com.holdtime.ops.module.entity.TrafficOpsGuardrailInsuranceDO;
import com.holdtime.ops.module.service.TrafficOpsGuardrailInsuranceService;

@Tag(name = "管理后台 - 护栏保险信息")
@RestController
@RequestMapping("/traffic/ops-guardrail-insurance")
@Validated
public class TrafficOpsGuardrailInsuranceController {

    @Resource
    private TrafficOpsGuardrailInsuranceService opsGuardrailInsuranceService;

    @PostMapping("/create")
    @Operation(summary = "创建护栏保险信息")
    @PreAuthorize("@ss.hasPermission('traffic:ops-guardrail-insurance:create')")
    public CommonResult<String> createOpsGuardrailInsurance(@Valid @RequestBody TrafficOpsGuardrailInsuranceSaveReqVO createReqVO) {
        return success(opsGuardrailInsuranceService.createOpsGuardrailInsurance(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新护栏保险信息")
    @PreAuthorize("@ss.hasPermission('traffic:ops-guardrail-insurance:update')")
    public CommonResult<Boolean> updateOpsGuardrailInsurance(@Valid @RequestBody TrafficOpsGuardrailInsuranceSaveReqVO updateReqVO) {
        opsGuardrailInsuranceService.updateOpsGuardrailInsurance(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除护栏保险信息")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('traffic:ops-guardrail-insurance:delete')")
    public CommonResult<Boolean> deleteOpsGuardrailInsurance(@RequestParam("id") String id) {
        opsGuardrailInsuranceService.deleteOpsGuardrailInsurance(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得护栏保险信息")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('traffic:ops-guardrail-insurance:query')")
    public CommonResult<TrafficOpsGuardrailInsuranceRespVO> getOpsGuardrailInsurance(@RequestParam("id") String id) {
        TrafficOpsGuardrailInsuranceDO opsGuardrailInsurance = opsGuardrailInsuranceService.getOpsGuardrailInsurance(id);
        return success(BeanUtils.toBean(opsGuardrailInsurance, TrafficOpsGuardrailInsuranceRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得护栏保险信息分页")
    @PreAuthorize("@ss.hasPermission('traffic:ops-guardrail-insurance:query')")
    public CommonResult<PageResult<TrafficOpsGuardrailInsuranceRespVO>> getOpsGuardrailInsurancePage(@Valid TrafficOpsGuardrailInsurancePageReqVO pageReqVO) {
        PageResult<TrafficOpsGuardrailInsuranceDO> pageResult = opsGuardrailInsuranceService.getOpsGuardrailInsurancePage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, TrafficOpsGuardrailInsuranceRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出护栏保险信息 Excel")
    @PreAuthorize("@ss.hasPermission('traffic:ops-guardrail-insurance:export')")
    public void exportOpsGuardrailInsuranceExcel(@Valid TrafficOpsGuardrailInsurancePageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<TrafficOpsGuardrailInsuranceDO> list = opsGuardrailInsuranceService.getOpsGuardrailInsurancePage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "护栏保险信息.xls", "数据", TrafficOpsGuardrailInsuranceRespVO.class,
                        BeanUtils.toBean(list, TrafficOpsGuardrailInsuranceRespVO.class));
    }

}