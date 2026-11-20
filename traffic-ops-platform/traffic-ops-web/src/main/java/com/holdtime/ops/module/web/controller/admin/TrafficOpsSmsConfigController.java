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
import com.holdtime.ops.module.entity.TrafficOpsSmsConfigDO;
import com.holdtime.ops.module.service.TrafficOpsSmsConfigService;

@Tag(name = "管理后台 - 资产短信发送配置")
@RestController
@RequestMapping("/traffic/ops-sms-config")
@Validated
public class TrafficOpsSmsConfigController {

    @Resource
    private TrafficOpsSmsConfigService opsSmsConfigService;

    @PostMapping("/create")
    @Operation(summary = "创建资产短信发送配置")
    @PreAuthorize("@ss.hasPermission('traffic:ops-sms-config:create')")
    public CommonResult<String> createOpsSmsConfig(@Valid @RequestBody TrafficOpsSmsConfigSaveReqVO createReqVO) {
        return success(opsSmsConfigService.createOpsSmsConfig(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新资产短信发送配置")
    @PreAuthorize("@ss.hasPermission('traffic:ops-sms-config:update')")
    public CommonResult<Boolean> updateOpsSmsConfig(@Valid @RequestBody TrafficOpsSmsConfigSaveReqVO updateReqVO) {
        opsSmsConfigService.updateOpsSmsConfig(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除资产短信发送配置")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('traffic:ops-sms-config:delete')")
    public CommonResult<Boolean> deleteOpsSmsConfig(@RequestParam("id") String id) {
        opsSmsConfigService.deleteOpsSmsConfig(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得资产短信发送配置")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('traffic:ops-sms-config:query')")
    public CommonResult<TrafficOpsSmsConfigRespVO> getOpsSmsConfig(@RequestParam("id") String id) {
        TrafficOpsSmsConfigDO opsSmsConfig = opsSmsConfigService.getOpsSmsConfig(id);
        return success(BeanUtils.toBean(opsSmsConfig, TrafficOpsSmsConfigRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得资产短信发送配置分页")
    @PreAuthorize("@ss.hasPermission('traffic:ops-sms-config:query')")
    public CommonResult<PageResult<TrafficOpsSmsConfigRespVO>> getOpsSmsConfigPage(@Valid TrafficOpsSmsConfigPageReqVO pageReqVO) {
        PageResult<TrafficOpsSmsConfigDO> pageResult = opsSmsConfigService.getOpsSmsConfigPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, TrafficOpsSmsConfigRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出资产短信发送配置 Excel")
    @PreAuthorize("@ss.hasPermission('traffic:ops-sms-config:export')")
    public void exportOpsSmsConfigExcel(@Valid TrafficOpsSmsConfigPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<TrafficOpsSmsConfigDO> list = opsSmsConfigService.getOpsSmsConfigPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "资产短信发送配置.xls", "数据", TrafficOpsSmsConfigRespVO.class,
                        BeanUtils.toBean(list, TrafficOpsSmsConfigRespVO.class));
    }

}