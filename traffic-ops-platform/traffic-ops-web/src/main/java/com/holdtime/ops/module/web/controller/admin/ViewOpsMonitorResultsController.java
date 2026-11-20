package com.holdtime.ops.module.web.controller.admin;

import cn.iocoder.yudao.framework.excel.core.util.ExcelUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.holdtime.ops.module.model.dto.TrafficOpsMonitorDetailResultDTO;
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

import com.holdtime.ops.module.model.vo.*;
import com.holdtime.ops.module.entity.ViewOpsMonitorResultsDO;
import com.holdtime.ops.module.service.ViewOpsMonitorResultsService;

@Tag(name = "管理后台 - VIEW")
@RestController
@RequestMapping("/traffic/view-ops-monitor-results")
@Validated
public class ViewOpsMonitorResultsController {

    @Resource
    private ViewOpsMonitorResultsService viewOpsMonitorResultsService;
    @Resource
    private ViewOpsMonitorResultsExtService viewOpsMonitorResultsExtService;

    @GetMapping("/get")
    @Operation(summary = "获得VIEW")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('traffic:view-ops-monitor-results:query')")
    public CommonResult<ViewOpsMonitorResultsRespVO> getViewOpsMonitorResults(@RequestParam("id") String id) {
        ViewOpsMonitorResultsDO viewOpsMonitorResults = viewOpsMonitorResultsService.getViewOpsMonitorResults(id);
        return success(BeanUtils.toBean(viewOpsMonitorResults, ViewOpsMonitorResultsRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得VIEW分页")
    public CommonResult<PageResult<ViewOpsMonitorResultsExtRespVO>> getViewOpsMonitorResultsPage(@Valid ViewOpsMonitorResultsPageReqVO pageReqVO) {
        return success(viewOpsMonitorResultsExtService.getViewOpsMonitorResultsPage(pageReqVO));
    }

    @GetMapping("/analysisMonitorDetailResult")
    @Operation(summary = "查询监控详情结果")
    public CommonResult<PageResult<TrafficOpsMonitorDetailResultDTO>> analysisMonitorDetailResult(@Valid TrafficMonitorResultsAnalysisPageReqVO pageReqVO) {
        Page<?> page = new Page<>(pageReqVO.getPageNo(), pageReqVO.getPageSize());
        return success(viewOpsMonitorResultsExtService.analysisMonitorDetailResult(page, pageReqVO.getIpAddr(), pageReqVO.getEventType(), pageReqVO.getStartDate(), pageReqVO.getEndDate()));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出VIEW Excel")
    public void exportViewOpsMonitorResultsExcel(@Valid ViewOpsMonitorResultsPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<ViewOpsMonitorResultsExtRespVO> list = viewOpsMonitorResultsExtService.getViewOpsMonitorResultsPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "交警电警通断.xls", "导出数据", ViewOpsMonitorResultsExtRespVO.class, list);
    }

}