package com.holdtime.ops.module.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.json.JSONUtil;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.holdtime.ops.module.entity.*;
import com.holdtime.ops.module.entity.mapper.*;
import com.holdtime.ops.module.enums.OpsConstants;
import com.holdtime.ops.module.enums.OpsErrorCodeConstants;
import com.holdtime.ops.module.kafka.producer.BoundaryMsgProducer;
import com.holdtime.ops.module.model.vo.PoliceMonitorPingDetailExtRespVO;
import com.holdtime.ops.module.service.PoliceMonitorPingExtService;
import com.holdtime.ops.module.utils.PingUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;

/**
 * 公安设备监测结果 Service 实现类
 *
 * @author 管理员
 */
@Service
@Validated
public class PoliceMonitorPingExtServiceImpl implements PoliceMonitorPingExtService {
    @Resource
    private PoliceOpsElectricPoliceMapper policeOpsElectricPoliceMapper;
    @Resource
    private PoliceMonitorPingDetailMapper policeMonitorPingDetailMapper;
    @Resource
    private PoliceMonitorPingAnalysisMapper policeMonitorPingAnalysisMapper;
    @Resource
    private ViewPoliceMonitorPingDetailAnalysisMapper viewPoliceMonitorPingDetailAnalysisMapper;
    @Resource
    private ObjectProvider<BoundaryMsgProducer> boundaryMsgProducerObjectProvider;

    @Override
    public PoliceMonitorPingDetailExtRespVO pingPoliceOpsElectricPolice(String equId, String source) {
        if (StringUtils.isBlank(equId)) {
            throw exception(OpsErrorCodeConstants.SYSTEM_COMMON_ERROR_PARAM_MISSING);
        }

        PoliceOpsElectricPoliceDO policeDO = policeOpsElectricPoliceMapper.selectById(equId);
        if  (policeDO == null) {
            throw exception(OpsErrorCodeConstants.POLICE_OPS_ELECTRIC_POLICE_NOT_EXISTS);
        }

        return  pingPoliceOpsElectricPolice(policeDO, null, source);
    }

    @Override
    public PoliceMonitorPingDetailExtRespVO pingPoliceOpsElectricPolice(PoliceOpsElectricPoliceDO policeOpsElectricPoliceDO, LocalDateTime occurrenceTime, String source) {
        if  (policeOpsElectricPoliceDO == null) {
            throw exception(OpsErrorCodeConstants.POLICE_OPS_ELECTRIC_POLICE_NOT_EXISTS);
        }

        if (occurrenceTime == null) {
            occurrenceTime = DateUtil.toLocalDateTime(new Date());
        }

        int pingCnt = 10;
        PingUtil.PingResult pingResult = PingUtil.pingWithLossRate(policeOpsElectricPoliceDO.getIpAddr(), 1000, pingCnt);
        PoliceMonitorPingDetailDO pingDetailDO = new PoliceMonitorPingDetailDO();
        pingDetailDO.setEquId(policeOpsElectricPoliceDO.getId());
        pingDetailDO.setIpAddr(policeOpsElectricPoliceDO.getIpAddr());
        pingDetailDO.setEquLable(policeOpsElectricPoliceDO.getEquLable());
        pingDetailDO.setOccurrenceTime(occurrenceTime);
        pingDetailDO.setPingStatus(pingResult.isReachable() ? "正常" : "IP不可达");
        pingDetailDO.setPingCnt(pingCnt);
        pingDetailDO.setLossRate(BigDecimal.valueOf(pingResult.getLossRate()));
        pingDetailDO.setSource(source);
        policeMonitorPingDetailMapper.insert(pingDetailDO);

        // 推送公安通断检测 (异步发送)
        sendPingToKafka(pingDetailDO);

        // 分析电警监控结果
//        analysisMonitorPing(policeOpsElectricPoliceDO.getId());

        return BeanUtils.toBean(pingDetailDO, PoliceMonitorPingDetailExtRespVO.class);
    }

    @Override
    public void analysisMonitorPing(String equId) {
        if (StringUtils.isBlank(equId)) {
            return;
        }

        LambdaQueryWrapperX<PoliceMonitorPingAnalysisDO> pingAnalysisDOLambdaQueryWrapperX = new LambdaQueryWrapperX<>();
        pingAnalysisDOLambdaQueryWrapperX.eq(PoliceMonitorPingAnalysisDO::getEquId, equId);
        List<PoliceMonitorPingAnalysisDO> pingAnalysisDOList = policeMonitorPingAnalysisMapper.selectList(pingAnalysisDOLambdaQueryWrapperX);

        PoliceMonitorPingAnalysisDO pingAnalysisDO = CollectionUtils.isEmpty(pingAnalysisDOList) ? new PoliceMonitorPingAnalysisDO() : pingAnalysisDOList.get(0);
        pingAnalysisDO.setUpdateTime(DateUtil.toLocalDateTime(new Date()));
        pingAnalysisDO.setEquId(equId);
//        pingAnalysisDO.setEquLable(policeDO.getEquLable());

        // 查询ping详情信息
        LambdaQueryWrapperX<PoliceMonitorPingDetailDO> pingDetailLambdaQueryWrapperX = new LambdaQueryWrapperX<>();
        pingDetailLambdaQueryWrapperX.eq(PoliceMonitorPingDetailDO::getEquId, equId);
        List<PoliceMonitorPingDetailDO> pingDetailDOList = policeMonitorPingDetailMapper.selectList(pingDetailLambdaQueryWrapperX);
        if (CollectionUtils.isEmpty(pingDetailDOList)) {
            return;
        }
        Map<String, List<PoliceMonitorPingDetailDO>> autoPingMap = pingDetailDOList.stream().filter(p -> OpsConstants.STRING_ONE.equals(p.getSource())).collect(Collectors.groupingBy(PoliceMonitorPingDetailDO::getPingStatus));
        if (!CollectionUtils.isEmpty(autoPingMap)) {
            int pingNormalCnt = autoPingMap.get("正常") == null ? 0 : autoPingMap.get("正常").size();
            int pingAbnormalCnt = autoPingMap.get("IP不可达") == null ? 0 : autoPingMap.get("IP不可达").size();
            pingAnalysisDO.setAutoPingNormalCnt(pingNormalCnt);
            pingAnalysisDO.setAutoPingAbnormalCnt(pingAbnormalCnt);
            pingAnalysisDO.setAutoMonitorCnt(pingNormalCnt + pingAbnormalCnt);
            pingAnalysisDO.setAutoPingHealthRate(new BigDecimal(pingNormalCnt).divide(new BigDecimal(pingAbnormalCnt == 0 ? 1 : pingAbnormalCnt), 4, RoundingMode.HALF_UP));
        } else {
            pingAnalysisDO.setAutoPingNormalCnt(0);
            pingAnalysisDO.setAutoPingAbnormalCnt(0);
            pingAnalysisDO.setAutoMonitorCnt(0);
            pingAnalysisDO.setAutoPingHealthRate(BigDecimal.ZERO);
        }

        Map<String, List<PoliceMonitorPingDetailDO>> pingMap = pingDetailDOList.stream().filter(p -> OpsConstants.STRING_TWO.equals(p.getSource())).collect(Collectors.groupingBy(PoliceMonitorPingDetailDO::getPingStatus));
        if (!CollectionUtils.isEmpty(pingMap)) {
            int pingNormalCnt = pingMap.get("正常") == null ? 0 : pingMap.get("正常").size();
            int pingAbnormalCnt = pingMap.get("IP不可达") == null ? 0 : pingMap.get("IP不可达").size();
            pingAnalysisDO.setPingNormalCnt(pingNormalCnt);
            pingAnalysisDO.setPingAbnormalCnt(pingAbnormalCnt);
            pingAnalysisDO.setMonitorCnt(pingNormalCnt + pingAbnormalCnt);
            pingAnalysisDO.setPingHealthRate(new BigDecimal(pingNormalCnt).divide(new BigDecimal(pingAbnormalCnt == 0 ? 1 : pingAbnormalCnt), 4, RoundingMode.HALF_UP));
        } else {
            pingAnalysisDO.setPingNormalCnt(0);
            pingAnalysisDO.setPingAbnormalCnt(0);
            pingAnalysisDO.setMonitorCnt(0);
            pingAnalysisDO.setPingHealthRate(BigDecimal.ZERO);
        }

        if (StringUtils.isBlank(pingAnalysisDO.getId())) {
            policeMonitorPingAnalysisMapper.insert(pingAnalysisDO);
        } else {
            policeMonitorPingAnalysisMapper.updateById(pingAnalysisDO);
        }
    }

    @Override
    public void analysisMonitorPing() {
        List<ViewPoliceMonitorPingDetailAnalysisDO> pingDetailAnalysisDOList =  viewPoliceMonitorPingDetailAnalysisMapper.selectList();
        if (CollectionUtils.isEmpty(pingDetailAnalysisDOList)) {
            return;
        }

        List<PoliceMonitorPingAnalysisDO> pingAnalysisDOList = policeMonitorPingAnalysisMapper.selectList();
        Map<String, PoliceMonitorPingAnalysisDO> autoPingMap = Optional.ofNullable(pingAnalysisDOList).orElse(new ArrayList<>()).stream().collect(Collectors.toMap(PoliceMonitorPingAnalysisDO::getEquId, Function.identity()));

        List<PoliceMonitorPingAnalysisDO> instList = new ArrayList<>();
        List<PoliceMonitorPingAnalysisDO> updList = new ArrayList<>();
        for (ViewPoliceMonitorPingDetailAnalysisDO detailDO : pingDetailAnalysisDOList) {
            PoliceMonitorPingAnalysisDO analysisDO = autoPingMap.get(detailDO.getEquId());
            if (analysisDO == null) {
                analysisDO = new PoliceMonitorPingAnalysisDO();
                analysisDO.setEquId(detailDO.getEquId());
            }

            analysisDO.setAutoPingNormalCnt(detailDO.getAutoNormalCnt() == null ? 0 : detailDO.getAutoNormalCnt().intValue());
            analysisDO.setAutoPingAbnormalCnt(detailDO.getAutoAbnormalCnt() == null ? 0 : detailDO.getAutoAbnormalCnt().intValue());
            analysisDO.setAutoMonitorCnt(detailDO.getAutoMonitorCnt() == null ? 0 : detailDO.getAutoMonitorCnt().intValue());
            analysisDO.setAutoPingHealthRate(detailDO.getAutoHealthRate());
            analysisDO.setPingNormalCnt(detailDO.getNormalCnt() == null ? 0 : detailDO.getNormalCnt().intValue());
            analysisDO.setPingAbnormalCnt(detailDO.getAbnormalCnt() == null ? 0 : detailDO.getAbnormalCnt().intValue());
            analysisDO.setMonitorCnt(detailDO.getMonitorCnt() == null ? 0 : detailDO.getMonitorCnt().intValue());
            analysisDO.setPingHealthRate(detailDO.getHealthRate());
            if (StringUtils.isBlank(analysisDO.getId())) {
                instList.add(analysisDO);
            } else {
                updList.add(analysisDO);
            }
        }

        if (!CollectionUtils.isEmpty(instList)) {
            policeMonitorPingAnalysisMapper.insert(instList);
        }

        if (!CollectionUtils.isEmpty(updList)) {
            policeMonitorPingAnalysisMapper.updateById(updList);
        }
    }

    @Override
    public void insertPingDetail(PoliceMonitorPingDetailDO pingDetail) {
        if (pingDetail == null) {
            return;
        }
        policeMonitorPingDetailMapper.insert(pingDetail);
    }

    @Override
    public void sendPingToKafka(PoliceMonitorPingDetailDO pingDetail) {
        if (pingDetail == null) {
            return;
        }
        // 推送公安通断检测 - 异步发送，不阻塞
        boundaryMsgProducerObjectProvider.ifAvailable(boundaryMsgProducer ->
            boundaryMsgProducer.batchSendMessageFromVideoToInternet("POLICE_EQU_PING_MONITOR", "PING_ADD", JSONUtil.toJsonStr(pingDetail))
        );
    }
}