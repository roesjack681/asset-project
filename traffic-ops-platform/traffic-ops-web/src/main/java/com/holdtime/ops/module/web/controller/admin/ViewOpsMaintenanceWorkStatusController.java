package com.holdtime.ops.module.web.controller.admin;

import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Operation;

import javax.validation.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

import com.holdtime.ops.module.model.vo.*;
import com.holdtime.ops.module.entity.ViewOpsMaintenanceWorkStatusDO;
import com.holdtime.ops.module.service.ViewOpsMaintenanceWorkStatusService;

@Tag(name = "管理后台 - VIEW")
@RestController
@RequestMapping("/traffic/view-ops-maintenance-work-status")
@Validated
public class ViewOpsMaintenanceWorkStatusController {

    @Resource
    private ViewOpsMaintenanceWorkStatusService viewOpsMaintenanceWorkStatusService;

    @GetMapping("/get")
    @Operation(summary = "获得VIEW")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    public CommonResult<ViewOpsMaintenanceWorkStatusRespVO> getViewOpsMaintenanceWorkStatus(@RequestParam("id") String id) {
        ViewOpsMaintenanceWorkStatusDO viewOpsMaintenanceWorkStatus = viewOpsMaintenanceWorkStatusService.getViewOpsMaintenanceWorkStatus(id);
        return success(BeanUtils.toBean(viewOpsMaintenanceWorkStatus, ViewOpsMaintenanceWorkStatusRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得VIEW分页")
    public CommonResult<PageResult<ViewOpsMaintenanceWorkStatusRespVO>> getViewOpsMaintenanceWorkStatusPage(@Valid ViewOpsMaintenanceWorkStatusPageReqVO pageReqVO) {
        PageResult<ViewOpsMaintenanceWorkStatusDO> pageResult = viewOpsMaintenanceWorkStatusService.getViewOpsMaintenanceWorkStatusPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, ViewOpsMaintenanceWorkStatusRespVO.class));
    }

}