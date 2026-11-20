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
import com.holdtime.ops.module.entity.PublicOpsStockDetailDO;
import com.holdtime.ops.module.service.PublicOpsStockDetailService;

@Tag(name = "管理后台 - 设备库存明细")
@RestController
@RequestMapping("/public/ops-stock-detail")
@Validated
public class PublicOpsStockDetailController {

    @Resource
    private PublicOpsStockDetailService opsStockDetailService;

//    @PostMapping("/create")
//    @Operation(summary = "创建设备库存明细")
//    @PreAuthorize("@ss.hasPermission('public:ops-stock-detail:create')")
//    public CommonResult<String> createOpsStockDetail(@Valid @RequestBody PublicOpsStockDetailSaveReqVO createReqVO) {
//        return success(opsStockDetailService.createOpsStockDetail(createReqVO));
//    }
//
//    @PutMapping("/update")
//    @Operation(summary = "更新设备库存明细")
//    @PreAuthorize("@ss.hasPermission('public:ops-stock-detail:update')")
//    public CommonResult<Boolean> updateOpsStockDetail(@Valid @RequestBody PublicOpsStockDetailSaveReqVO updateReqVO) {
//        opsStockDetailService.updateOpsStockDetail(updateReqVO);
//        return success(true);
//    }
//
//    @DeleteMapping("/delete")
//    @Operation(summary = "删除设备库存明细")
//    @Parameter(name = "id", description = "编号", required = true)
//    @PreAuthorize("@ss.hasPermission('public:ops-stock-detail:delete')")
//    public CommonResult<Boolean> deleteOpsStockDetail(@RequestParam("id") String id) {
//        opsStockDetailService.deleteOpsStockDetail(id);
//        return success(true);
//    }

//    @GetMapping("/get")
//    @Operation(summary = "获得设备库存明细")
//    @Parameter(name = "id", description = "编号", required = true, example = "1024")
//    @PreAuthorize("@ss.hasPermission('public:ops-stock-detail:query')")
//    public CommonResult<PublicOpsStockDetailRespVO> getOpsStockDetail(@RequestParam("id") String id) {
//        PublicOpsStockDetailDO opsStockDetail = opsStockDetailService.getOpsStockDetail(id);
//        return success(BeanUtils.toBean(opsStockDetail, PublicOpsStockDetailRespVO.class));
//    }

    @GetMapping("/page")
    @Operation(summary = "获得设备库存明细分页")
    @PreAuthorize("@ss.hasPermission('public:ops-stock-detail:query')")
    public CommonResult<PageResult<PublicOpsStockDetailRespVO>> getOpsStockDetailPage(@Valid PublicOpsStockDetailPageReqVO pageReqVO) {
        PageResult<PublicOpsStockDetailDO> pageResult = opsStockDetailService.getOpsStockDetailPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, PublicOpsStockDetailRespVO.class));
    }

//    @GetMapping("/export-excel")
//    @Operation(summary = "导出设备库存明细 Excel")
//    @PreAuthorize("@ss.hasPermission('public:ops-stock-detail:export')")
//    public void exportOpsStockDetailExcel(@Valid PublicOpsStockDetailPageReqVO pageReqVO,
//              HttpServletResponse response) throws IOException {
//        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
//        List<PublicOpsStockDetailDO> list = opsStockDetailService.getOpsStockDetailPage(pageReqVO).getList();
//        // 导出 Excel
//        ExcelUtils.write(response, "设备库存明细.xls", "数据", PublicOpsStockDetailRespVO.class,
//                        BeanUtils.toBean(list, PublicOpsStockDetailRespVO.class));
//    }

}