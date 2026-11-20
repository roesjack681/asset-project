package com.holdtime.ops.module.web.controller.admin;

import cn.hutool.core.date.LocalDateTimeUtil;
import com.holdtime.ops.module.entity.VDeviceLocationNewDO;
import com.holdtime.ops.module.service.PublicOpsDeviceLocationExtService;
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
import com.holdtime.ops.module.entity.PublicOpsDeviceLocationDO;
import com.holdtime.ops.module.service.PublicOpsDeviceLocationService;

@Tag(name = "管理后台 - 终端最新位置")
@RestController
@RequestMapping("/public/ops-device-location")
@Validated
public class PublicOpsDeviceLocationController {

    @Resource
    private PublicOpsDeviceLocationService opsDeviceLocationService;
    @Resource
    private PublicOpsDeviceLocationExtService deviceLocationExtService;

    @PostMapping("/create")
    @Operation(summary = "创建终端位置")
    @PreAuthorize("@ss.hasPermission('public:ops-device-location:create')")
    public CommonResult<String> createOpsDeviceLocation(@Valid @RequestBody PublicOpsDeviceLocationSaveReqVO createReqVO) {
        return success(opsDeviceLocationService.createOpsDeviceLocation(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新终端位置")
    @PreAuthorize("@ss.hasPermission('public:ops-device-location:update')")
    public CommonResult<Boolean> updateOpsDeviceLocation(@Valid @RequestBody PublicOpsDeviceLocationSaveReqVO updateReqVO) {
        opsDeviceLocationService.updateOpsDeviceLocation(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除终端位置")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('public:ops-device-location:delete')")
    public CommonResult<Boolean> deleteOpsDeviceLocation(@RequestParam("id") String id) {
        opsDeviceLocationService.deleteOpsDeviceLocation(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得终端位置")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('public:ops-device-location:query')")
    public CommonResult<PublicOpsDeviceLocationRespVO> getOpsDeviceLocation(@RequestParam("id") String id) {
        PublicOpsDeviceLocationDO opsDeviceLocation = opsDeviceLocationService.getOpsDeviceLocation(id);
        return success(BeanUtils.toBean(opsDeviceLocation, PublicOpsDeviceLocationRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得终端位置分页")
    @PreAuthorize("@ss.hasPermission('public:ops-device-location:query')")
    public CommonResult<PageResult<PublicOpsDeviceLocationRespVO>> getOpsDeviceLocationPage(@Valid PublicOpsDeviceLocationPageReqVO pageReqVO) {
        PageResult<PublicOpsDeviceLocationDO> pageResult = opsDeviceLocationService.getOpsDeviceLocationPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, PublicOpsDeviceLocationRespVO.class));
    }


    @GetMapping("/export-excel")
    @Operation(summary = "导出终端位置 Excel")
    @PreAuthorize("@ss.hasPermission('public:ops-device-location:export')")
    public void exportOpsDeviceLocationExcel(@Valid PublicOpsDeviceLocationPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<PublicOpsDeviceLocationDO> list = opsDeviceLocationService.getOpsDeviceLocationPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "终端最新位置.xls", "数据", PublicOpsDeviceLocationRespVO.class,
                        BeanUtils.toBean(list, PublicOpsDeviceLocationRespVO.class));
    }

    /**
     * 轨迹回放
     */
    @GetMapping("/getHistoryLocation")
    @Operation(summary = "获得终端历史位置")
    public CommonResult<List<PublicOpsDeviceLocationRespVO>> getHistoryLocation(@RequestParam("deviceId") String deviceId,
                                                                       @RequestParam("startTime") String startTime,
                                                                       @RequestParam("endTime") String endTime) {
        return success(BeanUtils.toBean(deviceLocationExtService.getHistoryLocation(deviceId,
                LocalDateTimeUtil.of(Long.parseLong(startTime)), LocalDateTimeUtil.of(Long.parseLong(endTime))), PublicOpsDeviceLocationRespVO.class));
    }

}