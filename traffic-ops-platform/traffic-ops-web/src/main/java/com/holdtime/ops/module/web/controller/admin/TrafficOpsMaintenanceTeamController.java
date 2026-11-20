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
import com.holdtime.ops.module.entity.TrafficOpsMaintenanceTeamDO;
import com.holdtime.ops.module.service.TrafficOpsMaintenanceTeamService;

@Tag(name = "管理后台 - 设备维保组员信息")
@RestController
@RequestMapping("/traffic/ops-maintenance-team")
@Validated
public class TrafficOpsMaintenanceTeamController {

    @Resource
    private TrafficOpsMaintenanceTeamService opsMaintenanceTeamService;

    @PostMapping("/create")
    @Operation(summary = "创建设备维保组员信息")
    @PreAuthorize("@ss.hasPermission('traffic:ops-maintenance-team:create')")
    public CommonResult<String> createOpsMaintenanceTeam(@Valid @RequestBody TrafficOpsMaintenanceTeamSaveReqVO createReqVO) {
        return success(opsMaintenanceTeamService.createOpsMaintenanceTeam(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新设备维保组员信息")
    @PreAuthorize("@ss.hasPermission('traffic:ops-maintenance-team:update')")
    public CommonResult<Boolean> updateOpsMaintenanceTeam(@Valid @RequestBody TrafficOpsMaintenanceTeamSaveReqVO updateReqVO) {
        opsMaintenanceTeamService.updateOpsMaintenanceTeam(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除设备维保组员信息")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('traffic:ops-maintenance-team:delete')")
    public CommonResult<Boolean> deleteOpsMaintenanceTeam(@RequestParam("id") String id) {
        opsMaintenanceTeamService.deleteOpsMaintenanceTeam(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得设备维保组员信息")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('traffic:ops-maintenance-team:query')")
    public CommonResult<TrafficOpsMaintenanceTeamRespVO> getOpsMaintenanceTeam(@RequestParam("id") String id) {
        TrafficOpsMaintenanceTeamDO opsMaintenanceTeam = opsMaintenanceTeamService.getOpsMaintenanceTeam(id);
        return success(BeanUtils.toBean(opsMaintenanceTeam, TrafficOpsMaintenanceTeamRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得设备维保组员信息分页")
    @PreAuthorize("@ss.hasPermission('traffic:ops-maintenance-team:query')")
    public CommonResult<PageResult<TrafficOpsMaintenanceTeamRespVO>> getOpsMaintenanceTeamPage(@Valid TrafficOpsMaintenanceTeamPageReqVO pageReqVO) {
        PageResult<TrafficOpsMaintenanceTeamDO> pageResult = opsMaintenanceTeamService.getOpsMaintenanceTeamPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, TrafficOpsMaintenanceTeamRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出设备维保组员信息 Excel")
    @PreAuthorize("@ss.hasPermission('traffic:ops-maintenance-team:export')")
    public void exportOpsMaintenanceTeamExcel(@Valid TrafficOpsMaintenanceTeamPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<TrafficOpsMaintenanceTeamDO> list = opsMaintenanceTeamService.getOpsMaintenanceTeamPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "设备维保组员信息.xls", "数据", TrafficOpsMaintenanceTeamRespVO.class,
                        BeanUtils.toBean(list, TrafficOpsMaintenanceTeamRespVO.class));
    }

}