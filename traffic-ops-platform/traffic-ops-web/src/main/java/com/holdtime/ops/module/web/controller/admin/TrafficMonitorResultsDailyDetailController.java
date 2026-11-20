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
import com.holdtime.ops.module.entity.TrafficMonitorResultsDailyDetailDO;
import com.holdtime.ops.module.service.TrafficMonitorResultsDailyDetailService;

@Tag(name = "管理后台 - 设备监测结果每日分析详情")
@RestController
@RequestMapping("/traffic/monitor-results-daily-detail")
@Validated
public class TrafficMonitorResultsDailyDetailController {

    @Resource
    private TrafficMonitorResultsDailyDetailService monitorResultsDailyDetailService;

    @PostMapping("/create")
    @Operation(summary = "创建设备监测结果每日分析详情")
    @PreAuthorize("@ss.hasPermission('traffic:monitor-results-daily-detail:create')")
    public CommonResult<String> createMonitorResultsDailyDetail(@Valid @RequestBody TrafficMonitorResultsDailyDetailSaveReqVO createReqVO) {
        return success(monitorResultsDailyDetailService.createMonitorResultsDailyDetail(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新设备监测结果每日分析详情")
    @PreAuthorize("@ss.hasPermission('traffic:monitor-results-daily-detail:update')")
    public CommonResult<Boolean> updateMonitorResultsDailyDetail(@Valid @RequestBody TrafficMonitorResultsDailyDetailSaveReqVO updateReqVO) {
        monitorResultsDailyDetailService.updateMonitorResultsDailyDetail(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除设备监测结果每日分析详情")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('traffic:monitor-results-daily-detail:delete')")
    public CommonResult<Boolean> deleteMonitorResultsDailyDetail(@RequestParam("id") String id) {
        monitorResultsDailyDetailService.deleteMonitorResultsDailyDetail(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得设备监测结果每日分析详情")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('traffic:monitor-results-daily-detail:query')")
    public CommonResult<TrafficMonitorResultsDailyDetailRespVO> getMonitorResultsDailyDetail(@RequestParam("id") String id) {
        TrafficMonitorResultsDailyDetailDO monitorResultsDailyDetail = monitorResultsDailyDetailService.getMonitorResultsDailyDetail(id);
        return success(BeanUtils.toBean(monitorResultsDailyDetail, TrafficMonitorResultsDailyDetailRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得设备监测结果每日分析详情分页")
    @PreAuthorize("@ss.hasPermission('traffic:monitor-results-daily-detail:query')")
    public CommonResult<PageResult<TrafficMonitorResultsDailyDetailRespVO>> getMonitorResultsDailyDetailPage(@Valid TrafficMonitorResultsDailyDetailPageReqVO pageReqVO) {
        PageResult<TrafficMonitorResultsDailyDetailDO> pageResult = monitorResultsDailyDetailService.getMonitorResultsDailyDetailPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, TrafficMonitorResultsDailyDetailRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出设备监测结果每日分析详情 Excel")
    @PreAuthorize("@ss.hasPermission('traffic:monitor-results-daily-detail:export')")
    public void exportMonitorResultsDailyDetailExcel(@Valid TrafficMonitorResultsDailyDetailPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<TrafficMonitorResultsDailyDetailDO> list = monitorResultsDailyDetailService.getMonitorResultsDailyDetailPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "设备监测结果每日分析详情.xls", "数据", TrafficMonitorResultsDailyDetailRespVO.class,
                        BeanUtils.toBean(list, TrafficMonitorResultsDailyDetailRespVO.class));
    }

}