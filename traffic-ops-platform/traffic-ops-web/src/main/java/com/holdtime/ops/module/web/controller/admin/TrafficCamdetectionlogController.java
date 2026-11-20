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
import com.holdtime.ops.module.entity.TrafficCamdetectionlogDO;
import com.holdtime.ops.module.service.TrafficCamdetectionlogService;

@Tag(name = "管理后台 - 设备状态检测")
@RestController
@RequestMapping("/traffic/camdetectionlog")
@Validated
public class TrafficCamdetectionlogController {

    @Resource
    private TrafficCamdetectionlogService camdetectionlogService;

    @PostMapping("/create")
    @Operation(summary = "创建设备状态检测")
    @PreAuthorize("@ss.hasPermission('traffic:camdetectionlog:create')")
    public CommonResult<Long> createCamdetectionlog(@Valid @RequestBody TrafficCamdetectionlogSaveReqVO createReqVO) {
        return success(camdetectionlogService.createCamdetectionlog(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新设备状态检测")
    @PreAuthorize("@ss.hasPermission('traffic:camdetectionlog:update')")
    public CommonResult<Boolean> updateCamdetectionlog(@Valid @RequestBody TrafficCamdetectionlogSaveReqVO updateReqVO) {
        camdetectionlogService.updateCamdetectionlog(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除设备状态检测")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('traffic:camdetectionlog:delete')")
    public CommonResult<Boolean> deleteCamdetectionlog(@RequestParam("id") Long id) {
        camdetectionlogService.deleteCamdetectionlog(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得设备状态检测")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('traffic:camdetectionlog:query')")
    public CommonResult<TrafficCamdetectionlogRespVO> getCamdetectionlog(@RequestParam("id") Long id) {
        TrafficCamdetectionlogDO camdetectionlog = camdetectionlogService.getCamdetectionlog(id);
        return success(BeanUtils.toBean(camdetectionlog, TrafficCamdetectionlogRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得设备状态检测分页")
    @PreAuthorize("@ss.hasPermission('traffic:camdetectionlog:query')")
    public CommonResult<PageResult<TrafficCamdetectionlogRespVO>> getCamdetectionlogPage(@Valid TrafficCamdetectionlogPageReqVO pageReqVO) {
        PageResult<TrafficCamdetectionlogDO> pageResult = camdetectionlogService.getCamdetectionlogPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, TrafficCamdetectionlogRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出设备状态检测 Excel")
    @PreAuthorize("@ss.hasPermission('traffic:camdetectionlog:export')")
    public void exportCamdetectionlogExcel(@Valid TrafficCamdetectionlogPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<TrafficCamdetectionlogDO> list = camdetectionlogService.getCamdetectionlogPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "设备状态检测.xls", "数据", TrafficCamdetectionlogRespVO.class,
                        BeanUtils.toBean(list, TrafficCamdetectionlogRespVO.class));
    }

}