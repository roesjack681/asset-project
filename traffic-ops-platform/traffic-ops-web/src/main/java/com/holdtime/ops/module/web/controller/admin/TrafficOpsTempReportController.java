package com.holdtime.ops.module.web.controller.admin;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.holdtime.ops.module.entity.mapper.TrafficOpsTempReportMapper;
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
import com.holdtime.ops.module.entity.TrafficOpsTempReportDO;
import com.holdtime.ops.module.service.TrafficOpsTempReportService;

@Tag(name = "管理后台 - 交警运维临时报")
@RestController
@RequestMapping("/traffic/ops-temp-report")
@Validated
public class TrafficOpsTempReportController {

    @Resource
    private TrafficOpsTempReportService opsTempReportService;
    @Resource
    private TrafficOpsTempReportMapper opsTempReportMapper;

    @PostMapping("/create")
    @Operation(summary = "创建交警运维临时报")
    @PreAuthorize("@ss.hasPermission('traffic:ops-temp-report:create')")
    public CommonResult<String> createOpsTempReport(@Valid @RequestBody TrafficOpsTempReportSaveReqVO createReqVO) {
        return success(opsTempReportService.createOpsTempReport(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新交警运维临时报")
    @PreAuthorize("@ss.hasPermission('traffic:ops-temp-report:update')")
    public CommonResult<Boolean> updateOpsTempReport(@Valid @RequestBody TrafficOpsTempReportSaveReqVO updateReqVO) {
        opsTempReportService.updateOpsTempReport(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除交警运维临时报")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('traffic:ops-temp-report:delete')")
    public CommonResult<Boolean> deleteOpsTempReport(@RequestParam("id") String id) {
        opsTempReportService.deleteOpsTempReport(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得交警运维临时报")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('traffic:ops-temp-report:query')")
    public CommonResult<TrafficOpsTempReportRespVO> getOpsTempReport(@RequestParam("id") String id) {
        TrafficOpsTempReportDO opsTempReport = opsTempReportService.getOpsTempReport(id);
        return success(BeanUtils.toBean(opsTempReport, TrafficOpsTempReportRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得交警运维临时报分页")
    @PreAuthorize("@ss.hasPermission('traffic:ops-temp-report:query')")
    public CommonResult<PageResult<TrafficOpsTempReportRespVO>> getOpsTempReportPage(@Valid TrafficOpsTempReportPageReqVO pageReqVO) {
        PageResult<TrafficOpsTempReportDO> pageResult = opsTempReportService.getOpsTempReportPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, TrafficOpsTempReportRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出交警运维临时报 Excel")
    @PreAuthorize("@ss.hasPermission('traffic:ops-temp-report:export')")
    public void exportOpsTempReportExcel(@Valid TrafficOpsTempReportPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<TrafficOpsTempReportDO> list = opsTempReportService.getOpsTempReportPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "交警运维临时报.xls", "数据", TrafficOpsTempReportRespVO.class,
                        BeanUtils.toBean(list, TrafficOpsTempReportRespVO.class));
    }

    @DeleteMapping("/clearAll")
    @Operation(summary = "清空交警运维临时报")
    @PreAuthorize("@ss.hasPermission('traffic:ops-temp-report:clear')")
    public CommonResult<Boolean> deleteOpsTempReport() {
        opsTempReportMapper.delete(new LambdaUpdateWrapper<>());
        return success(true);
    }

}