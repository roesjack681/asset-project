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
import com.holdtime.ops.module.entity.TrafficLightsdetectionlogDO;
import com.holdtime.ops.module.service.TrafficLightsdetectionlogService;

@Tag(name = "管理后台 - 信号灯检测信息")
@RestController
@RequestMapping("/traffic/lightsdetectionlog")
@Validated
public class TrafficLightsdetectionlogController {

    @Resource
    private TrafficLightsdetectionlogService lightsdetectionlogService;

    @PostMapping("/create")
    @Operation(summary = "创建信号灯检测信息")
    @PreAuthorize("@ss.hasPermission('traffic:lightsdetectionlog:create')")
    public CommonResult<Long> createLightsdetectionlog(@Valid @RequestBody TrafficLightsdetectionlogSaveReqVO createReqVO) {
        return success(lightsdetectionlogService.createLightsdetectionlog(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新信号灯检测信息")
    @PreAuthorize("@ss.hasPermission('traffic:lightsdetectionlog:update')")
    public CommonResult<Boolean> updateLightsdetectionlog(@Valid @RequestBody TrafficLightsdetectionlogSaveReqVO updateReqVO) {
        lightsdetectionlogService.updateLightsdetectionlog(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除信号灯检测信息")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('traffic:lightsdetectionlog:delete')")
    public CommonResult<Boolean> deleteLightsdetectionlog(@RequestParam("id") Long id) {
        lightsdetectionlogService.deleteLightsdetectionlog(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得信号灯检测信息")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('traffic:lightsdetectionlog:query')")
    public CommonResult<TrafficLightsdetectionlogRespVO> getLightsdetectionlog(@RequestParam("id") Long id) {
        TrafficLightsdetectionlogDO lightsdetectionlog = lightsdetectionlogService.getLightsdetectionlog(id);
        return success(BeanUtils.toBean(lightsdetectionlog, TrafficLightsdetectionlogRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得信号灯检测信息分页")
    @PreAuthorize("@ss.hasPermission('traffic:lightsdetectionlog:query')")
    public CommonResult<PageResult<TrafficLightsdetectionlogRespVO>> getLightsdetectionlogPage(@Valid TrafficLightsdetectionlogPageReqVO pageReqVO) {
        PageResult<TrafficLightsdetectionlogDO> pageResult = lightsdetectionlogService.getLightsdetectionlogPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, TrafficLightsdetectionlogRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出信号灯检测信息 Excel")
    @PreAuthorize("@ss.hasPermission('traffic:lightsdetectionlog:export')")
    public void exportLightsdetectionlogExcel(@Valid TrafficLightsdetectionlogPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<TrafficLightsdetectionlogDO> list = lightsdetectionlogService.getLightsdetectionlogPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "信号灯检测信息.xls", "数据", TrafficLightsdetectionlogRespVO.class,
                        BeanUtils.toBean(list, TrafficLightsdetectionlogRespVO.class));
    }

}