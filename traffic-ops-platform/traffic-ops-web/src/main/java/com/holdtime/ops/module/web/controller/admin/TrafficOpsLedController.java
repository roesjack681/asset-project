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
import com.holdtime.ops.module.entity.TrafficOpsLedDO;
import com.holdtime.ops.module.service.TrafficOpsLedService;

@Tag(name = "管理后台 - Led屏")
@RestController
@RequestMapping("/traffic/ops-led")
@Validated
public class TrafficOpsLedController {

    @Resource
    private TrafficOpsLedService opsLedService;

    @PostMapping("/create")
    @Operation(summary = "创建Led屏")
    @PreAuthorize("@ss.hasPermission('traffic:ops-led:create')")
    public CommonResult<String> createOpsLed(@Valid @RequestBody TrafficOpsLedSaveReqVO createReqVO) {
        return success(opsLedService.createOpsLed(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新Led屏")
    @PreAuthorize("@ss.hasPermission('traffic:ops-led:update')")
    public CommonResult<Boolean> updateOpsLed(@Valid @RequestBody TrafficOpsLedSaveReqVO updateReqVO) {
        opsLedService.updateOpsLed(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除Led屏")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('traffic:ops-led:delete')")
    public CommonResult<Boolean> deleteOpsLed(@RequestParam("id") String id) {
        opsLedService.deleteOpsLed(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得Led屏")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('traffic:ops-led:query')")
    public CommonResult<TrafficOpsLedRespVO> getOpsLed(@RequestParam("id") String id) {
        TrafficOpsLedDO opsLed = opsLedService.getOpsLed(id);
        return success(BeanUtils.toBean(opsLed, TrafficOpsLedRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得Led屏分页")
    @PreAuthorize("@ss.hasPermission('traffic:ops-led:query')")
    public CommonResult<PageResult<TrafficOpsLedRespVO>> getOpsLedPage(@Valid TrafficOpsLedPageReqVO pageReqVO) {
        PageResult<TrafficOpsLedDO> pageResult = opsLedService.getOpsLedPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, TrafficOpsLedRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出Led屏 Excel")
    @PreAuthorize("@ss.hasPermission('traffic:ops-led:export')")
    public void exportOpsLedExcel(@Valid TrafficOpsLedPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<TrafficOpsLedDO> list = opsLedService.getOpsLedPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "Led屏.xls", "数据", TrafficOpsLedRespVO.class,
                        BeanUtils.toBean(list, TrafficOpsLedRespVO.class));
    }

}