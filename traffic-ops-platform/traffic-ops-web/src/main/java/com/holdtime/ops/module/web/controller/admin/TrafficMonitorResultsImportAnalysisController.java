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
import com.holdtime.ops.module.entity.TrafficMonitorResultsImportAnalysisDO;
import com.holdtime.ops.module.service.TrafficMonitorResultsImportAnalysisService;

@Tag(name = "管理后台 - 设备监测结果导入分析")
@RestController
@RequestMapping("/traffic/monitor-results-import-analysis")
@Validated
public class TrafficMonitorResultsImportAnalysisController {

    @Resource
    private TrafficMonitorResultsImportAnalysisService monitorResultsImportAnalysisService;

    @PostMapping("/create")
    @Operation(summary = "创建设备监测结果导入分析")
    @PreAuthorize("@ss.hasPermission('traffic:monitor-results-import-analysis:create')")
    public CommonResult<String> createMonitorResultsImportAnalysis(@Valid @RequestBody TrafficMonitorResultsImportAnalysisSaveReqVO createReqVO) {
        return success(monitorResultsImportAnalysisService.createMonitorResultsImportAnalysis(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新设备监测结果导入分析")
    @PreAuthorize("@ss.hasPermission('traffic:monitor-results-import-analysis:update')")
    public CommonResult<Boolean> updateMonitorResultsImportAnalysis(@Valid @RequestBody TrafficMonitorResultsImportAnalysisSaveReqVO updateReqVO) {
        monitorResultsImportAnalysisService.updateMonitorResultsImportAnalysis(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除设备监测结果导入分析")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('traffic:monitor-results-import-analysis:delete')")
    public CommonResult<Boolean> deleteMonitorResultsImportAnalysis(@RequestParam("id") String id) {
        monitorResultsImportAnalysisService.deleteMonitorResultsImportAnalysis(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得设备监测结果导入分析")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('traffic:monitor-results-import-analysis:query')")
    public CommonResult<TrafficMonitorResultsImportAnalysisRespVO> getMonitorResultsImportAnalysis(@RequestParam("id") String id) {
        TrafficMonitorResultsImportAnalysisDO monitorResultsImportAnalysis = monitorResultsImportAnalysisService.getMonitorResultsImportAnalysis(id);
        return success(BeanUtils.toBean(monitorResultsImportAnalysis, TrafficMonitorResultsImportAnalysisRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得设备监测结果导入分析分页")
    @PreAuthorize("@ss.hasPermission('traffic:monitor-results-import-analysis:query')")
    public CommonResult<PageResult<TrafficMonitorResultsImportAnalysisRespVO>> getMonitorResultsImportAnalysisPage(@Valid TrafficMonitorResultsImportAnalysisPageReqVO pageReqVO) {
        PageResult<TrafficMonitorResultsImportAnalysisDO> pageResult = monitorResultsImportAnalysisService.getMonitorResultsImportAnalysisPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, TrafficMonitorResultsImportAnalysisRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出设备监测结果导入分析 Excel")
    @PreAuthorize("@ss.hasPermission('traffic:monitor-results-import-analysis:export')")
    public void exportMonitorResultsImportAnalysisExcel(@Valid TrafficMonitorResultsImportAnalysisPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<TrafficMonitorResultsImportAnalysisDO> list = monitorResultsImportAnalysisService.getMonitorResultsImportAnalysisPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "设备监测结果导入分析.xls", "数据", TrafficMonitorResultsImportAnalysisRespVO.class,
                        BeanUtils.toBean(list, TrafficMonitorResultsImportAnalysisRespVO.class));
    }

}