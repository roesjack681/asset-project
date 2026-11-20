package com.holdtime.ops.module.web.controller.admin;

import com.holdtime.ops.module.service.PoliceDailyRoundCheckExtService;
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
import com.holdtime.ops.module.entity.PoliceDailyRoundCheckDO;
import com.holdtime.ops.module.service.PoliceDailyRoundCheckService;

@Tag(name = "管理后台 - 日常巡检信息")
@RestController
@RequestMapping("/police/daily-round-check")
@Validated
public class PoliceDailyRoundCheckController {

    @Resource
    private PoliceDailyRoundCheckService dailyRoundCheckService;
    @Resource
    private PoliceDailyRoundCheckExtService dailyRoundCheckExtService;

    @PostMapping("/create")
    @Operation(summary = "创建日常巡检信息")
    @PreAuthorize("@ss.hasPermission('police:daily-round-check:create')")
    public CommonResult<String> createDailyRoundCheck(@Valid @RequestBody PoliceDailyRoundCheckSaveReqVO createReqVO) {
        return success(dailyRoundCheckExtService.createDailyRoundCheck(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新日常巡检信息")
    @PreAuthorize("@ss.hasPermission('police:daily-round-check:update')")
    public CommonResult<Boolean> updateDailyRoundCheck(@Valid @RequestBody PoliceDailyRoundCheckSaveReqVO updateReqVO) {
        dailyRoundCheckService.updateDailyRoundCheck(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除日常巡检信息")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('police:daily-round-check:delete')")
    public CommonResult<Boolean> deleteDailyRoundCheck(@RequestParam("id") String id) {
        dailyRoundCheckService.deleteDailyRoundCheck(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得日常巡检信息")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('police:daily-round-check:query')")
    public CommonResult<PoliceDailyRoundCheckExtRespVO> getDailyRoundCheck(@RequestParam("id") String id) {
        PoliceDailyRoundCheckExtRespVO dailyRoundCheck = dailyRoundCheckExtService.getDailyRoundCheck(id);
        return success(dailyRoundCheck);
    }

    @GetMapping("/page")
    @Operation(summary = "获得日常巡检信息分页")
    @PreAuthorize("@ss.hasPermission('police:daily-round-check:query')")
    public CommonResult<PageResult<PoliceDailyRoundCheckExtRespVO>> getDailyRoundCheckPage(@Valid PoliceDailyRoundCheckPageReqVO pageReqVO) {
        return success(dailyRoundCheckExtService.getDailyRoundCheckPage(pageReqVO));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出日常巡检信息 Excel")
    @PreAuthorize("@ss.hasPermission('police:daily-round-check:export')")
    public void exportDailyRoundCheckExcel(@Valid PoliceDailyRoundCheckPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<PoliceDailyRoundCheckDO> list = dailyRoundCheckService.getDailyRoundCheckPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "日常巡检信息.xls", "数据", PoliceDailyRoundCheckRespVO.class,
                        BeanUtils.toBean(list, PoliceDailyRoundCheckRespVO.class));
    }

}