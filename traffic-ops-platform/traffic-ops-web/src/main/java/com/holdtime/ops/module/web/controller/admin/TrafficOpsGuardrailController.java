package com.holdtime.ops.module.web.controller.admin;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.excel.write.style.HorizontalCellStyleStrategy;
import com.fhs.common.utils.StringUtil;
import com.holdtime.ops.module.handler.ExcelStyleHandler;
import com.holdtime.ops.module.handler.ExcelTitleSheetWriteHandler;
import com.holdtime.ops.module.service.TrafficOpsGuardrailExtService;
import org.apache.commons.lang3.StringUtils;
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
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.io.IOException;

import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

import cn.iocoder.yudao.framework.excel.core.util.ExcelUtils;

import com.holdtime.ops.module.model.vo.*;
import com.holdtime.ops.module.entity.TrafficOpsGuardrailDO;
import com.holdtime.ops.module.service.TrafficOpsGuardrailService;

@Tag(name = "管理后台 - 护栏")
@RestController
@RequestMapping("/traffic/ops-guardrail")
@Validated
public class TrafficOpsGuardrailController {

    @Resource
    private TrafficOpsGuardrailService opsGuardrailService;
    @Resource
    private TrafficOpsGuardrailExtService opsGuardrailExtService;

    @PostMapping("/create")
    @Operation(summary = "创建护栏")
    @PreAuthorize("@ss.hasPermission('traffic:ops-guardrail:create')")
    public CommonResult<String> createOpsGuardrail(@Valid @RequestBody TrafficOpsGuardrailExtSaveReqVO createReqVO) {
        return success(opsGuardrailExtService.saveOpsGuardrail(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新护栏")
    @PreAuthorize("@ss.hasPermission('traffic:ops-guardrail:update')")
    public CommonResult<Boolean> updateOpsGuardrail(@Valid @RequestBody TrafficOpsGuardrailExtSaveReqVO updateReqVO) {
        opsGuardrailExtService.saveOpsGuardrail(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除护栏")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('traffic:ops-guardrail:delete')")
    public CommonResult<Boolean> deleteOpsGuardrail(@RequestParam("id") String id) {
        opsGuardrailService.deleteOpsGuardrail(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得护栏")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('traffic:ops-guardrail:query')")
    public CommonResult<TrafficOpsGuardrailExtRespVO> getOpsGuardrail(@RequestParam("id") String id) {
        return success(opsGuardrailExtService.getOpsGuardrail(id));
    }

    @GetMapping("/page")
    @Operation(summary = "获得护栏分页")
    @PreAuthorize("@ss.hasPermission('traffic:ops-guardrail:query')")
    public CommonResult<PageResult<TrafficOpsGuardrailRespVO>> getOpsGuardrailPage(@Valid TrafficOpsGuardrailPageReqVO pageReqVO,@RequestParam(value="isFy",required=false) String isFy) {
        if(!StringUtil.isEmpty(isFy)){
            pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        }
        PageResult<TrafficOpsGuardrailDO> pageResult = opsGuardrailService.getOpsGuardrailPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, TrafficOpsGuardrailRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出护栏 Excel")
    @PreAuthorize("@ss.hasPermission('traffic:ops-guardrail:export')")
    public void exportOpsGuardrailExcel(@Valid TrafficOpsGuardrailPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<TrafficOpsGuardrailDO> list = opsGuardrailService.getOpsGuardrailPage(pageReqVO).getList();

        // 2. 计算汇总
        int total = list.stream().filter(doInfo -> doInfo.getLength() != null).mapToInt(doInfo -> doInfo.getLength().intValue()).sum();

        response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode("护栏台账.xlsx", StandardCharsets.UTF_8.name()));
        response.setContentType("application/vnd.ms-excel;charset=UTF-8");

        ExcelWriter excelWriter = EasyExcel.write(response.getOutputStream()).autoCloseStream(false).build();
        WriteSheet writeSheet = EasyExcel.writerSheet("数据")
                .head(TrafficOpsGuardrailRespVO.class)
                .registerWriteHandler(new HorizontalCellStyleStrategy(ExcelStyleHandler.getHeadStyle(), ExcelStyleHandler.getContentStyle()))
                .registerWriteHandler(new ExcelTitleSheetWriteHandler("护栏台账", "总长度：" + total + "米", 5))
                .relativeHeadRowIndex(2)
                .build();

        // 导出 Excel，添加大标题和副标题
        excelWriter.write(BeanUtils.toBean(list, TrafficOpsGuardrailRespVO.class), writeSheet);
        excelWriter.finish();
        response.flushBuffer();
    }

}