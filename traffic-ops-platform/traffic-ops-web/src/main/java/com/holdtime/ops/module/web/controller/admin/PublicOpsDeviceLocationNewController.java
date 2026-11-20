package com.holdtime.ops.module.web.controller.admin;

import com.holdtime.ops.module.entity.VDeviceLocationNewDO;
import com.holdtime.ops.module.service.VDeviceLocationNewService;
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
import com.holdtime.ops.module.entity.PublicOpsDeviceLocationNewDO;
import com.holdtime.ops.module.service.PublicOpsDeviceLocationNewService;

@Tag(name = "管理后台 - 终端最新位置")
@RestController
@RequestMapping("/public/ops-device-location-new")
@Validated
public class PublicOpsDeviceLocationNewController {

    @Resource
    private PublicOpsDeviceLocationNewService opsDeviceLocationNewService;
    @Resource
    private VDeviceLocationNewService vDeviceLocationNewService;

    @PostMapping("/create")
    @Operation(summary = "创建终端最新位置")
    @PreAuthorize("@ss.hasPermission('public:ops-device-location-new:create')")
    public CommonResult<String> createOpsDeviceLocationNew(@Valid @RequestBody PublicOpsDeviceLocationNewSaveReqVO createReqVO) {
        return success(opsDeviceLocationNewService.createOpsDeviceLocationNew(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新终端最新位置")
    @PreAuthorize("@ss.hasPermission('public:ops-device-location-new:update')")
    public CommonResult<Boolean> updateOpsDeviceLocationNew(@Valid @RequestBody PublicOpsDeviceLocationNewSaveReqVO updateReqVO) {
        opsDeviceLocationNewService.updateOpsDeviceLocationNew(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除终端最新位置")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('public:ops-device-location-new:delete')")
    public CommonResult<Boolean> deleteOpsDeviceLocationNew(@RequestParam("id") String id) {
        opsDeviceLocationNewService.deleteOpsDeviceLocationNew(id);
        return success(true);
    }


    @GetMapping("/get")
    @Operation(summary = "获得车辆最新位置查询")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('recep:device-location-new:query')")
    public CommonResult<VDeviceLocationNewRespVO> getVDeviceLocationNew(@RequestParam("id") String id) {
        VDeviceLocationNewDO vDeviceLocationNew = vDeviceLocationNewService.getVDeviceLocationNew(id);
        return success(BeanUtils.toBean(vDeviceLocationNew, VDeviceLocationNewRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得车辆最新位置查询分页")
    @PreAuthorize("@ss.hasPermission('recep:device-location-new:query')")
    public CommonResult<PageResult<VDeviceLocationNewRespVO>> getVDeviceLocationNewPage(@Valid VDeviceLocationNewPageReqVO pageReqVO) {
        PageResult<VDeviceLocationNewDO> pageResult = vDeviceLocationNewService.getVDeviceLocationNewPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, VDeviceLocationNewRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出终端最新位置 Excel")
    @PreAuthorize("@ss.hasPermission('public:ops-device-location-new:export')")
    public void exportOpsDeviceLocationNewExcel(@Valid PublicOpsDeviceLocationNewPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<PublicOpsDeviceLocationNewDO> list = opsDeviceLocationNewService.getOpsDeviceLocationNewPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "终端最新位置.xls", "数据", PublicOpsDeviceLocationNewRespVO.class,
                        BeanUtils.toBean(list, PublicOpsDeviceLocationNewRespVO.class));
    }

}