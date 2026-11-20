package com.holdtime.ops.module.kafka.handler.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.json.JSONUtil;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.infra.service.file.FileService;
import com.holdtime.ops.module.entity.*;
import com.holdtime.ops.module.entity.mapper.*;
import com.holdtime.ops.module.enums.OpsConstants;
import com.holdtime.ops.module.kafka.message.VideoNetBoundaryMessage;
import com.holdtime.ops.module.model.vo.TrafficOpsMaintainFlowRecordSaveReqVO;
import com.holdtime.ops.module.model.vo.TrafficOpsMaintainProgressSaveReqVO;
import com.holdtime.ops.module.model.vo.TrafficOpsMaintenanceConfirmSaveReqVO;
import com.holdtime.ops.module.model.vo.TrafficOpsMaintenanceSaveReqVO;
import com.holdtime.ops.module.service.TrafficOpsMaintainFlowRecordExtService;
import com.holdtime.ops.module.service.TrafficOpsMaintenanceExtService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;

@Component
@Slf4j
public class InternalPoliceMsgHandler {
    @Resource
    private PoliceOpsElectricPoliceMapper policeOpsElectricPoliceMapper;
    @Resource
    private PoliceMonitorPingDetailMapper policeMonitorPingDetailMapper;
    @Resource
    private TrafficOpsMaintenanceMapper trafficOpsMaintenanceMapper;
    @Resource
    private PublicEquipOnOffCheckResultMapper publicEquipOnOffCheckResultMapper;
    @Resource
    private TrafficOpsMaintainFlowRecordExtService opsMaintainFlowRecordExtService;
    @Resource
    private TrafficOpsMaintenanceExtService trafficOpsMaintenanceExtService;
    @Resource
    private PublicUserAreaGroupMapper publicUserAreaGroupMapper;
    @Resource
    private FileService fileService;

    public void handlerPingPoliceOpsElectricPoliceMsg(VideoNetBoundaryMessage message) {
        if (message == null || StringUtils.isBlank(message.getMsgData())) {
            return;
        }

        PoliceMonitorPingDetailDO pingDetailDO = JSONUtil.toBean(message.getMsgData(), PoliceMonitorPingDetailDO.class);
        if (pingDetailDO == null || pingDetailDO.getId() == null) {
            log.error("消息数据无效: {}", message.getMsgData());
            return;
        }

        // 优化：先尝试插入，利用数据库唯一索引做幂等性校验（减少一次查询）
        try {
            pingDetailDO.setUpdateTime(DateUtil.toLocalDateTime(new Date()));
            policeMonitorPingDetailMapper.insert(pingDetailDO);
        } catch (Exception e) {
            // 可能是唯一索引冲突，查询确认
            PoliceMonitorPingDetailDO exists = policeMonitorPingDetailMapper.selectById(pingDetailDO.getId());
            if (exists != null) {
                log.info("消息已处理（幂等性校验）, id: {}, equId: {}",
                    pingDetailDO.getId(), pingDetailDO.getEquId());
                return;
            }
            log.error("插入监控数据失败: {}", pingDetailDO, e);
            throw new RuntimeException("插入监控数据失败", e);
        }

        // 查询电警设备
        PoliceOpsElectricPoliceDO policeOpsElectricPoliceDO = policeOpsElectricPoliceMapper.selectById(pingDetailDO.getEquId());
        if (policeOpsElectricPoliceDO != null && "拆除暂时无法修复".contains(policeOpsElectricPoliceDO.getStatus())) {
            return;
        }

        // 异步处理维修单逻辑（不阻塞主流程）
        processMaintenanceAsync(pingDetailDO);
    }

    /**
     * 异步处理维修单逻辑
     */
    private void processMaintenanceAsync(PoliceMonitorPingDetailDO pingDetailDO) {
        try {
            if ("正常".equals(pingDetailDO.getPingStatus())) {
                handleNormalStatus(pingDetailDO);
            } else {
                handleAbnormalStatus(pingDetailDO);
            }
        } catch (Exception e) {
            log.error("处理维修单逻辑失败 equId: {}, pingStatus: {}",
                pingDetailDO.getEquId(), pingDetailDO.getPingStatus(), e);
            // 不抛出异常，避免影响主消息处理
        }
    }

    /**
     * 处理正常状态 - 自动完成维修单
     */
    private void handleNormalStatus(PoliceMonitorPingDetailDO pingDetailDO) {
        LambdaQueryWrapperX<TrafficOpsMaintenanceDO> lambdaQueryWrapperX = new LambdaQueryWrapperX<>();
        lambdaQueryWrapperX.eq(TrafficOpsMaintenanceDO::getEquId, pingDetailDO.getEquId());
        List<String> statusCondition = new ArrayList<>();
        statusCondition.add(OpsConstants.STATUS_PENDING_ORDERS);
        statusCondition.add(OpsConstants.STATUS_ACCEPT_ORDER);
        lambdaQueryWrapperX.in(TrafficOpsMaintenanceDO::getStatus, statusCondition);
        lambdaQueryWrapperX.orderByDesc(TrafficOpsMaintenanceDO::getCreateTime);

        List<TrafficOpsMaintenanceDO> maintenanceDOList = trafficOpsMaintenanceMapper.selectList(lambdaQueryWrapperX);
        if (CollectionUtils.isEmpty(maintenanceDOList)) {
            return;
        }

        String operateResult = "自动检测，恢复正常[系统检测]";

        for (TrafficOpsMaintenanceDO maintenanceDO : maintenanceDOList) {
            try {
                // 更新订单状态
                maintenanceDO.setStatus(OpsConstants.STATUS_COMPLETED);
                trafficOpsMaintenanceMapper.updateById(maintenanceDO);

                // 保存维修过程
                TrafficOpsMaintainProgressSaveReqVO progressSaveReqVO = new TrafficOpsMaintainProgressSaveReqVO();
                progressSaveReqVO.setAffiliatedUnit(OpsConstants.STRING_TWO);
                progressSaveReqVO.setMaintenanceId(maintenanceDO.getId());
                progressSaveReqVO.setMaintenance(operateResult);
                progressSaveReqVO.setMaintenancePersonnel("系统检测");
                progressSaveReqVO.setMaintenanceDate(pingDetailDO.getOccurrenceTime());
                trafficOpsMaintenanceExtService.saveMaintenanceProgress(progressSaveReqVO);

                // 保存流程
                TrafficOpsMaintainFlowRecordSaveReqVO opsMaintainFlowRecordSaveReqVO = new TrafficOpsMaintainFlowRecordSaveReqVO();
                opsMaintainFlowRecordSaveReqVO.setMaintenanceId(maintenanceDO.getId());
                opsMaintainFlowRecordSaveReqVO.setFlowType(OpsConstants.FLOW_TYPE_AUTO_FINISH);
                opsMaintainFlowRecordSaveReqVO.setOperateStatus(maintenanceDO.getStatus());
                opsMaintainFlowRecordSaveReqVO.setOperateResult(operateResult);
                opsMaintainFlowRecordSaveReqVO.setUserId(427L);
                opsMaintainFlowRecordExtService.saveMaintainFlowRecord(opsMaintainFlowRecordSaveReqVO);

                log.debug("自动完成维修单 success, maintainId: {}", maintenanceDO.getId());
            } catch (Exception e) {
                log.error("处理维修单失败 maintainId: {}", maintenanceDO.getId(), e);
            }
        }
    }

    /**
     * 处理异常状态 - 创建维修单
     */
    private void handleAbnormalStatus(PoliceMonitorPingDetailDO pingDetailDO) {
        // 检查是否已存在维修单
        String maintainId = trafficOpsMaintenanceExtService.queryOrderByIp(
            pingDetailDO.getEquId(), pingDetailDO.getIpAddr(), OpsConstants.STRING_TWO);
        if (StringUtils.isNotBlank(maintainId)) {
            return;
        }

        PoliceOpsElectricPoliceDO policeOpsElectricPoliceDO = policeOpsElectricPoliceMapper.selectById(pingDetailDO.getEquId());
        if (policeOpsElectricPoliceDO == null) {
            log.error("电警信息已不存在！equId: {}", pingDetailDO.getEquId());
            return;
        }

        TrafficOpsMaintenanceSaveReqVO saveReqVO = new TrafficOpsMaintenanceSaveReqVO();
        saveReqVO.setAffiliatedUnit(OpsConstants.STRING_TWO);
        saveReqVO.setEquType("电警");
        saveReqVO.setEquId(pingDetailDO.getEquId());
        saveReqVO.setEquAddr(policeOpsElectricPoliceDO.getAdress());
        saveReqVO.setEquLng(policeOpsElectricPoliceDO.getLng());
        saveReqVO.setEquLat(policeOpsElectricPoliceDO.getLat());
        saveReqVO.setProblem("自动通断检测，设备【" + policeOpsElectricPoliceDO.getIpAddr() + "】IP不可达，ping" + pingDetailDO.getPingCnt() + "次，丢包率为：" + pingDetailDO.getLossRate());
        saveReqVO.setFaultType("电警不在线");
        saveReqVO.setFaultDate(Date.from(pingDetailDO.getOccurrenceTime().atZone(ZoneId.systemDefault()).toInstant()));
        saveReqVO.setReportPersonnel("自动工单用户");
        saveReqVO.setReportUserId("427");
        saveReqVO.setDispatchUserId("427");
        saveReqVO.setDispatch("自动工单用户");
        saveReqVO.setReportTime(pingDetailDO.getOccurrenceTime());

        // 查询派单人员
        LambdaQueryWrapperX<PublicUserAreaGroupDO> userAreaGroupDOLambdaQueryWrapperX = new LambdaQueryWrapperX<>();
        userAreaGroupDOLambdaQueryWrapperX.eq(PublicUserAreaGroupDO::getAffiliatedUnit, OpsConstants.STRING_TWO);
        userAreaGroupDOLambdaQueryWrapperX.like(PublicUserAreaGroupDO::getGroupArea, policeOpsElectricPoliceDO.getUnit());
        List<PublicUserAreaGroupDO> userAreaGroupDOList = publicUserAreaGroupMapper.selectList(userAreaGroupDOLambdaQueryWrapperX);
        if (!CollectionUtils.isEmpty(userAreaGroupDOList)) {
            saveReqVO.setMaintenanceUserId(userAreaGroupDOList.get(0).getLeadUserId() + "");
            saveReqVO.setMaintenancePersonnel(userAreaGroupDOList.get(0).getLeadUserName());
            saveReqVO.setDispatchTime(pingDetailDO.getOccurrenceTime());
        }

        trafficOpsMaintenanceExtService.saveOpsMaintenance(saveReqVO, OpsConstants.STRING_THREE);
        log.debug("创建维修单 success, equId: {}", pingDetailDO.getEquId());
    }


    public void handlerPoliceEquConfirmResultMsg(VideoNetBoundaryMessage message) {
        if (message == null || StringUtils.isBlank(message.getMsgData())) {
            return;
        }

        PublicEquipOnOffCheckResultDO publicEquipOnOffCheckResultDO = JSONUtil.toBean(message.getMsgData(), PublicEquipOnOffCheckResultDO.class);
        if (publicEquipOnOffCheckResultDO == null || publicEquipOnOffCheckResultDO.getId() == null) {
            log.error("消息数据无效: {}", message.getMsgData());
            return;
        }

        if (StringUtils.isNotBlank(publicEquipOnOffCheckResultDO.getResultImg())) {
            String filePath = uploadImgFile(publicEquipOnOffCheckResultDO.getResultImg());
            publicEquipOnOffCheckResultDO.setResultImg(filePath);
        }
        publicEquipOnOffCheckResultMapper.updateById(publicEquipOnOffCheckResultDO);

        if (StringUtils.isNotBlank(publicEquipOnOffCheckResultDO.getMaintenanceId()) && OpsConstants.STRING_ONE.equals(publicEquipOnOffCheckResultDO.getCheckType())) { // 是维修单
            if ("正常".equals(publicEquipOnOffCheckResultDO.getPingStatus())) {
                if (StringUtils.isBlank(publicEquipOnOffCheckResultDO.getEquCode()) || "正常".equals(publicEquipOnOffCheckResultDO.getVideoStatus())) {
                    // 工单确认
                    TrafficOpsMaintenanceConfirmSaveReqVO maintenanceConfirmSaveReqVO = new TrafficOpsMaintenanceConfirmSaveReqVO();
                    maintenanceConfirmSaveReqVO.setId(publicEquipOnOffCheckResultDO.getMaintenanceId());
                    maintenanceConfirmSaveReqVO.setStatus(OpsConstants.STATUS_COMPLETED);
                    maintenanceConfirmSaveReqVO.setRemark("设备检测，已修复！");
                    trafficOpsMaintenanceExtService.confirmRepairOrder(maintenanceConfirmSaveReqVO, Long.parseLong(publicEquipOnOffCheckResultDO.getCheckUserId()));
                }
            }
        }
    }

    private String uploadImgFile(String base64Img) {
        // 将Hex字符串转换为字节数组
        byte[] imageBytes = Base64.getDecoder().decode(base64Img);
        String fileName = "wvp_check_" + System.currentTimeMillis() + ".png";
        return fileService.createFile(fileName, null, imageBytes);
    }
}
