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
import com.holdtime.ops.module.entity.PublicOpsInoutDetailDO;
import com.holdtime.ops.module.service.PublicOpsInoutDetailService;

@Tag(name = "管理后台 - 设备出入库存明细")
@RestController
@RequestMapping("/public/ops-inout-detail")
@Validated
public class PublicOpsInoutDetailController {

    @Resource
    private PublicOpsInoutDetailService opsInoutDetailService;

    @PostMapping("/create")
    @Operation(summary = "创建设备出入库存明细")
    @PreAuthorize("@ss.hasPermission('public:ops-inout-detail:create')")
    public CommonResult<String> createOpsInoutDetail(@Valid @RequestBody PublicOpsInoutDetailSaveReqVO createReqVO) {
        return success(opsInoutDetailService.createOpsInoutDetail(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新设备出入库存明细")
    @PreAuthorize("@ss.hasPermission('public:ops-inout-detail:update')")
    public CommonResult<Boolean> updateOpsInoutDetail(@Valid @RequestBody PublicOpsInoutDetailSaveReqVO updateReqVO) {
        opsInoutDetailService.updateOpsInoutDetail(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除设备出入库存明细")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('public:ops-inout-detail:delete')")
    public CommonResult<Boolean> deleteOpsInoutDetail(@RequestParam("id") String id) {
        opsInoutDetailService.deleteOpsInoutDetail(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得设备出入库存明细")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('public:ops-inout-detail:query')")
    public CommonResult<PublicOpsInoutDetailRespVO> getOpsInoutDetail(@RequestParam("id") String id) {
        PublicOpsInoutDetailDO opsInoutDetail = opsInoutDetailService.getOpsInoutDetail(id);
        return success(BeanUtils.toBean(opsInoutDetail, PublicOpsInoutDetailRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得设备出入库存明细分页")
    @PreAuthorize("@ss.hasPermission('public:ops-inout-detail:query')")
    public CommonResult<PageResult<PublicOpsInoutDetailRespVO>> getOpsInoutDetailPage(@Valid PublicOpsInoutDetailPageReqVO pageReqVO) {
        PageResult<PublicOpsInoutDetailDO> pageResult = opsInoutDetailService.getOpsInoutDetailPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, PublicOpsInoutDetailRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出设备出入库存明细 Excel")
    @PreAuthorize("@ss.hasPermission('public:ops-inout-detail:export')")
    public void exportOpsInoutDetailExcel(@Valid PublicOpsInoutDetailPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<PublicOpsInoutDetailDO> list = opsInoutDetailService.getOpsInoutDetailPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "设备出入库存明细.xls", "数据", PublicOpsInoutDetailRespVO.class,
                        BeanUtils.toBean(list, PublicOpsInoutDetailRespVO.class));
    }

}