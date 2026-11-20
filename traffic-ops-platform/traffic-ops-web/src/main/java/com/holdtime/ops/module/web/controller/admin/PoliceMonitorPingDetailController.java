package com.holdtime.ops.module.web.controller.admin;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.converters.longconverter.LongStringConverter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.excel.write.style.column.LongestMatchColumnWidthStyleStrategy;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Operation;

import javax.validation.*;
import javax.servlet.http.*;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

import com.holdtime.ops.module.model.vo.*;
import com.holdtime.ops.module.entity.PoliceMonitorPingDetailDO;
import com.holdtime.ops.module.service.PoliceMonitorPingDetailService;

@Tag(name = "管理后台 - 公安监测通达详情")
@RestController
@RequestMapping("/police/monitor-ping-detail")
@Validated
@Slf4j
public class PoliceMonitorPingDetailController {

    @Resource
    private PoliceMonitorPingDetailService monitorPingDetailService;

    @GetMapping("/get")
    @Operation(summary = "获得公安监测通达详情")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    public CommonResult<PoliceMonitorPingDetailRespVO> getMonitorPingDetail(@RequestParam("id") String id) {
        PoliceMonitorPingDetailDO monitorPingDetail = monitorPingDetailService.getMonitorPingDetail(id);
        return success(BeanUtils.toBean(monitorPingDetail, PoliceMonitorPingDetailRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得公安监测通达详情分页")
    public CommonResult<PageResult<PoliceMonitorPingDetailRespVO>> getMonitorPingDetailPage(@Valid PoliceMonitorPingDetailPageReqVO pageReqVO) {
        PageResult<PoliceMonitorPingDetailDO> pageResult = monitorPingDetailService.getMonitorPingDetailPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, PoliceMonitorPingDetailRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出公安监测通达详情 Excel")
    public void exportMonitorPingDetailExcel(@Valid PoliceMonitorPingDetailPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {

        response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode("公安监测通达详情.xlsx", StandardCharsets.UTF_8.name()));
        response.setContentType("application/vnd.ms-excel;charset=UTF-8");

        ExcelWriter excelWriter = EasyExcel.write(response.getOutputStream()).autoCloseStream(false).build();
        WriteSheet writeSheet = EasyExcel.writerSheet("数据")
                .head(PoliceMonitorPingDetailRespVO.class)
                .registerWriteHandler(new LongestMatchColumnWidthStyleStrategy())
                .registerConverter(new LongStringConverter())
                .build();

        int pageSize = 1000;
        int pageNo = 1;

        // 全局线程池
        ExecutorService executorService = Executors.newFixedThreadPool(10);

        try {
            do {
                pageReqVO.setPageSize(pageSize);
                pageReqVO.setPageNo(pageNo);
                // 获取监测结果数据
                List<PoliceMonitorPingDetailRespVO> exportList = Optional.ofNullable(
                                monitorPingDetailService.getMonitorPingDetailPage(pageReqVO).getList())
                        .map(list -> BeanUtils.toBean(list, PoliceMonitorPingDetailRespVO.class))
                        .orElseGet(ArrayList::new);
                if (CollectionUtils.isEmpty(exportList)) {
                    pageNo = 0;
                    continue;
                }
                pageNo++;

                excelWriter.write(exportList, writeSheet);

            } while (pageNo != 0);
            excelWriter.finish();
            response.flushBuffer();
        } finally {
            // 关闭线程池
            executorService.shutdown();
        }
    }

}