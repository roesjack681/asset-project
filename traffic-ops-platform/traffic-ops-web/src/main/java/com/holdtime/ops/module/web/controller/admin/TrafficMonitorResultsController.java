package com.holdtime.ops.module.web.controller.admin;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.holdtime.ops.module.model.dto.TrafficOpsMemeberCameraDTO;
import com.holdtime.ops.module.service.TrafficMonitorResultsExtService;
import org.apache.commons.lang3.StringUtils;
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
import com.holdtime.ops.module.entity.TrafficMonitorResultsDO;
import com.holdtime.ops.module.service.TrafficMonitorResultsService;

@Tag(name = "管理后台 - 设备监测结果")
@RestController
@RequestMapping("/traffic/monitor-results")
@Validated
public class TrafficMonitorResultsController {

    @Resource
    private TrafficMonitorResultsService monitorResultsService;
    @Resource
    private TrafficMonitorResultsExtService monitorResultsExtService;

    @PostMapping("/create")
    @Operation(summary = "创建设备监测结果")
    @PreAuthorize("@ss.hasPermission('traffic:monitor-results:create')")
    public CommonResult<String> createMonitorResults(@Valid @RequestBody TrafficMonitorResultsSaveReqVO createReqVO) {
        return success(monitorResultsService.createMonitorResults(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新设备监测结果")
    @PreAuthorize("@ss.hasPermission('traffic:monitor-results:update')")
    public CommonResult<Boolean> updateMonitorResults(@Valid @RequestBody TrafficMonitorResultsSaveReqVO updateReqVO) {
        monitorResultsService.updateMonitorResults(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除设备监测结果")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('traffic:monitor-results:delete')")
    public CommonResult<Boolean> deleteMonitorResults(@RequestParam("id") String id) {
        monitorResultsService.deleteMonitorResults(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得设备监测结果")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('traffic:monitor-results:query')")
    public CommonResult<TrafficMonitorResultsRespVO> getMonitorResults(@RequestParam("id") String id) {
        TrafficMonitorResultsDO monitorResults = monitorResultsService.getMonitorResults(id);
        return success(BeanUtils.toBean(monitorResults, TrafficMonitorResultsRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得设备监测结果分页")
    @PreAuthorize("@ss.hasPermission('traffic:monitor-results:query')")
    public CommonResult<PageResult<TrafficMonitorResultsRespVO>> getMonitorResultsPage(@Valid TrafficMonitorResultsPageReqVO pageReqVO) {
        PageResult<TrafficMonitorResultsDO> pageResult = monitorResultsService.getMonitorResultsPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, TrafficMonitorResultsRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出设备监测结果 Excel")
    @PreAuthorize("@ss.hasPermission('traffic:monitor-results:export')")
    public void exportMonitorResultsExcel(@Valid TrafficMonitorResultsPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<TrafficMonitorResultsDO> list = monitorResultsService.getMonitorResultsPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "设备监测结果.xls", "数据", TrafficMonitorResultsRespVO.class,
                        BeanUtils.toBean(list, TrafficMonitorResultsRespVO.class));
    }

    @GetMapping("/analysisMonitorResultPage")
    @Operation(summary = "获得设备监测结果分页")
    public CommonResult<PageResult<TrafficOpsMemeberCameraDTO>> analysisMonitorResultPage(@Valid TrafficMonitorResultsAnalysisPageReqVO pageReqVO) {
        Page<?> page = new Page<>(pageReqVO.getPageNo(), pageReqVO.getPageSize());
        if (StringUtils.isBlank(pageReqVO.getStartDate())) {
            pageReqVO.setStartDate("2025-07-20");
        }

        if (StringUtils.isBlank(pageReqVO.getEndDate())) {
            pageReqVO.setEndDate(DateUtil.format(new Date(), "yyyy-MM-dd"));
        }
        return success(monitorResultsExtService.analysisMonitorResult(page, pageReqVO.getStartDate(), pageReqVO.getEndDate()));
    }

}