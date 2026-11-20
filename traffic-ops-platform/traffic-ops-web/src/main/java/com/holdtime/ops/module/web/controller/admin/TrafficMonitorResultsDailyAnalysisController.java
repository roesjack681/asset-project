package com.holdtime.ops.module.web.controller.admin;

import com.holdtime.ops.module.service.ViewOpsMonitorResultsExtService;
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
import com.holdtime.ops.module.entity.TrafficMonitorResultsDailyAnalysisDO;
import com.holdtime.ops.module.service.TrafficMonitorResultsDailyAnalysisService;

@Tag(name = "管理后台 - 设备监测结果每日分析")
@RestController
@RequestMapping("/traffic/monitor-results-daily-analysis")
@Validated
public class TrafficMonitorResultsDailyAnalysisController {

    @Resource
    private TrafficMonitorResultsDailyAnalysisService monitorResultsDailyAnalysisService;
    @Resource
    private ViewOpsMonitorResultsExtService viewOpsMonitorResultsExtService;


    @GetMapping("/get")
    @Operation(summary = "获得设备监测结果每日分析")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('traffic:monitor-results-daily-analysis:query')")
    public CommonResult<TrafficMonitorResultsDailyAnalysisRespVO> getMonitorResultsDailyAnalysis(@RequestParam("id") String id) {
        TrafficMonitorResultsDailyAnalysisDO monitorResultsDailyAnalysis = monitorResultsDailyAnalysisService.getMonitorResultsDailyAnalysis(id);
        return success(BeanUtils.toBean(monitorResultsDailyAnalysis, TrafficMonitorResultsDailyAnalysisRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得设备监测结果每日分析分页")
    @PreAuthorize("@ss.hasPermission('traffic:monitor-results-daily-analysis:query')")
    public CommonResult<PageResult<TrafficMonitorResultsDailyAnalysisExtRespVO>> getMonitorResultsDailyAnalysisPage(@Valid TrafficMonitorResultsDailyAnalysisExtPageReqVO pageReqVO) {
        return success(viewOpsMonitorResultsExtService.getMonitorResultsDailyAnalysisPage(pageReqVO));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出设备监测结果每日分析 Excel")
    @PreAuthorize("@ss.hasPermission('traffic:monitor-results-daily-analysis:export')")
    public void exportMonitorResultsDailyAnalysisExcel(@Valid TrafficMonitorResultsDailyAnalysisPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<TrafficMonitorResultsDailyAnalysisDO> list = monitorResultsDailyAnalysisService.getMonitorResultsDailyAnalysisPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "设备监测结果每日分析.xls", "数据", TrafficMonitorResultsDailyAnalysisRespVO.class,
                        BeanUtils.toBean(list, TrafficMonitorResultsDailyAnalysisRespVO.class));
    }

}