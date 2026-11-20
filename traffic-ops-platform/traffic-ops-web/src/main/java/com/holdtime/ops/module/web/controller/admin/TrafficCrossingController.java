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
import com.holdtime.ops.module.entity.TrafficCrossingDO;
import com.holdtime.ops.module.service.TrafficCrossingService;

@Tag(name = "管理后台 - 路口信息")
@RestController
@RequestMapping("/traffic/crossing")
@Validated
public class TrafficCrossingController {

    @Resource
    private TrafficCrossingService crossingService;

    @PostMapping("/create")
    @Operation(summary = "创建路口信息")
    @PreAuthorize("@ss.hasPermission('traffic:crossing:create')")
    public CommonResult<Long> createCrossing(@Valid @RequestBody TrafficCrossingSaveReqVO createReqVO) {
        return success(crossingService.createCrossing(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新路口信息")
    @PreAuthorize("@ss.hasPermission('traffic:crossing:update')")
    public CommonResult<Boolean> updateCrossing(@Valid @RequestBody TrafficCrossingSaveReqVO updateReqVO) {
        crossingService.updateCrossing(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除路口信息")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('traffic:crossing:delete')")
    public CommonResult<Boolean> deleteCrossing(@RequestParam("id") Long id) {
        crossingService.deleteCrossing(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得路口信息")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('traffic:crossing:query')")
    public CommonResult<TrafficCrossingRespVO> getCrossing(@RequestParam("id") Long id) {
        TrafficCrossingDO crossing = crossingService.getCrossing(id);
        return success(BeanUtils.toBean(crossing, TrafficCrossingRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得路口信息分页")
    @PreAuthorize("@ss.hasPermission('traffic:crossing:query')")
    public CommonResult<PageResult<TrafficCrossingRespVO>> getCrossingPage(@Valid TrafficCrossingPageReqVO pageReqVO) {
        PageResult<TrafficCrossingDO> pageResult = crossingService.getCrossingPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, TrafficCrossingRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出路口信息 Excel")
    @PreAuthorize("@ss.hasPermission('traffic:crossing:export')")
    public void exportCrossingExcel(@Valid TrafficCrossingPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<TrafficCrossingDO> list = crossingService.getCrossingPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "路口信息.xls", "数据", TrafficCrossingRespVO.class,
                        BeanUtils.toBean(list, TrafficCrossingRespVO.class));
    }

}