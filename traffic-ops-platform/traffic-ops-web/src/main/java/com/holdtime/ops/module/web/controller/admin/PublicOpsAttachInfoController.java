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
import com.holdtime.ops.module.entity.PublicOpsAttachInfoDO;
import com.holdtime.ops.module.service.PublicOpsAttachInfoService;

@Tag(name = "管理后台 - 资产附件信息")
@RestController
@RequestMapping("/public/ops-attach-info")
@Validated
public class PublicOpsAttachInfoController {

    @Resource
    private PublicOpsAttachInfoService opsAttachInfoService;

    @PostMapping("/create")
    @Operation(summary = "创建资产附件信息")
    @PreAuthorize("@ss.hasPermission('public:ops-attach-info:create')")
    public CommonResult<String> createOpsAttachInfo(@Valid @RequestBody PublicOpsAttachInfoSaveReqVO createReqVO) {
        return success(opsAttachInfoService.createOpsAttachInfo(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新资产附件信息")
    @PreAuthorize("@ss.hasPermission('public:ops-attach-info:update')")
    public CommonResult<Boolean> updateOpsAttachInfo(@Valid @RequestBody PublicOpsAttachInfoSaveReqVO updateReqVO) {
        opsAttachInfoService.updateOpsAttachInfo(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除资产附件信息")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('public:ops-attach-info:delete')")
    public CommonResult<Boolean> deleteOpsAttachInfo(@RequestParam("id") String id) {
        opsAttachInfoService.deleteOpsAttachInfo(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得资产附件信息")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('public:ops-attach-info:query')")
    public CommonResult<PublicOpsAttachInfoRespVO> getOpsAttachInfo(@RequestParam("id") String id) {
        PublicOpsAttachInfoDO opsAttachInfo = opsAttachInfoService.getOpsAttachInfo(id);
        return success(BeanUtils.toBean(opsAttachInfo, PublicOpsAttachInfoRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得资产附件信息分页")
    @PreAuthorize("@ss.hasPermission('public:ops-attach-info:query')")
    public CommonResult<PageResult<PublicOpsAttachInfoRespVO>> getOpsAttachInfoPage(@Valid PublicOpsAttachInfoPageReqVO pageReqVO) {
        PageResult<PublicOpsAttachInfoDO> pageResult = opsAttachInfoService.getOpsAttachInfoPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, PublicOpsAttachInfoRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出资产附件信息 Excel")
    @PreAuthorize("@ss.hasPermission('public:ops-attach-info:export')")
    public void exportOpsAttachInfoExcel(@Valid PublicOpsAttachInfoPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<PublicOpsAttachInfoDO> list = opsAttachInfoService.getOpsAttachInfoPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "资产附件信息.xls", "数据", PublicOpsAttachInfoRespVO.class,
                        BeanUtils.toBean(list, PublicOpsAttachInfoRespVO.class));
    }

}