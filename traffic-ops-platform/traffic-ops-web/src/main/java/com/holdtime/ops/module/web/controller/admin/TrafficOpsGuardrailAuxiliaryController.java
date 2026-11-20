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
import com.holdtime.ops.module.entity.TrafficOpsGuardrailAuxiliaryDO;
import com.holdtime.ops.module.service.TrafficOpsGuardrailAuxiliaryService;

@Tag(name = "管理后台 - 护栏辅助设备")
@RestController
@RequestMapping("/traffic/ops-guardrail-auxiliary")
@Validated
public class TrafficOpsGuardrailAuxiliaryController {

    @Resource
    private TrafficOpsGuardrailAuxiliaryService opsGuardrailAuxiliaryService;

    @PostMapping("/create")
    @Operation(summary = "创建护栏辅助设备")
    @PreAuthorize("@ss.hasPermission('traffic:ops-guardrail-auxiliary:create')")
    public CommonResult<String> createOpsGuardrailAuxiliary(@Valid @RequestBody TrafficOpsGuardrailAuxiliarySaveReqVO createReqVO) {
        return success(opsGuardrailAuxiliaryService.createOpsGuardrailAuxiliary(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新护栏辅助设备")
    @PreAuthorize("@ss.hasPermission('traffic:ops-guardrail-auxiliary:update')")
    public CommonResult<Boolean> updateOpsGuardrailAuxiliary(@Valid @RequestBody TrafficOpsGuardrailAuxiliarySaveReqVO updateReqVO) {
        opsGuardrailAuxiliaryService.updateOpsGuardrailAuxiliary(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除护栏辅助设备")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('traffic:ops-guardrail-auxiliary:delete')")
    public CommonResult<Boolean> deleteOpsGuardrailAuxiliary(@RequestParam("id") String id) {
        opsGuardrailAuxiliaryService.deleteOpsGuardrailAuxiliary(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得护栏辅助设备")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('traffic:ops-guardrail-auxiliary:query')")
    public CommonResult<TrafficOpsGuardrailAuxiliaryRespVO> getOpsGuardrailAuxiliary(@RequestParam("id") String id) {
        TrafficOpsGuardrailAuxiliaryDO opsGuardrailAuxiliary = opsGuardrailAuxiliaryService.getOpsGuardrailAuxiliary(id);
        return success(BeanUtils.toBean(opsGuardrailAuxiliary, TrafficOpsGuardrailAuxiliaryRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得护栏辅助设备分页")
    @PreAuthorize("@ss.hasPermission('traffic:ops-guardrail-auxiliary:query')")
    public CommonResult<PageResult<TrafficOpsGuardrailAuxiliaryRespVO>> getOpsGuardrailAuxiliaryPage(@Valid TrafficOpsGuardrailAuxiliaryPageReqVO pageReqVO) {
        PageResult<TrafficOpsGuardrailAuxiliaryDO> pageResult = opsGuardrailAuxiliaryService.getOpsGuardrailAuxiliaryPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, TrafficOpsGuardrailAuxiliaryRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出护栏辅助设备 Excel")
    @PreAuthorize("@ss.hasPermission('traffic:ops-guardrail-auxiliary:export')")
    public void exportOpsGuardrailAuxiliaryExcel(@Valid TrafficOpsGuardrailAuxiliaryPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<TrafficOpsGuardrailAuxiliaryDO> list = opsGuardrailAuxiliaryService.getOpsGuardrailAuxiliaryPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "护栏辅助设备.xls", "数据", TrafficOpsGuardrailAuxiliaryRespVO.class,
                        BeanUtils.toBean(list, TrafficOpsGuardrailAuxiliaryRespVO.class));
    }

}