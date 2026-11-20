package com.holdtime.ops.module.web.controller.admin;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.excel.write.style.HorizontalCellStyleStrategy;
import com.fhs.common.utils.StringUtil;
import com.holdtime.ops.module.handler.ExcelStyleHandler;
import com.holdtime.ops.module.handler.ExcelTitleSheetWriteHandler;
import com.holdtime.ops.module.service.TrafficOpsMarklineExtService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Operation;

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

import com.holdtime.ops.module.model.vo.*;
import com.holdtime.ops.module.entity.TrafficOpsMarklineDO;
import com.holdtime.ops.module.service.TrafficOpsMarklineService;

@Tag(name = "管理后台 - 标线")
@RestController
@RequestMapping("/traffic/ops-markline")
@Validated
@Slf4j
public class TrafficOpsMarklineController {

    @Resource
    private TrafficOpsMarklineService opsMarklineService;
    @Resource
    private TrafficOpsMarklineExtService opsMarklineExtService;

    @PostMapping("/create")
    @Operation(summary = "创建标线")
    @PreAuthorize("@ss.hasPermission('traffic:ops-markline:create')")
    public CommonResult<String> createOpsMarkline(@Valid @RequestBody TrafficOpsMarklineExtSaveReqVO createReqVO) {
        return success(opsMarklineExtService.saveOpsMarkline(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新标线")
    @PreAuthorize("@ss.hasPermission('traffic:ops-markline:update')")
    public CommonResult<Boolean> updateOpsMarkline(@Valid @RequestBody TrafficOpsMarklineExtSaveReqVO updateReqVO) {
        opsMarklineExtService.saveOpsMarkline(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除标线")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('traffic:ops-markline:delete')")
    public CommonResult<Boolean> deleteOpsMarkline(@RequestParam("id") String id) {
        opsMarklineService.deleteOpsMarkline(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得标线")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('traffic:ops-markline:query')")
    public CommonResult<TrafficOpsMarklineExtRespVO> getOpsMarkline(@RequestParam("id") String id) {
        return success(opsMarklineExtService.getOpsMarkline(id));
    }

    @GetMapping("/page")
    @Operation(summary = "获得标线分页")
    @PreAuthorize("@ss.hasPermission('traffic:ops-markline:query')")
    public CommonResult<PageResult<TrafficOpsMarklineRespVO>> getOpsMarklinePage(@Valid TrafficOpsMarklinePageReqVO pageReqVO,@RequestParam(value="isFy",required=false) String isFy) {
        if(!StringUtil.isEmpty(isFy)){
            pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        }
        PageResult<TrafficOpsMarklineDO> pageResult = opsMarklineService.getOpsMarklinePage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, TrafficOpsMarklineRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出标线 Excel")
    @PreAuthorize("@ss.hasPermission('traffic:ops-markline:export')")
    public void exportOpsMarklineExcel(@Valid TrafficOpsMarklinePageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<TrafficOpsMarklineDO> list = opsMarklineService.getOpsMarklinePage(pageReqVO).getList();
        // 2. 计算汇总
        double total = list.stream().filter(doInfo -> StringUtils.isNotBlank(doInfo.getArea())).mapToDouble(doInfo -> Double.parseDouble(doInfo.getArea().replace("\u202D", ""))).sum();

        response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode("标线台账.xlsx", StandardCharsets.UTF_8.name()));
        response.setContentType("application/vnd.ms-excel;charset=UTF-8");

        ExcelWriter excelWriter = EasyExcel.write(response.getOutputStream()).autoCloseStream(false).build();
        WriteSheet writeSheet = EasyExcel.writerSheet("数据")
                .head(TrafficOpsMarklineRespVO.class)
                .registerWriteHandler(new HorizontalCellStyleStrategy(ExcelStyleHandler.getHeadStyle(), ExcelStyleHandler.getContentStyle()))
                .registerWriteHandler(new ExcelTitleSheetWriteHandler("标线台账", "总面积：" + total, 9))
                .relativeHeadRowIndex(2)
                .build();

        // 导出 Excel，添加大标题和副标题
        excelWriter.write(BeanUtils.toBean(list, TrafficOpsMarklineRespVO.class), writeSheet);
        excelWriter.finish();
        response.flushBuffer();
    }

}