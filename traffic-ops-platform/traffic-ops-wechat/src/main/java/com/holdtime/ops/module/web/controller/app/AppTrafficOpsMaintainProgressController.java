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
import com.holdtime.ops.module.entity.TrafficOpsMaintainProgressDO;
import com.holdtime.ops.module.service.TrafficOpsMaintainProgressService;

@Tag(name = "用户 APP - 设备维保进度")
@RestController
@RequestMapping("/traffic/ops-maintain-progress")
@Validated
public class AppTrafficOpsMaintainProgressController {

    @Resource
    private TrafficOpsMaintainProgressService opsMaintainProgressService;

    @PostMapping("/create")
    @Operation(summary = "创建设备维保进度")
    public CommonResult<String> createOpsMaintainProgress(@Valid @RequestBody AppTrafficOpsMaintainProgressSaveReqVO createReqVO) {
        return success(opsMaintainProgressService.createOpsMaintainProgress(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新设备维保进度")
    public CommonResult<Boolean> updateOpsMaintainProgress(@Valid @RequestBody AppTrafficOpsMaintainProgressSaveReqVO updateReqVO) {
        opsMaintainProgressService.updateOpsMaintainProgress(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除设备维保进度")
    @Parameter(name = "id", description = "编号", required = true)
    public CommonResult<Boolean> deleteOpsMaintainProgress(@RequestParam("id") String id) {
        opsMaintainProgressService.deleteOpsMaintainProgress(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得设备维保进度")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    public CommonResult<AppTrafficOpsMaintainProgressRespVO> getOpsMaintainProgress(@RequestParam("id") String id) {
        TrafficOpsMaintainProgressDO opsMaintainProgress = opsMaintainProgressService.getOpsMaintainProgress(id);
        return success(BeanUtils.toBean(opsMaintainProgress, AppTrafficOpsMaintainProgressRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得设备维保进度分页")
    public CommonResult<PageResult<AppTrafficOpsMaintainProgressRespVO>> getOpsMaintainProgressPage(@Valid AppTrafficOpsMaintainProgressPageReqVO pageReqVO) {
        PageResult<TrafficOpsMaintainProgressDO> pageResult = opsMaintainProgressService.getOpsMaintainProgressPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, AppTrafficOpsMaintainProgressRespVO.class));
    }

//    @GetMapping("/export-excel")
//    @Operation(summary = "导出设备维保进度 Excel")
//    public void exportOpsMaintainProgressExcel(@Valid AppTrafficOpsMaintainProgressPageReqVO pageReqVO,
//              HttpServletResponse response) throws IOException {
//        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
//        List<TrafficOpsMaintainProgressDO> list = opsMaintainProgressService.getOpsMaintainProgressPage(pageReqVO).getList();
//        // 导出 Excel
//        ExcelUtils.write(response, "设备维保进度.xls", "数据", TrafficOpsMaintainProgressRespVO.class,
//                        BeanUtils.toBean(list, TrafficOpsMaintainProgressRespVO.class));
//    }

}