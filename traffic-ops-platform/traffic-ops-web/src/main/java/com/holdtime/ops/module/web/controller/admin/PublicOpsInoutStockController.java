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
import com.holdtime.ops.module.entity.PublicOpsInoutStockDO;
import com.holdtime.ops.module.service.PublicOpsInoutStockService;

@Tag(name = "管理后台 - 设备出入库管理")
@RestController
@RequestMapping("/public/ops-inout-stock")
@Validated
public class PublicOpsInoutStockController {

    @Resource
    private PublicOpsInoutStockService opsInoutStockService;

    @PostMapping("/create")
    @Operation(summary = "创建设备出入库管理")
    @PreAuthorize("@ss.hasPermission('public:ops-inout-stock:create')")
    public CommonResult<String> createOpsInoutStock(@Valid @RequestBody PublicOpsInoutStockSaveReqVO createReqVO) {
        return success(opsInoutStockService.createOpsInoutStock(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新设备出入库管理")
    @PreAuthorize("@ss.hasPermission('public:ops-inout-stock:update')")
    public CommonResult<Boolean> updateOpsInoutStock(@Valid @RequestBody PublicOpsInoutStockSaveReqVO updateReqVO) {
        opsInoutStockService.updateOpsInoutStock(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除设备出入库管理")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('public:ops-inout-stock:delete')")
    public CommonResult<Boolean> deleteOpsInoutStock(@RequestParam("id") String id) {
        opsInoutStockService.deleteOpsInoutStock(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得设备出入库管理")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('public:ops-inout-stock:query')")
    public CommonResult<PublicOpsInoutStockRespVO> getOpsInoutStock(@RequestParam("id") String id) {
        PublicOpsInoutStockDO opsInoutStock = opsInoutStockService.getOpsInoutStock(id);
        return success(BeanUtils.toBean(opsInoutStock, PublicOpsInoutStockRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得设备出入库管理分页")
    @PreAuthorize("@ss.hasPermission('public:ops-inout-stock:query')")
    public CommonResult<PageResult<PublicOpsInoutStockRespVO>> getOpsInoutStockPage(@Valid PublicOpsInoutStockPageReqVO pageReqVO) {
        PageResult<PublicOpsInoutStockDO> pageResult = opsInoutStockService.getOpsInoutStockPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, PublicOpsInoutStockRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出设备出入库管理 Excel")
    @PreAuthorize("@ss.hasPermission('public:ops-inout-stock:export')")
    public void exportOpsInoutStockExcel(@Valid PublicOpsInoutStockPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<PublicOpsInoutStockDO> list = opsInoutStockService.getOpsInoutStockPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "设备出入库管理.xls", "数据", PublicOpsInoutStockRespVO.class,
                        BeanUtils.toBean(list, PublicOpsInoutStockRespVO.class));
    }

}