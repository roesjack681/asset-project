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
import com.holdtime.ops.module.entity.TrafficOpsGuardrailGapDO;
import com.holdtime.ops.module.service.TrafficOpsGuardrailGapService;

@Tag(name = "管理后台 - 护栏开口信息")
@RestController
@RequestMapping("/traffic/ops-guardrail-gap")
@Validated
public class TrafficOpsGuardrailGapController {

    @Resource
    private TrafficOpsGuardrailGapService opsGuardrailGapService;

    @PostMapping("/create")
    @Operation(summary = "创建护栏开口信息")
    @PreAuthorize("@ss.hasPermission('traffic:ops-guardrail-gap:create')")
    public CommonResult<String> createOpsGuardrailGap(@Valid @RequestBody TrafficOpsGuardrailGapSaveReqVO createReqVO) {
        return success(opsGuardrailGapService.createOpsGuardrailGap(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新护栏开口信息")
    @PreAuthorize("@ss.hasPermission('traffic:ops-guardrail-gap:update')")
    public CommonResult<Boolean> updateOpsGuardrailGap(@Valid @RequestBody TrafficOpsGuardrailGapSaveReqVO updateReqVO) {
        opsGuardrailGapService.updateOpsGuardrailGap(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除护栏开口信息")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('traffic:ops-guardrail-gap:delete')")
    public CommonResult<Boolean> deleteOpsGuardrailGap(@RequestParam("id") String id) {
        opsGuardrailGapService.deleteOpsGuardrailGap(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得护栏开口信息")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('traffic:ops-guardrail-gap:query')")
    public CommonResult<TrafficOpsGuardrailGapRespVO> getOpsGuardrailGap(@RequestParam("id") String id) {
        TrafficOpsGuardrailGapDO opsGuardrailGap = opsGuardrailGapService.getOpsGuardrailGap(id);
        return success(BeanUtils.toBean(opsGuardrailGap, TrafficOpsGuardrailGapRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得护栏开口信息分页")
    @PreAuthorize("@ss.hasPermission('traffic:ops-guardrail-gap:query')")
    public CommonResult<PageResult<TrafficOpsGuardrailGapRespVO>> getOpsGuardrailGapPage(@Valid TrafficOpsGuardrailGapPageReqVO pageReqVO) {
        PageResult<TrafficOpsGuardrailGapDO> pageResult = opsGuardrailGapService.getOpsGuardrailGapPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, TrafficOpsGuardrailGapRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出护栏开口信息 Excel")
    @PreAuthorize("@ss.hasPermission('traffic:ops-guardrail-gap:export')")
    public void exportOpsGuardrailGapExcel(@Valid TrafficOpsGuardrailGapPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<TrafficOpsGuardrailGapDO> list = opsGuardrailGapService.getOpsGuardrailGapPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "护栏开口信息.xls", "数据", TrafficOpsGuardrailGapRespVO.class,
                        BeanUtils.toBean(list, TrafficOpsGuardrailGapRespVO.class));
    }

}