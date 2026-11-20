package com.holdtime.ops.module.service.impl;


import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.IoUtil;
import cn.iocoder.yudao.framework.common.util.validation.ValidationUtils;
import cn.iocoder.yudao.framework.excel.core.util.ExcelUtils;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.security.core.util.SecurityFrameworkUtils;
import cn.iocoder.yudao.module.infra.service.file.FileService;
import cn.iocoder.yudao.module.system.dal.dataobject.user.AdminUserDO;
import cn.iocoder.yudao.module.system.dal.mysql.user.AdminUserMapper;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.converters.longconverter.LongStringConverter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.excel.write.style.column.LongestMatchColumnWidthStyleStrategy;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.fhs.common.utils.StringUtil;
import com.holdtime.ops.module.entity.*;
import com.holdtime.ops.module.entity.mapper.ImportRecordMapper;
import com.holdtime.ops.module.entity.mapper.TrafficOpsElectricPoliceMapper;
import com.holdtime.ops.module.entity.mapper.TrafficOpsMaintenanceMapper;
import com.holdtime.ops.module.entity.mapper.TrafficOpsSignalMapper;
import com.holdtime.ops.module.enums.OpsConstants;
import com.holdtime.ops.module.enums.OpsErrorCodeConstants;
import com.holdtime.ops.module.model.dto.ElectricFaultImportExcelDTO;
import com.holdtime.ops.module.model.dto.EquipFaultImportExcelDTO;
import com.holdtime.ops.module.model.vo.ImportRecordRespVO;
import com.holdtime.ops.module.service.TrafficEquipFaultImportExtService;
import com.holdtime.ops.module.utils.HanLPUtils;
import com.holdtime.ops.module.utils.StrUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.tika.utils.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.validation.ConstraintViolationException;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;

@Service
@Validated
@Slf4j
public class TrafficEquipFaultImportExtServiceImpl implements TrafficEquipFaultImportExtService {
    @Resource
    FileService fileService;
    @Resource
    TrafficOpsElectricPoliceMapper trafficOpsElectricPoliceMapper;
    @Resource
    TrafficOpsSignalMapper trafficOpsSignalMapper;
    @Resource
    TrafficOpsMaintenanceMapper trafficOpsMaintenanceMapper;
    @Resource
    ImportRecordMapper importRecordMapper;
    @Resource
    AdminUserMapper adminUserMapper;

    @Override
    public ImportRecordRespVO importEquipFaultInfo(MultipartFile file) {
        ImportRecordRespVO respVO = new ImportRecordRespVO();

        ImportRecordDO recordDO = new ImportRecordDO();
        List<EquipFaultImportExcelDTO> successList = new ArrayList<>();
        List<EquipFaultImportExcelDTO> failList = new ArrayList<>();

        AtomicInteger importSucCnt = new AtomicInteger();
        AtomicInteger importFailCnt = new AtomicInteger();
        try {
            // 1.存储上传的导入文件
            String fileUrl = fileService.createFile(file.getOriginalFilename(), null, IoUtil.readBytes(file.getInputStream()));
            recordDO.setFileName(file.getOriginalFilename());
            recordDO.setImportFile(fileUrl);
            respVO.setImportFile(fileUrl);

            // 解析Excel
            List<EquipFaultImportExcelDTO> importExcelVOList = ExcelUtils.read(file, EquipFaultImportExcelDTO.class);
            if (CollectionUtils.isEmpty(importExcelVOList)) {
                throw exception(OpsErrorCodeConstants.IMPORT_DATA_NOT_EXISTS);
            }

            recordDO.setImportNum(importExcelVOList.size());

            // 数据处理
            importExcelVOList.forEach(importExcel -> {
                // 去除属性前后空格
                StrUtils.trimObjAttr(importExcel);

                if (StringUtils.isEmpty(importExcel.getIntersectionName() )) {
                    importExcel.setFailReason("所在道路名称不能为空");
                    failList.add(importExcel);
                    importFailCnt.addAndGet(1);
                    return;
                }

                if (StringUtils.isEmpty(importExcel.getDirection() )) {
                    importExcel.setFailReason("方向不能为空");
                    failList.add(importExcel);
                    importFailCnt.addAndGet(1);
                    return;
                }

                // 查询信号机
                LambdaQueryWrapperX<TrafficOpsElectricPoliceDO> opsDOLambdaQueryWrapperX = new LambdaQueryWrapperX<>();
                if(!StringUtil.isEmpty(importExcel.getIntersectionName())){
                    List<String> roadNameList = HanLPUtils.segmentForRoad(importExcel.getIntersectionName());
                    if (!CollectionUtils.isEmpty(roadNameList)) {
                        for (String roadName : roadNameList) {
                            opsDOLambdaQueryWrapperX.like(TrafficOpsElectricPoliceDO::getIntersectionName, roadName);
                        }
                    }
                }
                opsDOLambdaQueryWrapperX.like(TrafficOpsElectricPoliceDO::getDirection, importExcel.getDirection());
                List<TrafficOpsElectricPoliceDO> electricPoliceDOList = trafficOpsElectricPoliceMapper.selectList(opsDOLambdaQueryWrapperX);
                if (CollectionUtils.isEmpty(electricPoliceDOList)) {
                    importExcel.setFailReason("未找到对应的电警设备");
                    failList.add(importExcel);
                    importFailCnt.addAndGet(1);
                    return;
                }

                if (electricPoliceDOList.size() > 1)  {
                    importExcel.setFailReason("匹配到多个电警设备");
                    failList.add(importExcel);
                    importFailCnt.addAndGet(1);
                    return;
                }

                importExcel.setEquipId(electricPoliceDOList.get(0).getId());
                importSucCnt.addAndGet(1);
                successList.add(importExcel);
            });

            // 批量更新
            LambdaUpdateWrapper<TrafficOpsElectricPoliceDO> updwrapper = new LambdaUpdateWrapper<>();
            updwrapper.set(TrafficOpsElectricPoliceDO::getStatus, OpsConstants.EQU_STATUS_GZ);
            updwrapper.in(TrafficOpsElectricPoliceDO::getId, successList.stream().map(EquipFaultImportExcelDTO::getEquipId).collect(Collectors.toList()));
            trafficOpsElectricPoliceMapper.update(updwrapper);

            // 导入结果写为excel
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

            ExcelWriter excelWriter = EasyExcel.write(outputStream).autoCloseStream(false).build();

            WriteSheet writeSheet1 = EasyExcel.writerSheet("同步成功")
                    .head(EquipFaultImportExcelDTO.class)
                    .registerWriteHandler(new LongestMatchColumnWidthStyleStrategy())
                    .registerConverter(new LongStringConverter())
                    .build();
            excelWriter.write(successList, writeSheet1);

            // 创建Sheet2对象
            WriteSheet writeSheet2 = EasyExcel.writerSheet("同步失败")
                    .head(EquipFaultImportExcelDTO.class)
                    .registerWriteHandler(new LongestMatchColumnWidthStyleStrategy())
                    .registerConverter(new LongStringConverter())
                    .build();
            excelWriter.write(failList, writeSheet2);

            excelWriter.finish();

            byte[] bytes = outputStream.toByteArray();
            String fileName = "Import_Result_" + System.currentTimeMillis() + ".xlsx";
            String failUrl = fileService.createFile(fileName, null, bytes);
            recordDO.setFailFile(failUrl);

            respVO.setImportNum(importExcelVOList.size());
            respVO.setSucNum(importSucCnt.get());
            respVO.setFailNum(importFailCnt.get());
            respVO.setFailFile(failUrl);
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

    @Override
    @SuppressWarnings("unchecked")
    public ImportRecordRespVO importEquipFaultInfo(MultipartFile file, Class<?> importTypeClass) {
        ImportRecordRespVO respVO = new ImportRecordRespVO();

        ImportRecordDO recordDO = new ImportRecordDO();
        List<?> successList = new ArrayList<>();
        List<?> failList = new ArrayList<>();

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

            recordDO.setImportNum(importExcelVOList.size());

            if (EquipFaultImportExcelDTO.class.equals(importTypeClass)) {
                handleImportExcel((List<EquipFaultImportExcelDTO>) importExcelVOList,
                        (List<EquipFaultImportExcelDTO>) successList, (List<EquipFaultImportExcelDTO>) failList,
                        importSucCnt, importFailCnt);
            } else {
                handleElectricFaultImportExcel((List<ElectricFaultImportExcelDTO>) importExcelVOList,
                        (List<ElectricFaultImportExcelDTO>) successList, (List<ElectricFaultImportExcelDTO>) failList,
                        importSucCnt, importFailCnt);
            }

            // 导入结果写为excel
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
            String failUrl = fileService.createFile(fileName, null, bytes);
            recordDO.setFailFile(failUrl);

            respVO.setImportNum(importExcelVOList.size());
            respVO.setSucNum(importSucCnt.get());
            respVO.setFailNum(importFailCnt.get());
            respVO.setFailFile(failUrl);
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

    private void handleImportExcel(List<EquipFaultImportExcelDTO> importExcelVOList, List<EquipFaultImportExcelDTO> successList,
                                   List<EquipFaultImportExcelDTO> failList, AtomicInteger importSucCnt, AtomicInteger importFailCnt) {
        // 数据处理
        importExcelVOList.forEach(importExcel -> {
            // 去除属性前后空格
            StrUtils.trimObjAttr(importExcel);

            if (StringUtils.isEmpty(importExcel.getIntersectionName() )) {
                importExcel.setFailReason("所在道路名称不能为空");
                failList.add(importExcel);
                importFailCnt.addAndGet(1);
                return;
            }

            if (StringUtils.isEmpty(importExcel.getDirection() )) {
                importExcel.setFailReason("方向不能为空");
                failList.add(importExcel);
                importFailCnt.addAndGet(1);
                return;
            }

            // 查询信号机
            LambdaQueryWrapperX<TrafficOpsElectricPoliceDO> opsDOLambdaQueryWrapperX = new LambdaQueryWrapperX<>();
            if(!StringUtil.isEmpty(importExcel.getIntersectionName())){
                List<String> roadNameList = HanLPUtils.segmentForRoad(importExcel.getIntersectionName());
                if (!CollectionUtils.isEmpty(roadNameList)) {
                    for (String roadName : roadNameList) {
                        opsDOLambdaQueryWrapperX.like(TrafficOpsElectricPoliceDO::getIntersectionName, roadName);
                    }
                }
            }
            opsDOLambdaQueryWrapperX.like(TrafficOpsElectricPoliceDO::getDirection, importExcel.getDirection());
            List<TrafficOpsElectricPoliceDO> electricPoliceDOList = trafficOpsElectricPoliceMapper.selectList(opsDOLambdaQueryWrapperX);
            if (CollectionUtils.isEmpty(electricPoliceDOList)) {
                importExcel.setFailReason("未找到对应的电警设备");
                failList.add(importExcel);
                importFailCnt.addAndGet(1);
                return;
            }

            if (electricPoliceDOList.size() > 1)  {
                importExcel.setFailReason("匹配到多个电警设备");
                failList.add(importExcel);
                importFailCnt.addAndGet(1);
                return;
            }

            importExcel.setEquipId(electricPoliceDOList.get(0).getId());
            importSucCnt.addAndGet(1);
            successList.add(importExcel);
        });

        // 批量更新
        LambdaUpdateWrapper<TrafficOpsElectricPoliceDO> updwrapper = new LambdaUpdateWrapper<>();
        updwrapper.set(TrafficOpsElectricPoliceDO::getStatus, OpsConstants.EQU_STATUS_GZ);
        updwrapper.in(TrafficOpsElectricPoliceDO::getId, successList.stream().map(EquipFaultImportExcelDTO::getEquipId).collect(Collectors.toList()));
        trafficOpsElectricPoliceMapper.update(updwrapper);
    }

    private void handleElectricFaultImportExcel(List<ElectricFaultImportExcelDTO> importExcelVOList, List<ElectricFaultImportExcelDTO> successList,
                                                List<ElectricFaultImportExcelDTO> failList, AtomicInteger importSucCnt, AtomicInteger importFailCnt) {
        // 数据处理
        List<TrafficOpsMaintenanceDO> maintenanceDOList = new ArrayList<>();
        List<String> signalIdList = new ArrayList<>();
        List<String> electricIdList = new ArrayList<>();
        importExcelVOList.forEach(importExcel -> {
            // 去除属性前后空格
            StrUtils.trimObjAttr(importExcel);

            // 1-1.校验数据
            try {
                ValidationUtils.validate(importExcel);
            } catch (ConstraintViolationException ex){
                importExcel.setFailReason(ex.getMessage());
                failList.add(importExcel);
                importFailCnt.addAndGet(1);
                return;
            }

//            if (StringUtils.isEmpty(importExcel.getEquipIP() )) {
//                importExcel.setFailReason("设备IP不能为空");
//                failList.add(importExcel);
//                importFailCnt.addAndGet(1);
//                return;
//            }
//
//            if (StringUtils.isEmpty(importExcel.getFaultType() )) {
//                importExcel.setFailReason("故障类型不能为空");
//                failList.add(importExcel);
//                importFailCnt.addAndGet(1);
//                return;
//            }
//
//            // 上报人
//            if (StringUtils.isEmpty(importExcel.getReportPersonnel() )) {
//                importExcel.setFailReason("上报人不能为空");
//                failList.add(importExcel);
//                importFailCnt.addAndGet(1);
//                return;
//            }

            // 查询人员ID
            Long reportPersionId = null;
            Long reportDeptId = null;
            LambdaQueryWrapperX<AdminUserDO> userLambdaQueryWrapperX = new LambdaQueryWrapperX<>();
            userLambdaQueryWrapperX.eq(AdminUserDO::getNickname, importExcel.getReportPersonnel());
            List<AdminUserDO> reportPersionList = adminUserMapper.selectList(userLambdaQueryWrapperX);
            if (!CollectionUtils.isEmpty(reportPersionList)) {
                reportPersionId = reportPersionList.get(0).getId();
                reportDeptId = reportPersionList.get(0).getDeptId();
            }

            Long maintenancePersonnelId = null;
            if (!StringUtils.isEmpty(importExcel.getMaintenancePersonnel())) {
                LambdaQueryWrapperX<AdminUserDO> maintenanceWrapper = new LambdaQueryWrapperX<>();
                maintenanceWrapper.eq(AdminUserDO::getNickname, importExcel.getMaintenancePersonnel());
                List<AdminUserDO> maintenancePersionList = adminUserMapper.selectList(userLambdaQueryWrapperX);
                if (!CollectionUtils.isEmpty(maintenancePersionList)) {
                    maintenancePersonnelId = maintenancePersionList.get(0).getId();
                }
            }


            Date reportDate = new Date();
            if (!StringUtils.isBlank(importExcel.getReportTime())) {
                try {
                    reportDate = DateUtil.parse(importExcel.getReportTime(), "yyyy-MM-dd HH:mm:ss");
                } catch (Exception e) {
                    importExcel.setFailReason("日期格式不正确");
                    failList.add(importExcel);
                    importFailCnt.addAndGet(1);
                    return;
                }
            }

            TrafficOpsMaintenanceDO maintenanceDO = new TrafficOpsMaintenanceDO();
            maintenanceDO.setReportUserId(reportPersionId + "");
            maintenanceDO.setEquAddr(importExcel.getEquAddr());
            maintenanceDO.setReportPersonnel(importExcel.getReportPersonnel());
            maintenanceDO.setReportDeptId(reportDeptId + "");
            maintenanceDO.setReportTime(DateUtil.toLocalDateTime(reportDate));
            maintenanceDO.setFaultDate(DateUtil.toLocalDateTime(reportDate));
            maintenanceDO.setAffiliatedUnit(OpsConstants.STRING_ONE);
            maintenanceDO.setSource(OpsConstants.STRING_ONE);
            if (maintenancePersonnelId != null) {
                maintenanceDO.setDispatchUserId(reportDeptId + "");
                maintenanceDO.setDispatch(importExcel.getReportPersonnel());
                maintenanceDO.setDispatchTime(DateUtil.toLocalDateTime(reportDate));

                maintenanceDO.setMaintenanceUserId(maintenancePersonnelId + "");
                maintenanceDO.setMaintenancePersonnel(importExcel.getMaintenancePersonnel());
                maintenanceDO.setStatus(OpsConstants.STATUS_ACCEPT_ORDER);
            } else {
                maintenanceDO.setStatus(OpsConstants.STATUS_PENDING_ORDERS);
            }

            maintenanceDO.setProblem(importExcel.getProblem());
            maintenanceDO.setFaultType(importExcel.getFaultType());
//            if (maintenancePersonnelId != null) {
//                maintenanceDO.setDispatchUserId(reportDeptId + "");
//                maintenanceDO.setDispatch(importExcel.getReportPersonnel());
//                maintenanceDO.setDispatchTime(DateUtil.toLocalDateTime(reportDate));
//                maintenanceDO.setStatus(OpsConstants.STATUS_IN_MAINTENANCE);
//            }

            if ("信控平台离线设备".equals(importExcel.getFaultType())) { // 查询信号机
                LambdaQueryWrapperX<TrafficOpsSignalDO> opsSignalDOLambdaQueryWrapperX = new LambdaQueryWrapperX<>();
                opsSignalDOLambdaQueryWrapperX.eq(TrafficOpsSignalDO::getIpAddr, importExcel.getEquipIP());
                List<TrafficOpsSignalDO> signalDOList = trafficOpsSignalMapper.selectList(opsSignalDOLambdaQueryWrapperX);
//                if (CollectionUtils.isEmpty(signalDOList)) {
//                    importExcel.setFailReason("未找到对应的信号机设备");
//                    failList.add(importExcel);
//                    importFailCnt.addAndGet(1);
//                    return;
//                }
//
//                if (signalDOList.size() > 1)  {
//                    importExcel.setFailReason("匹配到多个信号机设备");
//                    failList.add(importExcel);
//                    importFailCnt.addAndGet(1);
//                    return;
//                }
                if (!CollectionUtils.isEmpty(signalDOList)) {
                    maintenanceDO.setEquType(OpsConstants.EQU_TYPE_XHJ);
                    maintenanceDO.setEquId(signalDOList.get(0).getId());

                    signalIdList.add(signalDOList.get(0).getId());
                    importExcel.setEquipId(signalDOList.get(0).getId());
                }
            } else {
                LambdaQueryWrapperX<TrafficOpsElectricPoliceDO> opsDOLambdaQueryWrapperX = new LambdaQueryWrapperX<>();
                opsDOLambdaQueryWrapperX.eq(TrafficOpsElectricPoliceDO::getIpAddr, importExcel.getEquipIP());
                List<TrafficOpsElectricPoliceDO> electricPoliceDOList = trafficOpsElectricPoliceMapper.selectList(opsDOLambdaQueryWrapperX);
//                if (CollectionUtils.isEmpty(electricPoliceDOList)) {
//                    importExcel.setFailReason("未找到对应的电警设备");
//                    failList.add(importExcel);
//                    importFailCnt.addAndGet(1);
//                    return;
//                }
//
//                if (electricPoliceDOList.size() > 1)  {
//                    importExcel.setFailReason("匹配到多个电警设备");
//                    failList.add(importExcel);
//                    importFailCnt.addAndGet(1);
//                    return;
//                }

                if (!CollectionUtils.isEmpty(electricPoliceDOList)) {
                    maintenanceDO.setEquType(OpsConstants.EQU_TYPE_DJ);
                    maintenanceDO.setEquId(electricPoliceDOList.get(0).getId());

                    electricIdList.add(electricPoliceDOList.get(0).getId());
                    importExcel.setEquipId(electricPoliceDOList.get(0).getId());
                }
            }
            importSucCnt.addAndGet(1);
            successList.add(importExcel);
            maintenanceDOList.add(maintenanceDO);
        });

        // 批量更新
        if (!CollectionUtils.isEmpty(signalIdList)) {
            LambdaUpdateWrapper<TrafficOpsSignalDO> updwrapper = new LambdaUpdateWrapper<>();
            updwrapper.set(TrafficOpsSignalDO::getStatus, OpsConstants.EQU_STATUS_GZ);
            updwrapper.in(TrafficOpsSignalDO::getId, signalIdList);
            trafficOpsSignalMapper.update(updwrapper);
        }

        if (!CollectionUtils.isEmpty(electricIdList)) {
            LambdaUpdateWrapper<TrafficOpsElectricPoliceDO> updwrapper = new LambdaUpdateWrapper<>();
            updwrapper.set(TrafficOpsElectricPoliceDO::getStatus, OpsConstants.EQU_STATUS_GZ);
            updwrapper.in(TrafficOpsElectricPoliceDO::getId, electricIdList);
            trafficOpsElectricPoliceMapper.update(updwrapper);
        }

        if (!CollectionUtils.isEmpty(maintenanceDOList)) {
            trafficOpsMaintenanceMapper.insertBatch(maintenanceDOList);
        }

    }
}
