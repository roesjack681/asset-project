package com.holdtime.ops.module.web.controller.admin;

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
import com.holdtime.ops.module.entity.ViewOpsDjMonitorStatisticsDO;
import com.holdtime.ops.module.service.ViewOpsDjMonitorStatisticsService;

@Tag(name = "管理后台 - 电警监控信息")
@RestController
@RequestMapping("/traffic/view-ops-dj-monitor-statistics")
@Validated
public class ViewOpsDjMonitorStatisticsController {

    @Resource
    private ViewOpsDjMonitorStatisticsService viewOpsDjMonitorStatisticsService;

    @PostMapping("/create")
    @Operation(summary = "创建电警监控信息")
    public CommonResult<String> createViewOpsDjMonitorStatistics(@Valid @RequestBody ViewOpsDjMonitorStatisticsSaveReqVO createReqVO) {
        return success(viewOpsDjMonitorStatisticsService.createViewOpsDjMonitorStatistics(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新电警监控信息")
    public CommonResult<Boolean> updateViewOpsDjMonitorStatistics(@Valid @RequestBody ViewOpsDjMonitorStatisticsSaveReqVO updateReqVO) {
        viewOpsDjMonitorStatisticsService.updateViewOpsDjMonitorStatistics(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除电警监控信息")
    @Parameter(name = "id", description = "编号", required = true)
    public CommonResult<Boolean> deleteViewOpsDjMonitorStatistics(@RequestParam("id") String id) {
        viewOpsDjMonitorStatisticsService.deleteViewOpsDjMonitorStatistics(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得电警监控信息")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    public CommonResult<ViewOpsDjMonitorStatisticsRespVO> getViewOpsDjMonitorStatistics(@RequestParam("id") String id) {
        ViewOpsDjMonitorStatisticsDO viewOpsDjMonitorStatistics = viewOpsDjMonitorStatisticsService.getViewOpsDjMonitorStatistics(id);
        return success(BeanUtils.toBean(viewOpsDjMonitorStatistics, ViewOpsDjMonitorStatisticsRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得电警监控信息分页")
    public CommonResult<PageResult<ViewOpsDjMonitorStatisticsRespVO>> getViewOpsDjMonitorStatisticsPage(@Valid ViewOpsDjMonitorStatisticsPageReqVO pageReqVO) {
        PageResult<ViewOpsDjMonitorStatisticsDO> pageResult = viewOpsDjMonitorStatisticsService.getViewOpsDjMonitorStatisticsPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, ViewOpsDjMonitorStatisticsRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出电警监控信息 Excel")
    public void exportViewOpsDjMonitorStatisticsExcel(@Valid ViewOpsDjMonitorStatisticsPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<ViewOpsDjMonitorStatisticsDO> list = viewOpsDjMonitorStatisticsService.getViewOpsDjMonitorStatisticsPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "电警监控信息.xls", "数据", ViewOpsDjMonitorStatisticsRespVO.class,
                        BeanUtils.toBean(list, ViewOpsDjMonitorStatisticsRespVO.class));
    }

}