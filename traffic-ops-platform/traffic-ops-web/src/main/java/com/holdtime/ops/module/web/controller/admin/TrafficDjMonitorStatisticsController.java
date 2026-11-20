package com.holdtime.ops.module.web.controller.admin;

import com.holdtime.ops.module.model.dto.ExcelImportExcelDjMonitorDTO;
import com.holdtime.ops.module.service.ExcelImportExtService;
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
import com.holdtime.ops.module.entity.TrafficDjMonitorStatisticsDO;
import com.holdtime.ops.module.service.TrafficDjMonitorStatisticsService;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "管理后台 - 监测电警设备统计")
@RestController
@RequestMapping("/traffic/dj-monitor-statistics")
@Validated
public class TrafficDjMonitorStatisticsController {

    @Resource
    private TrafficDjMonitorStatisticsService djMonitorStatisticsService;
    @Resource
    private ExcelImportExtService excelImportExtService;

    @PostMapping("/create")
    @Operation(summary = "创建监测电警设备统计")
    @PreAuthorize("@ss.hasPermission('traffic:dj-monitor-statistics:create')")
    public CommonResult<String> createDjMonitorStatistics(@Valid @RequestBody TrafficDjMonitorStatisticsSaveReqVO createReqVO) {
        return success(djMonitorStatisticsService.createDjMonitorStatistics(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新监测电警设备统计")
    @PreAuthorize("@ss.hasPermission('traffic:dj-monitor-statistics:update')")
    public CommonResult<Boolean> updateDjMonitorStatistics(@Valid @RequestBody TrafficDjMonitorStatisticsSaveReqVO updateReqVO) {
        djMonitorStatisticsService.updateDjMonitorStatistics(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除监测电警设备统计")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('traffic:dj-monitor-statistics:delete')")
    public CommonResult<Boolean> deleteDjMonitorStatistics(@RequestParam("id") String id) {
        djMonitorStatisticsService.deleteDjMonitorStatistics(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得监测电警设备统计")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('traffic:dj-monitor-statistics:query')")
    public CommonResult<TrafficDjMonitorStatisticsRespVO> getDjMonitorStatistics(@RequestParam("id") String id) {
        TrafficDjMonitorStatisticsDO djMonitorStatistics = djMonitorStatisticsService.getDjMonitorStatistics(id);
        return success(BeanUtils.toBean(djMonitorStatistics, TrafficDjMonitorStatisticsRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得监测电警设备统计分页")
    @PreAuthorize("@ss.hasPermission('traffic:dj-monitor-statistics:query')")
    public CommonResult<PageResult<TrafficDjMonitorStatisticsRespVO>> getDjMonitorStatisticsPage(@Valid TrafficDjMonitorStatisticsPageReqVO pageReqVO) {
        PageResult<TrafficDjMonitorStatisticsDO> pageResult = djMonitorStatisticsService.getDjMonitorStatisticsPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, TrafficDjMonitorStatisticsRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出监测电警设备统计 Excel")
    @PreAuthorize("@ss.hasPermission('traffic:dj-monitor-statistics:export')")
    public void exportDjMonitorStatisticsExcel(@Valid TrafficDjMonitorStatisticsPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<TrafficDjMonitorStatisticsDO> list = djMonitorStatisticsService.getDjMonitorStatisticsPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "监测电警设备统计.xls", "数据", TrafficDjMonitorStatisticsRespVO.class,
                        BeanUtils.toBean(list, TrafficDjMonitorStatisticsRespVO.class));
    }

    @PostMapping("/importDjMonitorInfo")
    @Operation(summary = "导入电警监测信息")
    public CommonResult<ImportRecordRespVO> importMaintenanceInfo(@RequestParam("file") MultipartFile file) {
        return success(excelImportExtService.importExcelInfo("电警监测", file, ExcelImportExcelDjMonitorDTO.class));
    }

}