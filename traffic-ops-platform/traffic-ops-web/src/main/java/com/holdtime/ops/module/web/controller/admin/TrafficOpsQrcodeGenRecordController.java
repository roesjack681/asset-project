package com.holdtime.ops.module.web.controller.admin;

import com.holdtime.ops.module.service.TrafficOpsQrCodeExtService;
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
import com.holdtime.ops.module.entity.TrafficOpsQrcodeGenRecordDO;
import com.holdtime.ops.module.service.TrafficOpsQrcodeGenRecordService;

@Tag(name = "管理后台 - 资产二维码生成记录")
@RestController
@RequestMapping("/traffic/ops-qrcode-gen-record")
@Validated
public class TrafficOpsQrcodeGenRecordController {

    @Resource
    private TrafficOpsQrcodeGenRecordService opsQrcodeGenRecordService;
    @Resource
    private TrafficOpsQrCodeExtService trafficOpsQrCodeExtService;

    @PostMapping("/create")
    @Operation(summary = "创建资产二维码生成记录")
    @PreAuthorize("@ss.hasPermission('traffic:ops-qrcode-gen-record:create')")
    public CommonResult<?> createOpsQrcodeGenRecord(@Valid @RequestBody TrafficOpsQrcodeGenRecordExtSaveReqVO createReqVO) {
        trafficOpsQrCodeExtService.genEquipQrCode(createReqVO);
        return success(true);
    }

    @PutMapping("/update")
    @Operation(summary = "更新资产二维码生成记录")
    @PreAuthorize("@ss.hasPermission('traffic:ops-qrcode-gen-record:update')")
    public CommonResult<Boolean> updateOpsQrcodeGenRecord(@Valid @RequestBody TrafficOpsQrcodeGenRecordExtSaveReqVO updateReqVO) {
        trafficOpsQrCodeExtService.genEquipQrCode(updateReqVO);
        return success(true);
    }

    @PostMapping("/createPoliceEquipQrCode")
    @Operation(summary = "创建公安资产二维码生成记录")
    public CommonResult<?> createPoliceEquipQrCode(@Valid @RequestBody PoliceOpsQrcodeGenRecordExtSaveReqVO createReqVO) {
        trafficOpsQrCodeExtService.genPoliceEquipQrCode(createReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除资产二维码生成记录")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('traffic:ops-qrcode-gen-record:delete')")
    public CommonResult<Boolean> deleteOpsQrcodeGenRecord(@RequestParam("id") String id) {
        trafficOpsQrCodeExtService.deleteEquipQrCodeByGenId(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得资产二维码生成记录")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('traffic:ops-qrcode-gen-record:query')")
    public CommonResult<TrafficOpsQrcodeGenRecordRespVO> getOpsQrcodeGenRecord(@RequestParam("id") String id) {
        TrafficOpsQrcodeGenRecordDO opsQrcodeGenRecord = opsQrcodeGenRecordService.getOpsQrcodeGenRecord(id);
        return success(BeanUtils.toBean(opsQrcodeGenRecord, TrafficOpsQrcodeGenRecordRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得资产二维码生成记录分页")
    @PreAuthorize("@ss.hasPermission('traffic:ops-qrcode-gen-record:query')")
    public CommonResult<PageResult<TrafficOpsQrcodeGenRecordRespVO>> getOpsQrcodeGenRecordPage(@Valid TrafficOpsQrcodeGenRecordPageReqVO pageReqVO) {
        PageResult<TrafficOpsQrcodeGenRecordDO> pageResult = opsQrcodeGenRecordService.getOpsQrcodeGenRecordPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, TrafficOpsQrcodeGenRecordRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出资产二维码生成记录 Excel")
    @PreAuthorize("@ss.hasPermission('traffic:ops-qrcode-gen-record:export')")
    public void exportOpsQrcodeGenRecordExcel(@Valid TrafficOpsQrcodeGenRecordPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<TrafficOpsQrcodeGenRecordDO> list = opsQrcodeGenRecordService.getOpsQrcodeGenRecordPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "资产二维码生成记录.xls", "数据", TrafficOpsQrcodeGenRecordRespVO.class,
                        BeanUtils.toBean(list, TrafficOpsQrcodeGenRecordRespVO.class));
    }

}