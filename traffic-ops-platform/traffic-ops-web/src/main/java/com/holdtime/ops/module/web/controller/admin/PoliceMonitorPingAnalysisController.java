package com.holdtime.ops.module.web.controller.admin;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.holdtime.ops.module.enums.OpsConstants;
import com.holdtime.ops.module.model.dto.ExcelImportPoliceMonitorPingDetailDTO;
import com.holdtime.ops.module.model.dto.PoliceMonitorPingDetailDTO;
import com.holdtime.ops.module.service.ExcelImportExtService;
import com.holdtime.ops.module.service.PoliceMonitorPingAnalysisExtService;
import com.holdtime.ops.module.service.PoliceMonitorPingExtService;
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
import com.holdtime.ops.module.entity.PoliceMonitorPingAnalysisDO;
import com.holdtime.ops.module.service.PoliceMonitorPingAnalysisService;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "管理后台 - 电警通断检测管理")
@RestController
@RequestMapping("/police/monitor-ping-analysis")
@Validated
public class PoliceMonitorPingAnalysisController {

    @Resource
    private PoliceMonitorPingAnalysisService monitorPingAnalysisService;
    @Resource
    private PoliceMonitorPingExtService policeMonitorPingExtService;
    @Resource
    private PoliceMonitorPingAnalysisExtService policeMonitorPingAnalysisExtService;
    @Resource
    private ExcelImportExtService excelImportExtService;

    @PostMapping("/create")
    @Operation(summary = "创建电警通断检测管理")
    @PreAuthorize("@ss.hasPermission('police:monitor-ping-analysis:create')")
    public CommonResult<String> createMonitorPingAnalysis(@Valid @RequestBody PoliceMonitorPingAnalysisSaveReqVO createReqVO) {
        return success(monitorPingAnalysisService.createMonitorPingAnalysis(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新电警通断检测管理")
    @PreAuthorize("@ss.hasPermission('police:monitor-ping-analysis:update')")
    public CommonResult<Boolean> updateMonitorPingAnalysis(@Valid @RequestBody PoliceMonitorPingAnalysisSaveReqVO updateReqVO) {
        monitorPingAnalysisService.updateMonitorPingAnalysis(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除电警通断检测管理")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('police:monitor-ping-analysis:delete')")
    public CommonResult<Boolean> deleteMonitorPingAnalysis(@RequestParam("id") String id) {
        monitorPingAnalysisService.deleteMonitorPingAnalysis(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得电警通断检测管理")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('police:monitor-ping-analysis:query')")
    public CommonResult<PoliceMonitorPingAnalysisRespVO> getMonitorPingAnalysis(@RequestParam("id") String id) {
        PoliceMonitorPingAnalysisDO monitorPingAnalysis = monitorPingAnalysisService.getMonitorPingAnalysis(id);
        return success(BeanUtils.toBean(monitorPingAnalysis, PoliceMonitorPingAnalysisRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得电警通断检测管理分页")
    @PreAuthorize("@ss.hasPermission('police:monitor-ping-analysis:query')")
    public CommonResult<PageResult<PoliceMonitorPingAnalysisExtRespVO>> getMonitorPingAnalysisPage(@Valid PoliceMonitorPingAnalysisExtPageReqVO pageReqVO) {
        return success(policeMonitorPingAnalysisExtService.getMonitorPingAnalysisPage(pageReqVO));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出电警通断检测管理 Excel")
    @PreAuthorize("@ss.hasPermission('police:monitor-ping-analysis:export')")
    public void exportMonitorPingAnalysisExcel(@Valid PoliceMonitorPingAnalysisPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<PoliceMonitorPingAnalysisDO> list = monitorPingAnalysisService.getMonitorPingAnalysisPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "电警通断检测管理.xls", "数据", PoliceMonitorPingAnalysisRespVO.class,
                        BeanUtils.toBean(list, PoliceMonitorPingAnalysisRespVO.class));
    }

    @GetMapping("/pingPoliceOpsElectricPolice")
    @Operation(summary = "设备通断情况检测")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    public CommonResult<PoliceMonitorPingDetailExtRespVO> pingPoliceOpsElectricPolice(@RequestParam("equId") String equId) {
        return success(policeMonitorPingExtService.pingPoliceOpsElectricPolice(equId, OpsConstants.STRING_TWO));
    }


    @GetMapping("/queryMonitorPingDetailResult")
    @Operation(summary = "查询监控通断检详情结果")
    public CommonResult<PageResult<PoliceMonitorPingDetailDTO>> queryMonitorPingDetailResult(@Valid PoliceMonitorPingDetailExtPageReqVO pageReqVO) {
        Page<?> page = new Page<>(pageReqVO.getPageNo(), pageReqVO.getPageSize());
        return success(policeMonitorPingAnalysisExtService.queryMonitorPingDetailResult(page, pageReqVO.getEquId(), pageReqVO.getStartDate(), pageReqVO.getEndDate()));
    }

    @PostMapping("/importPoliceMonitorPingDetail")
    @Operation(summary = "导入电警通断监测信息")
    public CommonResult<ImportRecordRespVO> importPoliceMonitorPingDetail(@RequestParam("file") MultipartFile file) {
        return success(excelImportExtService.importExcelInfo("公安电警通断监测", file, ExcelImportPoliceMonitorPingDetailDTO.class));
    }

}