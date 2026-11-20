package com.holdtime.ops.module.web.controller.admin;

import com.holdtime.ops.module.model.dto.ExcelImportPoliceMonitorResultsDTO;
import com.holdtime.ops.module.model.dto.ExcelImportTrafficMonitorResultsDTO;
import com.holdtime.ops.module.service.PoliceMonitorResultsImportAnalysisExtService;
import com.holdtime.ops.module.service.impl.ExcelImportExtServiceImpl;
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
import com.holdtime.ops.module.entity.PoliceMonitorResultsImportAnalysisDO;
import com.holdtime.ops.module.service.PoliceMonitorResultsImportAnalysisService;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "管理后台 - 公安监测结果导入分析")
@RestController
@RequestMapping("/police/monitor-results-import-analysis")
@Validated
public class PoliceMonitorResultsImportAnalysisController {

    @Resource
    private PoliceMonitorResultsImportAnalysisService monitorResultsImportAnalysisService;
    @Resource
    private PoliceMonitorResultsImportAnalysisExtService policeMonitorResultsImportAnalysisExtService;
    @Resource
    private ExcelImportExtServiceImpl excelImportExtService;

    @PostMapping("/create")
    @Operation(summary = "创建公安监测结果导入分析")
    @PreAuthorize("@ss.hasPermission('police:monitor-results-import-analysis:create')")
    public CommonResult<String> createMonitorResultsImportAnalysis(@Valid @RequestBody PoliceMonitorResultsImportAnalysisSaveReqVO createReqVO) {
        return success(monitorResultsImportAnalysisService.createMonitorResultsImportAnalysis(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新公安监测结果导入分析")
    @PreAuthorize("@ss.hasPermission('police:monitor-results-import-analysis:update')")
    public CommonResult<Boolean> updateMonitorResultsImportAnalysis(@Valid @RequestBody PoliceMonitorResultsImportAnalysisSaveReqVO updateReqVO) {
        monitorResultsImportAnalysisService.updateMonitorResultsImportAnalysis(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除公安监测结果导入分析")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('police:monitor-results-import-analysis:delete')")
    public CommonResult<Boolean> deleteMonitorResultsImportAnalysis(@RequestParam("id") String id) {
        monitorResultsImportAnalysisService.deleteMonitorResultsImportAnalysis(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得公安监测结果导入分析")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('police:monitor-results-import-analysis:query')")
    public CommonResult<PoliceMonitorResultsImportAnalysisRespVO> getMonitorResultsImportAnalysis(@RequestParam("id") String id) {
        PoliceMonitorResultsImportAnalysisDO monitorResultsImportAnalysis = monitorResultsImportAnalysisService.getMonitorResultsImportAnalysis(id);
        return success(BeanUtils.toBean(monitorResultsImportAnalysis, PoliceMonitorResultsImportAnalysisRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得公安监测结果导入分析分页")
    @PreAuthorize("@ss.hasPermission('police:monitor-results-import-analysis:query')")
    public CommonResult<PageResult<PoliceMonitorResultsImportAnalysisExtRespVO>> getMonitorResultsImportAnalysisPage(@Valid PoliceMonitorResultsImportAnalysisExtPageReqVO pageReqVO) {
        return success(policeMonitorResultsImportAnalysisExtService.getMonitorResultsImportAnalysisPage(pageReqVO));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出公安监测结果导入分析 Excel")
    @PreAuthorize("@ss.hasPermission('police:monitor-results-import-analysis:export')")
    public void exportMonitorResultsImportAnalysisExcel(@Valid PoliceMonitorResultsImportAnalysisPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<PoliceMonitorResultsImportAnalysisDO> list = monitorResultsImportAnalysisService.getMonitorResultsImportAnalysisPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "公安监测结果导入分析.xls", "数据", PoliceMonitorResultsImportAnalysisRespVO.class,
                        BeanUtils.toBean(list, PoliceMonitorResultsImportAnalysisRespVO.class));
    }

    @PostMapping("/importPoliceMonitorResultsInfo")
    @Operation(summary = "导入交警检测结果信息")
    public CommonResult<ImportRecordRespVO> importPoliceMonitorResultsInfo(@RequestParam("file") MultipartFile file) {
        return success(excelImportExtService.importExcelInfo("华智平台电警检测导入", file, ExcelImportPoliceMonitorResultsDTO.class));
    }

}