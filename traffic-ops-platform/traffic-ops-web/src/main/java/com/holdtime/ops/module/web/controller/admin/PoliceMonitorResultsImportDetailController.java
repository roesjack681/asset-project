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
import com.holdtime.ops.module.entity.PoliceMonitorResultsImportDetailDO;
import com.holdtime.ops.module.service.PoliceMonitorResultsImportDetailService;

@Tag(name = "管理后台 - 公安监测结果导入详情")
@RestController
@RequestMapping("/police/monitor-results-import-detail")
@Validated
public class PoliceMonitorResultsImportDetailController {

    @Resource
    private PoliceMonitorResultsImportDetailService monitorResultsImportDetailService;

    @PostMapping("/create")
    @Operation(summary = "创建公安监测结果导入详情")
    @PreAuthorize("@ss.hasPermission('police:monitor-results-import-detail:create')")
    public CommonResult<String> createMonitorResultsImportDetail(@Valid @RequestBody PoliceMonitorResultsImportDetailSaveReqVO createReqVO) {
        return success(monitorResultsImportDetailService.createMonitorResultsImportDetail(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新公安监测结果导入详情")
    @PreAuthorize("@ss.hasPermission('police:monitor-results-import-detail:update')")
    public CommonResult<Boolean> updateMonitorResultsImportDetail(@Valid @RequestBody PoliceMonitorResultsImportDetailSaveReqVO updateReqVO) {
        monitorResultsImportDetailService.updateMonitorResultsImportDetail(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除公安监测结果导入详情")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('police:monitor-results-import-detail:delete')")
    public CommonResult<Boolean> deleteMonitorResultsImportDetail(@RequestParam("id") String id) {
        monitorResultsImportDetailService.deleteMonitorResultsImportDetail(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得公安监测结果导入详情")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('police:monitor-results-import-detail:query')")
    public CommonResult<PoliceMonitorResultsImportDetailRespVO> getMonitorResultsImportDetail(@RequestParam("id") String id) {
        PoliceMonitorResultsImportDetailDO monitorResultsImportDetail = monitorResultsImportDetailService.getMonitorResultsImportDetail(id);
        return success(BeanUtils.toBean(monitorResultsImportDetail, PoliceMonitorResultsImportDetailRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得公安监测结果导入详情分页")
    public CommonResult<PageResult<PoliceMonitorResultsImportDetailRespVO>> getMonitorResultsImportDetailPage(@Valid PoliceMonitorResultsImportDetailPageReqVO pageReqVO) {
        PageResult<PoliceMonitorResultsImportDetailDO> pageResult = monitorResultsImportDetailService.getMonitorResultsImportDetailPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, PoliceMonitorResultsImportDetailRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出公安监测结果导入详情 Excel")
    @PreAuthorize("@ss.hasPermission('police:monitor-results-import-detail:export')")
    public void exportMonitorResultsImportDetailExcel(@Valid PoliceMonitorResultsImportDetailPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<PoliceMonitorResultsImportDetailDO> list = monitorResultsImportDetailService.getMonitorResultsImportDetailPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "公安监测结果导入详情.xls", "数据", PoliceMonitorResultsImportDetailRespVO.class,
                        BeanUtils.toBean(list, PoliceMonitorResultsImportDetailRespVO.class));
    }

}