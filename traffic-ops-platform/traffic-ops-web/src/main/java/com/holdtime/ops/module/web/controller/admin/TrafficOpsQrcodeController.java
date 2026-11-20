package com.holdtime.ops.module.web.controller.admin;

import com.holdtime.ops.module.service.TrafficOpsQrCodeExtService;
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
import com.holdtime.ops.module.entity.TrafficOpsQrcodeDO;
import com.holdtime.ops.module.service.TrafficOpsQrcodeService;

@Tag(name = "管理后台 - 资产二维码信息")
@RestController
@RequestMapping("/traffic/ops-qrcode")
@Validated
public class TrafficOpsQrcodeController {

    @Resource
    private TrafficOpsQrcodeService opsQrcodeService;

    @Resource
    private TrafficOpsQrCodeExtService trafficOpsQrCodeExtService;

    @PostMapping("/create")
    @Operation(summary = "创建资产二维码信息")
    @PreAuthorize("@ss.hasPermission('traffic:ops-qrcode:create')")
    public CommonResult<String> createOpsQrcode(@Valid @RequestBody TrafficOpsQrcodeSaveReqVO createReqVO) {
        return success(opsQrcodeService.createOpsQrcode(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新资产二维码信息")
    @PreAuthorize("@ss.hasPermission('traffic:ops-qrcode:update')")
    public CommonResult<Boolean> updateOpsQrcode(@Valid @RequestBody TrafficOpsQrcodeSaveReqVO updateReqVO) {
        opsQrcodeService.updateOpsQrcode(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除资产二维码信息")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('traffic:ops-qrcode:delete')")
    public CommonResult<Boolean> deleteOpsQrcode(@RequestParam("id") String id) {
        opsQrcodeService.deleteOpsQrcode(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得资产二维码信息")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('traffic:ops-qrcode:query')")
    public CommonResult<TrafficOpsQrcodeRespVO> getOpsQrcode(@RequestParam("id") String id) {
        TrafficOpsQrcodeDO opsQrcode = opsQrcodeService.getOpsQrcode(id);
        return success(BeanUtils.toBean(opsQrcode, TrafficOpsQrcodeRespVO.class));
    }

    @GetMapping("/queryPrintData")
    @Operation(summary = "获得资产二维码打印数据信息")
    public CommonResult<List<TrafficOpsQrcodePrintRespVO>> queryPrintData(@RequestParam("printType") String printType,
                                                               @RequestParam("keyword") String keyword) {
        return success(trafficOpsQrCodeExtService.queryOpsQrcodePrintDataList(printType, keyword));
    }

    @GetMapping("/page")
    @Operation(summary = "获得资产二维码信息分页")
    @PreAuthorize("@ss.hasPermission('traffic:ops-qrcode:query')")
    public CommonResult<PageResult<TrafficOpsQrcodeRespVO>> getOpsQrcodePage(@Valid TrafficOpsQrcodePageReqVO pageReqVO) {
        PageResult<TrafficOpsQrcodeDO> pageResult = opsQrcodeService.getOpsQrcodePage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, TrafficOpsQrcodeRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出资产二维码信息 Excel")
    @PreAuthorize("@ss.hasPermission('traffic:ops-qrcode:export')")
    public void exportOpsQrcodeExcel(@Valid TrafficOpsQrcodePageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<TrafficOpsQrcodeDO> list = opsQrcodeService.getOpsQrcodePage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "资产二维码信息.xls", "数据", TrafficOpsQrcodeRespVO.class,
                        BeanUtils.toBean(list, TrafficOpsQrcodeRespVO.class));
    }

}