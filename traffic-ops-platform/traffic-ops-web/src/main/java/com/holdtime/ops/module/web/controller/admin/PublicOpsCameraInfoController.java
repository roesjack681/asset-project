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
import com.holdtime.ops.module.entity.PublicOpsCameraInfoDO;
import com.holdtime.ops.module.service.PublicOpsCameraInfoService;

@Tag(name = "管理后台 - 资产摄像设备信息")
@RestController
@RequestMapping("/public/ops-camera-info")
@Validated
public class PublicOpsCameraInfoController {

    @Resource
    private PublicOpsCameraInfoService opsCameraInfoService;

    @PostMapping("/create")
    @Operation(summary = "创建资产摄像设备信息")
    @PreAuthorize("@ss.hasPermission('public:ops-camera-info:create')")
    public CommonResult<String> createOpsCameraInfo(@Valid @RequestBody PublicOpsCameraInfoSaveReqVO createReqVO) {
        return success(opsCameraInfoService.createOpsCameraInfo(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新资产摄像设备信息")
    @PreAuthorize("@ss.hasPermission('public:ops-camera-info:update')")
    public CommonResult<Boolean> updateOpsCameraInfo(@Valid @RequestBody PublicOpsCameraInfoSaveReqVO updateReqVO) {
        opsCameraInfoService.updateOpsCameraInfo(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除资产摄像设备信息")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('public:ops-camera-info:delete')")
    public CommonResult<Boolean> deleteOpsCameraInfo(@RequestParam("id") String id) {
        opsCameraInfoService.deleteOpsCameraInfo(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得资产摄像设备信息")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('public:ops-camera-info:query')")
    public CommonResult<PublicOpsCameraInfoRespVO> getOpsCameraInfo(@RequestParam("id") String id) {
        PublicOpsCameraInfoDO opsCameraInfo = opsCameraInfoService.getOpsCameraInfo(id);
        return success(BeanUtils.toBean(opsCameraInfo, PublicOpsCameraInfoRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得资产摄像设备信息分页")
    @PreAuthorize("@ss.hasPermission('public:ops-camera-info:query')")
    public CommonResult<PageResult<PublicOpsCameraInfoRespVO>> getOpsCameraInfoPage(@Valid PublicOpsCameraInfoPageReqVO pageReqVO) {
        PageResult<PublicOpsCameraInfoDO> pageResult = opsCameraInfoService.getOpsCameraInfoPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, PublicOpsCameraInfoRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出资产摄像设备信息 Excel")
    @PreAuthorize("@ss.hasPermission('public:ops-camera-info:export')")
    public void exportOpsCameraInfoExcel(@Valid PublicOpsCameraInfoPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<PublicOpsCameraInfoDO> list = opsCameraInfoService.getOpsCameraInfoPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "资产摄像设备信息.xls", "数据", PublicOpsCameraInfoRespVO.class,
                        BeanUtils.toBean(list, PublicOpsCameraInfoRespVO.class));
    }

}