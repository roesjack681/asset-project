package com.holdtime.ops.module.web.controller.admin;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.framework.excel.core.util.ExcelUtils;
import com.holdtime.ops.module.entity.PoliceMonitorResultsImportAnalysisDO;
import com.holdtime.ops.module.model.vo.*;
import com.holdtime.ops.module.service.PoliceMonitorResultsImportAnalysisExtService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import java.io.IOException;
import java.util.List;

import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

@Tag(name = "管理后台 - 公安监测结果导入分析")
@RestController
@RequestMapping("/police/monitor-results-huazhi-analysis")
@Validated
public class PoliceMonitorResultsHuaZhiAnalysisController {
    @Resource
    private PoliceMonitorResultsImportAnalysisExtService policeMonitorResultsImportAnalysisExtService;


    @GetMapping("/pageOnOff")
    @Operation(summary = "获得公安华智监测通断结果导入分析分页")
    public CommonResult<PageResult<PoliceMonitorResultsImportAnalysisExtRespVO>> queryHuazhiMonitorOnOffResultPage(@Valid PoliceMonitorResultsImportAnalysisExtPageReqVO pageReqVO) {
        return success(policeMonitorResultsImportAnalysisExtService.queryHuazhiMonitorOnOffResultPage(pageReqVO));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出公安监测结果导入分析 Excel")
    public void exportMonitorResultsHuazhiAnalysisExcel(@Valid PoliceMonitorResultsImportAnalysisExtPageReqVO pageReqVO,
                                                        HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<PoliceMonitorResultsImportAnalysisExtRespVO> list = policeMonitorResultsImportAnalysisExtService.queryHuazhiMonitorOnOffResultPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "华智监测通断分析.xls", "数据", PoliceMonitorResultsImportAnalysisExtRespVO.class, list);
    }


}