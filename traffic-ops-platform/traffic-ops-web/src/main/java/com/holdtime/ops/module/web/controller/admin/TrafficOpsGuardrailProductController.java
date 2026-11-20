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
import com.holdtime.ops.module.entity.TrafficOpsGuardrailProductDO;
import com.holdtime.ops.module.service.TrafficOpsGuardrailProductService;

@Tag(name = "管理后台 - 护栏产品信息")
@RestController
@RequestMapping("/traffic/ops-guardrail-product")
@Validated
public class TrafficOpsGuardrailProductController {

    @Resource
    private TrafficOpsGuardrailProductService opsGuardrailProductService;

    @PostMapping("/create")
    @Operation(summary = "创建护栏产品信息")
    @PreAuthorize("@ss.hasPermission('traffic:ops-guardrail-product:create')")
    public CommonResult<String> createOpsGuardrailProduct(@Valid @RequestBody TrafficOpsGuardrailProductSaveReqVO createReqVO) {
        return success(opsGuardrailProductService.createOpsGuardrailProduct(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新护栏产品信息")
    @PreAuthorize("@ss.hasPermission('traffic:ops-guardrail-product:update')")
    public CommonResult<Boolean> updateOpsGuardrailProduct(@Valid @RequestBody TrafficOpsGuardrailProductSaveReqVO updateReqVO) {
        opsGuardrailProductService.updateOpsGuardrailProduct(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除护栏产品信息")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('traffic:ops-guardrail-product:delete')")
    public CommonResult<Boolean> deleteOpsGuardrailProduct(@RequestParam("id") String id) {
        opsGuardrailProductService.deleteOpsGuardrailProduct(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得护栏产品信息")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('traffic:ops-guardrail-product:query')")
    public CommonResult<TrafficOpsGuardrailProductRespVO> getOpsGuardrailProduct(@RequestParam("id") String id) {
        TrafficOpsGuardrailProductDO opsGuardrailProduct = opsGuardrailProductService.getOpsGuardrailProduct(id);
        return success(BeanUtils.toBean(opsGuardrailProduct, TrafficOpsGuardrailProductRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得护栏产品信息分页")
    public CommonResult<PageResult<TrafficOpsGuardrailProductRespVO>> getOpsGuardrailProductPage(@Valid TrafficOpsGuardrailProductPageReqVO pageReqVO) {
        PageResult<TrafficOpsGuardrailProductDO> pageResult = opsGuardrailProductService.getOpsGuardrailProductPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, TrafficOpsGuardrailProductRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出护栏产品信息 Excel")
    @PreAuthorize("@ss.hasPermission('traffic:ops-guardrail-product:export')")
    public void exportOpsGuardrailProductExcel(@Valid TrafficOpsGuardrailProductPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<TrafficOpsGuardrailProductDO> list = opsGuardrailProductService.getOpsGuardrailProductPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "护栏产品信息.xls", "数据", TrafficOpsGuardrailProductRespVO.class,
                        BeanUtils.toBean(list, TrafficOpsGuardrailProductRespVO.class));
    }

}