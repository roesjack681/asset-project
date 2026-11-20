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
import com.holdtime.ops.module.entity.TrafficOpsPoliceMaintenanceDetailDO;
import com.holdtime.ops.module.service.TrafficOpsPoliceMaintenanceDetailService;

@Tag(name = "管理后台 - 警用装备报修详情信息")
@RestController
@RequestMapping("/traffic/ops-police-maintenance-detail")
@Validated
public class TrafficOpsPoliceMaintenanceDetailController {

    @Resource
    private TrafficOpsPoliceMaintenanceDetailService opsPoliceMaintenanceDetailService;

    @PostMapping("/create")
    @Operation(summary = "创建警用装备报修详情信息")
    @PreAuthorize("@ss.hasPermission('traffic:ops-police-maintenance-detail:create')")
    public CommonResult<String> createOpsPoliceMaintenanceDetail(@Valid @RequestBody TrafficOpsPoliceMaintenanceDetailSaveReqVO createReqVO) {
        return success(opsPoliceMaintenanceDetailService.createOpsPoliceMaintenanceDetail(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新警用装备报修详情信息")
    @PreAuthorize("@ss.hasPermission('traffic:ops-police-maintenance-detail:update')")
    public CommonResult<Boolean> updateOpsPoliceMaintenanceDetail(@Valid @RequestBody TrafficOpsPoliceMaintenanceDetailSaveReqVO updateReqVO) {
        opsPoliceMaintenanceDetailService.updateOpsPoliceMaintenanceDetail(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除警用装备报修详情信息")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('traffic:ops-police-maintenance-detail:delete')")
    public CommonResult<Boolean> deleteOpsPoliceMaintenanceDetail(@RequestParam("id") String id) {
        opsPoliceMaintenanceDetailService.deleteOpsPoliceMaintenanceDetail(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得警用装备报修详情信息")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('traffic:ops-police-maintenance-detail:query')")
    public CommonResult<TrafficOpsPoliceMaintenanceDetailRespVO> getOpsPoliceMaintenanceDetail(@RequestParam("id") String id) {
        TrafficOpsPoliceMaintenanceDetailDO opsPoliceMaintenanceDetail = opsPoliceMaintenanceDetailService.getOpsPoliceMaintenanceDetail(id);
        return success(BeanUtils.toBean(opsPoliceMaintenanceDetail, TrafficOpsPoliceMaintenanceDetailRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得警用装备报修详情信息分页")
    @PreAuthorize("@ss.hasPermission('traffic:ops-police-maintenance-detail:query')")
    public CommonResult<PageResult<TrafficOpsPoliceMaintenanceDetailRespVO>> getOpsPoliceMaintenanceDetailPage(@Valid TrafficOpsPoliceMaintenanceDetailPageReqVO pageReqVO) {
        PageResult<TrafficOpsPoliceMaintenanceDetailDO> pageResult = opsPoliceMaintenanceDetailService.getOpsPoliceMaintenanceDetailPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, TrafficOpsPoliceMaintenanceDetailRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出警用装备报修详情信息 Excel")
    @PreAuthorize("@ss.hasPermission('traffic:ops-police-maintenance-detail:export')")
    public void exportOpsPoliceMaintenanceDetailExcel(@Valid TrafficOpsPoliceMaintenanceDetailPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<TrafficOpsPoliceMaintenanceDetailDO> list = opsPoliceMaintenanceDetailService.getOpsPoliceMaintenanceDetailPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "警用装备报修详情信息.xls", "数据", TrafficOpsPoliceMaintenanceDetailRespVO.class,
                        BeanUtils.toBean(list, TrafficOpsPoliceMaintenanceDetailRespVO.class));
    }

}