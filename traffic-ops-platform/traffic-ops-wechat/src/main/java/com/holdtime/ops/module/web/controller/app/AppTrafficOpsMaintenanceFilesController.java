package com.holdtime.ops.module.web.controller.app;

import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;

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
import com.holdtime.ops.module.entity.TrafficOpsMaintenanceFilesDO;
import com.holdtime.ops.module.service.TrafficOpsMaintenanceFilesService;

@Tag(name = "用户 APP - 维保材料信息")
@RestController
@RequestMapping("/traffic/ops-maintenance-files")
@Validated
public class AppTrafficOpsMaintenanceFilesController {

    @Resource
    private TrafficOpsMaintenanceFilesService opsMaintenanceFilesService;

    @PostMapping("/create")
    @Operation(summary = "创建维保材料信息")
    public CommonResult<String> createOpsMaintenanceFiles(@Valid @RequestBody AppTrafficOpsMaintenanceFilesSaveReqVO createReqVO) {
        return success(opsMaintenanceFilesService.createOpsMaintenanceFiles(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新维保材料信息")
    public CommonResult<Boolean> updateOpsMaintenanceFiles(@Valid @RequestBody AppTrafficOpsMaintenanceFilesSaveReqVO updateReqVO) {
        opsMaintenanceFilesService.updateOpsMaintenanceFiles(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除维保材料信息")
    @Parameter(name = "id", description = "编号", required = true)
    public CommonResult<Boolean> deleteOpsMaintenanceFiles(@RequestParam("id") String id) {
        opsMaintenanceFilesService.deleteOpsMaintenanceFiles(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得维保材料信息")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    public CommonResult<AppTrafficOpsMaintenanceFilesRespVO> getOpsMaintenanceFiles(@RequestParam("id") String id) {
        TrafficOpsMaintenanceFilesDO opsMaintenanceFiles = opsMaintenanceFilesService.getOpsMaintenanceFiles(id);
        return success(BeanUtils.toBean(opsMaintenanceFiles, AppTrafficOpsMaintenanceFilesRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得维保材料信息分页")
    public CommonResult<PageResult<AppTrafficOpsMaintenanceFilesRespVO>> getOpsMaintenanceFilesPage(@Valid AppTrafficOpsMaintenanceFilesPageReqVO pageReqVO) {
        PageResult<TrafficOpsMaintenanceFilesDO> pageResult = opsMaintenanceFilesService.getOpsMaintenanceFilesPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, AppTrafficOpsMaintenanceFilesRespVO.class));
    }

//    @GetMapping("/export-excel")
//    @Operation(summary = "导出维保材料信息 Excel")
//    public void exportOpsMaintenanceFilesExcel(@Valid AppTrafficOpsMaintenanceFilesPageReqVO pageReqVO,
//              HttpServletResponse response) throws IOException {
//        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
//        List<TrafficOpsMaintenanceFilesDO> list = opsMaintenanceFilesService.getOpsMaintenanceFilesPage(pageReqVO).getList();
//        // 导出 Excel
//        ExcelUtils.write(response, "维保材料信息.xls", "数据", TrafficOpsMaintenanceFilesRespVO.class,
//                        BeanUtils.toBean(list, TrafficOpsMaintenanceFilesRespVO.class));
//    }

}