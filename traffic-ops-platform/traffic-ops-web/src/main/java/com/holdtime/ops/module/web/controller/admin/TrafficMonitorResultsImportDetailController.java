package com.holdtime.ops.module.web.controller.admin;

import cn.hutool.core.date.DateUtil;
import cn.hutool.http.HttpUtil;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.converters.longconverter.LongStringConverter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.excel.write.style.column.LongestMatchColumnWidthStyleStrategy;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.holdtime.ops.module.entity.TrafficOpsElectricPoliceDO;
import com.holdtime.ops.module.entity.mapper.TrafficOpsElectricPoliceMapper;
import com.holdtime.ops.module.model.dto.ExcelImportTrafficMonitorResultsDTO;
import com.holdtime.ops.module.service.TrafficMonitorResultsImportDetailExtService;
import com.holdtime.ops.module.service.impl.ExcelImportExtServiceImpl;
import com.xingyuv.captcha.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.stream.Collectors;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

import com.holdtime.ops.module.model.vo.*;
import com.holdtime.ops.module.entity.TrafficMonitorResultsImportDetailDO;
import com.holdtime.ops.module.service.TrafficMonitorResultsImportDetailService;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "管理后台 - 设备监测结果导入信息")
@RestController
@Slf4j
@RequestMapping("/traffic/monitor-results-import-detail")
@Validated
public class TrafficMonitorResultsImportDetailController {

    @Resource
    private TrafficMonitorResultsImportDetailService monitorResultsImportDetailService;
    @Resource
    private TrafficMonitorResultsImportDetailExtService trafficMonitorResultsImportDetailExtService;
    @Resource
    private ExcelImportExtServiceImpl excelImportExtService;
    @Autowired
    private TrafficOpsElectricPoliceMapper trafficOpsElectricPoliceMapper;

    @PostMapping("/create")
    @Operation(summary = "创建设备监测结果导入信息")
    @PreAuthorize("@ss.hasPermission('traffic:monitor-results-import-detail:create')")
    public CommonResult<String> createMonitorResultsImportDetail(@Valid @RequestBody TrafficMonitorResultsImportDetailSaveReqVO createReqVO) {
        return success(monitorResultsImportDetailService.createMonitorResultsImportDetail(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新设备监测结果导入信息")
    @PreAuthorize("@ss.hasPermission('traffic:monitor-results-import-detail:update')")
    public CommonResult<Boolean> updateMonitorResultsImportDetail(@Valid @RequestBody TrafficMonitorResultsImportDetailSaveReqVO updateReqVO) {
        monitorResultsImportDetailService.updateMonitorResultsImportDetail(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除设备监测结果导入信息")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('traffic:monitor-results-import-detail:delete')")
    public CommonResult<Boolean> deleteMonitorResultsImportDetail(@RequestParam("id") String id) {
        monitorResultsImportDetailService.deleteMonitorResultsImportDetail(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得设备监测结果导入信息")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('traffic:monitor-results-import-detail:query')")
    public CommonResult<TrafficMonitorResultsImportDetailRespVO> getMonitorResultsImportDetail(@RequestParam("id") String id) {
        TrafficMonitorResultsImportDetailDO monitorResultsImportDetail = monitorResultsImportDetailService.getMonitorResultsImportDetail(id);
        return success(BeanUtils.toBean(monitorResultsImportDetail, TrafficMonitorResultsImportDetailRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得设备监测结果导入信息分页")
    @PreAuthorize("@ss.hasPermission('traffic:monitor-results-import-detail:query')")
    public CommonResult<PageResult<TrafficMonitorResultsImportDetailExtRespVO>> getMonitorResultsImportDetailPage(@Valid TrafficMonitorResultsImportDetailExtPageReqVO pageReqVO) {
        return success(trafficMonitorResultsImportDetailExtService.getMonitorResultsImportDetailPage(pageReqVO));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出设备监测结果导入信息 Excel")
    @PreAuthorize("@ss.hasPermission('traffic:monitor-results-import-detail:export')")
    public void exportMonitorResultsImportDetailExcel(@Valid TrafficMonitorResultsImportDetailExtPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode("重点数据日检测管理" + DateUtil.format(new Date(), "yyyyMMddHHmmss") + ".xlsx", StandardCharsets.UTF_8.name()));
        response.setContentType("application/vnd.ms-excel;charset=UTF-8");

        ExcelWriter excelWriter = EasyExcel.write(response.getOutputStream()).autoCloseStream(false).build();
        WriteSheet writeSheet = EasyExcel.writerSheet("数据")
                .head(TrafficMonitorResultsImportDetailExtRespVO.class)
                .registerWriteHandler(new LongestMatchColumnWidthStyleStrategy())
                .registerConverter(new LongStringConverter())
                .build();

        int pageSize = 500;
        int pageNo = 1;

        // 全局线程池
        ExecutorService executorService = Executors.newFixedThreadPool(10);

        try {
            do {
                pageReqVO.setPageSize(pageSize);
                pageReqVO.setPageNo(pageNo);
                // 获取监测结果数据
                List<TrafficMonitorResultsImportDetailExtRespVO> exportList = Optional.ofNullable(
                                trafficMonitorResultsImportDetailExtService.getMonitorResultsImportDetailPage(pageReqVO).getList())
                        .map(list -> BeanUtils.toBean(list, TrafficMonitorResultsImportDetailExtRespVO.class))
                        .orElseGet(ArrayList::new);
                if (CollectionUtils.isEmpty(exportList)) {
                    pageNo = 0;
                    continue;
                }
                pageNo++;

                // 获取设备ID列表并过滤空值
                List<String> equIdList = exportList.stream()
                        .map(TrafficMonitorResultsImportDetailExtRespVO::getEquId)
                        .filter(StringUtils::isNotBlank)
                        .collect(Collectors.toList());

                // 查询设备信息
                Map<String, TrafficOpsElectricPoliceDO> trafficOpsElectricPoliceDOMap = CollectionUtils.isEmpty(equIdList)
                        ? new HashMap<>()
                        : Optional.ofNullable(trafficOpsElectricPoliceMapper.selectList(
                                new LambdaQueryWrapperX<TrafficOpsElectricPoliceDO>()
                                        .in(TrafficOpsElectricPoliceDO::getId, equIdList)))
                        .orElseGet(ArrayList::new)
                        .stream()
                        .collect(Collectors.toMap(TrafficOpsElectricPoliceDO::getId, Function.identity()));

                // 收集需要下载图片的记录
                List<TrafficMonitorResultsImportDetailExtRespVO> needDownloadItems = exportList.stream()
                        .filter(item -> StringUtils.isNotBlank(item.getResultImg()))
                        .collect(Collectors.toList());

                // 使用CountDownLatch等待所有下载任务完成
                CountDownLatch latch = new CountDownLatch(needDownloadItems.size());

                // 多线程下载图片
                needDownloadItems.forEach(item -> executorService.submit(() -> {
                    try {
                        if (StringUtils.isNotBlank(item.getResultImg())) {
                            item.setImageBytes(HttpUtil.downloadBytes(item.getResultImg()));
                        }
                    } catch (Exception e) {
                        log.error("下载图片失败: {}", item.getResultImg(), e);
                    } finally {
                        latch.countDown();
                    }
                }));

                // 等待所有下载任务完成
                try {
                    latch.await(60, TimeUnit.SECONDS); // 设置超时时间60秒
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    log.error("等待图片下载线程被中断", e);
                }

                // 处理每条记录（非图片下载部分）
                exportList.forEach(item -> {

                    // 设置设备标签
                    Optional.ofNullable(trafficOpsElectricPoliceDOMap.get(item.getEquId()))
                            .ifPresent(electricPoliceDO -> {
                                item.setIpAddr(electricPoliceDO.getIpAddr());
                                item.setRoadName(electricPoliceDO.getRoadName());
                                item.setKkbh(electricPoliceDO.getKkbh());
                                item.setDjType(electricPoliceDO.getDjType());
                                item.setUnit(electricPoliceDO.getUnit());
                                item.setEquLabel(electricPoliceDO.getEquLable());
                            });
                });
                excelWriter.write(exportList, writeSheet);

            } while (pageNo != 0);
            excelWriter.finish();
            response.flushBuffer();
        } finally {
            // 关闭线程池
            executorService.shutdown();
        }
    }

    @PostMapping("/importTrafficMonitorResultsInfo")
    @Operation(summary = "导入交警检测结果信息")
    public CommonResult<ImportRecordRespVO> importTrafficMonitorResultsInfo(@RequestParam("file") MultipartFile file) {
        return success(excelImportExtService.importExcelInfo("交警电警检测", file, ExcelImportTrafficMonitorResultsDTO.class));
    }

    @PostMapping("/remarkMonitorResults")
    @Operation(summary = "备注设备情况")
    public CommonResult<Boolean> remarkMonitorResults(@Valid @RequestBody TrafficMonitorResultsImportDetailSaveReqVO saveReqVO) {
        trafficMonitorResultsImportDetailExtService.remarkMonitorResults(saveReqVO);
        return success(true);
    }

}