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
import com.holdtime.ops.module.entity.TrafficMonitorResultsHistoryDO;
import com.holdtime.ops.module.service.TrafficMonitorResultsHistoryService;

@Tag(name = "管理后台 - 设备监测结果历史")
@RestController
@RequestMapping("/traffic/monitor-results-history")
@Validated
public class TrafficMonitorResultsHistoryController {

    @Resource
    private TrafficMonitorResultsHistoryService monitorResultsHistoryService;

    @PostMapping("/create")
    @Operation(summary = "创建设备监测结果历史")
    @PreAuthorize("@ss.hasPermission('traffic:monitor-results-history:create')")
    public CommonResult<String> createMonitorResultsHistory(@Valid @RequestBody TrafficMonitorResultsHistorySaveReqVO createReqVO) {
        return success(monitorResultsHistoryService.createMonitorResultsHistory(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新设备监测结果历史")
    @PreAuthorize("@ss.hasPermission('traffic:monitor-results-history:update')")
    public CommonResult<Boolean> updateMonitorResultsHistory(@Valid @RequestBody TrafficMonitorResultsHistorySaveReqVO updateReqVO) {
        monitorResultsHistoryService.updateMonitorResultsHistory(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除设备监测结果历史")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('traffic:monitor-results-history:delete')")
    public CommonResult<Boolean> deleteMonitorResultsHistory(@RequestParam("id") String id) {
        monitorResultsHistoryService.deleteMonitorResultsHistory(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得设备监测结果历史")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('traffic:monitor-results-history:query')")
    public CommonResult<TrafficMonitorResultsHistoryRespVO> getMonitorResultsHistory(@RequestParam("id") String id) {
        TrafficMonitorResultsHistoryDO monitorResultsHistory = monitorResultsHistoryService.getMonitorResultsHistory(id);
        return success(BeanUtils.toBean(monitorResultsHistory, TrafficMonitorResultsHistoryRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得设备监测结果历史分页")
    @PreAuthorize("@ss.hasPermission('traffic:monitor-results-history:query')")
    public CommonResult<PageResult<TrafficMonitorResultsHistoryRespVO>> getMonitorResultsHistoryPage(@Valid TrafficMonitorResultsHistoryPageReqVO pageReqVO) {
        PageResult<TrafficMonitorResultsHistoryDO> pageResult = monitorResultsHistoryService.getMonitorResultsHistoryPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, TrafficMonitorResultsHistoryRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出设备监测结果历史 Excel")
    @PreAuthorize("@ss.hasPermission('traffic:monitor-results-history:export')")
    public void exportMonitorResultsHistoryExcel(@Valid TrafficMonitorResultsHistoryPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<TrafficMonitorResultsHistoryDO> list = monitorResultsHistoryService.getMonitorResultsHistoryPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "设备监测结果历史.xls", "数据", TrafficMonitorResultsHistoryRespVO.class,
                        BeanUtils.toBean(list, TrafficMonitorResultsHistoryRespVO.class));
    }

}