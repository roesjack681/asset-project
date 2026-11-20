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
import com.holdtime.ops.module.entity.PoliceMonitorResultsImportSituationDO;
import com.holdtime.ops.module.service.PoliceMonitorResultsImportSituationService;

@Tag(name = "管理后台 - 公安监测结果导入近两日状态")
@RestController
@RequestMapping("/police/monitor-results-import-situation")
@Validated
public class PoliceMonitorResultsImportSituationController {

    @Resource
    private PoliceMonitorResultsImportSituationService monitorResultsImportSituationService;

    @PostMapping("/create")
    @Operation(summary = "创建公安监测结果导入近两日状态")
    @PreAuthorize("@ss.hasPermission('police:monitor-results-import-situation:create')")
    public CommonResult<String> createMonitorResultsImportSituation(@Valid @RequestBody PoliceMonitorResultsImportSituationSaveReqVO createReqVO) {
        return success(monitorResultsImportSituationService.createMonitorResultsImportSituation(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新公安监测结果导入近两日状态")
    @PreAuthorize("@ss.hasPermission('police:monitor-results-import-situation:update')")
    public CommonResult<Boolean> updateMonitorResultsImportSituation(@Valid @RequestBody PoliceMonitorResultsImportSituationSaveReqVO updateReqVO) {
        monitorResultsImportSituationService.updateMonitorResultsImportSituation(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除公安监测结果导入近两日状态")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('police:monitor-results-import-situation:delete')")
    public CommonResult<Boolean> deleteMonitorResultsImportSituation(@RequestParam("id") String id) {
        monitorResultsImportSituationService.deleteMonitorResultsImportSituation(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得公安监测结果导入近两日状态")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('police:monitor-results-import-situation:query')")
    public CommonResult<PoliceMonitorResultsImportSituationRespVO> getMonitorResultsImportSituation(@RequestParam("id") String id) {
        PoliceMonitorResultsImportSituationDO monitorResultsImportSituation = monitorResultsImportSituationService.getMonitorResultsImportSituation(id);
        return success(BeanUtils.toBean(monitorResultsImportSituation, PoliceMonitorResultsImportSituationRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得公安监测结果导入近两日状态分页")
    @PreAuthorize("@ss.hasPermission('police:monitor-results-import-situation:query')")
    public CommonResult<PageResult<PoliceMonitorResultsImportSituationRespVO>> getMonitorResultsImportSituationPage(@Valid PoliceMonitorResultsImportSituationPageReqVO pageReqVO) {
        PageResult<PoliceMonitorResultsImportSituationDO> pageResult = monitorResultsImportSituationService.getMonitorResultsImportSituationPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, PoliceMonitorResultsImportSituationRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出公安监测结果导入近两日状态 Excel")
    @PreAuthorize("@ss.hasPermission('police:monitor-results-import-situation:export')")
    public void exportMonitorResultsImportSituationExcel(@Valid PoliceMonitorResultsImportSituationPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<PoliceMonitorResultsImportSituationDO> list = monitorResultsImportSituationService.getMonitorResultsImportSituationPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "公安监测结果导入近两日状态.xls", "数据", PoliceMonitorResultsImportSituationRespVO.class,
                        BeanUtils.toBean(list, PoliceMonitorResultsImportSituationRespVO.class));
    }

}