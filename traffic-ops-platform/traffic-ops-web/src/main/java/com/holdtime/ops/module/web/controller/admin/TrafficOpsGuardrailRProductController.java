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
import com.holdtime.ops.module.entity.TrafficOpsGuardrailRProductDO;
import com.holdtime.ops.module.service.TrafficOpsGuardrailRProductService;

@Tag(name = "管理后台 - 护栏关联产品信息")
@RestController
@RequestMapping("/traffic/ops-guardrail-R-product")
@Validated
public class TrafficOpsGuardrailRProductController {

    @Resource
    private TrafficOpsGuardrailRProductService opsGuardrailRProductService;

    @PostMapping("/create")
    @Operation(summary = "创建护栏关联产品信息")
    @PreAuthorize("@ss.hasPermission('traffic:ops-guardrail-R-product:create')")
    public CommonResult<String> createOpsGuardrailRProduct(@Valid @RequestBody TrafficOpsGuardrailRProductSaveReqVO createReqVO) {
        return success(opsGuardrailRProductService.createOpsGuardrailRProduct(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新护栏关联产品信息")
    @PreAuthorize("@ss.hasPermission('traffic:ops-guardrail-R-product:update')")
    public CommonResult<Boolean> updateOpsGuardrailRProduct(@Valid @RequestBody TrafficOpsGuardrailRProductSaveReqVO updateReqVO) {
        opsGuardrailRProductService.updateOpsGuardrailRProduct(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除护栏关联产品信息")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('traffic:ops-guardrail-R-product:delete')")
    public CommonResult<Boolean> deleteOpsGuardrailRProduct(@RequestParam("id") String id) {
        opsGuardrailRProductService.deleteOpsGuardrailRProduct(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得护栏关联产品信息")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('traffic:ops-guardrail-R-product:query')")
    public CommonResult<TrafficOpsGuardrailRProductRespVO> getOpsGuardrailRProduct(@RequestParam("id") String id) {
        TrafficOpsGuardrailRProductDO opsGuardrailRProduct = opsGuardrailRProductService.getOpsGuardrailRProduct(id);
        return success(BeanUtils.toBean(opsGuardrailRProduct, TrafficOpsGuardrailRProductRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得护栏关联产品信息分页")
    @PreAuthorize("@ss.hasPermission('traffic:ops-guardrail-R-product:query')")
    public CommonResult<PageResult<TrafficOpsGuardrailRProductRespVO>> getOpsGuardrailRProductPage(@Valid TrafficOpsGuardrailRProductPageReqVO pageReqVO) {
        PageResult<TrafficOpsGuardrailRProductDO> pageResult = opsGuardrailRProductService.getOpsGuardrailRProductPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, TrafficOpsGuardrailRProductRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出护栏关联产品信息 Excel")
    @PreAuthorize("@ss.hasPermission('traffic:ops-guardrail-R-product:export')")
    public void exportOpsGuardrailRProductExcel(@Valid TrafficOpsGuardrailRProductPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<TrafficOpsGuardrailRProductDO> list = opsGuardrailRProductService.getOpsGuardrailRProductPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "护栏关联产品信息.xls", "数据", TrafficOpsGuardrailRProductRespVO.class,
                        BeanUtils.toBean(list, TrafficOpsGuardrailRProductRespVO.class));
    }

}