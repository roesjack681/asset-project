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
import com.holdtime.ops.module.entity.TrafficOpsAccessoryEquipmentDO;
import com.holdtime.ops.module.service.TrafficOpsAccessoryEquipmentService;

@Tag(name = "管理后台 - 杆件附属设备")
@RestController
@RequestMapping("/traffic/ops-accessory-equipment")
@Validated
public class TrafficOpsAccessoryEquipmentController {

    @Resource
    private TrafficOpsAccessoryEquipmentService opsAccessoryEquipmentService;

    @PostMapping("/create")
    @Operation(summary = "创建杆件附属设备")
    @PreAuthorize("@ss.hasPermission('traffic:ops-accessory-equipment:create')")
    public CommonResult<String> createOpsAccessoryEquipment(@Valid @RequestBody TrafficOpsAccessoryEquipmentSaveReqVO createReqVO) {
        return success(opsAccessoryEquipmentService.createOpsAccessoryEquipment(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新杆件附属设备")
    @PreAuthorize("@ss.hasPermission('traffic:ops-accessory-equipment:update')")
    public CommonResult<Boolean> updateOpsAccessoryEquipment(@Valid @RequestBody TrafficOpsAccessoryEquipmentSaveReqVO updateReqVO) {
        opsAccessoryEquipmentService.updateOpsAccessoryEquipment(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除杆件附属设备")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('traffic:ops-accessory-equipment:delete')")
    public CommonResult<Boolean> deleteOpsAccessoryEquipment(@RequestParam("id") String id) {
        opsAccessoryEquipmentService.deleteOpsAccessoryEquipment(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得杆件附属设备")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('traffic:ops-accessory-equipment:query')")
    public CommonResult<TrafficOpsAccessoryEquipmentRespVO> getOpsAccessoryEquipment(@RequestParam("id") String id) {
        TrafficOpsAccessoryEquipmentDO opsAccessoryEquipment = opsAccessoryEquipmentService.getOpsAccessoryEquipment(id);
        return success(BeanUtils.toBean(opsAccessoryEquipment, TrafficOpsAccessoryEquipmentRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得杆件附属设备分页")
    @PreAuthorize("@ss.hasPermission('traffic:ops-accessory-equipment:query')")
    public CommonResult<PageResult<TrafficOpsAccessoryEquipmentRespVO>> getOpsAccessoryEquipmentPage(@Valid TrafficOpsAccessoryEquipmentPageReqVO pageReqVO) {
        PageResult<TrafficOpsAccessoryEquipmentDO> pageResult = opsAccessoryEquipmentService.getOpsAccessoryEquipmentPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, TrafficOpsAccessoryEquipmentRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出杆件附属设备 Excel")
    @PreAuthorize("@ss.hasPermission('traffic:ops-accessory-equipment:export')")
    public void exportOpsAccessoryEquipmentExcel(@Valid TrafficOpsAccessoryEquipmentPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<TrafficOpsAccessoryEquipmentDO> list = opsAccessoryEquipmentService.getOpsAccessoryEquipmentPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "杆件附属设备.xls", "数据", TrafficOpsAccessoryEquipmentRespVO.class,
                        BeanUtils.toBean(list, TrafficOpsAccessoryEquipmentRespVO.class));
    }

}