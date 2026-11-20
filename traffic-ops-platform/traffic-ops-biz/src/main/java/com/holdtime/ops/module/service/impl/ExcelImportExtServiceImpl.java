package com.holdtime.ops.module.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.HexUtil;
import cn.hutool.json.JSONUtil;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.framework.excel.core.util.ExcelUtils;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.security.core.util.SecurityFrameworkUtils;
import cn.iocoder.yudao.module.infra.dal.dataobject.config.ConfigDO;
import cn.iocoder.yudao.module.infra.service.config.ConfigService;
import cn.iocoder.yudao.module.infra.service.file.FileService;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.converters.longconverter.LongStringConverter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.excel.write.style.column.LongestMatchColumnWidthStyleStrategy;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.holdtime.ops.module.entity.*;
import com.holdtime.ops.module.entity.mapper.*;
import com.holdtime.ops.module.enums.OpsConstants;
import com.holdtime.ops.module.enums.OpsErrorCodeConstants;
import com.holdtime.ops.module.mapper.PoliceOpsEquipInfoExtMapper;
import com.holdtime.ops.module.model.dto.*;
import com.holdtime.ops.module.model.vo.ImportRecordRespVO;
import com.holdtime.ops.module.service.*;
import com.holdtime.ops.module.utils.StrUtils;
import com.holdtime.ops.module.utils.TextConsistencyExtractor;
import com.holdtime.ops.module.utils.TextSimilarityAnalyzer;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;

@Service
@Validated
@Slf4j
@Transactional(rollbackFor = Exception.class)
public class ExcelImportExtServiceImpl implements ExcelImportExtService {
    @Resource
    private FileService fileService;
    @Resource
    private PoliceOpsMemberMapper policeOpsMemberMapper;
    @Resource
    private TrafficMonitorResultsMapper monitorResultsMapper;
    @Resource
    private TrafficOpsElectricPoliceMapper trafficOpsElectricPoliceMapper;
    @Resource
    private PoliceOpsElectricPoliceMapper policeOpsElectricPoliceMapper;
    @Resource
    private TrafficOpsMaintenanceMapper trafficOpsMaintenanceMapper;
    @Resource
    private PoliceMonitorResultsMapper policeMonitorResultsMapper;
    @Resource
    private TrafficOpsMaintainProgressMapper opsMaintainProgressMapper;
    @Resource
    private TrafficOpsMaintenanceFilesMapper opsMaintenanceFilesMapper;
    @Resource
    private TrafficOpsTempReportMapper trafficOpsTempReportMapper;
    @Resource
    private ImportRecordMapper importRecordMapper;
    @Resource
    private PoliceMonitorResultsHistoryExtService policeMonitorResultsHistoryExtService;
    @Resource
    private TrafficOpsPoliceEquipmentMapper trafficOpsPoliceEquipmentMapper;
    @Resource
    private TrafficMonitorResultsImportDetailMapper trafficMonitorResultsImportDetailMapper;
    @Resource
    private TrafficMonitorResultsImportDetailHistoryMapper trafficMonitorResultsImportDetailHistoryMapper;
    @Resource
    private TrafficMonitorResultsImportAnalysisMapper trafficMonitorResultsImportAnalysisMapper;
    @Resource
    private PoliceMonitorResultsImportDetailMapper  policeMonitorResultsImportDetailMapper;
    @Resource
    private PoliceMonitorResultsImportSituationMapper  policeMonitorResultsImportSituationMapper;
    @Resource
    private PoliceOpsEquipInfoExtMapper policeOpsEquipInfoExtMapper;
    @Resource
    private PoliceMonitorResultsImportAnalysisExtService policeMonitorResultsImportAnalysisExtService;
    @Resource
    private PoliceMonitorPingDetailMapper policeMonitorPingDetailMapper;
    @Resource
    private PoliceMonitorPingExtService policeMonitorPingExtService;
    @Resource
    private ConfigService configService;
    @Resource
    private PoliceMonitorPingSituationMapper policeMonitorPingSituationMapper;
    @Resource
    private BtjczhVioEquipmentDailySituationMapper btjczhVioEquipmentDailySituationMapper;
    @Resource
    private TrafficVioEquipmentMonitorAnalysisExtService trafficVioEquipmentMonitorAnalysisExtService;
    @Resource
    private TrafficVioEquipmentInvalidReasonAnalysisMapper trafficVioEquipmentInvalidReasonAnalysisMapper;
    @Resource
    private TrafficVioEquipmentIllegalAnalysisMapper trafficVioEquipmentIllegalAnalysisMapper;


    @Override
    @SuppressWarnings("unchecked")
    public ImportRecordRespVO importExcelInfo(String importType, MultipartFile file, Class<?> importTypeClass) {
        ImportRecordRespVO respVO = new ImportRecordRespVO();

        ImportRecordDO recordDO = new ImportRecordDO();


        AtomicInteger importSucCnt = new AtomicInteger();
        AtomicInteger importFailCnt = new AtomicInteger();
        try {
            // 1.存储上传的导入文件
            String fileUrl = fileService.createFile(file.getOriginalFilename(), null, IoUtil.readBytes(file.getInputStream()));
            recordDO.setFileName(file.getOriginalFilename());
            recordDO.setImportFile(fileUrl);
            respVO.setImportFile(fileUrl);

            // 解析Excel
            List<?> importExcelVOList = ExcelUtils.read(file, importTypeClass);
            if (CollectionUtils.isEmpty(importExcelVOList)) {
                throw exception(OpsErrorCodeConstants.IMPORT_DATA_NOT_EXISTS);
            }

            recordDO.setImportType(importType);
            recordDO.setImportNum(importExcelVOList.size());

            if (ExcelImportExcelPaBtDTO.class.equals(importTypeClass)) {
                handlePaBtImportExcel((List<ExcelImportExcelPaBtDTO>) importExcelVOList);
            } else if (ExcelImportExcelDjMonitorDTO.class.equals(importTypeClass)) {
                List<ExcelImportExcelDjMonitorDTO> successList = new ArrayList<>();
                List<ExcelImportExcelDjMonitorDTO> failList = new ArrayList<>();
                handleDjMonitorImportExcel((List<ExcelImportExcelDjMonitorDTO>) importExcelVOList, successList, failList, importSucCnt, importFailCnt);

                String resultFail = writeResultExcel(successList, failList, importTypeClass);
                recordDO.setFailFile(resultFail);
            } else if (ExcelImportPoliceDjMonitorDTO.class.equals(importTypeClass)) {
                List<ExcelImportPoliceDjMonitorDTO> successList = new ArrayList<>();
                List<ExcelImportPoliceDjMonitorDTO> failList = new ArrayList<>();
                handlePoliceDjMonitorImportExcel((List<ExcelImportPoliceDjMonitorDTO>) importExcelVOList, successList, failList, importSucCnt, importFailCnt);

                String resultFail = writeResultExcel(successList, failList, importTypeClass);
                recordDO.setFailFile(resultFail);
            } else if (ExcelImportTempReportDTO.class.equals(importTypeClass) ) {
                List<ExcelImportTempReportDTO> successList = new ArrayList<>();
                List<ExcelImportTempReportDTO> failList = new ArrayList<>();
                handleTempRemportImportExcel((List<ExcelImportTempReportDTO>) importExcelVOList, successList, failList, importSucCnt, importFailCnt);

                String resultFail = writeResultExcel(successList, failList, importTypeClass);
                recordDO.setFailFile(resultFail);
            } else if (ExcelImportPoliceEquipDTO.class.equals(importTypeClass)) {
                List<ExcelImportPoliceEquipDTO> successList = new ArrayList<>();
                List<ExcelImportPoliceEquipDTO> failList = new ArrayList<>();
                handlePoliceEquipImportExcel((List<ExcelImportPoliceEquipDTO>) importExcelVOList, successList, failList, importSucCnt, importFailCnt);

                String resultFail = writeResultExcel(successList, failList, importTypeClass);
                recordDO.setFailFile(resultFail);
            } else if (ExcelImportTrafficMonitorResultsDTO.class.equals(importTypeClass)) {
                List<ExcelImportTrafficMonitorResultsDTO> successList = new ArrayList<>();
                List<ExcelImportTrafficMonitorResultsDTO> failList = new ArrayList<>();
                handleTrafficMonitorResultsImportExcel((List<ExcelImportTrafficMonitorResultsDTO>) importExcelVOList, successList, failList, importSucCnt, importFailCnt);

                String resultFail = writeResultExcel(successList, failList, importTypeClass);
                recordDO.setFailFile(resultFail);
            } else if (ExcelImportPoliceMonitorResultsDTO.class.equals(importTypeClass)) {
                List<ExcelImportPoliceMonitorResultsDTO> successList = new ArrayList<>();
                List<ExcelImportPoliceMonitorResultsDTO> failList = new ArrayList<>();
                handlePoliceMonitorResultsImportExcel((List<ExcelImportPoliceMonitorResultsDTO>) importExcelVOList, successList, failList, importSucCnt, importFailCnt);

                String resultFail = writeResultExcel(successList, failList, importTypeClass);
                recordDO.setFailFile(resultFail);
            } else if (ExcelImportPoliceMonitorPingDetailDTO.class.equals(importTypeClass)) {
                List<ExcelImportPoliceMonitorPingDetailDTO> successList = new ArrayList<>();
                List<ExcelImportPoliceMonitorPingDetailDTO> failList = new ArrayList<>();
                handlePoliceMonitorPingDetailImportExcel((List<ExcelImportPoliceMonitorPingDetailDTO>) importExcelVOList, successList, failList, importSucCnt, importFailCnt);

                String resultFail = writeResultExcel(successList, failList, importTypeClass);
                recordDO.setFailFile(resultFail);
            } else if (ExcelImportVioEquipmentDailySituationDTO.class.equals(importTypeClass)) {
                List<ExcelImportVioEquipmentDailySituationDTO> successList = new ArrayList<>();
                List<ExcelImportVioEquipmentDailySituationDTO> failList = new ArrayList<>();
                handleVioEquipmentDailySituationImportExcel((List<ExcelImportVioEquipmentDailySituationDTO>) importExcelVOList, successList, failList, importSucCnt, importFailCnt);

                String resultFail = writeResultExcel(successList, failList, importTypeClass);
                recordDO.setFailFile(resultFail);
            } else if (ExcelImportVioInvaliReasonDTO.class.equals(importTypeClass)) {
                List<ExcelImportVioInvaliReasonDTO> successList = new ArrayList<>();
                List<ExcelImportVioInvaliReasonDTO> failList = new ArrayList<>();
                handleVioEquipmentInvalidReasonImportExcel((List<ExcelImportVioInvaliReasonDTO>) importExcelVOList, successList, failList, importSucCnt, importFailCnt);

                String resultFail = writeResultExcel(successList, failList, importTypeClass);
                recordDO.setFailFile(resultFail);
            } else if (ExcelImportVioIllegalDTO.class.equals(importTypeClass)) {
                List<ExcelImportVioIllegalDTO> successList = new ArrayList<>();
                List<ExcelImportVioIllegalDTO> failList = new ArrayList<>();
                handleVioEquipmentIllegalImportExcel((List<ExcelImportVioIllegalDTO>) importExcelVOList, successList, failList, importSucCnt, importFailCnt);

                String resultFail = writeResultExcel(successList, failList, importTypeClass);
                recordDO.setFailFile(resultFail);
            }

            respVO.setImportNum(importExcelVOList.size());
            respVO.setSucNum(importSucCnt.get());
            respVO.setFailNum(importFailCnt.get());
            respVO.setFailFile(recordDO.getFailFile());
        } catch (IOException e) {
            throw exception(OpsErrorCodeConstants.IMPORT_DATA_EXCEPTION);
        } finally {
            recordDO.setSucNum(importSucCnt.get());
            recordDO.setFailNum(importFailCnt.get());
            recordDO.setDeptId(SecurityFrameworkUtils.getLoginUserDeptId() + "");
            importRecordMapper.insert(recordDO);
        }

        return respVO;
    }

    private String writeResultExcel(List<?> successList, List<?> failList, Class<?> importTypeClass)  {
        if (CollectionUtils.isEmpty(successList) && CollectionUtils.isEmpty(failList))  {
            return null;
        }
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        ExcelWriter excelWriter = EasyExcel.write(outputStream).autoCloseStream(false).build();

        WriteSheet writeSheet1 = EasyExcel.writerSheet("同步成功")
                .head(importTypeClass)
                .registerWriteHandler(new LongestMatchColumnWidthStyleStrategy())
                .registerConverter(new LongStringConverter())
                .build();
        excelWriter.write(successList, writeSheet1);

        // 创建Sheet2对象
        WriteSheet writeSheet2 = EasyExcel.writerSheet("同步失败")
                .head(importTypeClass)
                .registerWriteHandler(new LongestMatchColumnWidthStyleStrategy())
                .registerConverter(new LongStringConverter())
                .build();
        excelWriter.write(failList, writeSheet2);

        excelWriter.finish();

        byte[] bytes = outputStream.toByteArray();
        String fileName = "Import_Result_" + System.currentTimeMillis() + ".xlsx";
        return fileService.createFile(fileName, null, bytes);
    }

    private void handlePaBtImportExcel(List<ExcelImportExcelPaBtDTO> importExcelVOList) {
        if (CollectionUtils.isEmpty(importExcelVOList)) {
            return;
        }

        List<String> addressList = importExcelVOList.stream().map(ExcelImportExcelPaBtDTO::getCameraName).collect(Collectors.toList());
        double threshold = 0.8; // 相似度阈值
        Set<String> addressSet = new HashSet<>();
        for (String address : addressList) {
            List<TextSimilarityAnalyzer.SimilarText> similarTexts = TextSimilarityAnalyzer.extractSimilarTexts(address, addressList, threshold, "levenshtein");
            if (CollectionUtils.isEmpty(similarTexts)) {
                log.error("没有相似的地址：{}", address);
                continue;
            }

            List<String> similarAddress = similarTexts.stream().map(TextSimilarityAnalyzer.SimilarText::getText).collect(Collectors.toList());
            String consistentText = TextConsistencyExtractor.extractConsistentText(similarAddress, 1.0);

            addressSet.add(consistentText);
            log.info("地址：{}，相似地址：{}，一致地址：{}", address, similarAddress, consistentText);
        }

        List<PoliceOpsMemberDO> policeOpsMemberDOList = addressSet.stream().map(address -> {
            PoliceOpsMemberDO policeOpsMemberDO = new PoliceOpsMemberDO();
            policeOpsMemberDO.setAdress(address);
            return policeOpsMemberDO;
        }).collect(Collectors.toList());
        policeOpsMemberMapper.insertBatch(policeOpsMemberDOList);
    }

    private void handleDjMonitorImportExcel(List<ExcelImportExcelDjMonitorDTO> importExcelVOList, List<ExcelImportExcelDjMonitorDTO> successList,
                                            List<ExcelImportExcelDjMonitorDTO> failList, AtomicInteger importSucCnt, AtomicInteger importFailCnt) {
        if (CollectionUtils.isEmpty(importExcelVOList)) {
            return;
        }

        for (ExcelImportExcelDjMonitorDTO importExcel : importExcelVOList) {
            if (StringUtils.isEmpty(importExcel.getIpAddr() )) {
                importExcel.setFailReason("IP地址不能为空");
                failList.add(importExcel);
                importFailCnt.addAndGet(1);
                continue;
            }

            if (StringUtils.isBlank(importExcel.getEventType())) {
                importExcel.setFailReason("检测类型不能为空");
                failList.add(importExcel);
                importFailCnt.addAndGet(1);
                continue;
            }

            if (StringUtils.isBlank(importExcel.getMonitorResult())) {
                importExcel.setFailReason("检测结果不能为空");
                failList.add(importExcel);
                importFailCnt.addAndGet(1);
                continue;
            }

            if (!JSONUtil.isTypeJSONArray(importExcel.getMonitorResult())) {
                importExcel.setFailReason("检测结果格式不正确");
                failList.add(importExcel);
                importFailCnt.addAndGet(1);
                continue;
            }

            // 根据IP查询电警信息
            LambdaQueryWrapperX<TrafficOpsElectricPoliceDO> lambdaQueryWrapperX = new LambdaQueryWrapperX<>();
            lambdaQueryWrapperX.eq(TrafficOpsElectricPoliceDO::getIpAddr, importExcel.getIpAddr());
            List<TrafficOpsElectricPoliceDO> trafficOpsElectricPoliceDOList = trafficOpsElectricPoliceMapper.selectList(lambdaQueryWrapperX);
            TrafficOpsElectricPoliceDO trafficOpsElectricPoliceDO = null;
            if (!CollectionUtils.isEmpty(trafficOpsElectricPoliceDOList) ) {
                trafficOpsElectricPoliceDO = trafficOpsElectricPoliceDOList.get(0);
            }


            TrafficMonitorResultsDO tempMonitorResultsDO = new TrafficMonitorResultsDO();
            tempMonitorResultsDO.setIpAddr(importExcel.getIpAddr());
            tempMonitorResultsDO.setEquType(OpsConstants.STRING_TWO);
            tempMonitorResultsDO.setSource(OpsConstants.STRING_TWO);
            tempMonitorResultsDO.setEventType(importExcel.getEventType());
            if (trafficOpsElectricPoliceDO != null)  {
                tempMonitorResultsDO.setDirection(trafficOpsElectricPoliceDO.getDirection());
                tempMonitorResultsDO.setEquId(trafficOpsElectricPoliceDO.getId());
                tempMonitorResultsDO.setRoadName(trafficOpsElectricPoliceDO.getRoadName());
                tempMonitorResultsDO.setIntersectionName(trafficOpsElectricPoliceDO.getIntersectionName());
                tempMonitorResultsDO.setMemberBh(trafficOpsElectricPoliceDO.getMemberBh());
            }

            try {
                ObjectMapper objectMapper = new ObjectMapper();
                List<Map<String, String>> monitorResultList = objectMapper.readValue(importExcel.getMonitorResult(), new TypeReference<List<Map<String, String>>>() {});
                List<TrafficMonitorResultsDO> instResultList = new ArrayList<>();
                for (Map<String, String> map  : monitorResultList) {
                    TrafficMonitorResultsDO instResult = BeanUtils.toBean(tempMonitorResultsDO, TrafficMonitorResultsDO.class);
                    if (!map.containsKey("occurrenceTime") || !map.containsKey("status")) {
                        importExcel.setFailReason("检测结果却少必要信息！");
                        failList.add(importExcel);
                        importFailCnt.addAndGet(1);
                        break;
                    }

                    String occurrenceTimeStr = map.get("occurrenceTime");
                    try {
                        LocalDateTime localDateTime = LocalDateTimeUtil.parse(occurrenceTimeStr, "yyyy-MM-dd HH:mm:ss");
                        instResult.setOccurrenceTime(localDateTime);
                    } catch (Exception e) {
                        importExcel.setFailReason("检测结果日期不正确");
                        failList.add(importExcel);
                        importFailCnt.addAndGet(1);
                        break;
                    }
                    String status = map.get("status");
                    instResult.setStatus(status);
                    instResultList.add(instResult);
                }
                monitorResultsMapper.insertBatch(instResultList);
                successList.add(importExcel);
                importSucCnt.addAndGet(1);
            } catch (JsonProcessingException e) {
                importExcel.setFailReason("检测结果解析失败");
                failList.add(importExcel);
                importFailCnt.addAndGet(1);
            }

        }
    }

    private final ExecutorService importExecutor = Executors.newFixedThreadPool(
            Runtime.getRuntime().availableProcessors() * 2,
            new ThreadFactoryBuilder().setNameFormat("import-pool-%d").build()
    );

    private void handlePoliceDjMonitorImportExcel(List<ExcelImportPoliceDjMonitorDTO> importExcelVOList,
                                                  List<ExcelImportPoliceDjMonitorDTO> successList,
                                                  List<ExcelImportPoliceDjMonitorDTO> failList,
                                                  AtomicInteger importSucCnt,
                                                  AtomicInteger importFailCnt) {

        if (CollectionUtils.isEmpty(importExcelVOList)) {
            return;
        }

        // 删除历史记录
        policeMonitorResultsHistoryExtService.deleteAllOldMonitorResultsHistory();

        // 使用线程安全的集合
        List<ExcelImportPoliceDjMonitorDTO> threadSafeSuccessList = Collections.synchronizedList(new ArrayList<>());
        List<ExcelImportPoliceDjMonitorDTO> threadSafeFailList = Collections.synchronizedList(new ArrayList<>());
        AtomicInteger threadSafeSucCnt = new AtomicInteger(0);
        AtomicInteger threadSafeFailCnt = new AtomicInteger(0);

        // 创建所有异步任务
        List<CompletableFuture<Void>> futures = importExcelVOList.stream()
                .map(importExcel -> CompletableFuture.runAsync(() ->
                                processSingleRecord(importExcel, threadSafeSuccessList, threadSafeFailList, threadSafeSucCnt, threadSafeFailCnt),
                        importExecutor))
                .collect(Collectors.toList());

        // 等待所有任务完成
        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();

        // 合并结果
        successList.addAll(threadSafeSuccessList);
        failList.addAll(threadSafeFailList);
        importSucCnt.addAndGet(threadSafeSucCnt.get());
        importFailCnt.addAndGet(threadSafeFailCnt.get());
    }

    private void processSingleRecord(ExcelImportPoliceDjMonitorDTO importExcel,
                                     List<ExcelImportPoliceDjMonitorDTO> successList,
                                     List<ExcelImportPoliceDjMonitorDTO> failList,
                                     AtomicInteger importSucCnt,
                                     AtomicInteger importFailCnt) {

        try {
            // 1. 参数校验
            if (StringUtils.isEmpty(importExcel.getIpAddr())) {
                addFailRecord(importExcel, "IP地址不能为空", failList, importFailCnt);
                return;
            }
            if (StringUtils.isBlank(importExcel.getStatus())) {
                addFailRecord(importExcel, "检测结果不能为空", failList, importFailCnt);
                return;
            }
            if (StringUtils.isBlank(importExcel.getOccurrenceTime())) {
                addFailRecord(importExcel, "检测时间不能为空", failList, importFailCnt);
                return;
            }

            // 2. 查询电警信息
            PoliceOpsElectricPoliceDO policeOpsElectricPoliceDO = getPoliceOpsElectricPolice(importExcel.getIpAddr());
            if (policeOpsElectricPoliceDO == null) {
                addFailRecord(importExcel, "未找到对应的公安电警信息", failList, importFailCnt);
                return;
            }

            // 3. 构建结果对象
            PoliceMonitorResultsDO resultsDO = buildPoliceMonitorResults(importExcel, policeOpsElectricPoliceDO);
            if (resultsDO == null) {
                addFailRecord(importExcel, "检测结果日期不正确", failList, importFailCnt);
                return;
            }

            // 4. 处理图片
            processImages(importExcel, resultsDO);

            // 5. 处理工单逻辑
            handleMaintenanceOrder(importExcel, policeOpsElectricPoliceDO, resultsDO);

            // 6. 保存结果
            saveOrUpdateResults(importExcel, resultsDO, successList, importSucCnt);

            // 7.保存历史记录
            policeMonitorResultsHistoryExtService.saveMonitorResultsHistory(resultsDO.getId());
        } catch (Exception e) {
            log.error("处理导入记录异常", e);
            addFailRecord(importExcel, "处理异常: " + e.getMessage(), failList, importFailCnt);
        }
    }

    // 参数校验
    private boolean validateImportData(ExcelImportPoliceDjMonitorDTO importExcel,
                                       List<ExcelImportPoliceDjMonitorDTO> failList,
                                       AtomicInteger importFailCnt) {
        if (StringUtils.isEmpty(importExcel.getIpAddr())) {
            addFailRecord(importExcel, "IP地址不能为空", failList, importFailCnt);
            return false;
        }
        if (StringUtils.isBlank(importExcel.getStatus())) {
            addFailRecord(importExcel, "检测结果不能为空", failList, importFailCnt);
            return false;
        }
        if (StringUtils.isBlank(importExcel.getOccurrenceTime())) {
            addFailRecord(importExcel, "检测时间不能为空", failList, importFailCnt);
            return false;
        }
        return true;
    }

    // 查询电警信息
    private PoliceOpsElectricPoliceDO getPoliceOpsElectricPolice(String ipAddr) {
        LambdaQueryWrapperX<PoliceOpsElectricPoliceDO> queryWrapper = new LambdaQueryWrapperX<>();
        queryWrapper.eq(PoliceOpsElectricPoliceDO::getIpAddr, ipAddr);
        List<PoliceOpsElectricPoliceDO> list = policeOpsElectricPoliceMapper.selectList(queryWrapper);
        return CollectionUtils.isEmpty(list) ? null : list.get(0);
    }

    // 构建结果对象
    private PoliceMonitorResultsDO buildPoliceMonitorResults(ExcelImportPoliceDjMonitorDTO importExcel,
                                                             PoliceOpsElectricPoliceDO policeOpsElectricPoliceDO) {
        try {
            PoliceMonitorResultsDO resultsDO = BeanUtils.toBean(importExcel, PoliceMonitorResultsDO.class);
            resultsDO.setEquType("电警");
            resultsDO.setEquId(policeOpsElectricPoliceDO.getId());
            resultsDO.setEquName(policeOpsElectricPoliceDO.getEquName());
            resultsDO.setMemberBh(policeOpsElectricPoliceDO.getMemberBh());
            resultsDO.setDirection(policeOpsElectricPoliceDO.getDirection());
            resultsDO.setSource(OpsConstants.STRING_TWO);

            LocalDateTime localDateTime = LocalDateTimeUtil.parse(importExcel.getOccurrenceTime(), "yyyy-MM-dd HH:mm:ss");
            resultsDO.setOccurrenceTime(localDateTime);

            return resultsDO;
        } catch (Exception e) {
            return null;
        }
    }

    // 处理图片
    private void processImages(ExcelImportPoliceDjMonitorDTO importExcel, PoliceMonitorResultsDO resultsDO) {
        String imgHex = Stream.of(
                        importExcel.getImgHex1(),
                        importExcel.getImgHex2(),
                        importExcel.getImgHex3(),
                        importExcel.getImgHex4(),
                        importExcel.getImgHex5())
                .filter(StringUtils::isNotBlank)
                .collect(Collectors.joining());

        if (StringUtils.isNotBlank(imgHex)) {
            resultsDO.setResultImg(uploadImgFile(imgHex));
        }
    }

    // 处理工单逻辑
    private void handleMaintenanceOrder(ExcelImportPoliceDjMonitorDTO importExcel,
                                        PoliceOpsElectricPoliceDO policeOpsElectricPoliceDO,
                                        PoliceMonitorResultsDO resultsDO) {

        LambdaQueryWrapperX<TrafficOpsMaintenanceDO> queryWrapper = new LambdaQueryWrapperX<>();
        queryWrapper.eq(TrafficOpsMaintenanceDO::getEquId, policeOpsElectricPoliceDO.getId());
        queryWrapper.ne(TrafficOpsMaintenanceDO::getStatus, OpsConstants.STATUS_COMPLETED);
        queryWrapper.orderByDesc(TrafficOpsMaintenanceDO::getCreateTime);
        List<TrafficOpsMaintenanceDO> maintenanceList = trafficOpsMaintenanceMapper.selectList(queryWrapper);

        if (CollectionUtils.isEmpty(maintenanceList)) {
            resultsDO.setIsRepairOrder(OpsConstants.STRING_ZERO);
            if (!"正常".equals(importExcel.getStatus()) && StringUtils.isBlank(resultsDO.getResultImg())) {
                createNewMaintenanceOrder(importExcel, policeOpsElectricPoliceDO, resultsDO);
            }
        } else {
            handleExistingMaintenanceOrder(maintenanceList.get(0), resultsDO);
        }
    }

    // 创建新工单
    private void createNewMaintenanceOrder(ExcelImportPoliceDjMonitorDTO importExcel,
                                           PoliceOpsElectricPoliceDO policeOpsElectricPoliceDO,
                                           PoliceMonitorResultsDO resultsDO) {

        TrafficOpsMaintenanceDO maintenanceDO = new TrafficOpsMaintenanceDO();
        maintenanceDO.setOrderNum(DateUtil.format(new Date(), "yyyyMMddHHmmssSSS"));
        maintenanceDO.setEquId(policeOpsElectricPoliceDO.getId());
        maintenanceDO.setEquAddr(policeOpsElectricPoliceDO.getAdress());
        maintenanceDO.setEquLng(policeOpsElectricPoliceDO.getLng());
        maintenanceDO.setEquLat(policeOpsElectricPoliceDO.getLat());
        maintenanceDO.setProblem(String.format("地址为%s，IP为%s的电警%s",
                policeOpsElectricPoliceDO.getAdress(),
                policeOpsElectricPoliceDO.getIpAddr(),
                importExcel.getStatus()));
        maintenanceDO.setFaultType("电警" + importExcel.getStatus());
        maintenanceDO.setFaultDate(DateUtil.toLocalDateTime(new Date()));
        maintenanceDO.setReportUserId(String.valueOf(SecurityFrameworkUtils.getLoginUserId()));
        maintenanceDO.setReportPersonnel(SecurityFrameworkUtils.getLoginUserNickname());
        maintenanceDO.setAffiliatedUnit(OpsConstants.STRING_TWO);
        maintenanceDO.setStatus(OpsConstants.STATUS_PENDING_ORDERS);

        trafficOpsMaintenanceMapper.insert(maintenanceDO);
        resultsDO.setMaintenanceId(maintenanceDO.getId());
    }

    // 处理已有工单
    private void handleExistingMaintenanceOrder(TrafficOpsMaintenanceDO maintenanceDO,
                                                PoliceMonitorResultsDO resultsDO) {

        resultsDO.setIsRepairOrder(OpsConstants.STRING_ONE);
        resultsDO.setMaintenanceId(maintenanceDO.getId());
        resultsDO.setMaintenanceUserId(maintenanceDO.getMaintenanceUserId());
        resultsDO.setMaintenancePersonnel(maintenanceDO.getMaintenancePersonnel());
        resultsDO.setMaintenancePhone(maintenanceDO.getMaintenancePhone());

        if (StringUtils.isNotBlank(resultsDO.getResultImg())) {
            maintenanceDO.setStatus(OpsConstants.STATUS_COMPLETED);
            trafficOpsMaintenanceMapper.updateById(maintenanceDO);

            TrafficOpsMaintainProgressDO progressDO = buildMaintainProgressDO(maintenanceDO);
            opsMaintainProgressMapper.insert(progressDO);

            TrafficOpsMaintenanceFilesDO fileDO = buildMaintenanceFilesDO(maintenanceDO, progressDO, resultsDO);
            opsMaintenanceFilesMapper.insert(fileDO);
        }
    }

    // 构建维修记录
    private TrafficOpsMaintainProgressDO buildMaintainProgressDO(TrafficOpsMaintenanceDO maintenanceDO) {
        TrafficOpsMaintainProgressDO progressDO = new TrafficOpsMaintainProgressDO();
        progressDO.setMaintenanceId(maintenanceDO.getId());
        progressDO.setMaintenance("经检测，已修复");
        progressDO.setMaintenanceUserId(maintenanceDO.getMaintenanceUserId());
        progressDO.setMaintenancePersonnel(maintenanceDO.getMaintenancePersonnel());
        progressDO.setMaintenancePhone(maintenanceDO.getMaintenancePhone());
        progressDO.setMaintenanceDate(DateUtil.toLocalDateTime(new Date()));
        progressDO.setIsChange(OpsConstants.STRING_ZERO);
        return progressDO;
    }

    // 构建维修文件记录
    private TrafficOpsMaintenanceFilesDO buildMaintenanceFilesDO(
            TrafficOpsMaintenanceDO maintenanceDO,
            TrafficOpsMaintainProgressDO progressDO,
            PoliceMonitorResultsDO resultsDO) {

        TrafficOpsMaintenanceFilesDO fileDO = new TrafficOpsMaintenanceFilesDO();
        fileDO.setMaintenanceId(maintenanceDO.getId());
        fileDO.setProgressId(progressDO.getId());
        fileDO.setRepairType(OpsConstants.REPAIR_TYPE_TWO);
        fileDO.setFileType(OpsConstants.STRING_ONE);
        fileDO.setFileUrl(resultsDO.getResultImg());
        return fileDO;
    }

    // 保存或更新结果
    private void saveOrUpdateResults(ExcelImportPoliceDjMonitorDTO importExcel,
                                     PoliceMonitorResultsDO resultsDO,
                                     List<ExcelImportPoliceDjMonitorDTO> successList,
                                     AtomicInteger importSucCnt) {

        LambdaQueryWrapperX<PoliceMonitorResultsDO> queryWrapper = new LambdaQueryWrapperX<>();
        queryWrapper.eq(PoliceMonitorResultsDO::getIpAddr, importExcel.getIpAddr());
        List<PoliceMonitorResultsDO> existingResults = policeMonitorResultsMapper.selectList(queryWrapper);

        if (CollectionUtils.isEmpty(existingResults)) {
            policeMonitorResultsMapper.insert(resultsDO);
        } else {
            resultsDO.setId(existingResults.get(0).getId());
            policeMonitorResultsMapper.updateById(resultsDO);
        }

        successList.add(importExcel);
        importSucCnt.incrementAndGet();
    }

    // 添加失败记录
    private void addFailRecord(ExcelImportPoliceDjMonitorDTO importExcel,
                               String reason,
                               List<ExcelImportPoliceDjMonitorDTO> failList,
                               AtomicInteger importFailCnt) {

        importExcel.setFailReason(reason);
        failList.add(importExcel);
        importFailCnt.incrementAndGet();
    }


    private String uploadImgFile(String imgHexString) {
        // 将Hex字符串转换为字节数组
        byte[] imageBytes = HexUtil.decodeHex(imgHexString);

        String fileName = "police_monitor_result_" + System.currentTimeMillis() + ".png";
        return fileService.createFile(fileName, null, imageBytes);
    }

    private void handleTempRemportImportExcel(List<ExcelImportTempReportDTO> importExcelVOList,
                                                  List<ExcelImportTempReportDTO> successList,
                                                  List<ExcelImportTempReportDTO> failList,
                                                  AtomicInteger importSucCnt,
                                                  AtomicInteger importFailCnt) {

        if (CollectionUtils.isEmpty(importExcelVOList)) {
            return;
        }

        // 使用线程安全的集合
        List<ExcelImportTempReportDTO> threadSafeSuccessList = Collections.synchronizedList(new ArrayList<>());
        List<ExcelImportTempReportDTO> threadSafeFailList = Collections.synchronizedList(new ArrayList<>());
        AtomicInteger threadSafeSucCnt = new AtomicInteger(0);
        AtomicInteger threadSafeFailCnt = new AtomicInteger(0);

        // 创建所有异步任务

        // 等待所有任务完成
        CompletableFuture.allOf(importExcelVOList.stream()
                .map(importExcel -> CompletableFuture.runAsync(() ->
                                processSingleTempRecord(importExcel, threadSafeSuccessList, threadSafeFailList, threadSafeSucCnt, threadSafeFailCnt),
                        importExecutor)).toArray(CompletableFuture[]::new)).join();

        // 合并结果
        successList.addAll(threadSafeSuccessList);
        failList.addAll(threadSafeFailList);
        importSucCnt.addAndGet(threadSafeSucCnt.get());
        importFailCnt.addAndGet(threadSafeFailCnt.get());
    }

    private void processSingleTempRecord(ExcelImportTempReportDTO importExcel,
                                     List<ExcelImportTempReportDTO> successList,
                                     List<ExcelImportTempReportDTO> failList,
                                     AtomicInteger importSucCnt,
                                     AtomicInteger importFailCnt) {

        try {
            // 去除属性前后空格
            StrUtils.trimObjAttr(importExcel);

            // 1. 参数校验
            if (StringUtils.isEmpty(importExcel.getIpAddr())) {
                importExcel.setFailReason("IP地址不能为空");
                failList.add(importExcel);
                importFailCnt.incrementAndGet();
                return;
            }

            // 2. 查询电警信息
            LambdaQueryWrapperX<TrafficOpsElectricPoliceDO> queryWrapper = new LambdaQueryWrapperX<>();
            queryWrapper.eq(TrafficOpsElectricPoliceDO::getIpAddr, importExcel.getIpAddr());
            List<TrafficOpsElectricPoliceDO> list = trafficOpsElectricPoliceMapper.selectList(queryWrapper);
            if (CollectionUtils.isEmpty(list)) {
                importExcel.setFailReason("电警信息不存在！");
                failList.add(importExcel);
                importFailCnt.incrementAndGet();
                return;
            }

            // 3. 构建结果对象
            TrafficOpsTempReportDO tempReportDO = new TrafficOpsTempReportDO();
            tempReportDO.setIpAddr(importExcel.getIpAddr());
            tempReportDO.setEquId(list.get(0).getId());
            tempReportDO.setSource(OpsConstants.STRING_TWO);
            trafficOpsTempReportMapper.insert(tempReportDO);
            successList.add(importExcel);
            importSucCnt.incrementAndGet();
        } catch (Exception e) {
            log.error("处理导入记录异常", e);
            importExcel.setFailReason("发生异常！");
            failList.add(importExcel);
            importFailCnt.incrementAndGet();
        }
    }

    private void handlePoliceEquipImportExcel(List<ExcelImportPoliceEquipDTO> importExcelVOList, List<ExcelImportPoliceEquipDTO> successList,
                                            List<ExcelImportPoliceEquipDTO> failList, AtomicInteger importSucCnt, AtomicInteger importFailCnt) {
        if (CollectionUtils.isEmpty(importExcelVOList)) {
            return;
        }

        List<TrafficOpsPoliceEquipmentDO> instList = new ArrayList<>();
        for (ExcelImportPoliceEquipDTO importExcel : importExcelVOList) {
            if (StringUtils.isEmpty(importExcel.getPsn())) {
                importExcel.setFailReason("S/N不能为空");
                failList.add(importExcel);
                importFailCnt.addAndGet(1);
                continue;
            }

//            if (StringUtils.isBlank(importExcel.getCallSign())) {
//                importExcel.setFailReason("个呼号不能为空");
//                failList.add(importExcel);
//                importFailCnt.addAndGet(1);
//                continue;
//            }

            // 根据IP查询警用装备信息
            LambdaQueryWrapperX<TrafficOpsPoliceEquipmentDO> lambdaQueryWrapperX = new LambdaQueryWrapperX<>();
            lambdaQueryWrapperX.eq(TrafficOpsPoliceEquipmentDO::getPsn, importExcel.getPsn());
            List<TrafficOpsPoliceEquipmentDO> policeEquipmentDOList = trafficOpsPoliceEquipmentMapper.selectList(lambdaQueryWrapperX);
            if (!CollectionUtils.isEmpty(policeEquipmentDOList)) {
                importExcel.setFailReason("警用设备已存在");
                failList.add(importExcel);
                importFailCnt.addAndGet(1);
                continue;
            }

            TrafficOpsPoliceEquipmentDO policeEquipmentDO = new TrafficOpsPoliceEquipmentDO();
            policeEquipmentDO.setEquType(importExcel.getEquType());
            policeEquipmentDO.setPsn(importExcel.getPsn());
            policeEquipmentDO.setCallSign(importExcel.getCallSign());
            policeEquipmentDO.setBrand(importExcel.getBrand());
            policeEquipmentDO.setModel(importExcel.getModel());
            policeEquipmentDO.setManufacturer(importExcel.getManufacturer());
            policeEquipmentDO.setSupplier(importExcel.getSupplier());
            policeEquipmentDO.setBattery(importExcel.getBattery());
            policeEquipmentDO.setRemark(importExcel.getRemark());
            instList.add(policeEquipmentDO);

            successList.add(importExcel);
            importSucCnt.addAndGet(1);
        }
        if (!CollectionUtils.isEmpty(instList)) {
            trafficOpsPoliceEquipmentMapper.insert(instList);
        }
    }

    private void handleTrafficMonitorResultsImportExcel(List<ExcelImportTrafficMonitorResultsDTO> importExcelVOList, List<ExcelImportTrafficMonitorResultsDTO> successList,
                                              List<ExcelImportTrafficMonitorResultsDTO> failList, AtomicInteger importSucCnt, AtomicInteger importFailCnt) {
        if (CollectionUtils.isEmpty(importExcelVOList)) {
            return;
        }

        // 查询所有电警设备
        List<TrafficOpsElectricPoliceDO> electricPoliceDOList = trafficOpsElectricPoliceMapper.selectList();
        if (CollectionUtils.isEmpty(electricPoliceDOList)) {
            return;
        }
        Map<String, TrafficOpsElectricPoliceDO> electricPoliceDOMap = electricPoliceDOList.stream().collect(Collectors.toMap(TrafficOpsElectricPoliceDO::getIpAddr, electricPoliceDO -> electricPoliceDO,   (k1, k2) -> k1));

        // 查询历史记录
        List<TrafficMonitorResultsImportDetailDO> detailDOList = trafficMonitorResultsImportDetailMapper.selectList();

        AtomicReference<Date> date = new AtomicReference<>(DateUtil.parse(importExcelVOList.get(0).getOccurrenceTime(), "yyyy-MM-dd"));
        if (!CollectionUtils.isEmpty(detailDOList)) {
            List<String> idList = detailDOList.stream().map(TrafficMonitorResultsImportDetailDO::getId).collect(Collectors.toList());
            LambdaQueryWrapperX<TrafficMonitorResultsImportDetailHistoryDO> historyDOLambdaQueryWrapperX = new LambdaQueryWrapperX<>();
            historyDOLambdaQueryWrapperX.in(TrafficMonitorResultsImportDetailHistoryDO::getDetailId, idList);
            historyDOLambdaQueryWrapperX.eq(TrafficMonitorResultsImportDetailHistoryDO::getOccurrenceTime, DateUtil.beginOfDay(date.get()));
            trafficMonitorResultsImportDetailHistoryMapper.delete(historyDOLambdaQueryWrapperX);

            // 重新保存到历史表
            List<TrafficMonitorResultsImportDetailHistoryDO> historyDOList = detailDOList.stream().map(detail -> {
                TrafficMonitorResultsImportDetailHistoryDO historyDO = BeanUtils.toBean(detail, TrafficMonitorResultsImportDetailHistoryDO.class);
                historyDO.setId(null);
                historyDO.setDetailId(detail.getId());
                historyDO.setCreateTime(DateUtil.toLocalDateTime(new Date()));
                historyDO.setUpdateTime(DateUtil.toLocalDateTime(new Date()));
                return historyDO;
            }).collect(Collectors.toList());
            if (!CollectionUtils.isEmpty(historyDOList)) {
                trafficMonitorResultsImportDetailHistoryMapper.insert(historyDOList);
            }
        }
        Map<String, TrafficMonitorResultsImportDetailDO> detailDOMap = Optional.ofNullable(detailDOList).orElse(new ArrayList<>()).stream().collect(Collectors.toMap(TrafficMonitorResultsImportDetailDO::getIpAddr, detailDO -> detailDO,  (k1, k2) -> k1));

        // 使用线程安全的集合
        List<ExcelImportTrafficMonitorResultsDTO> threadSafeSuccessList = Collections.synchronizedList(new ArrayList<>());
        List<ExcelImportTrafficMonitorResultsDTO> threadSafeFailList = Collections.synchronizedList(new ArrayList<>());
        AtomicInteger threadSafeSucCnt = new AtomicInteger(0);
        AtomicInteger threadSafeFailCnt = new AtomicInteger(0);
        List<TrafficMonitorResultsImportDetailDO> threadSafeInstList = Collections.synchronizedList(new ArrayList<>());
        List<TrafficMonitorResultsImportDetailDO> threadSafeUpdList = Collections.synchronizedList(new ArrayList<>());

        // 创建所有异步任务
        CompletableFuture.allOf(importExcelVOList.stream()
                .map(importExcel -> CompletableFuture.runAsync(() -> {
                    try {
                        if (StringUtils.isEmpty(importExcel.getIpAddr())) {
                            importExcel.setFailReason("IP地址不能为空");
                            threadSafeFailList.add(importExcel);
                            threadSafeFailCnt.incrementAndGet();
                            return;
                        }

                        if (StringUtils.isBlank(importExcel.getStatus())) {
                            importExcel.setFailReason("异常事件不能为空");
                            threadSafeFailList.add(importExcel);
                            threadSafeFailCnt.incrementAndGet();
                            return;
                        }

                        if (StringUtils.isBlank(importExcel.getOccurrenceTime())) {
                            importExcel.setFailReason("检测时间不能为空");
                            threadSafeFailList.add(importExcel);
                            threadSafeFailCnt.incrementAndGet();
                            return;
                        }

                        date.set(DateUtil.parse(importExcel.getOccurrenceTime(), "yyyy-MM-dd"));

                        // 根据IP查询电警信息
                        TrafficOpsElectricPoliceDO electricPoliceDO = electricPoliceDOMap.get(importExcel.getIpAddr());
                        if (electricPoliceDO == null) {
                            importExcel.setFailReason("该电警信息不存在！");
                            threadSafeFailList.add(importExcel);
                            threadSafeFailCnt.incrementAndGet();
                            return;
                        }

                        TrafficMonitorResultsImportDetailDO importDetailDO = detailDOMap.get(importExcel.getIpAddr());
                        if (importDetailDO == null) {
                            importDetailDO = new TrafficMonitorResultsImportDetailDO();
                        }
                        importDetailDO.setEquId(electricPoliceDO.getId());
                        importDetailDO.setIpAddr(importExcel.getIpAddr());
                        importDetailDO.setRoadName(electricPoliceDO.getRoadName());
                        importDetailDO.setIntersectionName(electricPoliceDO.getIntersectionName());
                        importDetailDO.setMemberBh(electricPoliceDO.getMemberBh());
                        importDetailDO.setDirection(electricPoliceDO.getDirection());
                        importDetailDO.setOccurrenceTime(DateUtil.toLocalDateTime(date.get()));
                        if (importExcel.getStatus().contains("IP不可达")) {
                            importDetailDO.setStatus("IP不可达");
                            importDetailDO.setMonitorSituation(null);
                        } else {
                            importDetailDO.setStatus(OpsConstants.EQU_STATUS_ZC);

                            String monitorSituation = importExcel.getStatus().replace(OpsConstants.EQU_STATUS_ZC, "");
                            if (StringUtils.isBlank(monitorSituation)) {
                                monitorSituation = null;
                            }
                            importDetailDO.setMonitorSituation(monitorSituation);
                        }

                        processImages(importExcel, importDetailDO);

                        if (StringUtils.isNotBlank(importDetailDO.getId())) {
                            importDetailDO.setUpdateTime(DateUtil.toLocalDateTime(new Date()));
                            threadSafeUpdList.add(importDetailDO);
                        } else {
                            threadSafeInstList.add(importDetailDO);
                        }

                        threadSafeSuccessList.add(importExcel);
                        threadSafeSucCnt.incrementAndGet();
                    } catch (Exception e) {
                        log.error("处理导入记录异常", e);
                        importExcel.setFailReason("处理异常: " + e.getMessage());
                        threadSafeFailList.add(importExcel);
                        threadSafeFailCnt.incrementAndGet();
                    }
                }, importExecutor)).toArray(CompletableFuture[]::new)).join();

        // 合并结果
        successList.addAll(threadSafeSuccessList);
        failList.addAll(threadSafeFailList);
        importSucCnt.addAndGet(threadSafeSucCnt.get());
        importFailCnt.addAndGet(threadSafeFailCnt.get());

        if (!CollectionUtils.isEmpty(threadSafeInstList)) {
            trafficMonitorResultsImportDetailMapper.insert(threadSafeInstList);
        }

        if (!CollectionUtils.isEmpty(threadSafeUpdList)) {
            trafficMonitorResultsImportDetailMapper.updateById(threadSafeUpdList);
        }

        analysisImportMonitorResults(date.get());
    }

    // 处理图片
    private void processImages(ExcelImportTrafficMonitorResultsDTO importExcel, TrafficMonitorResultsImportDetailDO resultsDO) {
        String imgHex = Stream.of(
                        importExcel.getImgHex1(),
                        importExcel.getImgHex2(),
                        importExcel.getImgHex3(),
                        importExcel.getImgHex4(),
                        importExcel.getImgHex5())
                .filter(StringUtils::isNotBlank)
                .collect(Collectors.joining());

        if (StringUtils.isNotBlank(imgHex)) {
            resultsDO.setResultImg(uploadImgFile(imgHex));
        }
    }

    private void analysisImportMonitorResults(Date date) {
        if (date == null) {
            date = new Date();
        }
        date = DateUtil.beginOfDay(date);
        // 查询指定日期的检测结果
        LambdaQueryWrapperX<TrafficMonitorResultsImportDetailDO> lambdaQueryWrapperX = new LambdaQueryWrapperX<>();
        lambdaQueryWrapperX.eq(TrafficMonitorResultsImportDetailDO::getOccurrenceTime, date);
        List<TrafficMonitorResultsImportDetailDO> list = trafficMonitorResultsImportDetailMapper.selectList(lambdaQueryWrapperX);
        if (CollectionUtils.isEmpty(list)) {
            return;
        }
        ConfigDO monitorNormalSituationConfig = configService.getConfigByKey(OpsConstants.PARAM_KEY_MONITOR_NORMAL_SITUATION);
        String monitorNormalSituation;
        if (monitorNormalSituationConfig != null) {
            monitorNormalSituation = monitorNormalSituationConfig.getValue();
        } else {
            monitorNormalSituation = "";
        }

        long normalCnt = list.stream()
                .filter(detail -> OpsConstants.EQU_STATUS_ZC.equals(detail.getStatus()))
                .filter(detail -> StringUtils.isBlank(detail.getMonitorSituation()) ||
                        Arrays.stream(detail.getMonitorSituation().split(OpsConstants.EN_STRING_SEMICOLON))
                                .anyMatch(monitorNormalSituation::contains))
                .count();
        long abnormalCnt = list.stream().filter(detail -> !OpsConstants.EQU_STATUS_ZC.equals(detail.getStatus()) || StringUtils.isNotBlank(detail.getMonitorSituation())).count();

        // 查询指定日期的分析结果
        LambdaQueryWrapperX<TrafficMonitorResultsImportAnalysisDO> analysisDOLambdaQueryWrapperX = new LambdaQueryWrapperX<>();
        analysisDOLambdaQueryWrapperX.eq(TrafficMonitorResultsImportAnalysisDO::getAnalysisDate, date);
        List<TrafficMonitorResultsImportAnalysisDO> analysisDOList = trafficMonitorResultsImportAnalysisMapper.selectList(analysisDOLambdaQueryWrapperX);

        TrafficMonitorResultsImportAnalysisDO analysisDO = new TrafficMonitorResultsImportAnalysisDO();
        analysisDO.setAnalysisDate(DateUtil.toLocalDateTime(date));
        analysisDO.setNormalCnt((int) normalCnt);
        analysisDO.setAbnormalCnt((int) abnormalCnt);
        analysisDO.setHealthRate(new BigDecimal(normalCnt).divide(new BigDecimal(list.size()), 4, RoundingMode.HALF_UP));
        if (CollectionUtils.isEmpty(analysisDOList)) {
            trafficMonitorResultsImportAnalysisMapper.insert(analysisDO);
        } else {
            analysisDO.setId(analysisDOList.get(0).getId());
            trafficMonitorResultsImportAnalysisMapper.updateById(analysisDO);
        }
    }

    private void handlePoliceMonitorResultsImportExcel(List<ExcelImportPoliceMonitorResultsDTO> importExcelVOList, List<ExcelImportPoliceMonitorResultsDTO> successList,
                                                        List<ExcelImportPoliceMonitorResultsDTO> failList, AtomicInteger importSucCnt, AtomicInteger importFailCnt) {
        if (CollectionUtils.isEmpty(importExcelVOList)) {
            return;
        }

        // 查询所有电警设备
        List<PoliceOpsElectricPoliceDO> electricPoliceDOList = policeOpsElectricPoliceMapper.selectList();
        if (CollectionUtils.isEmpty(electricPoliceDOList)) {
            return;
        }
        Map<String, PoliceOpsElectricPoliceDO> electricPoliceDOMap = electricPoliceDOList.stream().collect(Collectors.toMap(PoliceOpsElectricPoliceDO::getIpAddr, electricPoliceDO -> electricPoliceDO,   (k1, k2) -> k1));

        // 删除并保留最新一条的检测情况数据
        policeOpsEquipInfoExtMapper.deletePoliceMonitorResultsImportSituationKeepLastOne();

        // 使用线程安全的集合
        List<ExcelImportPoliceMonitorResultsDTO> threadSafeSuccessList = Collections.synchronizedList(new ArrayList<>());
        List<ExcelImportPoliceMonitorResultsDTO> threadSafeFailList = Collections.synchronizedList(new ArrayList<>());
        AtomicInteger threadSafeSucCnt = new AtomicInteger(0);
        AtomicInteger threadSafeFailCnt = new AtomicInteger(0);
        List<PoliceMonitorResultsImportDetailDO> threadSafeInstList = Collections.synchronizedList(new ArrayList<>());
        List<PoliceMonitorResultsImportSituationDO> threadSafeInstSituationDOList = Collections.synchronizedList(new ArrayList<>());

        AtomicReference<Date> occurrenceTime = new AtomicReference<>();
        // 创建所有异步任务
        CompletableFuture.allOf(importExcelVOList.stream()
                .map(importExcel -> CompletableFuture.runAsync(() -> {
                    try {
                        if (StringUtils.isEmpty(importExcel.getIpAddr())) {
                            importExcel.setFailReason("IP地址不能为空");
                            threadSafeFailList.add(importExcel);
                            threadSafeFailCnt.incrementAndGet();
                            return;
                        }

                        if (StringUtils.isBlank(importExcel.getVideoStatus())) {
                            importExcel.setFailReason("实时视频检测状态不能为空");
                            threadSafeFailList.add(importExcel);
                            threadSafeFailCnt.incrementAndGet();
                            return;
                        }

                        if (StringUtils.isBlank(importExcel.getPingStatus())) {
                            importExcel.setFailReason("ping检测状态不能为空");
                            threadSafeFailList.add(importExcel);
                            threadSafeFailCnt.incrementAndGet();
                            return;
                        }

                        if (StringUtils.isBlank(importExcel.getOccurrenceTime())) {
                            importExcel.setFailReason("检测时间不能为空");
                            threadSafeFailList.add(importExcel);
                            threadSafeFailCnt.incrementAndGet();
                            return;
                        }

                        // 根据IP查询电警信息
                        PoliceOpsElectricPoliceDO electricPoliceDO = electricPoliceDOMap.get(importExcel.getIpAddr());
                        if (electricPoliceDO == null) {
                            importExcel.setFailReason("IP为" + importExcel.getIpAddr() + "电警信息不存在！");
                            threadSafeFailList.add(importExcel);
                            threadSafeFailCnt.incrementAndGet();
                            return;
                        }


                        occurrenceTime.set(DateUtil.parse(importExcel.getOccurrenceTime(), "yyyy-MM-dd HH:mm:ss"));

                        PoliceMonitorResultsImportDetailDO importDetailDO = new PoliceMonitorResultsImportDetailDO();
                        importDetailDO.setEquId(electricPoliceDO.getId());
                        importDetailDO.setIpAddr(importExcel.getIpAddr());
                        importDetailDO.setEquLable(electricPoliceDO.getEquLable());
                        importDetailDO.setOccurrenceTime(DateUtil.toLocalDateTime(occurrenceTime.get()));
                        importDetailDO.setVideoStatus(importExcel.getVideoStatus());
                        importDetailDO.setPingStatus(importExcel.getPingStatus());

                        PoliceMonitorResultsImportSituationDO importSituationDO = BeanUtils.toBean(importDetailDO, PoliceMonitorResultsImportSituationDO.class);
                        threadSafeInstList.add(importDetailDO);
                        threadSafeInstSituationDOList.add(importSituationDO);

                        threadSafeSuccessList.add(importExcel);
                        threadSafeSucCnt.incrementAndGet();
                    } catch (Exception e) {
                        log.error("处理导入记录异常", e);
                        importExcel.setFailReason("处理异常: " + e.getMessage());
                        threadSafeFailList.add(importExcel);
                        threadSafeFailCnt.incrementAndGet();
                    }
                }, importExecutor)).toArray(CompletableFuture[]::new)).join();

        // 合并结果
        successList.addAll(threadSafeSuccessList);
        failList.addAll(threadSafeFailList);
        importSucCnt.addAndGet(threadSafeSucCnt.get());
        importFailCnt.addAndGet(threadSafeFailCnt.get());

        if (!CollectionUtils.isEmpty(threadSafeInstList)) {
            policeMonitorResultsImportDetailMapper.insert(threadSafeInstList);
        }

        if (!CollectionUtils.isEmpty(threadSafeInstSituationDOList)) {
            policeMonitorResultsImportSituationMapper.insert(threadSafeInstSituationDOList);
        }

        policeMonitorResultsImportAnalysisExtService.analysisMonitorResultsImportDetail(DateUtil.toLocalDateTime(DateUtil.beginOfDay(occurrenceTime.get())));
    }

    private void handlePoliceMonitorPingDetailImportExcel(List<ExcelImportPoliceMonitorPingDetailDTO> importExcelVOList, List<ExcelImportPoliceMonitorPingDetailDTO> successList,
                                                       List<ExcelImportPoliceMonitorPingDetailDTO> failList, AtomicInteger importSucCnt, AtomicInteger importFailCnt) {
        if (CollectionUtils.isEmpty(importExcelVOList)) {
            return;
        }

        // 查询所有电警设备
        List<PoliceOpsElectricPoliceDO> electricPoliceDOList = policeOpsElectricPoliceMapper.selectList();
        if (CollectionUtils.isEmpty(electricPoliceDOList)) {
            return;
        }
        Map<String, PoliceOpsElectricPoliceDO> electricPoliceDOMap = electricPoliceDOList.stream().collect(Collectors.toMap(PoliceOpsElectricPoliceDO::getIpAddr, electricPoliceDO -> electricPoliceDO,   (k1, k2) -> k1));

        // 使用线程安全的集合
        List<ExcelImportPoliceMonitorPingDetailDTO> threadSafeSuccessList = Collections.synchronizedList(new ArrayList<>());
        List<ExcelImportPoliceMonitorPingDetailDTO> threadSafeFailList = Collections.synchronizedList(new ArrayList<>());
        AtomicInteger threadSafeSucCnt = new AtomicInteger(0);
        AtomicInteger threadSafeFailCnt = new AtomicInteger(0);
        List<PoliceMonitorPingDetailDO> threadSafeInstList = Collections.synchronizedList(new ArrayList<>());
        List<PoliceMonitorPingDetailDO> threadSafeUpdList = Collections.synchronizedList(new ArrayList<>());
        // 创建所有异步任务
        List<PoliceMonitorPingSituationDO> threadSafeInstSituationList = Collections.synchronizedList(new ArrayList<>());
        CompletableFuture.allOf(importExcelVOList.stream()
                .map(importExcel -> CompletableFuture.runAsync(() -> {
                    try {
                        if (StringUtils.isEmpty(importExcel.getIpAddr())) {
                            importExcel.setFailReason("IP地址不能为空");
                            threadSafeFailList.add(importExcel);
                            threadSafeFailCnt.incrementAndGet();
                            return;
                        }

                        if (StringUtils.isBlank(importExcel.getPingStatus())) {
                            importExcel.setFailReason("ping检测状态不能为空");
                            threadSafeFailList.add(importExcel);
                            threadSafeFailCnt.incrementAndGet();
                            return;
                        }

                        if (StringUtils.isBlank(importExcel.getOccurrenceTime())) {
                            importExcel.setFailReason("检测时间不能为空");
                            threadSafeFailList.add(importExcel);
                            threadSafeFailCnt.incrementAndGet();
                            return;
                        }

                        // 根据IP查询电警信息
                        PoliceOpsElectricPoliceDO electricPoliceDO = electricPoliceDOMap.get(importExcel.getIpAddr());
                        if (electricPoliceDO == null) {
                            importExcel.setFailReason("IP为" + importExcel.getIpAddr() + "电警信息不存在！");
                            threadSafeFailList.add(importExcel);
                            threadSafeFailCnt.incrementAndGet();
                            return;
                        }

                        PoliceMonitorPingDetailDO importDetailDO = policeMonitorPingDetailMapper.selectById(importExcel.getId());
                        boolean isInst = false;
                        if (importDetailDO == null) {
                            importDetailDO = new PoliceMonitorPingDetailDO();
                            importDetailDO.setId(importExcel.getId());
                            isInst = true;
                        }

                        importDetailDO.setEquId(electricPoliceDO.getId());
                        importDetailDO.setIpAddr(importExcel.getIpAddr());
                        importDetailDO.setEquLable(electricPoliceDO.getEquLable());
                        importDetailDO.setOccurrenceTime(DateUtil.toLocalDateTime(DateUtil.parse(importExcel.getOccurrenceTime(), "yyyy-MM-dd HH:mm:ss")));
                        importDetailDO.setPingStatus(importExcel.getPingStatus());
                        importDetailDO.setPingCnt(importExcel.getPingCnt());
                        importDetailDO.setSource(importExcel.getSource());
                        importDetailDO.setLossRate(BigDecimal.valueOf(Long.parseLong(importExcel.getLossRate())));
                        importDetailDO.setUpdateTime(DateUtil.toLocalDateTime(new Date()));

                        PoliceMonitorPingSituationDO situationDO = new PoliceMonitorPingSituationDO();
                        situationDO.setEquId(importDetailDO.getEquId());
                        situationDO.setIpAddr(importDetailDO.getIpAddr());
                        situationDO.setOccurrenceTime(importDetailDO.getOccurrenceTime());
                        situationDO.setPingStatus(importDetailDO.getPingStatus());
                        threadSafeInstSituationList.add(situationDO);

                        if (isInst) {
                            threadSafeInstList.add(importDetailDO);
                        } else {
                            threadSafeUpdList.add(importDetailDO);
                        }

                        threadSafeSuccessList.add(importExcel);
                        threadSafeSucCnt.incrementAndGet();
                    } catch (Exception e) {
                        log.error("处理导入记录异常", e);
                        importExcel.setFailReason("处理异常: " + e.getMessage());
                        threadSafeFailList.add(importExcel);
                        threadSafeFailCnt.incrementAndGet();
                    }
                }, importExecutor)).toArray(CompletableFuture[]::new)).join();

        // 合并结果
        successList.addAll(threadSafeSuccessList);
        failList.addAll(threadSafeFailList);
        importSucCnt.addAndGet(threadSafeSucCnt.get());
        importFailCnt.addAndGet(threadSafeFailCnt.get());

        if (!CollectionUtils.isEmpty(threadSafeInstList)) {
            policeMonitorPingDetailMapper.insert(threadSafeInstList);
        }

        if (!CollectionUtils.isEmpty(threadSafeUpdList)) {
            policeMonitorPingDetailMapper.updateById(threadSafeUpdList);
        }

        if (!CollectionUtils.isEmpty(threadSafeInstSituationList)) {
            policeMonitorPingSituationMapper.insert(threadSafeInstSituationList);

            policeOpsEquipInfoExtMapper.deletePoliceMonitorPingSituationKeepLastTwo();
        }

        // 通断分析
        policeMonitorPingExtService.analysisMonitorPing();
    }

    private void handleVioEquipmentDailySituationImportExcel(List<ExcelImportVioEquipmentDailySituationDTO> importExcelVOList, List<ExcelImportVioEquipmentDailySituationDTO> successList,
                                                          List<ExcelImportVioEquipmentDailySituationDTO> failList, AtomicInteger importSucCnt, AtomicInteger importFailCnt) {
        if (CollectionUtils.isEmpty(importExcelVOList)) {
            return;
        }

//        // 查询所有电警设备
//        List<PoliceOpsElectricPoliceDO> electricPoliceDOList = policeOpsElectricPoliceMapper.selectList();
//        if (CollectionUtils.isEmpty(electricPoliceDOList)) {
//            return;
//        }
//        Map<String, PoliceOpsElectricPoliceDO> electricPoliceDOMap = electricPoliceDOList.stream().collect(Collectors.toMap(PoliceOpsElectricPoliceDO::getIpAddr, electricPoliceDO -> electricPoliceDO,   (k1, k2) -> k1));

        // 使用线程安全的集合
        List<ExcelImportVioEquipmentDailySituationDTO> threadSafeSuccessList = Collections.synchronizedList(new ArrayList<>());
        List<ExcelImportVioEquipmentDailySituationDTO> threadSafeFailList = Collections.synchronizedList(new ArrayList<>());
        AtomicInteger threadSafeSucCnt = new AtomicInteger(0);
        AtomicInteger threadSafeFailCnt = new AtomicInteger(0);
        List<BtjczhVioEquipmentDailySituationDO> threadSafeInstList = Collections.synchronizedList(new ArrayList<>());
        AtomicReference<Date> date = new AtomicReference<>(new Date());
//        Optional<LocalDateTime> date = importExcelVOList.stream().map(vo -> DateUtil.toLocalDateTime(DateUtil.parse(vo.getOccurrenceDay(), "yyyy-MM-dd"))).min(LocalDateTime::compareTo);
        CompletableFuture.allOf(importExcelVOList.stream()
                .map(importExcel -> CompletableFuture.runAsync(() -> {
                    try {
                        if (StringUtils.isEmpty(importExcel.getId())) {
                            importExcel.setFailReason("主键不能为空");
                            threadSafeFailList.add(importExcel);
                            threadSafeFailCnt.incrementAndGet();
                            return;
                        }

                        if (StringUtils.isBlank(importExcel.getOccurrenceDay())) {
                            importExcel.setFailReason("检测时间不能为空");
                            threadSafeFailList.add(importExcel);
                            threadSafeFailCnt.incrementAndGet();
                            return;
                        }

                        if (StringUtils.isBlank(importExcel.getSbbh())) {
                            importExcel.setFailReason("设备编号不能为空");
                            threadSafeFailList.add(importExcel);
                            threadSafeFailCnt.incrementAndGet();
                            return;
                        }

                        BtjczhVioEquipmentDailySituationDO dailySituationDO = BeanUtils.toBean(importExcel, BtjczhVioEquipmentDailySituationDO.class);
                        Date occDate = DateUtil.parse(importExcel.getOccurrenceDay(), "yyyy-MM-dd");
                        if (date.get().after(occDate)) {
                            date.set(occDate);
                        }
                        dailySituationDO.setOccurrenceDay(occDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());

                        dailySituationDO.setNoSurveilCnt(Integer.valueOf(importExcel.getNoSurveilCnt()));
                        dailySituationDO.setInvalidSurveilCnt(Integer.valueOf(importExcel.getInvalidSurveilCnt()));
                        dailySituationDO.setValidSurveilCnt(Integer.valueOf(importExcel.getValidSurveilCnt()));
                        if (StringUtils.isNotBlank(importExcel.getRecognitionAccuracyRate())) {
                            dailySituationDO.setRecognitionAccuracyRate(BigDecimal.valueOf(Double.parseDouble(importExcel.getRecognitionAccuracyRate())));
                        }
                        if (StringUtils.isNotBlank(importExcel.getRecognitionAccuracyPickDate())) {

                            dailySituationDO.setRecognitionAccuracyPickDate(DateUtil.toLocalDateTime(DateUtil.parse(importExcel.getRecognitionAccuracyPickDate(), "yyyy-MM-dd HH:mm:ss")));
                        }
                        if (StringUtils.isNotBlank(importExcel.getCreateTime())) {
                            dailySituationDO.setCreateTime(DateUtil.toLocalDateTime(DateUtil.parse(importExcel.getCreateTime(), "yyyy-MM-dd HH:mm:ss")));
                        }
                        threadSafeInstList.add(dailySituationDO);

                        threadSafeSuccessList.add(importExcel);
                        threadSafeSucCnt.incrementAndGet();
                    } catch (Exception e) {
                        log.error("处理导入记录异常", e);
                        importExcel.setFailReason("处理异常: " + e.getMessage());
                        threadSafeFailList.add(importExcel);
                        threadSafeFailCnt.incrementAndGet();
                    }
                }, importExecutor)).toArray(CompletableFuture[]::new)).join();

        // 合并结果
        successList.addAll(threadSafeSuccessList);
        failList.addAll(threadSafeFailList);
        importSucCnt.addAndGet(threadSafeSucCnt.get());
        importFailCnt.addAndGet(threadSafeFailCnt.get());

        if (!CollectionUtils.isEmpty(threadSafeInstList)) {
            // 删除历史记录
//            List<String> sbbhList = threadSafeInstList.stream().map(BtjczhVioEquipmentDailySituationDO::getSbbh).collect(Collectors.toList());
            LambdaQueryWrapperX<BtjczhVioEquipmentDailySituationDO> deleteLambdaQueryWrapperX = new LambdaQueryWrapperX<>();
            deleteLambdaQueryWrapperX.ge(BtjczhVioEquipmentDailySituationDO::getOccurrenceDay, date.get());
//            deleteLambdaQueryWrapperX.in(BtjczhVioEquipmentDailySituationDO::getSbbh, sbbhList);
            btjczhVioEquipmentDailySituationMapper.delete(deleteLambdaQueryWrapperX);

            btjczhVioEquipmentDailySituationMapper.insert(threadSafeInstList);
        }

        // 通断分析
        if (date.get() != null) {
            trafficVioEquipmentMonitorAnalysisExtService.analysisVioEquipmentSurveil(date.get());
        }


    }

    private void handleVioEquipmentInvalidReasonImportExcel(List<ExcelImportVioInvaliReasonDTO> importExcelVOList, List<ExcelImportVioInvaliReasonDTO> successList,
                                                             List<ExcelImportVioInvaliReasonDTO> failList, AtomicInteger importSucCnt, AtomicInteger importFailCnt) {
        if (CollectionUtils.isEmpty(importExcelVOList)) {
            return;
        }

        // 使用线程安全的集合
        List<ExcelImportVioInvaliReasonDTO> threadSafeSuccessList = Collections.synchronizedList(new ArrayList<>());
        List<ExcelImportVioInvaliReasonDTO> threadSafeFailList = Collections.synchronizedList(new ArrayList<>());
        AtomicInteger threadSafeSucCnt = new AtomicInteger(0);
        AtomicInteger threadSafeFailCnt = new AtomicInteger(0);
        List<TrafficVioEquipmentInvalidReasonAnalysisDO> threadSafeInstList = Collections.synchronizedList(new ArrayList<>());
        AtomicReference<Date> date = new AtomicReference<>(new Date());
        CompletableFuture.allOf(importExcelVOList.stream()
                .map(importExcel -> CompletableFuture.runAsync(() -> {
                    try {
                        if (StringUtils.isEmpty(importExcel.getId())) {
                            importExcel.setFailReason("主键不能为空");
                            threadSafeFailList.add(importExcel);
                            threadSafeFailCnt.incrementAndGet();
                            return;
                        }

                        if (StringUtils.isBlank(importExcel.getOccurrenceTime())) {
                            importExcel.setFailReason("检测时间不能为空");
                            threadSafeFailList.add(importExcel);
                            threadSafeFailCnt.incrementAndGet();
                            return;
                        }

                        if (StringUtils.isBlank(importExcel.getSbbh())) {
                            importExcel.setFailReason("设备编号不能为空");
                            threadSafeFailList.add(importExcel);
                            threadSafeFailCnt.incrementAndGet();
                            return;
                        }

                        TrafficVioEquipmentInvalidReasonAnalysisDO invalidReasonAnalysisDO = BeanUtils.toBean(importExcel, TrafficVioEquipmentInvalidReasonAnalysisDO.class);
                        Date occDate = DateUtil.parse(importExcel.getOccurrenceTime(), "yyyy-MM-dd");
                        invalidReasonAnalysisDO.setOccurrenceTime(DateUtil.toLocalDateTime(occDate));
                        if (date.get().after(occDate)) {
                            date.set(occDate);
                        }

                        if (StringUtils.isNotBlank(importExcel.getCreateTime())) {
                            invalidReasonAnalysisDO.setCreateTime(DateUtil.toLocalDateTime(DateUtil.parse(importExcel.getCreateTime(), "yyyy-MM-dd HH:mm:ss")));
                        }
                        threadSafeInstList.add(invalidReasonAnalysisDO);

                        threadSafeSuccessList.add(importExcel);
                        threadSafeSucCnt.incrementAndGet();
                    } catch (Exception e) {
                        log.error("处理导入记录异常", e);
                        importExcel.setFailReason("处理异常: " + e.getMessage());
                        threadSafeFailList.add(importExcel);
                        threadSafeFailCnt.incrementAndGet();
                    }
                }, importExecutor)).toArray(CompletableFuture[]::new)).join();

        // 合并结果
        successList.addAll(threadSafeSuccessList);
        failList.addAll(threadSafeFailList);
        importSucCnt.addAndGet(threadSafeSucCnt.get());
        importFailCnt.addAndGet(threadSafeFailCnt.get());

        if (!CollectionUtils.isEmpty(threadSafeInstList)) {
            // 删除历史记录
            List<String> sbbhList = threadSafeInstList.stream().map(TrafficVioEquipmentInvalidReasonAnalysisDO::getSbbh).collect(Collectors.toList());
            LambdaQueryWrapperX<TrafficVioEquipmentInvalidReasonAnalysisDO> deleteLambdaQueryWrapperX = new LambdaQueryWrapperX<>();
            deleteLambdaQueryWrapperX.ge(TrafficVioEquipmentInvalidReasonAnalysisDO::getOccurrenceTime, date.get());
            deleteLambdaQueryWrapperX.in(TrafficVioEquipmentInvalidReasonAnalysisDO::getSbbh, sbbhList);
            trafficVioEquipmentInvalidReasonAnalysisMapper.delete(deleteLambdaQueryWrapperX);

            trafficVioEquipmentInvalidReasonAnalysisMapper.insert(threadSafeInstList);
        }
    }

    private void handleVioEquipmentIllegalImportExcel(List<ExcelImportVioIllegalDTO> importExcelVOList, List<ExcelImportVioIllegalDTO> successList,
                                                            List<ExcelImportVioIllegalDTO> failList, AtomicInteger importSucCnt, AtomicInteger importFailCnt) {
        if (CollectionUtils.isEmpty(importExcelVOList)) {
            return;
        }

        // 使用线程安全的集合
        List<ExcelImportVioIllegalDTO> threadSafeSuccessList = Collections.synchronizedList(new ArrayList<>());
        List<ExcelImportVioIllegalDTO> threadSafeFailList = Collections.synchronizedList(new ArrayList<>());
        AtomicInteger threadSafeSucCnt = new AtomicInteger(0);
        AtomicInteger threadSafeFailCnt = new AtomicInteger(0);
        List<TrafficVioEquipmentIllegalAnalysisDO> threadSafeInstList = Collections.synchronizedList(new ArrayList<>());
        AtomicReference<Date> date = new AtomicReference<>(new Date());
        CompletableFuture.allOf(importExcelVOList.stream()
                .map(importExcel -> CompletableFuture.runAsync(() -> {
                    try {
                        if (StringUtils.isEmpty(importExcel.getId())) {
                            importExcel.setFailReason("主键不能为空");
                            threadSafeFailList.add(importExcel);
                            threadSafeFailCnt.incrementAndGet();
                            return;
                        }

                        if (StringUtils.isBlank(importExcel.getOccurrenceTime())) {
                            importExcel.setFailReason("检测时间不能为空");
                            threadSafeFailList.add(importExcel);
                            threadSafeFailCnt.incrementAndGet();
                            return;
                        }

                        if (StringUtils.isBlank(importExcel.getSbbh())) {
                            importExcel.setFailReason("设备编号不能为空");
                            threadSafeFailList.add(importExcel);
                            threadSafeFailCnt.incrementAndGet();
                            return;
                        }

                        TrafficVioEquipmentIllegalAnalysisDO illegalAnalysisDO = BeanUtils.toBean(importExcel, TrafficVioEquipmentIllegalAnalysisDO.class);
                        Date occDate = DateUtil.parse(importExcel.getOccurrenceTime(), "yyyy-MM-dd");
                        illegalAnalysisDO.setOccurrenceTime(DateUtil.toLocalDateTime(occDate));
                        if (date.get().after(occDate)) {
                            date.set(occDate);
                        }

                        if (StringUtils.isNotBlank(importExcel.getCreateTime())) {
                            illegalAnalysisDO.setCreateTime(DateUtil.toLocalDateTime(DateUtil.parse(importExcel.getCreateTime(), "yyyy-MM-dd HH:mm:ss")));
                        }
                        threadSafeInstList.add(illegalAnalysisDO);

                        threadSafeSuccessList.add(importExcel);
                        threadSafeSucCnt.incrementAndGet();
                    } catch (Exception e) {
                        log.error("处理导入记录异常", e);
                        importExcel.setFailReason("处理异常: " + e.getMessage());
                        threadSafeFailList.add(importExcel);
                        threadSafeFailCnt.incrementAndGet();
                    }
                }, importExecutor)).toArray(CompletableFuture[]::new)).join();

        // 合并结果
        successList.addAll(threadSafeSuccessList);
        failList.addAll(threadSafeFailList);
        importSucCnt.addAndGet(threadSafeSucCnt.get());
        importFailCnt.addAndGet(threadSafeFailCnt.get());

        if (!CollectionUtils.isEmpty(threadSafeInstList)) {
            // 删除历史记录
            List<String> sbbhList = threadSafeInstList.stream().map(TrafficVioEquipmentIllegalAnalysisDO::getSbbh).collect(Collectors.toList());
            LambdaQueryWrapperX<TrafficVioEquipmentIllegalAnalysisDO> deleteLambdaQueryWrapperX = new LambdaQueryWrapperX<>();
            deleteLambdaQueryWrapperX.ge(TrafficVioEquipmentIllegalAnalysisDO::getOccurrenceTime, date.get());
            deleteLambdaQueryWrapperX.in(TrafficVioEquipmentIllegalAnalysisDO::getSbbh, sbbhList);
            trafficVioEquipmentIllegalAnalysisMapper.delete(deleteLambdaQueryWrapperX);

            trafficVioEquipmentIllegalAnalysisMapper.insert(threadSafeInstList);
        }
    }
}
