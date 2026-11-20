package com.holdtime.ops.module.web.controller.admin;

import cn.hutool.http.HttpUtil;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.system.dal.dataobject.user.AdminUserDO;
import cn.iocoder.yudao.module.system.dal.mysql.user.AdminUserMapper;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.converters.longconverter.LongStringConverter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.excel.write.style.column.LongestMatchColumnWidthStyleStrategy;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.holdtime.ops.module.entity.PoliceOpsElectricPoliceDO;
import com.holdtime.ops.module.entity.mapper.PoliceOpsElectricPoliceMapper;
import com.holdtime.ops.module.enums.OpsConstants;
import com.holdtime.ops.module.model.dto.ExcelImportPoliceDjMonitorDTO;
import com.holdtime.ops.module.service.ExcelImportExtService;
import com.holdtime.ops.module.service.PoliceMonitorResultsExtService;
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

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

import com.holdtime.ops.module.model.vo.*;
import com.holdtime.ops.module.entity.PoliceMonitorResultsDO;
import com.holdtime.ops.module.service.PoliceMonitorResultsService;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "管理后台 - 公安设备监测结果")
@RestController
@RequestMapping("/police/monitor-results")
@Validated
@Slf4j
public class PoliceMonitorResultsController {

    @Resource
    private PoliceMonitorResultsService monitorResultsService;
    @Resource
    private ExcelImportExtService excelImportExtService;
    @Resource
    private PoliceMonitorResultsExtService policeMonitorResultsBizService;
    @Resource
    private PoliceOpsElectricPoliceMapper policeOpsElectricPoliceMapper;
    @Resource
    private AdminUserMapper adminUserMapper;

    @PostMapping("/create")
    @Operation(summary = "创建公安设备监测结果")
    @PreAuthorize("@ss.hasPermission('police:monitor-results:create')")
    public CommonResult<String> createMonitorResults(@Valid @RequestBody PoliceMonitorResultsSaveReqVO createReqVO) {
        return success(monitorResultsService.createMonitorResults(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新公安设备监测结果")
    @PreAuthorize("@ss.hasPermission('police:monitor-results:update')")
    public CommonResult<Boolean> updateMonitorResults(@Valid @RequestBody PoliceMonitorResultsSaveReqVO updateReqVO) {
        monitorResultsService.updateMonitorResults(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除公安设备监测结果")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('police:monitor-results:delete')")
    public CommonResult<Boolean> deleteMonitorResults(@RequestParam("id") String id) {
        monitorResultsService.deleteMonitorResults(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得公安设备监测结果")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('police:monitor-results:query')")
    public CommonResult<PoliceMonitorResultsRespVO> getMonitorResults(@RequestParam("id") String id) {
        PoliceMonitorResultsDO monitorResults = monitorResultsService.getMonitorResults(id);
        return success(BeanUtils.toBean(monitorResults, PoliceMonitorResultsRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得公安设备监测结果分页")
    @PreAuthorize("@ss.hasPermission('police:monitor-results:query')")
    public CommonResult<PageResult<PoliceMonitorResultsRespVO>> getMonitorResultsPage(@Valid PoliceMonitorResultsPageReqVO pageReqVO) {
        PageResult<PoliceMonitorResultsRespVO> pageResult = BeanUtils.toBean(policeMonitorResultsBizService.getMonitorResultsPage(pageReqVO), PoliceMonitorResultsRespVO.class);

        Optional.ofNullable(pageResult.getList()).orElse(new ArrayList<>()).forEach((item) -> {
            // 查询电警信息
            item.setIsBindMember("否");
            PoliceOpsElectricPoliceDO policeOpsElectricPoliceDO = policeOpsElectricPoliceMapper.selectById(item.getEquId());
            if (policeOpsElectricPoliceDO != null) {
                item.setEquName(policeOpsElectricPoliceDO.getEquName());
                item.setEquLabel(policeOpsElectricPoliceDO.getEquLable());
                item.setUnit(policeOpsElectricPoliceDO.getUnit());
                if (StringUtils.isNotBlank(policeOpsElectricPoliceDO.getMemberBh() )) {
                    item.setIsBindMember("是");
                }
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
    @Operation(summary = "导出公安设备监测结果 Excel")
    @PreAuthorize("@ss.hasPermission('police:monitor-results:export')")
    public void exportMonitorResultsExcel(@Valid PoliceMonitorResultsPageReqVO pageReqVO,
              HttpServletResponse response, @RequestParam(value ="isExportImg",  defaultValue = "1") String isExportImg) throws IOException {

        response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode("公安设备监测结果.xlsx", StandardCharsets.UTF_8.name()));
        response.setContentType("application/vnd.ms-excel;charset=UTF-8");

        ExcelWriter excelWriter = EasyExcel.write(response.getOutputStream()).autoCloseStream(false).build();
        WriteSheet writeSheet = EasyExcel.writerSheet("数据1")
                .head(PoliceMonitorResultsRespVO.class)
                .registerWriteHandler(new LongestMatchColumnWidthStyleStrategy())
                .registerConverter(new LongStringConverter())
                .build();

        int pageSize = 1000;
        int pageNo = 1;
        int sheetIndex = 1;

        // 全局线程池
        ExecutorService executorService = Executors.newFixedThreadPool(10);

        try {
            do {
                pageReqVO.setPageSize(pageSize);
                pageReqVO.setPageNo(pageNo);
                // 获取监测结果数据
                List<PoliceMonitorResultsRespVO> exportList = Optional.ofNullable(
                                policeMonitorResultsBizService.getMonitorResultsPage(pageReqVO).getList())
                        .map(list -> BeanUtils.toBean(list, PoliceMonitorResultsRespVO.class))
                        .orElseGet(ArrayList::new);
                if (CollectionUtils.isEmpty(exportList)) {
                    pageNo = 0;
                    continue;
                }
                pageNo++;

                // 获取设备ID列表并过滤空值
                List<String> equIdList = exportList.stream()
                        .map(PoliceMonitorResultsRespVO::getEquId)
                        .filter(StringUtils::isNotBlank)
                        .collect(Collectors.toList());

                // 查询设备信息
                Map<String, PoliceOpsElectricPoliceDO> policeOpsElectricPoliceDOMap = CollectionUtils.isEmpty(equIdList)
                        ? new HashMap<>()
                        : Optional.ofNullable(policeOpsElectricPoliceMapper.selectList(
                                new LambdaQueryWrapperX<PoliceOpsElectricPoliceDO>()
                                        .in(PoliceOpsElectricPoliceDO::getId, equIdList)))
                        .orElseGet(ArrayList::new)
                        .stream()
                        .collect(Collectors.toMap(PoliceOpsElectricPoliceDO::getId, Function.identity()));

                // 收集需要下载图片的记录
                if (OpsConstants.STRING_ONE.equals(isExportImg)) {
                    List<PoliceMonitorResultsRespVO> needDownloadItems = exportList.stream()
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
                }

                // 处理每条记录（非图片下载部分）
                exportList.forEach(item -> {
                    // 设置维修订单状态
                    item.setIsRepairOrder(OpsConstants.STRING_ONE.equals(item.getIsRepairOrder()) ? "有" : "无");

                    // 设置设备标签
                    Optional.ofNullable(policeOpsElectricPoliceDOMap.get(item.getEquId()))
                            .ifPresent(police -> {
                                item.setEquName(police.getEquName());
                                item.setEquLabel(police.getEquLable());
                                item.setUnit(police.getUnit());
                                item.setIsBindMember(StringUtils.isNotBlank(police.getMemberBh()) ? "是" : "否");
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

    @PostMapping("/importPoliceMonitorInfo")
    @Operation(summary = "导入电警监测信息")
    public CommonResult<ImportRecordRespVO> importMaintenanceInfo(@RequestParam("file") MultipartFile file) {
        return success(excelImportExtService.importExcelInfo("公安电警监测", file, ExcelImportPoliceDjMonitorDTO.class));
    }

    @PostMapping("/updateMonitorResults")
    @Operation(summary = "更新检测结果")
    public CommonResult<Boolean> uploadMonitorResults(@Valid @RequestBody PoliceMonitorResultsSaveReqVO saveReqVO) {
        policeMonitorResultsBizService.updateMonitorResults(saveReqVO);
        return success(true);
    }

    @PostMapping("/remarkMonitorResults")
    @Operation(summary = "备注设备情况")
    public CommonResult<Boolean> remarkMonitorResults(@Valid @RequestBody PoliceMonitorResultsSaveReqVO saveReqVO) {
        policeMonitorResultsBizService.remarkMonitorResults(saveReqVO);
        return success(true);
    }

}