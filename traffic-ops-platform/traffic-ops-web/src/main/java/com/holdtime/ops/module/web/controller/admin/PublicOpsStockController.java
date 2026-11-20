package com.holdtime.ops.module.web.controller.admin;

import com.holdtime.ops.module.service.PublicOpsStockExtService;
import com.holdtime.ops.module.service.PublicOpsStockService;
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
import com.holdtime.ops.module.entity.PublicOpsStockDO;

@Tag(name = "管理后台 - 设备库存管理")
@RestController
@RequestMapping("/public/ops-stock")
@Validated
public class PublicOpsStockController {

    @Resource
    private PublicOpsStockService opsStockService;
    @Resource
    private PublicOpsStockExtService opsStockExtService;

    @PostMapping("/instock")
    @Operation(summary = "入库")
    @PreAuthorize("@ss.hasPermission('public:ops-stock:create')")
    public CommonResult<String> instock(@Valid @RequestBody PublicOpsInoutStockSaveReqVO createReqVO) {
        return success(opsStockExtService.instock(createReqVO));
    }

    @PutMapping("/outstock")
    @Operation(summary = "出库")
    @PreAuthorize("@ss.hasPermission('public:ops-stock:update')")
    public CommonResult<Boolean> outstock(@Valid @RequestBody PublicOpsInoutStockSaveReqVO updateReqVO) {
        opsStockExtService.outstock(updateReqVO);
        return success(true);
    }
//
//    @DeleteMapping("/delete")
//    @Operation(summary = "删除设备库存管理")
//    @Parameter(name = "id", description = "编号", required = true)
//    @PreAuthorize("@ss.hasPermission('public:ops-stock:delete')")
//    public CommonResult<Boolean> deleteOpsStock(@RequestParam("id") String id) {
//        opsStockService.deleteOpsStock(id);
//        return success(true);
//    }

    @GetMapping("/get")
    @Operation(summary = "获得设备库存管理")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('public:ops-stock:query')")
    public CommonResult<PublicOpsStockRespVO> getOpsStock(@RequestParam("id") String id) {
        PublicOpsStockDO opsStock = opsStockService.getOpsStock(id);
        return success(BeanUtils.toBean(opsStock, PublicOpsStockRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得设备库存管理分页")
    @PreAuthorize("@ss.hasPermission('public:ops-stock:query')")
    public CommonResult<PageResult<PublicOpsStockRespVO>> getOpsStockPage(@Valid PublicOpsStockPageReqVO pageReqVO) {
        PageResult<PublicOpsStockDO> pageResult = opsStockService.getOpsStockPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, PublicOpsStockRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出设备库存管理 Excel")
    @PreAuthorize("@ss.hasPermission('public:ops-stock:export')")
    public void exportOpsStockExcel(@Valid PublicOpsStockPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<PublicOpsStockDO> list = opsStockService.getOpsStockPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "设备库存管理.xls", "数据", PublicOpsStockRespVO.class,
                        BeanUtils.toBean(list, PublicOpsStockRespVO.class));
    }

}