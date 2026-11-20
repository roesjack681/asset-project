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
import com.holdtime.ops.module.entity.TrafficVioSurveilScreenErrDO;
import com.holdtime.ops.module.service.TrafficVioSurveilScreenErrService;

@Tag(name = "管理后台 - 违法抓拍无效记录")
@RestController
@RequestMapping("/traffic/vio-surveil-screen-err")
@Validated
public class TrafficVioSurveilScreenErrController {

    @Resource
    private TrafficVioSurveilScreenErrService vioSurveilScreenErrService;

    @PostMapping("/create")
    @Operation(summary = "创建违法抓拍无效记录")
    @PreAuthorize("@ss.hasPermission('traffic:vio-surveil-screen-err:create')")
    public CommonResult<String> createVioSurveilScreenErr(@Valid @RequestBody TrafficVioSurveilScreenErrSaveReqVO createReqVO) {
        return success(vioSurveilScreenErrService.createVioSurveilScreenErr(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新违法抓拍无效记录")
    @PreAuthorize("@ss.hasPermission('traffic:vio-surveil-screen-err:update')")
    public CommonResult<Boolean> updateVioSurveilScreenErr(@Valid @RequestBody TrafficVioSurveilScreenErrSaveReqVO updateReqVO) {
        vioSurveilScreenErrService.updateVioSurveilScreenErr(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除违法抓拍无效记录")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('traffic:vio-surveil-screen-err:delete')")
    public CommonResult<Boolean> deleteVioSurveilScreenErr(@RequestParam("id") String id) {
        vioSurveilScreenErrService.deleteVioSurveilScreenErr(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得违法抓拍无效记录")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('traffic:vio-surveil-screen-err:query')")
    public CommonResult<TrafficVioSurveilScreenErrRespVO> getVioSurveilScreenErr(@RequestParam("id") String id) {
        TrafficVioSurveilScreenErrDO vioSurveilScreenErr = vioSurveilScreenErrService.getVioSurveilScreenErr(id);
        return success(BeanUtils.toBean(vioSurveilScreenErr, TrafficVioSurveilScreenErrRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得违法抓拍无效记录分页")
    @PreAuthorize("@ss.hasPermission('traffic:vio-surveil-screen-err:query')")
    public CommonResult<PageResult<TrafficVioSurveilScreenErrRespVO>> getVioSurveilScreenErrPage(@Valid TrafficVioSurveilScreenErrPageReqVO pageReqVO) {
        PageResult<TrafficVioSurveilScreenErrDO> pageResult = vioSurveilScreenErrService.getVioSurveilScreenErrPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, TrafficVioSurveilScreenErrRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出违法抓拍无效记录 Excel")
    @PreAuthorize("@ss.hasPermission('traffic:vio-surveil-screen-err:export')")
    public void exportVioSurveilScreenErrExcel(@Valid TrafficVioSurveilScreenErrPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<TrafficVioSurveilScreenErrDO> list = vioSurveilScreenErrService.getVioSurveilScreenErrPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "违法抓拍无效记录.xls", "数据", TrafficVioSurveilScreenErrRespVO.class,
                        BeanUtils.toBean(list, TrafficVioSurveilScreenErrRespVO.class));
    }

}