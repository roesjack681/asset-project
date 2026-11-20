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
import com.holdtime.ops.module.entity.HaPsFeedbackDO;
import com.holdtime.ops.module.service.HaPsFeedbackService;

@Tag(name = "管理后台 - 方案展示留言记录")
@RestController
@RequestMapping("/traffic/ha-ps-feedback")
@Validated
public class HaPsFeedbackController {

    @Resource
    private HaPsFeedbackService haPsFeedbackService;

    @PostMapping("/create")
    @Operation(summary = "创建方案展示留言记录")
    @PreAuthorize("@ss.hasPermission('traffic:ha-ps-feedback:create')")
    public CommonResult<String> createHaPsFeedback(@Valid @RequestBody HaPsFeedbackSaveReqVO createReqVO) {
        return success(haPsFeedbackService.createHaPsFeedback(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新方案展示留言记录")
    @PreAuthorize("@ss.hasPermission('traffic:ha-ps-feedback:update')")
    public CommonResult<Boolean> updateHaPsFeedback(@Valid @RequestBody HaPsFeedbackSaveReqVO updateReqVO) {
        haPsFeedbackService.updateHaPsFeedback(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除方案展示留言记录")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('traffic:ha-ps-feedback:delete')")
    public CommonResult<Boolean> deleteHaPsFeedback(@RequestParam("id") String id) {
        haPsFeedbackService.deleteHaPsFeedback(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得方案展示留言记录")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('traffic:ha-ps-feedback:query')")
    public CommonResult<HaPsFeedbackRespVO> getHaPsFeedback(@RequestParam("id") String id) {
        HaPsFeedbackDO haPsFeedback = haPsFeedbackService.getHaPsFeedback(id);
        return success(BeanUtils.toBean(haPsFeedback, HaPsFeedbackRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得方案展示留言记录分页")
    @PreAuthorize("@ss.hasPermission('traffic:ha-ps-feedback:query')")
    public CommonResult<PageResult<HaPsFeedbackRespVO>> getHaPsFeedbackPage(@Valid HaPsFeedbackPageReqVO pageReqVO) {
        PageResult<HaPsFeedbackDO> pageResult = haPsFeedbackService.getHaPsFeedbackPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, HaPsFeedbackRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出方案展示留言记录 Excel")
    @PreAuthorize("@ss.hasPermission('traffic:ha-ps-feedback:export')")
    public void exportHaPsFeedbackExcel(@Valid HaPsFeedbackPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<HaPsFeedbackDO> list = haPsFeedbackService.getHaPsFeedbackPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "方案展示留言记录.xls", "数据", HaPsFeedbackRespVO.class,
                        BeanUtils.toBean(list, HaPsFeedbackRespVO.class));
    }

}