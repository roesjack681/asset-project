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
import com.holdtime.ops.module.entity.ViewPoliceMonitorResultsImportSituationDO;
import com.holdtime.ops.module.service.ViewPoliceMonitorResultsImportSituationService;

@Tag(name = "管理后台 - VIEW")
@RestController
@RequestMapping("/police/view-police-monitor-results-import-situation")
@Validated
public class ViewPoliceMonitorResultsImportSituationController {

    @Resource
    private ViewPoliceMonitorResultsImportSituationService viewPoliceMonitorResultsImportSituationService;

    @PostMapping("/create")
    @Operation(summary = "创建VIEW")
    @PreAuthorize("@ss.hasPermission('police:view-police-monitor-results-import-situation:create')")
    public CommonResult<String> createViewPoliceMonitorResultsImportSituation(@Valid @RequestBody ViewPoliceMonitorResultsImportSituationSaveReqVO createReqVO) {
        return success(viewPoliceMonitorResultsImportSituationService.createViewPoliceMonitorResultsImportSituation(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新VIEW")
    @PreAuthorize("@ss.hasPermission('police:view-police-monitor-results-import-situation:update')")
    public CommonResult<Boolean> updateViewPoliceMonitorResultsImportSituation(@Valid @RequestBody ViewPoliceMonitorResultsImportSituationSaveReqVO updateReqVO) {
        viewPoliceMonitorResultsImportSituationService.updateViewPoliceMonitorResultsImportSituation(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除VIEW")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('police:view-police-monitor-results-import-situation:delete')")
    public CommonResult<Boolean> deleteViewPoliceMonitorResultsImportSituation(@RequestParam("id") String id) {
        viewPoliceMonitorResultsImportSituationService.deleteViewPoliceMonitorResultsImportSituation(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得VIEW")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('police:view-police-monitor-results-import-situation:query')")
    public CommonResult<ViewPoliceMonitorResultsImportSituationRespVO> getViewPoliceMonitorResultsImportSituation(@RequestParam("id") String id) {
        ViewPoliceMonitorResultsImportSituationDO viewPoliceMonitorResultsImportSituation = viewPoliceMonitorResultsImportSituationService.getViewPoliceMonitorResultsImportSituation(id);
        return success(BeanUtils.toBean(viewPoliceMonitorResultsImportSituation, ViewPoliceMonitorResultsImportSituationRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得VIEW分页")
    @PreAuthorize("@ss.hasPermission('police:view-police-monitor-results-import-situation:query')")
    public CommonResult<PageResult<ViewPoliceMonitorResultsImportSituationRespVO>> getViewPoliceMonitorResultsImportSituationPage(@Valid ViewPoliceMonitorResultsImportSituationPageReqVO pageReqVO) {
        PageResult<ViewPoliceMonitorResultsImportSituationDO> pageResult = viewPoliceMonitorResultsImportSituationService.getViewPoliceMonitorResultsImportSituationPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, ViewPoliceMonitorResultsImportSituationRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出VIEW Excel")
    @PreAuthorize("@ss.hasPermission('police:view-police-monitor-results-import-situation:export')")
    public void exportViewPoliceMonitorResultsImportSituationExcel(@Valid ViewPoliceMonitorResultsImportSituationPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<ViewPoliceMonitorResultsImportSituationDO> list = viewPoliceMonitorResultsImportSituationService.getViewPoliceMonitorResultsImportSituationPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "VIEW.xls", "数据", ViewPoliceMonitorResultsImportSituationRespVO.class,
                        BeanUtils.toBean(list, ViewPoliceMonitorResultsImportSituationRespVO.class));
    }

}