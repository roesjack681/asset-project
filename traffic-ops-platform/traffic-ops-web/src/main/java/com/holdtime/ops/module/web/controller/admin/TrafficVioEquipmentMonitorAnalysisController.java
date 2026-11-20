package com.holdtime.ops.module.web.controller.admin;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.holdtime.ops.module.entity.*;
import com.holdtime.ops.module.model.dto.ExcelImportVioEquipmentDailySituationDTO;
import com.holdtime.ops.module.model.dto.ExcelImportVioIllegalDTO;
import com.holdtime.ops.module.model.dto.ExcelImportVioInvaliReasonDTO;
import com.holdtime.ops.module.service.*;
import org.apache.commons.lang3.StringUtils;
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
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "管理后台 - 集指设备检测分析")
@RestController
@RequestMapping("/traffic/vio-equipment-monitor-analysis")
@Validated
public class TrafficVioEquipmentMonitorAnalysisController {

    @Resource
    private TrafficVioEquipmentMonitorAnalysisService vioEquipmentMonitorAnalysisService;
    @Resource
    private BtjczhVioEquipmentDailySituationService btjczhVioEquipmentDailySituationService;
    @Resource
    private TrafficVioEquipmentMonitorDetailService vioEquipmentMonitorDetailService;
    @Resource
    private TrafficVioEquipmentMonitorAnalysisExtService trafficVioEquipmentMonitorAnalysisExtService;
    @Resource
    private ExcelImportExtService excelImportExtService;
    @Resource
    private TrafficVioEquipmentInvalidReasonAnalysisService vioEquipmentInvalidReasonAnalysisService;
    @Resource
    private TrafficVioEquipmentIllegalAnalysisService trafficVioEquipmentIllegalAnalysisService;

    @PostMapping("/create")
    @Operation(summary = "创建集指设备检测分析")
    @PreAuthorize("@ss.hasPermission('traffic:vio-equipment-monitor-analysis:create')")
    public CommonResult<String> createVioEquipmentMonitorAnalysis(@Valid @RequestBody TrafficVioEquipmentMonitorAnalysisSaveReqVO createReqVO) {
        return success(vioEquipmentMonitorAnalysisService.createVioEquipmentMonitorAnalysis(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新集指设备检测分析")
    @PreAuthorize("@ss.hasPermission('traffic:vio-equipment-monitor-analysis:update')")
    public CommonResult<Boolean> updateVioEquipmentMonitorAnalysis(@Valid @RequestBody TrafficVioEquipmentMonitorAnalysisSaveReqVO updateReqVO) {
        vioEquipmentMonitorAnalysisService.updateVioEquipmentMonitorAnalysis(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除集指设备检测分析")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('traffic:vio-equipment-monitor-analysis:delete')")
    public CommonResult<Boolean> deleteVioEquipmentMonitorAnalysis(@RequestParam("id") String id) {
        vioEquipmentMonitorAnalysisService.deleteVioEquipmentMonitorAnalysis(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得集指设备检测分析")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('traffic:vio-equipment-monitor-analysis:query')")
    public CommonResult<TrafficVioEquipmentMonitorAnalysisRespVO> getVioEquipmentMonitorAnalysis(@RequestParam("id") String id) {
        TrafficVioEquipmentMonitorAnalysisDO vioEquipmentMonitorAnalysis = vioEquipmentMonitorAnalysisService.getVioEquipmentMonitorAnalysis(id);
        return success(BeanUtils.toBean(vioEquipmentMonitorAnalysis, TrafficVioEquipmentMonitorAnalysisRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得集指设备检测分析分页")
    @PreAuthorize("@ss.hasPermission('traffic:vio-equipment-monitor-analysis:query')")
    public CommonResult<PageResult<TrafficVioEquipmentMonitorAnalysisExtRespVO>> getVioEquipmentMonitorAnalysisPage(@Valid TrafficVioEquipmentMonitorAnalysisExtPageReqVO pageReqVO) {
        return success(trafficVioEquipmentMonitorAnalysisExtService.getVioEquipmentMonitorAnalysisPage(pageReqVO));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出集指设备检测分析 Excel")
    @PreAuthorize("@ss.hasPermission('traffic:vio-equipment-monitor-analysis:export')")
    public void exportVioEquipmentMonitorAnalysisExcel(@Valid TrafficVioEquipmentMonitorAnalysisPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<TrafficVioEquipmentMonitorAnalysisDO> list = vioEquipmentMonitorAnalysisService.getVioEquipmentMonitorAnalysisPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "集指设备检测分析.xls", "数据", TrafficVioEquipmentMonitorAnalysisRespVO.class,
                        BeanUtils.toBean(list, TrafficVioEquipmentMonitorAnalysisRespVO.class));
    }

    @GetMapping("/pageDetail")
    @Operation(summary = "获得集指设备检测记录分页")
    public CommonResult<PageResult<TrafficVioEquipmentMonitorDetailRespVO>> getVioEquipmentMonitorDetailPage(@Valid TrafficVioEquipmentMonitorDetailPageReqVO pageReqVO) {
        PageResult<TrafficVioEquipmentMonitorDetailDO> pageResult = vioEquipmentMonitorDetailService.getVioEquipmentMonitorDetailPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, TrafficVioEquipmentMonitorDetailRespVO.class));
    }

    @GetMapping("/pageDailySituation")
    @Operation(summary = "获得违法抓拍设备每日情况分页")
    public CommonResult<PageResult<BtjczhVioEquipmentDailySituationRespVO>> getBtjczhVioEquipmentDailySituationPage(@Valid BtjczhVioEquipmentDailySituationPageReqVO pageReqVO) {
        PageResult<BtjczhVioEquipmentDailySituationDO> pageResult = btjczhVioEquipmentDailySituationService.getBtjczhVioEquipmentDailySituationPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, BtjczhVioEquipmentDailySituationRespVO.class));
    }

    @GetMapping("/exportDailySituation")
    @Operation(summary = "导出违法抓拍设备每日情况 Excel")
    public void exportBtjczhVioEquipmentDailySituationExcel(@Valid BtjczhVioEquipmentDailySituationPageReqVO pageReqVO,
                                                          HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<BtjczhVioEquipmentDailySituationDO> list = btjczhVioEquipmentDailySituationService.getBtjczhVioEquipmentDailySituationPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "集指设备每日情况.xls", "数据", BtjczhVioEquipmentDailySituationRespVO.class,
                BeanUtils.toBean(list, BtjczhVioEquipmentDailySituationRespVO.class));
    }

    @PostMapping("/importVioEquipmentDailySituation")
    @Operation(summary = "导入集指设备违法抓拍情况分析")
    public CommonResult<ImportRecordRespVO> importVioEquipmentDailySituation(@RequestParam("file") MultipartFile file) {
        return success(excelImportExtService.importExcelInfo("集指设备违法抓拍情况分析", file, ExcelImportVioEquipmentDailySituationDTO.class));
    }

    @GetMapping("/analysisVioInvalidReasonPieChart")
    @Operation(summary = "分析无效原因饼图")
    public CommonResult<List<Map<String, Collection<Integer>>>> analysisVioInvalidReasonPieChart(@RequestParam(value = "sbbh") String sbbh,
                                                                                                 @RequestParam(value = "analysisDay", required = false) String analysisDay) {
        if (StringUtils.isBlank(analysisDay)) {
            analysisDay = "";
        }
        return success(trafficVioEquipmentMonitorAnalysisExtService.analysisVioInvalidReasonPieChart(sbbh, analysisDay));
    }

    @GetMapping("/analysisVioValidLineChart")
    @Operation(summary = "有效违法折线图")
    @JsonInclude(JsonInclude.Include.ALWAYS)
    public CommonResult<List<Map<String, Object>>> analysisVioValidLineChart(@RequestParam(value = "sbbh") String sbbh) {
        return success(trafficVioEquipmentMonitorAnalysisExtService.analysisVioValidLineChart(sbbh));
    }

    @GetMapping("/pageInvalidReasonAnalysis")
    @Operation(summary = "获得集指设备错误原因分析分页")
    public CommonResult<PageResult<TrafficVioEquipmentInvalidReasonAnalysisRespVO>> getVioEquipmentInvalidReasonAnalysisPage(@Valid TrafficVioEquipmentInvalidReasonAnalysisPageReqVO pageReqVO) {
        PageResult<TrafficVioEquipmentInvalidReasonAnalysisDO> pageResult = vioEquipmentInvalidReasonAnalysisService.getVioEquipmentInvalidReasonAnalysisPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, TrafficVioEquipmentInvalidReasonAnalysisRespVO.class));
    }

    @GetMapping("/exportInvalidReasonAnalysis")
    @Operation(summary = "导出集指设备错误原因分析 Excel")
    public void exportVioEquipmentInvalidReasonAnalysisExcel(@Valid TrafficVioEquipmentInvalidReasonAnalysisPageReqVO pageReqVO,
                                                             HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<TrafficVioEquipmentInvalidReasonAnalysisDO> list = vioEquipmentInvalidReasonAnalysisService.getVioEquipmentInvalidReasonAnalysisPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "集指设备错误原因分析.xls", "数据", TrafficVioEquipmentInvalidReasonAnalysisRespVO.class,
                BeanUtils.toBean(list, TrafficVioEquipmentInvalidReasonAnalysisRespVO.class));
    }

    @PostMapping("/importInvalidReasonAnalysis")
    @Operation(summary = "导入集指设备违法无效原因分析")
    public CommonResult<ImportRecordRespVO> importInvalidReasonAnalysis(@RequestParam("file") MultipartFile file) {
        return success(excelImportExtService.importExcelInfo("集指设备违法无效原因分析", file, ExcelImportVioInvaliReasonDTO.class));
    }

    @PostMapping("/remarkVioEquipmentMonitor")
    @Operation(summary = "备注抓拍设备情况")
    public CommonResult<Boolean> remarkVioEquipmentMonitor(@Valid @RequestBody TrafficVioEquipmentMonitorRemarkSaveReqVO saveReqVO) {
        trafficVioEquipmentMonitorAnalysisExtService.remarkVioEquipmentMonitor(saveReqVO);
        return success(true);
    }

    @GetMapping("/pageIllegalAnalysis")
    @Operation(summary = "获得集指设备违法原因分析分页")
    public CommonResult<PageResult<TrafficVioEquipmentIllegalAnalysisRespVO>> getVioEquipmentIllegalAnalysisPage(@Valid TrafficVioEquipmentIllegalAnalysisPageReqVO pageReqVO) {
        PageResult<TrafficVioEquipmentIllegalAnalysisDO> pageResult = trafficVioEquipmentIllegalAnalysisService.getVioEquipmentIllegalAnalysisPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, TrafficVioEquipmentIllegalAnalysisRespVO.class));
    }

    @GetMapping("/exportIllegalAnalysis")
    @Operation(summary = "集指设备有效违法行为分析 Excel")
    public void exportVioEquipmentIllegalAnalysisExcel(@Valid TrafficVioEquipmentIllegalAnalysisPageReqVO pageReqVO,
                                                       HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<TrafficVioEquipmentIllegalAnalysisDO> list = trafficVioEquipmentIllegalAnalysisService.getVioEquipmentIllegalAnalysisPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "集指设备有效违法行为分析.xls", "数据", TrafficVioEquipmentIllegalAnalysisRespVO.class,
                BeanUtils.toBean(list, TrafficVioEquipmentIllegalAnalysisRespVO.class));
    }

    @PostMapping("/importIllegalAnalysis")
    @Operation(summary = "导入集指设备违法行为分析")
    public CommonResult<ImportRecordRespVO> importIllegalAnalysis(@RequestParam("file") MultipartFile file) {
        return success(excelImportExtService.importExcelInfo("集指设备有效违法行为分析", file, ExcelImportVioIllegalDTO.class));
    }

    @GetMapping("/analysisVioIllegalPieChart")
    @Operation(summary = "分析有效违法行为饼图")
    public CommonResult<List<Map<String, Collection<Integer>>>> analysisVioIllegalPieChart(@RequestParam(value = "sbbh") String sbbh,
                                                                                                 @RequestParam(value = "analysisDay", required = false) String analysisDay) {
        if (StringUtils.isBlank(analysisDay)) {
            analysisDay = "";
        }
        return success(trafficVioEquipmentMonitorAnalysisExtService.analysisVioIllegalPieChart(sbbh, analysisDay));
    }

}