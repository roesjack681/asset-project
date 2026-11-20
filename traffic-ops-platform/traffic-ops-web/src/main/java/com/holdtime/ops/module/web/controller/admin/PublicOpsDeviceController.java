package com.holdtime.ops.module.web.controller.admin;

import com.holdtime.ops.module.service.PublicOpsDeviceExtService;
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
import com.holdtime.ops.module.entity.PublicOpsDeviceDO;
import com.holdtime.ops.module.service.PublicOpsDeviceService;

@Tag(name = "管理后台 - 终端设备信息")
@RestController
@RequestMapping("/public/ops-device")
@Validated
public class PublicOpsDeviceController {

    @Resource
    private PublicOpsDeviceService opsDeviceService;
    @Resource
    private PublicOpsDeviceExtService deviceExtService;

    @PostMapping("/create")
    @Operation(summary = "创建终端设备信息")
    @PreAuthorize("@ss.hasPermission('public:ops-device:create')")
    public CommonResult<String> createOpsDevice(@Valid @RequestBody PublicOpsDeviceSaveReqVO createReqVO) {
        return success(deviceExtService.saveDevice(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新终端设备信息")
    @PreAuthorize("@ss.hasPermission('public:ops-device:update')")
    public CommonResult<Boolean> updateOpsDevice(@Valid @RequestBody PublicOpsDeviceSaveReqVO updateReqVO) {
        deviceExtService.saveDevice(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除终端设备信息")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('public:ops-device:delete')")
    public CommonResult<Boolean> deleteOpsDevice(@RequestParam("id") String id) {
        deviceExtService.deleteDevice(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得终端设备信息")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('public:ops-device:query')")
    public CommonResult<PublicOpsDeviceRespVO> getOpsDevice(@RequestParam("id") String id) {
        PublicOpsDeviceDO opsDevice = opsDeviceService.getOpsDevice(id);
        return success(BeanUtils.toBean(opsDevice, PublicOpsDeviceRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得终端设备信息分页")
    @PreAuthorize("@ss.hasPermission('public:ops-device:query')")
    public CommonResult<PageResult<PublicOpsDeviceRespVO>> getOpsDevicePage(@Valid PublicOpsDevicePageReqVO pageReqVO) {
        PageResult<PublicOpsDeviceDO> pageResult = opsDeviceService.getOpsDevicePage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, PublicOpsDeviceRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出终端设备信息 Excel")
    @PreAuthorize("@ss.hasPermission('public:ops-device:export')")
    public void exportOpsDeviceExcel(@Valid PublicOpsDevicePageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<PublicOpsDeviceDO> list = opsDeviceService.getOpsDevicePage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "终端设备信息.xls", "数据", PublicOpsDeviceRespVO.class,
                        BeanUtils.toBean(list, PublicOpsDeviceRespVO.class));
    }

}