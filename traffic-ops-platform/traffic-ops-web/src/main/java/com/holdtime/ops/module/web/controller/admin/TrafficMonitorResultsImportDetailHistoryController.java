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
import com.holdtime.ops.module.entity.TrafficMonitorResultsImportDetailHistoryDO;
import com.holdtime.ops.module.service.TrafficMonitorResultsImportDetailHistoryService;

@Tag(name = "管理后台 - 设备监测结果导入信息历史")
@RestController
@RequestMapping("/traffic/monitor-results-import-detail-history")
@Validated
public class TrafficMonitorResultsImportDetailHistoryController {

    @Resource
    private TrafficMonitorResultsImportDetailHistoryService monitorResultsImportDetailHistoryService;

    @PostMapping("/create")
    @Operation(summary = "创建设备监测结果导入信息历史")
    @PreAuthorize("@ss.hasPermission('traffic:monitor-results-import-detail-history:create')")
    public CommonResult<String> createMonitorResultsImportDetailHistory(@Valid @RequestBody TrafficMonitorResultsImportDetailHistorySaveReqVO createReqVO) {
        return success(monitorResultsImportDetailHistoryService.createMonitorResultsImportDetailHistory(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新设备监测结果导入信息历史")
    @PreAuthorize("@ss.hasPermission('traffic:monitor-results-import-detail-history:update')")
    public CommonResult<Boolean> updateMonitorResultsImportDetailHistory(@Valid @RequestBody TrafficMonitorResultsImportDetailHistorySaveReqVO updateReqVO) {
        monitorResultsImportDetailHistoryService.updateMonitorResultsImportDetailHistory(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除设备监测结果导入信息历史")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('traffic:monitor-results-import-detail-history:delete')")
    public CommonResult<Boolean> deleteMonitorResultsImportDetailHistory(@RequestParam("id") String id) {
        monitorResultsImportDetailHistoryService.deleteMonitorResultsImportDetailHistory(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得设备监测结果导入信息历史")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('traffic:monitor-results-import-detail-history:query')")
    public CommonResult<TrafficMonitorResultsImportDetailHistoryRespVO> getMonitorResultsImportDetailHistory(@RequestParam("id") String id) {
        TrafficMonitorResultsImportDetailHistoryDO monitorResultsImportDetailHistory = monitorResultsImportDetailHistoryService.getMonitorResultsImportDetailHistory(id);
        return success(BeanUtils.toBean(monitorResultsImportDetailHistory, TrafficMonitorResultsImportDetailHistoryRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得设备监测结果导入信息历史分页")
    @PreAuthorize("@ss.hasPermission('traffic:monitor-results-import-detail-history:query')")
    public CommonResult<PageResult<TrafficMonitorResultsImportDetailHistoryRespVO>> getMonitorResultsImportDetailHistoryPage(@Valid TrafficMonitorResultsImportDetailHistoryPageReqVO pageReqVO) {
        PageResult<TrafficMonitorResultsImportDetailHistoryDO> pageResult = monitorResultsImportDetailHistoryService.getMonitorResultsImportDetailHistoryPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, TrafficMonitorResultsImportDetailHistoryRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出设备监测结果导入信息历史 Excel")
    @PreAuthorize("@ss.hasPermission('traffic:monitor-results-import-detail-history:export')")
    public void exportMonitorResultsImportDetailHistoryExcel(@Valid TrafficMonitorResultsImportDetailHistoryPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<TrafficMonitorResultsImportDetailHistoryDO> list = monitorResultsImportDetailHistoryService.getMonitorResultsImportDetailHistoryPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "设备监测结果导入信息历史.xls", "数据", TrafficMonitorResultsImportDetailHistoryRespVO.class,
                        BeanUtils.toBean(list, TrafficMonitorResultsImportDetailHistoryRespVO.class));
    }

}