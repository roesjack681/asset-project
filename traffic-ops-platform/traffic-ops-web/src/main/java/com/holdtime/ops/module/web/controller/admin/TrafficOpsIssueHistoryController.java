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
import com.holdtime.ops.module.entity.TrafficOpsIssueHistoryDO;
import com.holdtime.ops.module.service.TrafficOpsIssueHistoryService;

@Tag(name = "管理后台 - 警用装备历史领用记录")
@RestController
@RequestMapping("/traffic/ops-issue-history")
@Validated
public class TrafficOpsIssueHistoryController {

    @Resource
    private TrafficOpsIssueHistoryService opsIssueHistoryService;

    @PostMapping("/create")
    @Operation(summary = "创建警用装备历史领用记录")
    @PreAuthorize("@ss.hasPermission('traffic:ops-issue-history:create')")
    public CommonResult<String> createOpsIssueHistory(@Valid @RequestBody TrafficOpsIssueHistorySaveReqVO createReqVO) {
        return success(opsIssueHistoryService.createOpsIssueHistory(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新警用装备历史领用记录")
    @PreAuthorize("@ss.hasPermission('traffic:ops-issue-history:update')")
    public CommonResult<Boolean> updateOpsIssueHistory(@Valid @RequestBody TrafficOpsIssueHistorySaveReqVO updateReqVO) {
        opsIssueHistoryService.updateOpsIssueHistory(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除警用装备历史领用记录")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('traffic:ops-issue-history:delete')")
    public CommonResult<Boolean> deleteOpsIssueHistory(@RequestParam("id") String id) {
        opsIssueHistoryService.deleteOpsIssueHistory(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得警用装备历史领用记录")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('traffic:ops-issue-history:query')")
    public CommonResult<TrafficOpsIssueHistoryRespVO> getOpsIssueHistory(@RequestParam("id") String id) {
        TrafficOpsIssueHistoryDO opsIssueHistory = opsIssueHistoryService.getOpsIssueHistory(id);
        return success(BeanUtils.toBean(opsIssueHistory, TrafficOpsIssueHistoryRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得警用装备历史领用记录分页")
    @PreAuthorize("@ss.hasPermission('traffic:ops-issue-history:query')")
    public CommonResult<PageResult<TrafficOpsIssueHistoryRespVO>> getOpsIssueHistoryPage(@Valid TrafficOpsIssueHistoryPageReqVO pageReqVO) {
        PageResult<TrafficOpsIssueHistoryDO> pageResult = opsIssueHistoryService.getOpsIssueHistoryPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, TrafficOpsIssueHistoryRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出警用装备历史领用记录 Excel")
    @PreAuthorize("@ss.hasPermission('traffic:ops-issue-history:export')")
    public void exportOpsIssueHistoryExcel(@Valid TrafficOpsIssueHistoryPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<TrafficOpsIssueHistoryDO> list = opsIssueHistoryService.getOpsIssueHistoryPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "警用装备历史领用记录.xls", "数据", TrafficOpsIssueHistoryRespVO.class,
                        BeanUtils.toBean(list, TrafficOpsIssueHistoryRespVO.class));
    }

}