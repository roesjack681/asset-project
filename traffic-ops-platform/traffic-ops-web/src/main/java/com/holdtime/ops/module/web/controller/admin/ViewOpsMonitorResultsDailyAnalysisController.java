package com.holdtime.ops.module.web.controller.admin;

import com.holdtime.ops.module.service.ViewOpsMonitorResultsDailyAnalysisExtService;
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
import com.holdtime.ops.module.entity.ViewOpsMonitorResultsDailyAnalysisDO;
import com.holdtime.ops.module.service.ViewOpsMonitorResultsDailyAnalysisService;

@Tag(name = "管理后台 - 检测结果每日分析视图")
@RestController
@RequestMapping("/traffic/view-ops-monitor-results-daily-analysis")
@Validated
public class ViewOpsMonitorResultsDailyAnalysisController {

    @Resource
    private ViewOpsMonitorResultsDailyAnalysisService viewOpsMonitorResultsDailyAnalysisService;
    @Resource
    private ViewOpsMonitorResultsDailyAnalysisExtService viewOpsMonitorResultsDailyAnalysisExtService;

    @PostMapping("/create")
    @Operation(summary = "创建检测结果每日分析视图")
    @PreAuthorize("@ss.hasPermission('traffic:view-ops-monitor-results-daily-analysis:create')")
    public CommonResult<String> createViewOpsMonitorResultsDailyAnalysis(@Valid @RequestBody ViewOpsMonitorResultsDailyAnalysisSaveReqVO createReqVO) {
        return success(viewOpsMonitorResultsDailyAnalysisService.createViewOpsMonitorResultsDailyAnalysis(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新检测结果每日分析视图")
    @PreAuthorize("@ss.hasPermission('traffic:view-ops-monitor-results-daily-analysis:update')")
    public CommonResult<Boolean> updateViewOpsMonitorResultsDailyAnalysis(@Valid @RequestBody ViewOpsMonitorResultsDailyAnalysisSaveReqVO updateReqVO) {
        viewOpsMonitorResultsDailyAnalysisService.updateViewOpsMonitorResultsDailyAnalysis(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除检测结果每日分析视图")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('traffic:view-ops-monitor-results-daily-analysis:delete')")
    public CommonResult<Boolean> deleteViewOpsMonitorResultsDailyAnalysis(@RequestParam("id") String id) {
        viewOpsMonitorResultsDailyAnalysisService.deleteViewOpsMonitorResultsDailyAnalysis(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得检测结果每日分析视图")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    public CommonResult<ViewOpsMonitorResultsDailyAnalysisRespVO> getViewOpsMonitorResultsDailyAnalysis(@RequestParam("id") String id) {
        ViewOpsMonitorResultsDailyAnalysisDO viewOpsMonitorResultsDailyAnalysis = viewOpsMonitorResultsDailyAnalysisService.getViewOpsMonitorResultsDailyAnalysis(id);
        return success(BeanUtils.toBean(viewOpsMonitorResultsDailyAnalysis, ViewOpsMonitorResultsDailyAnalysisRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得检测结果每日分析视图分页")
    public CommonResult<PageResult<ViewOpsMonitorResultsDailyAnalysisRespVO>> getViewOpsMonitorResultsDailyAnalysisPage(@Valid ViewOpsMonitorResultsDailyAnalysisPageReqVO pageReqVO) {
        return success(viewOpsMonitorResultsDailyAnalysisExtService.getViewOpsMonitorResultsDailyAnalysisPage(pageReqVO));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出检测结果每日分析视图 Excel")
    @PreAuthorize("@ss.hasPermission('traffic:view-ops-monitor-results-daily-analysis:export')")
    public void exportViewOpsMonitorResultsDailyAnalysisExcel(@Valid ViewOpsMonitorResultsDailyAnalysisPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<ViewOpsMonitorResultsDailyAnalysisDO> list = viewOpsMonitorResultsDailyAnalysisService.getViewOpsMonitorResultsDailyAnalysisPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "检测结果每日分析视图.xls", "数据", ViewOpsMonitorResultsDailyAnalysisRespVO.class,
                        BeanUtils.toBean(list, ViewOpsMonitorResultsDailyAnalysisRespVO.class));
    }

}