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
import com.holdtime.ops.module.entity.TrafficLightsDO;
import com.holdtime.ops.module.service.TrafficLightsService;

@Tag(name = "管理后台 - 信号灯信息")
@RestController
@RequestMapping("/traffic/lights")
@Validated
public class TrafficLightsController {

    @Resource
    private TrafficLightsService lightsService;

    @PostMapping("/create")
    @Operation(summary = "创建信号灯信息")
    @PreAuthorize("@ss.hasPermission('traffic:lights:create')")
    public CommonResult<Long> createLights(@Valid @RequestBody TrafficLightsSaveReqVO createReqVO) {
        return success(lightsService.createLights(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新信号灯信息")
    @PreAuthorize("@ss.hasPermission('traffic:lights:update')")
    public CommonResult<Boolean> updateLights(@Valid @RequestBody TrafficLightsSaveReqVO updateReqVO) {
        lightsService.updateLights(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除信号灯信息")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('traffic:lights:delete')")
    public CommonResult<Boolean> deleteLights(@RequestParam("id") Long id) {
        lightsService.deleteLights(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得信号灯信息")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('traffic:lights:query')")
    public CommonResult<TrafficLightsRespVO> getLights(@RequestParam("id") Long id) {
        TrafficLightsDO lights = lightsService.getLights(id);
        return success(BeanUtils.toBean(lights, TrafficLightsRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得信号灯信息分页")
    @PreAuthorize("@ss.hasPermission('traffic:lights:query')")
    public CommonResult<PageResult<TrafficLightsRespVO>> getLightsPage(@Valid TrafficLightsPageReqVO pageReqVO) {
        PageResult<TrafficLightsDO> pageResult = lightsService.getLightsPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, TrafficLightsRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出信号灯信息 Excel")
    @PreAuthorize("@ss.hasPermission('traffic:lights:export')")
    public void exportLightsExcel(@Valid TrafficLightsPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<TrafficLightsDO> list = lightsService.getLightsPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "信号灯信息.xls", "数据", TrafficLightsRespVO.class,
                        BeanUtils.toBean(list, TrafficLightsRespVO.class));
    }

}