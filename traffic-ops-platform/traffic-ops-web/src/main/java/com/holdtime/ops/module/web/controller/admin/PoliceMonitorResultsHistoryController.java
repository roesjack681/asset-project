package com.holdtime.ops.module.web.controller.admin;

import cn.hutool.http.HttpUtil;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.system.dal.dataobject.user.AdminUserDO;
import cn.iocoder.yudao.module.system.dal.mysql.user.AdminUserMapper;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.converters.longconverter.LongStringConverter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.excel.write.style.HorizontalCellStyleStrategy;
import com.alibaba.excel.write.style.column.LongestMatchColumnWidthStyleStrategy;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.holdtime.ops.module.entity.PoliceOpsElectricPoliceDO;
import com.holdtime.ops.module.entity.mapper.PoliceOpsElectricPoliceMapper;
import com.holdtime.ops.module.enums.OpsConstants;
import com.holdtime.ops.module.handler.ExcelCellByMultiHandler;
import com.holdtime.ops.module.handler.ExcelStyleHandler;
import com.holdtime.ops.module.service.PoliceMonitorResultsHistoryExtService;
import com.xingyuv.captcha.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
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

import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

import cn.iocoder.yudao.framework.excel.core.util.ExcelUtils;

import com.holdtime.ops.module.model.vo.*;
import com.holdtime.ops.module.entity.PoliceMonitorResultsHistoryDO;
import com.holdtime.ops.module.service.PoliceMonitorResultsHistoryService;

@Tag(name = "管理后台 - 公安设备监测结果历史")
@RestController
@RequestMapping("/police/monitor-results-history")
@Validated
@Slf4j
public class PoliceMonitorResultsHistoryController {

    @Resource
    private PoliceMonitorResultsHistoryService monitorResultsHistoryService;
    @Resource
    private PoliceMonitorResultsHistoryExtService monitorResultsHistoryExtService;
    @Resource
    private PoliceOpsElectricPoliceMapper opsElectricPoliceMapper;
    @Resource
    private AdminUserMapper adminUserMapper;

    @PostMapping("/create")
    @Operation(summary = "创建公安设备监测结果历史")
    @PreAuthorize("@ss.hasPermission('police:monitor-results-history:create')")
    public CommonResult<String> createMonitorResultsHistory(@Valid @RequestBody PoliceMonitorResultsHistorySaveReqVO createReqVO) {
        return success(monitorResultsHistoryService.createMonitorResultsHistory(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新公安设备监测结果历史")
    @PreAuthorize("@ss.hasPermission('police:monitor-results-history:update')")
    public CommonResult<Boolean> updateMonitorResultsHistory(@Valid @RequestBody PoliceMonitorResultsHistorySaveReqVO updateReqVO) {
        monitorResultsHistoryService.updateMonitorResultsHistory(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除公安设备监测结果历史")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('police:monitor-results-history:delete')")
    public CommonResult<Boolean> deleteMonitorResultsHistory(@RequestParam("id") String id) {
        monitorResultsHistoryService.deleteMonitorResultsHistory(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得公安设备监测结果历史")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('police:monitor-results-history:query')")
    public CommonResult<PoliceMonitorResultsHistoryRespVO> getMonitorResultsHistory(@RequestParam("id") String id) {
        PoliceMonitorResultsHistoryDO monitorResultsHistory = monitorResultsHistoryService.getMonitorResultsHistory(id);
        return success(BeanUtils.toBean(monitorResultsHistory, PoliceMonitorResultsHistoryRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得公安设备监测结果历史分页")
    @PreAuthorize("@ss.hasPermission('police:monitor-results-history:query')")
    public CommonResult<PageResult<PoliceMonitorResultsHistoryRespVO>> getMonitorResultsHistoryPage(@Valid PoliceMonitorResultsHistoryPageReqVO pageReqVO) {
        PageResult<PoliceMonitorResultsHistoryRespVO> pageResult = BeanUtils.toBean(monitorResultsHistoryExtService.getMonitorResultsHistoryPage(pageReqVO), PoliceMonitorResultsHistoryRespVO.class);
        Optional.ofNullable(pageResult.getList()).orElse(new ArrayList<>()).forEach((item) -> {
            // 查询电警信息
            PoliceOpsElectricPoliceDO policeOpsElectricPoliceDO = opsElectricPoliceMapper.selectById(item.getEquId());
            if (policeOpsElectricPoliceDO != null) {
                item.setEquLabel(policeOpsElectricPoliceDO.getEquLable());
                item.setUnit(policeOpsElectricPoliceDO.getUnit());
            }

            item.setIsBindMember("否");
            if (StringUtils.isNotBlank(item.getMemberBh() )) {
                item.setIsBindMember("是");
            }

            if (StringUtils.isNotBlank(item.getUpdater())) {
                AdminUserDO adminUserDO = adminUserMapper.selectById(item.getUpdater());
                if (adminUserDO != null)  {
                    item.setUpdater(adminUserDO.getNickname());
                }
            }
        });
        return success(pageResult);
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出公安设备监测结果历史 Excel")
    @PreAuthorize("@ss.hasPermission('police:monitor-results-history:export')")
    public void exportMonitorResultsHistoryExcel(@Valid PoliceMonitorResultsHistoryPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        // 设置不分页查询
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);

        // 获取监测结果数据
        List<PoliceMonitorResultsHistoryRespVO> exportList = Optional.ofNullable(
                        monitorResultsHistoryExtService.getMonitorResultsHistoryPage(pageReqVO).getList())
                .map(list -> BeanUtils.toBean(list, PoliceMonitorResultsHistoryRespVO.class))
                .orElseGet(ArrayList::new);

        // 获取设备ID列表并过滤空值
        List<String> equIdList = exportList.stream()
                .map(PoliceMonitorResultsHistoryRespVO::getEquId)
                .filter(StringUtils::isNotBlank)
                .collect(Collectors.toList());

        // 查询设备信息
        Map<String, PoliceOpsElectricPoliceDO> policeOpsElectricPoliceDOMap = CollectionUtils.isEmpty(equIdList)
                ? new HashMap<>()
                : Optional.ofNullable(opsElectricPoliceMapper.selectList(
                        new LambdaQueryWrapperX<PoliceOpsElectricPoliceDO>()
                                .in(PoliceOpsElectricPoliceDO::getId, equIdList)))
                .orElseGet(ArrayList::new)
                .stream()
                .collect(Collectors.toMap(PoliceOpsElectricPoliceDO::getId, Function.identity()));

        // 创建线程池（根据实际情况调整核心线程数）
        ExecutorService executorService = Executors.newFixedThreadPool(10);

        // 收集需要下载图片的记录
        List<PoliceMonitorResultsHistoryRespVO> needDownloadItems = exportList.stream()
                .filter(item -> StringUtils.isNotBlank(item.getResultImg()) || StringUtils.isNotBlank(item.getEvidenceFileId()))
                .collect(Collectors.toList());

        // 使用CountDownLatch等待所有下载任务完成
        CountDownLatch latch = new CountDownLatch(needDownloadItems.size());

        // 多线程下载图片
        needDownloadItems.forEach(item -> executorService.submit(() -> {
            try {
                if (StringUtils.isNotBlank(item.getResultImg())) {
                    item.setImageBytes(HttpUtil.downloadBytes(item.getResultImg()));
                }

                if (StringUtils.isNotBlank(item.getEvidenceFileId())) {
                    item.setEvidenceBytes(HttpUtil.downloadBytes(item.getEvidenceFileId()));
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

        // 关闭线程池
        executorService.shutdown();

        // 处理每条记录（非图片下载部分）
        exportList.forEach(item -> {
            // 设置维修订单状态
            item.setIsRepairOrder(OpsConstants.STRING_ONE.equals(item.getIsRepairOrder()) ? "有" : "无");

            // 设置绑定成员状态
            item.setIsBindMember(StringUtils.isNotBlank(item.getMemberBh()) ? "是" : "否");

            // 设置设备标签
            Optional.ofNullable(policeOpsElectricPoliceDOMap.get(item.getEquId()))
                    .ifPresent(police -> {
                        item.setEquLabel(police.getEquLable());
                        item.setUnit(police.getUnit());
                    });
        });

        Map<String, List<PoliceMonitorResultsHistoryRespVO>> historyRespVOMap = exportList.stream().collect(Collectors.groupingBy(PoliceMonitorResultsHistoryRespVO::getChangeSituation));
        List<PoliceMonitorResultsHistoryRespVO> sucExportList = historyRespVOMap.get("1");
        List<PoliceMonitorResultsHistoryRespVO> badExportList = historyRespVOMap.get("2");
        List<PoliceMonitorResultsHistoryRespVO> noChangeExportList = historyRespVOMap.get("0");

        response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode("公安设备修复情况历史.xls", StandardCharsets.UTF_8.name()));
        response.setContentType("application/vnd.ms-excel;charset=UTF-8");

        ExcelWriter excelWriter = EasyExcel.write(response.getOutputStream()).autoCloseStream(false).build();

        if (!CollectionUtils.isEmpty(sucExportList)) {
            WriteSheet writeSheet1 = EasyExcel.writerSheet("已修复")
                    .head(PoliceMonitorResultsHistoryRespVO.class)
                    .registerWriteHandler(new LongestMatchColumnWidthStyleStrategy())
                    .registerConverter(new LongStringConverter())
                    .build();
            excelWriter.write(sucExportList, writeSheet1);
        }

        if (!CollectionUtils.isEmpty(badExportList)) {
            WriteSheet writeSheet2 = EasyExcel.writerSheet("已损坏")
                    .head(PoliceMonitorResultsHistoryRespVO.class)
                    .registerWriteHandler(new LongestMatchColumnWidthStyleStrategy())
                    .registerConverter(new LongStringConverter())
                    .build();
            excelWriter.write(badExportList, writeSheet2);
        }

        if (!CollectionUtils.isEmpty(noChangeExportList)) {
            WriteSheet writeSheet3 = EasyExcel.writerSheet("无变化")
                    .head(PoliceMonitorResultsHistoryRespVO.class)
                    .registerWriteHandler(new LongestMatchColumnWidthStyleStrategy())
                    .registerConverter(new LongStringConverter())
                    .build();
            excelWriter.write(noChangeExportList, writeSheet3);
        }

    }

}