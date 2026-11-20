package com.holdtime.ops.module.web.controller.admin;

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
import com.holdtime.ops.module.entity.TrafficEquipPingMonitorResultDO;
import com.holdtime.ops.module.service.TrafficEquipPingMonitorResultService;

@Tag(name = "管理后台 - 重点考核设备通断检测")
@RestController
@RequestMapping("/traffic/equip-ping-monitor-result")
@Validated
public class TrafficEquipPingMonitorResultController {

    @Resource
    private TrafficEquipPingMonitorResultService equipPingMonitorResultService;

    @PostMapping("/create")
    @Operation(summary = "创建重点考核设备通断检测")
    @PreAuthorize("@ss.hasPermission('traffic:equip-ping-monitor-result:create')")
    public CommonResult<String> createEquipPingMonitorResult(@Valid @RequestBody TrafficEquipPingMonitorResultSaveReqVO createReqVO) {
        return success(equipPingMonitorResultService.createEquipPingMonitorResult(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新重点考核设备通断检测")
    @PreAuthorize("@ss.hasPermission('traffic:equip-ping-monitor-result:update')")
    public CommonResult<Boolean> updateEquipPingMonitorResult(@Valid @RequestBody TrafficEquipPingMonitorResultSaveReqVO updateReqVO) {
        equipPingMonitorResultService.updateEquipPingMonitorResult(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除重点考核设备通断检测")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('traffic:equip-ping-monitor-result:delete')")
    public CommonResult<Boolean> deleteEquipPingMonitorResult(@RequestParam("id") String id) {
        equipPingMonitorResultService.deleteEquipPingMonitorResult(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得重点考核设备通断检测")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('traffic:equip-ping-monitor-result:query')")
    public CommonResult<TrafficEquipPingMonitorResultRespVO> getEquipPingMonitorResult(@RequestParam("id") String id) {
        TrafficEquipPingMonitorResultDO equipPingMonitorResult = equipPingMonitorResultService.getEquipPingMonitorResult(id);
        return success(BeanUtils.toBean(equipPingMonitorResult, TrafficEquipPingMonitorResultRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得重点考核设备通断检测分页")
    @PreAuthorize("@ss.hasPermission('traffic:equip-ping-monitor-result:query')")
    public CommonResult<PageResult<TrafficEquipPingMonitorResultRespVO>> getEquipPingMonitorResultPage(@Valid TrafficEquipPingMonitorResultPageReqVO pageReqVO) {
        PageResult<TrafficEquipPingMonitorResultDO> pageResult = equipPingMonitorResultService.getEquipPingMonitorResultPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, TrafficEquipPingMonitorResultRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出重点考核设备通断检测 Excel")
    @PreAuthorize("@ss.hasPermission('traffic:equip-ping-monitor-result:export')")
    public void exportEquipPingMonitorResultExcel(@Valid TrafficEquipPingMonitorResultPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<TrafficEquipPingMonitorResultDO> list = equipPingMonitorResultService.getEquipPingMonitorResultPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "重点考核设备通断检测.xls", "数据", TrafficEquipPingMonitorResultRespVO.class,
                        BeanUtils.toBean(list, TrafficEquipPingMonitorResultRespVO.class));
    }

}