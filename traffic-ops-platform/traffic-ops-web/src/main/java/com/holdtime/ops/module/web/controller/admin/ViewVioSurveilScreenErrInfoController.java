package com.holdtime.ops.module.web.controller.admin;

import com.holdtime.ops.module.enums.OpsConstants;
import com.holdtime.ops.module.service.ViewVioSurveilScreenErrInfoExtService;
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
import com.holdtime.ops.module.entity.ViewVioSurveilScreenErrInfoDO;
import com.holdtime.ops.module.service.ViewVioSurveilScreenErrInfoService;

@Tag(name = "管理后台 - 集指无效违法记录")
@RestController
@RequestMapping("/traffic/view-vio-surveil-screen-err-info")
@Validated
public class ViewVioSurveilScreenErrInfoController {

    @Resource
    private ViewVioSurveilScreenErrInfoService viewVioSurveilScreenErrInfoService;
    @Resource
    private ViewVioSurveilScreenErrInfoExtService viewVioSurveilScreenErrInfoExtService;

    @PostMapping("/create")
    @Operation(summary = "创建集指无效违法记录")
    @PreAuthorize("@ss.hasPermission('traffic:view-vio-surveil-screen-err-info:create')")
    public CommonResult<String> createViewVioSurveilScreenErrInfo(@Valid @RequestBody ViewVioSurveilScreenErrInfoSaveReqVO createReqVO) {
        return success(viewVioSurveilScreenErrInfoService.createViewVioSurveilScreenErrInfo(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新集指无效违法记录")
    @PreAuthorize("@ss.hasPermission('traffic:view-vio-surveil-screen-err-info:update')")
    public CommonResult<Boolean> updateViewVioSurveilScreenErrInfo(@Valid @RequestBody ViewVioSurveilScreenErrInfoSaveReqVO updateReqVO) {
        viewVioSurveilScreenErrInfoService.updateViewVioSurveilScreenErrInfo(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除集指无效违法记录")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('traffic:view-vio-surveil-screen-err-info:delete')")
    public CommonResult<Boolean> deleteViewVioSurveilScreenErrInfo(@RequestParam("id") String id) {
        viewVioSurveilScreenErrInfoService.deleteViewVioSurveilScreenErrInfo(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得集指无效违法记录")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('traffic:view-vio-surveil-screen-err-info:query')")
    public CommonResult<ViewVioSurveilScreenErrInfoRespVO> getViewVioSurveilScreenErrInfo(@RequestParam("id") String id) {
        ViewVioSurveilScreenErrInfoDO viewVioSurveilScreenErrInfo = viewVioSurveilScreenErrInfoService.getViewVioSurveilScreenErrInfo(id);
        return success(BeanUtils.toBean(viewVioSurveilScreenErrInfo, ViewVioSurveilScreenErrInfoRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得集指无效违法记录分页")
    @PreAuthorize("@ss.hasPermission('traffic:view-vio-surveil-screen-err-info:query')")
    public CommonResult<PageResult<ViewVioSurveilScreenErrInfoRespVO>> getViewVioSurveilScreenErrInfoPage(@Valid ViewVioSurveilScreenErrInfoExtPageReqVO pageReqVO) {
        PageResult<ViewVioSurveilScreenErrInfoDO> pageResult = viewVioSurveilScreenErrInfoExtService.getViewVioSurveilScreenErrInfoPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, ViewVioSurveilScreenErrInfoRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出集指无效违法记录 Excel")
    @PreAuthorize("@ss.hasPermission('traffic:view-vio-surveil-screen-err-info:export')")
    public void exportViewVioSurveilScreenErrInfoExcel(@Valid ViewVioSurveilScreenErrInfoPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<ViewVioSurveilScreenErrInfoDO> list = viewVioSurveilScreenErrInfoService.getViewVioSurveilScreenErrInfoPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "集指无效违法记录.xls", "数据", ViewVioSurveilScreenErrInfoRespVO.class,
                        BeanUtils.toBean(list, ViewVioSurveilScreenErrInfoRespVO.class));
    }

    @GetMapping("/reviewPage")
    @Operation(summary = "获得集指无效违法复审分页")
    public CommonResult<PageResult<ViewVioSurveilScreenErrInfoRespVO>> getViewVioSurveilScreenErrInfoReviewPage(@Valid ViewVioSurveilScreenErrInfoExtPageReqVO pageReqVO) {
        pageReqVO.setOptType(OpsConstants.STRING_ONE);
        PageResult<ViewVioSurveilScreenErrInfoDO> pageResult = viewVioSurveilScreenErrInfoExtService.getViewVioSurveilScreenErrInfoPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, ViewVioSurveilScreenErrInfoRespVO.class));
    }

    @PutMapping("/saveReviewResult")
    @Operation(summary = "保存无效复审结果")
    public CommonResult<Boolean> saveReviewResult(@Valid @RequestBody TrafficVioSurveilScreenErrSaveReqVO updateReqVO) {
        viewVioSurveilScreenErrInfoExtService.updateVioSurveilScreenErrInfo(updateReqVO);
        return success(true);
    }

}