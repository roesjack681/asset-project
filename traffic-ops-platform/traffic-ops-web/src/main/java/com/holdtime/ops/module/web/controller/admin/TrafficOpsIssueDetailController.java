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
import com.holdtime.ops.module.entity.TrafficOpsIssueDetailDO;
import com.holdtime.ops.module.service.TrafficOpsIssueDetailService;

@Tag(name = "管理后台 - 领用详情")
@RestController
@RequestMapping("/traffic/ops-issue-detail")
@Validated
public class TrafficOpsIssueDetailController {

    @Resource
    private TrafficOpsIssueDetailService opsIssueDetailService;

    @PostMapping("/create")
    @Operation(summary = "创建领用详情")
    @PreAuthorize("@ss.hasPermission('traffic:ops-issue-detail:create')")
    public CommonResult<String> createOpsIssueDetail(@Valid @RequestBody TrafficOpsIssueDetailSaveReqVO createReqVO) {
        return success(opsIssueDetailService.createOpsIssueDetail(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新领用详情")
    @PreAuthorize("@ss.hasPermission('traffic:ops-issue-detail:update')")
    public CommonResult<Boolean> updateOpsIssueDetail(@Valid @RequestBody TrafficOpsIssueDetailSaveReqVO updateReqVO) {
        opsIssueDetailService.updateOpsIssueDetail(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除领用详情")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('traffic:ops-issue-detail:delete')")
    public CommonResult<Boolean> deleteOpsIssueDetail(@RequestParam("id") String id) {
        opsIssueDetailService.deleteOpsIssueDetail(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得领用详情")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('traffic:ops-issue-detail:query')")
    public CommonResult<TrafficOpsIssueDetailRespVO> getOpsIssueDetail(@RequestParam("id") String id) {
        TrafficOpsIssueDetailDO opsIssueDetail = opsIssueDetailService.getOpsIssueDetail(id);
        return success(BeanUtils.toBean(opsIssueDetail, TrafficOpsIssueDetailRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得领用详情分页")
    @PreAuthorize("@ss.hasPermission('traffic:ops-issue-detail:query')")
    public CommonResult<PageResult<TrafficOpsIssueDetailRespVO>> getOpsIssueDetailPage(@Valid TrafficOpsIssueDetailPageReqVO pageReqVO) {
        PageResult<TrafficOpsIssueDetailDO> pageResult = opsIssueDetailService.getOpsIssueDetailPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, TrafficOpsIssueDetailRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出领用详情 Excel")
    @PreAuthorize("@ss.hasPermission('traffic:ops-issue-detail:export')")
    public void exportOpsIssueDetailExcel(@Valid TrafficOpsIssueDetailPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<TrafficOpsIssueDetailDO> list = opsIssueDetailService.getOpsIssueDetailPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "领用详情.xls", "数据", TrafficOpsIssueDetailRespVO.class,
                        BeanUtils.toBean(list, TrafficOpsIssueDetailRespVO.class));
    }

}