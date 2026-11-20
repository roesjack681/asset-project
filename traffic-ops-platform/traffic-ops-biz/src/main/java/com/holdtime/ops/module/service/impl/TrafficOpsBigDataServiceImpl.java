package com.holdtime.ops.module.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.holdtime.ops.module.entity.*;
import com.holdtime.ops.module.entity.mapper.*;
import com.holdtime.ops.module.enums.OpsConstants;
import com.holdtime.ops.module.mapper.OpsAnalysisExtMapper;
import com.holdtime.ops.module.mapper.OpsMaintenanceExtMapper;
import com.holdtime.ops.module.model.dto.TrafficOpsHealthRateDTO;
import com.holdtime.ops.module.model.dto.TrafficOpsMaintenanceRankDTO;
import com.holdtime.ops.module.model.vo.*;
import com.holdtime.ops.module.service.TrafficOpsBigDataService;
import com.holdtime.ops.module.utils.CoordinateUtils;
import com.holdtime.ops.module.utils.HanLPUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.annotation.Validated;
import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 设备维保信息 Service 实现类
 *
 * @author 芋道源码
 */
@Service
@Validated
public class TrafficOpsBigDataServiceImpl implements TrafficOpsBigDataService {

    @Resource
    private TrafficOpsRoadMapper opsRoadMapper;
    @Resource
    private TrafficOpsIntersectionMapper opsIntersectionMapper;
    @Resource
    private TrafficOpsElectricPoliceMapper opsElectricPoliceMapper;
    @Resource
    private TrafficOpsSignalMapper opsSignalMapper;
    @Resource
    private TrafficOpsBigledMapper opsBigledMapper;
    @Resource
    private TrafficOpsMemberMapper opsMemberMapper;
    @Resource
    private TrafficOpsSignalLightMapper opsSignalLightMapper;
    @Resource
    private TrafficOpsMarklineMapper opsMarklineMapper;
    @Resource
    private TrafficOpsSignBoardMapper opsSignBoardMapper;
    @Resource
    private TrafficOpsRadarMapper opsRadarMapper;
    @Resource
    private TrafficOpsGuardrailMapper opsGuardrailMapper;
    @Resource
    private TrafficOpsMaintenanceMapper opsMaintenanceMapper;
    @Resource
    private OpsMaintenanceExtMapper opsMaintenanceExtMapper;
    @Resource
    private OpsAnalysisExtMapper opsAnalysisExtMapper;
    @Resource
    private TrafficMonitorResultsImportAnalysisMapper trafficMonitorResultsImportAnalysisMapper;

    @Override
    public TrafficOpsBigDataBaseRespVO getOpsBigDataBase(String jck, String dominion, String status) {
        TrafficOpsBigDataBaseRespVO respVo = new TrafficOpsBigDataBaseRespVO();

        long roadNum = 1L;
        if (StringUtils.isEmpty(jck)) {
            roadNum = opsRoadMapper.selectCount();
        }
        respVo.setRoadNum(roadNum + "");

        List<String> roadNameList = HanLPUtils.segmentForRoad(jck);
        LambdaQueryWrapperX<TrafficOpsIntersectionDO> intersectionDOLambdaQueryWrapperX = new LambdaQueryWrapperX<>();
        if (!CollectionUtils.isEmpty(roadNameList)) {
            for (String roadName : roadNameList) {
                intersectionDOLambdaQueryWrapperX.like(TrafficOpsIntersectionDO::getLdmc, roadName);
            }
        }
        long intersectionNum = opsIntersectionMapper.selectCount(intersectionDOLambdaQueryWrapperX);

        respVo.setIntersectionNum(intersectionNum + "");

        LambdaQueryWrapperX<TrafficOpsElectricPoliceDO> electricPoliceDOLambdaQueryWrapperX = new LambdaQueryWrapperX<>();
        if (!CollectionUtils.isEmpty(roadNameList)) {
            for (String roadName : roadNameList) {
                electricPoliceDOLambdaQueryWrapperX.like(TrafficOpsElectricPoliceDO::getIntersectionName, roadName);
            }
        }
        electricPoliceDOLambdaQueryWrapperX.likeIfPresent(TrafficOpsElectricPoliceDO::getDominion, dominion);
        electricPoliceDOLambdaQueryWrapperX.eqIfPresent(TrafficOpsElectricPoliceDO::getStatus, status);
        long electricPoliceNum = opsElectricPoliceMapper.selectCount(electricPoliceDOLambdaQueryWrapperX);
        respVo.setElectricPoliceNum(electricPoliceNum + "");
        if (StringUtils.isEmpty(status)) {
            List<TrafficOpsElectricPoliceDO> list = opsElectricPoliceMapper.selectList(electricPoliceDOLambdaQueryWrapperX);
            Map<String, Long> statusMap = Optional.ofNullable(list).orElse(new ArrayList<>()).stream().filter(s -> StringUtils.isNotBlank(s.getStatus())).collect(Collectors.groupingBy(TrafficOpsElectricPoliceDO::getStatus, Collectors.counting()));
            if (!CollectionUtils.isEmpty(statusMap)) {
                respVo.setElectricPoliceZNum(statusMap.get(OpsConstants.EQU_STATUS_ZC) + "");
                respVo.setElectricPoliceWNum(statusMap.get(OpsConstants.EQU_STATUS_WXZ) + "");
                respVo.setElectricPoliceGNum(statusMap.get(OpsConstants.EQU_STATUS_GZ) + "");
            }
        } else {
            if (status.equals(OpsConstants.EQU_STATUS_ZC)) {
                electricPoliceDOLambdaQueryWrapperX.eq(TrafficOpsElectricPoliceDO::getStatus, OpsConstants.EQU_STATUS_ZC);
                long electricPoliceZNum = opsElectricPoliceMapper.selectCount(electricPoliceDOLambdaQueryWrapperX);
                respVo.setElectricPoliceZNum(electricPoliceZNum + "");
                respVo.setElectricPoliceWNum("0");
                respVo.setElectricPoliceGNum("0");
            } else if (status.equals(OpsConstants.EQU_STATUS_WXZ)) {
                electricPoliceDOLambdaQueryWrapperX.eq(TrafficOpsElectricPoliceDO::getStatus, OpsConstants.EQU_STATUS_WXZ);
                long electricPoliceWNum = opsElectricPoliceMapper.selectCount(electricPoliceDOLambdaQueryWrapperX);
                respVo.setElectricPoliceZNum("0");
                respVo.setElectricPoliceWNum(electricPoliceWNum + "");
                respVo.setElectricPoliceGNum("0");
            } else {
                electricPoliceDOLambdaQueryWrapperX.eq(TrafficOpsElectricPoliceDO::getStatus, OpsConstants.EQU_STATUS_GZ);
                long electricPoliceGNum = opsElectricPoliceMapper.selectCount(electricPoliceDOLambdaQueryWrapperX);
                respVo.setElectricPoliceZNum("0");
                respVo.setElectricPoliceWNum("0");
                respVo.setElectricPoliceGNum(electricPoliceGNum + "");
            }
        }


        LambdaQueryWrapperX<TrafficOpsSignalDO> signalDOLambdaQueryWrapperX = new LambdaQueryWrapperX<>();
        if (!CollectionUtils.isEmpty(roadNameList)) {
            for (String roadName : roadNameList) {
                signalDOLambdaQueryWrapperX.like(TrafficOpsSignalDO::getIntersectionName, roadName);
            }
        }
        signalDOLambdaQueryWrapperX.likeIfPresent(TrafficOpsSignalDO::getDominion, dominion);
        signalDOLambdaQueryWrapperX.eqIfPresent(TrafficOpsSignalDO::getStatus, status);
        long signalNum = opsSignalMapper.selectCount(signalDOLambdaQueryWrapperX);
        respVo.setSignalNum(signalNum + "");
        if (StringUtils.isEmpty(status)) {
            List<TrafficOpsSignalDO> list = opsSignalMapper.selectList(signalDOLambdaQueryWrapperX);
            Map<String, Long> statusMap = Optional.ofNullable(list).orElse(new ArrayList<>()).stream().filter(s -> StringUtils.isNotBlank(s.getStatus())).collect(Collectors.groupingBy(TrafficOpsSignalDO::getStatus, Collectors.counting()));
            if (!CollectionUtils.isEmpty(statusMap)) {
                respVo.setSignalZNum(statusMap.get(OpsConstants.EQU_STATUS_ZC) + "");
                respVo.setSignalWNum(statusMap.get(OpsConstants.EQU_STATUS_WXZ) + "");
                respVo.setSignalGNum(statusMap.get(OpsConstants.EQU_STATUS_GZ) + "");
            }
        } else {
            if (status.equals(OpsConstants.EQU_STATUS_ZC)) {
                signalDOLambdaQueryWrapperX.eq(TrafficOpsSignalDO::getStatus, OpsConstants.EQU_STATUS_ZC);
                long signalZNum = opsSignalMapper.selectCount(signalDOLambdaQueryWrapperX);
                respVo.setSignalZNum(signalZNum + "");
                respVo.setSignalWNum("0");
                respVo.setSignalGNum("0");
            } else if (status.equals(OpsConstants.EQU_STATUS_WXZ)) {
                signalDOLambdaQueryWrapperX.eq(TrafficOpsSignalDO::getStatus, OpsConstants.EQU_STATUS_WXZ);
                long signalWNum = opsSignalMapper.selectCount(signalDOLambdaQueryWrapperX);
                respVo.setSignalZNum("0");
                respVo.setSignalWNum(signalWNum + "");
                respVo.setSignalGNum("0");
            } else {
                signalDOLambdaQueryWrapperX.eq(TrafficOpsSignalDO::getStatus, OpsConstants.EQU_STATUS_GZ);
                long signalGNum = opsSignalMapper.selectCount(signalDOLambdaQueryWrapperX);
                respVo.setSignalZNum("0");
                respVo.setSignalWNum("0");
                respVo.setSignalGNum(signalGNum + "");
            }
        }


        LambdaQueryWrapperX<TrafficOpsBigledDO> bigledDOLambdaQueryWrapperX = new LambdaQueryWrapperX<>();
        if (!CollectionUtils.isEmpty(roadNameList)) for (String roadName : roadNameList) {
            bigledDOLambdaQueryWrapperX.like(TrafficOpsBigledDO::getIntersectionName, roadName);
        }
        bigledDOLambdaQueryWrapperX.likeIfPresent(TrafficOpsBigledDO::getDominion, dominion);
        bigledDOLambdaQueryWrapperX.eqIfPresent(TrafficOpsBigledDO::getStatus, status);
        long bigLedNum = opsBigledMapper.selectCount(bigledDOLambdaQueryWrapperX);
        respVo.setBigLedNum(bigLedNum + "");
        if (StringUtils.isEmpty(status)) {
            List<TrafficOpsBigledDO> list = opsBigledMapper.selectList(bigledDOLambdaQueryWrapperX);
            Map<String, Long> statusMap = Optional.ofNullable(list).orElse(new ArrayList<>()).stream().filter(s -> StringUtils.isNotBlank(s.getStatus())).collect(Collectors.groupingBy(TrafficOpsBigledDO::getStatus, Collectors.counting()));
            if (!CollectionUtils.isEmpty(statusMap)) {
                respVo.setBigLedZNum(statusMap.get(OpsConstants.EQU_STATUS_ZC) + "");
                respVo.setBigLedWNum(statusMap.get(OpsConstants.EQU_STATUS_WXZ) + "");
                respVo.setBigLedGNum(statusMap.get(OpsConstants.EQU_STATUS_GZ) + "");
            }
        } else {
            if (status.equals(OpsConstants.EQU_STATUS_ZC)) {
                bigledDOLambdaQueryWrapperX.eq(TrafficOpsBigledDO::getStatus, OpsConstants.EQU_STATUS_ZC);
                long bigLedZNum = opsBigledMapper.selectCount(bigledDOLambdaQueryWrapperX);
                respVo.setBigLedZNum(bigLedZNum + "");
                respVo.setBigLedWNum("0");
                respVo.setBigLedGNum("0");
            } else if (status.equals(OpsConstants.EQU_STATUS_WXZ)) {
                bigledDOLambdaQueryWrapperX.eq(TrafficOpsBigledDO::getStatus, OpsConstants.EQU_STATUS_WXZ);
                long bigLedWNum = opsBigledMapper.selectCount(bigledDOLambdaQueryWrapperX);
                respVo.setBigLedZNum("0");
                respVo.setBigLedWNum(bigLedWNum + "");
                respVo.setBigLedGNum("0");
            } else {
                bigledDOLambdaQueryWrapperX.eq(TrafficOpsBigledDO::getStatus, OpsConstants.EQU_STATUS_GZ);
                long bigLedGNum = opsBigledMapper.selectCount(bigledDOLambdaQueryWrapperX);
                respVo.setBigLedZNum("0");
                respVo.setBigLedWNum("0");
                respVo.setBigLedGNum(bigLedGNum + "");
            }
        }

        LambdaQueryWrapperX<TrafficOpsMemberDO> memberDOLambdaQueryWrapperX = new LambdaQueryWrapperX<>();
        if (!CollectionUtils.isEmpty(roadNameList)) {
            for (String roadName : roadNameList) {
                memberDOLambdaQueryWrapperX.like(TrafficOpsMemberDO::getIntersectionName, roadName);
            }
        }
        memberDOLambdaQueryWrapperX.likeIfPresent(TrafficOpsMemberDO::getDominion, dominion);
        memberDOLambdaQueryWrapperX.eqIfPresent(TrafficOpsMemberDO::getStatus, status);
        long memberNum = opsMemberMapper.selectCount(memberDOLambdaQueryWrapperX);
        respVo.setMemberNum(memberNum + "");
        if (StringUtils.isEmpty(status)) {
            List<TrafficOpsMemberDO> list = opsMemberMapper.selectList(memberDOLambdaQueryWrapperX);
            Map<String, Long> statusMap = Optional.ofNullable(list).orElse(new ArrayList<>()).stream().filter(s -> StringUtils.isNotBlank(s.getStatus())).collect(Collectors.groupingBy(TrafficOpsMemberDO::getStatus, Collectors.counting()));
            if (!CollectionUtils.isEmpty(statusMap)) {
                respVo.setMemberZNum(statusMap.get(OpsConstants.EQU_STATUS_ZC) + "");
                respVo.setMemberWNum(statusMap.get(OpsConstants.EQU_STATUS_WXZ) + "");
                respVo.setMemberGNum(statusMap.get(OpsConstants.EQU_STATUS_GZ) + "");
            }
        } else {
            if (status.equals(OpsConstants.EQU_STATUS_ZC)) {
                memberDOLambdaQueryWrapperX.eq(TrafficOpsMemberDO::getStatus, OpsConstants.EQU_STATUS_ZC);
                long memberZNum = opsMemberMapper.selectCount(memberDOLambdaQueryWrapperX);
                respVo.setMemberZNum(memberZNum + "");
                respVo.setMemberWNum("0");
                respVo.setMemberGNum("0");
            } else if (status.equals(OpsConstants.EQU_STATUS_WXZ)) {
                memberDOLambdaQueryWrapperX.eq(TrafficOpsMemberDO::getStatus, OpsConstants.EQU_STATUS_WXZ);
                long memberWNum = opsMemberMapper.selectCount(memberDOLambdaQueryWrapperX);
                respVo.setMemberZNum("0");
                respVo.setMemberWNum(memberWNum + "");
                respVo.setMemberGNum("0");
            } else {
                memberDOLambdaQueryWrapperX.eq(TrafficOpsMemberDO::getStatus, OpsConstants.EQU_STATUS_GZ);
                long memberGNum = opsMemberMapper.selectCount(memberDOLambdaQueryWrapperX);
                respVo.setMemberZNum("0");
                respVo.setMemberWNum("0");
                respVo.setMemberGNum(memberGNum + "");
            }
        }

        //信号灯
        LambdaQueryWrapperX<TrafficOpsSignalLightDO> signalLightDOLambdaQueryWrapperX = new LambdaQueryWrapperX<>();
        if (!CollectionUtils.isEmpty(roadNameList)) {
            for (String roadName : roadNameList) {
                signalLightDOLambdaQueryWrapperX.like(TrafficOpsSignalLightDO::getIntersectionName, roadName);
            }
        }
        signalLightDOLambdaQueryWrapperX.likeIfPresent(TrafficOpsSignalLightDO::getDominion, dominion);
        signalLightDOLambdaQueryWrapperX.eqIfPresent(TrafficOpsSignalLightDO::getStatus, status);
        long signalLightNum = opsSignalLightMapper.selectCount(signalLightDOLambdaQueryWrapperX);
        respVo.setSignalLightNum(signalLightNum + "");
        if (StringUtils.isEmpty(status)) {
            List<TrafficOpsSignalLightDO> list = opsSignalLightMapper.selectList(signalLightDOLambdaQueryWrapperX);
            Map<String, Long> statusMap = Optional.ofNullable(list).orElse(new ArrayList<>()).stream().filter(s -> StringUtils.isNotBlank(s.getStatus())).collect(Collectors.groupingBy(TrafficOpsSignalLightDO::getStatus, Collectors.counting()));
            if (!CollectionUtils.isEmpty(statusMap)) {
                respVo.setSignalLightZNum(statusMap.get(OpsConstants.EQU_STATUS_ZC) + "");
                respVo.setSignalLightWNum(statusMap.get(OpsConstants.EQU_STATUS_WXZ) + "");
                respVo.setSignalLightGNum(statusMap.get(OpsConstants.EQU_STATUS_GZ) + "");
            }
        } else {
            if (status.equals(OpsConstants.EQU_STATUS_ZC)) {
                signalLightDOLambdaQueryWrapperX.eq(TrafficOpsSignalLightDO::getStatus, OpsConstants.EQU_STATUS_ZC);
                long signalLightZNum = opsSignalLightMapper.selectCount(signalLightDOLambdaQueryWrapperX);
                respVo.setSignalLightZNum(signalLightZNum + "");
                respVo.setSignalLightWNum("0");
                respVo.setSignalLightGNum("0");
            } else if (status.equals(OpsConstants.EQU_STATUS_WXZ)) {
                signalLightDOLambdaQueryWrapperX.eq(TrafficOpsSignalLightDO::getStatus, OpsConstants.EQU_STATUS_WXZ);
                long signalLightWNum = opsSignalLightMapper.selectCount(signalLightDOLambdaQueryWrapperX);
                respVo.setSignalLightZNum("0");
                respVo.setSignalLightWNum(signalLightWNum + "");
                respVo.setSignalLightGNum("0");
            } else {
                signalLightDOLambdaQueryWrapperX.eq(TrafficOpsSignalLightDO::getStatus, OpsConstants.EQU_STATUS_GZ);
                long signalLightGNum = opsSignalLightMapper.selectCount(signalLightDOLambdaQueryWrapperX);
                respVo.setSignalLightZNum("0");
                respVo.setSignalLightWNum("0");
                respVo.setSignalLightGNum(signalLightGNum + "");
            }
        }

        //标线
        LambdaQueryWrapperX<TrafficOpsMarklineDO> marklineDOLambdaQueryWrapperX = new LambdaQueryWrapperX<>();
        if (!CollectionUtils.isEmpty(roadNameList)) {
            for (String roadName : roadNameList) {
                marklineDOLambdaQueryWrapperX.like(TrafficOpsMarklineDO::getRoadName, roadName);
            }
        }
        marklineDOLambdaQueryWrapperX.likeIfPresent(TrafficOpsMarklineDO::getDominion, dominion);
        marklineDOLambdaQueryWrapperX.eqIfPresent(TrafficOpsMarklineDO::getStatus, status);
        long marklineNum = opsMarklineMapper.selectCount(marklineDOLambdaQueryWrapperX);
        respVo.setMarklineNum(marklineNum + "");

        //标志牌
        LambdaQueryWrapperX<TrafficOpsSignBoardDO> boardDOLambdaQueryWrapperX = new LambdaQueryWrapperX<>();
        if (!CollectionUtils.isEmpty(roadNameList)) {
            for (String roadName : roadNameList) {
                boardDOLambdaQueryWrapperX.like(TrafficOpsSignBoardDO::getIntersectionName, roadName);
            }
        }
        boardDOLambdaQueryWrapperX.likeIfPresent(TrafficOpsSignBoardDO::getDominion, dominion);
        boardDOLambdaQueryWrapperX.eqIfPresent(TrafficOpsSignBoardDO::getStatus, status);
        long boardNum = opsSignBoardMapper.selectCount(boardDOLambdaQueryWrapperX);
        respVo.setBoardNum(boardNum + "");
        if (StringUtils.isEmpty(status)) {
            List<TrafficOpsSignBoardDO> list = opsSignBoardMapper.selectList(boardDOLambdaQueryWrapperX);
            Map<String, Long> statusMap = Optional.ofNullable(list).orElse(new ArrayList<>()).stream().filter(s -> StringUtils.isNotBlank(s.getStatus())).collect(Collectors.groupingBy(TrafficOpsSignBoardDO::getStatus, Collectors.counting()));
            if (!CollectionUtils.isEmpty(statusMap)) {
                respVo.setBoardZNum(statusMap.get(OpsConstants.EQU_STATUS_ZC) + "");
                respVo.setBoardWNum(statusMap.get(OpsConstants.EQU_STATUS_WXZ) + "");
                respVo.setBoardGNum(statusMap.get(OpsConstants.EQU_STATUS_GZ) + "");
            }
        } else {
            if (status.equals(OpsConstants.EQU_STATUS_ZC)) {
                boardDOLambdaQueryWrapperX.eq(TrafficOpsSignBoardDO::getStatus, OpsConstants.EQU_STATUS_ZC);
                long boardZNum = opsSignBoardMapper.selectCount(boardDOLambdaQueryWrapperX);
                respVo.setBoardZNum(boardZNum + "");
                respVo.setBoardWNum("0");
                respVo.setBoardGNum("0");
            } else if (status.equals(OpsConstants.EQU_STATUS_WXZ)) {
                boardDOLambdaQueryWrapperX.eq(TrafficOpsSignBoardDO::getStatus, OpsConstants.EQU_STATUS_WXZ);
                long boardWNum = opsSignBoardMapper.selectCount(boardDOLambdaQueryWrapperX);
                respVo.setBoardZNum("0");
                respVo.setBoardWNum(boardWNum + "");
                respVo.setBoardGNum("0");
            } else {
                boardDOLambdaQueryWrapperX.eq(TrafficOpsSignBoardDO::getStatus, OpsConstants.EQU_STATUS_GZ);
                long boardGNum = opsSignBoardMapper.selectCount(boardDOLambdaQueryWrapperX);
                respVo.setBoardZNum("0");
                respVo.setBoardWNum("0");
                respVo.setBoardGNum(boardGNum + "");
            }
        }

        //雷达
        LambdaQueryWrapperX<TrafficOpsRadarDO> radarDOLambdaQueryWrapperX = new LambdaQueryWrapperX<>();
        if (!CollectionUtils.isEmpty(roadNameList)) {
            for (String roadName : roadNameList) {
                radarDOLambdaQueryWrapperX.like(TrafficOpsRadarDO::getIntersectionName, roadName);
            }
        }
        radarDOLambdaQueryWrapperX.likeIfPresent(TrafficOpsRadarDO::getDominion, dominion);
        radarDOLambdaQueryWrapperX.eqIfPresent(TrafficOpsRadarDO::getStatus, status);
        long radarNum = opsRadarMapper.selectCount(radarDOLambdaQueryWrapperX);
        respVo.setRadarNum(radarNum + "");
        if (StringUtils.isEmpty(status)) {
            List<TrafficOpsRadarDO> list = opsRadarMapper.selectList(radarDOLambdaQueryWrapperX);
            Map<String, Long> statusMap = Optional.ofNullable(list).orElse(new ArrayList<>()).stream().filter(s -> StringUtils.isNotBlank(s.getStatus())).collect(Collectors.groupingBy(TrafficOpsRadarDO::getStatus, Collectors.counting()));
            if (!CollectionUtils.isEmpty(statusMap)) {
                respVo.setElectricPoliceZNum(statusMap.get(OpsConstants.EQU_STATUS_ZC) + "");
                respVo.setElectricPoliceWNum(statusMap.get(OpsConstants.EQU_STATUS_WXZ) + "");
                respVo.setElectricPoliceGNum(statusMap.get(OpsConstants.EQU_STATUS_GZ) + "");
            }
        } else {
            if (status.equals(OpsConstants.EQU_STATUS_ZC)) {
                radarDOLambdaQueryWrapperX.eq(TrafficOpsRadarDO::getStatus, OpsConstants.EQU_STATUS_ZC);
                long radarZNum = opsRadarMapper.selectCount(radarDOLambdaQueryWrapperX);
                respVo.setRadarZNum(radarZNum + "");
                respVo.setRadarWNum("0");
                respVo.setRadarGNum("0");
            } else if (status.equals(OpsConstants.EQU_STATUS_WXZ)) {
                radarDOLambdaQueryWrapperX.eq(TrafficOpsRadarDO::getStatus, OpsConstants.EQU_STATUS_WXZ);
                long radarWNum = opsRadarMapper.selectCount(radarDOLambdaQueryWrapperX);
                respVo.setRadarZNum("0");
                respVo.setRadarWNum(radarWNum + "");
                respVo.setRadarGNum("0");
            } else {
                radarDOLambdaQueryWrapperX.eq(TrafficOpsRadarDO::getStatus, OpsConstants.EQU_STATUS_GZ);
                long radarGNum = opsRadarMapper.selectCount(radarDOLambdaQueryWrapperX);
                respVo.setRadarZNum("0");
                respVo.setRadarWNum("0");
                respVo.setRadarGNum(radarGNum + "");
            }
        }

        //护栏
        LambdaQueryWrapperX<TrafficOpsGuardrailDO> guardraiDOLambdaQueryWrapperX = new LambdaQueryWrapperX<>();
        if (!CollectionUtils.isEmpty(roadNameList)) {
            for (String roadName : roadNameList) {
                guardraiDOLambdaQueryWrapperX.like(TrafficOpsGuardrailDO::getIntersectionName, roadName);
            }
        }
        guardraiDOLambdaQueryWrapperX.likeIfPresent(TrafficOpsGuardrailDO::getDominion, dominion);
        guardraiDOLambdaQueryWrapperX.eqIfPresent(TrafficOpsGuardrailDO::getStatus, status);
        long guardraiNum = opsGuardrailMapper.selectCount(guardraiDOLambdaQueryWrapperX);
        respVo.setGuardrailNum(guardraiNum + "");
        if (StringUtils.isEmpty(status)) {
            List<TrafficOpsGuardrailDO> list = opsGuardrailMapper.selectList(guardraiDOLambdaQueryWrapperX);
            Map<String, Long> statusMap = Optional.ofNullable(list).orElse(new ArrayList<>()).stream().filter(s -> StringUtils.isNotBlank(s.getStatus())).collect(Collectors.groupingBy(TrafficOpsGuardrailDO::getStatus, Collectors.counting()));
            if (!CollectionUtils.isEmpty(statusMap)) {
                respVo.setGuardrailZNum(statusMap.get(OpsConstants.EQU_STATUS_ZC) + "");
                respVo.setGuardrailWNum(statusMap.get(OpsConstants.EQU_STATUS_WXZ) + "");
                respVo.setGuardrailGNum(statusMap.get(OpsConstants.EQU_STATUS_GZ) + "");
            }
        } else {
            if (status.equals(OpsConstants.EQU_STATUS_ZC)) {
                guardraiDOLambdaQueryWrapperX.eq(TrafficOpsGuardrailDO::getStatus, OpsConstants.EQU_STATUS_ZC);
                long guardraiZNum = opsGuardrailMapper.selectCount(guardraiDOLambdaQueryWrapperX);
                respVo.setGuardrailZNum(guardraiZNum + "");
                respVo.setGuardrailWNum("0");
                respVo.setGuardrailGNum("0");
            } else if (status.equals(OpsConstants.EQU_STATUS_WXZ)) {
                guardraiDOLambdaQueryWrapperX.eq(TrafficOpsGuardrailDO::getStatus, OpsConstants.EQU_STATUS_WXZ);
                long guardraiWNum = opsGuardrailMapper.selectCount(guardraiDOLambdaQueryWrapperX);
                respVo.setGuardrailZNum("0");
                respVo.setGuardrailWNum(guardraiWNum + "");
                respVo.setGuardrailGNum("0");
            } else {
                guardraiDOLambdaQueryWrapperX.eq(TrafficOpsGuardrailDO::getStatus, OpsConstants.EQU_STATUS_GZ);
                long guardraiGNum = opsGuardrailMapper.selectCount(guardraiDOLambdaQueryWrapperX);
                respVo.setGuardrailZNum("0");
                respVo.setGuardrailWNum("0");
                respVo.setGuardrailGNum(guardraiGNum + "");
            }
        }

        long equNum = signalNum + electricPoliceNum + bigLedNum + memberNum + signalLightNum + marklineNum + boardNum + radarNum + guardraiNum;
        respVo.setEquNum(equNum + "");

        // 0不能作被除数
        if (equNum == 0) {
            equNum = 1;
        }

        DecimalFormat df = new DecimalFormat("#");
        respVo.setSignalProp(df.format(((double) signalNum / equNum) * 100));
        respVo.setElectricPoliceProp(df.format(((double) electricPoliceNum / equNum) * 100));
        respVo.setBigLedProp(df.format(((double) bigLedNum / equNum) * 100));
        respVo.setMemberProp(df.format(((double) memberNum / equNum) * 100));
        respVo.setSignalLightProp(df.format(((double) signalLightNum / equNum) * 100));
        respVo.setMarklineProp(df.format(((double) marklineNum / equNum) * 100));
        respVo.setBoardProp(df.format(((double) boardNum / equNum) * 100));
        respVo.setRadarProp(df.format(((double) radarNum / equNum) * 100));
        respVo.setGuardrailProp(df.format(((double) guardraiNum / equNum) * 100));

        return respVo;
    }

    @Override
    public List<TrafficOpsPositionRespVO> queryTrafficOpsPositionList(String intersectionName) {
        List<String> roadNameList = HanLPUtils.segmentForRoad(intersectionName);
//        //信号机
//        LambdaQueryWrapperX<TrafficOpsSignalDO> lambdaQueryWrapperX = new LambdaQueryWrapperX<>();
//        lambdaQueryWrapperX.isNotNull(TrafficOpsSignalDO::getLat);
//        lambdaQueryWrapperX.isNotNull(TrafficOpsSignalDO::getLng);
//        if (!CollectionUtils.isEmpty(roadNameList)) {
//            for (String roadName : roadNameList) {
//                lambdaQueryWrapperX.like(TrafficOpsSignalDO::getIntersectionName, roadName);
//            }
//        }
//        List<TrafficOpsSignalDO> signalDOList = trafficOpsSignalMapper.selectList(lambdaQueryWrapperX);
//        List<TrafficOpsPositionRespVO> signalPositionList = BeanUtils.toBean(signalDOList, TrafficOpsPositionRespVO.class);
//
        //电警
        LambdaQueryWrapperX<TrafficOpsElectricPoliceDO> lambdaQueryWrapperX1 = new LambdaQueryWrapperX<>();
        lambdaQueryWrapperX1.isNotNull(TrafficOpsElectricPoliceDO::getLat);
        lambdaQueryWrapperX1.isNotNull(TrafficOpsElectricPoliceDO::getLng);
        if (!CollectionUtils.isEmpty(roadNameList)) {
            for (String roadName : roadNameList) {
                lambdaQueryWrapperX1.like(TrafficOpsElectricPoliceDO::getAdress, roadName);
            }
        }
        List<TrafficOpsElectricPoliceDO> electricPoliceDOList = opsElectricPoliceMapper.selectList(lambdaQueryWrapperX1);
        List<TrafficOpsPositionRespVO> electricPolicePositionList = BeanUtils.toBean(electricPoliceDOList, TrafficOpsPositionRespVO.class);
//
//        //led大屏
//        LambdaQueryWrapperX<TrafficOpsBigledDO> lambdaQueryWrapperX2 = new LambdaQueryWrapperX<>();
//        lambdaQueryWrapperX2.isNotNull(TrafficOpsBigledDO::getLat);
//        lambdaQueryWrapperX2.isNotNull(TrafficOpsBigledDO::getLng);
//        if (!CollectionUtils.isEmpty(roadNameList)) {
//            for (String roadName : roadNameList) {
//                lambdaQueryWrapperX2.like(TrafficOpsBigledDO::getIntersectionName, roadName);
//            }
//        }
//        List<TrafficOpsBigledDO> bigLedDOList = opsBigledMapper.selectList(lambdaQueryWrapperX2);
//        List<TrafficOpsPositionRespVO> bigLedPositionList = BeanUtils.toBean(bigLedDOList, TrafficOpsPositionRespVO.class);

        //杆件
        LambdaQueryWrapperX<TrafficOpsMemberDO> memberDOLambdaQueryWrapperX = new LambdaQueryWrapperX<>();
        memberDOLambdaQueryWrapperX.isNotNull(TrafficOpsMemberDO::getLat);
        memberDOLambdaQueryWrapperX.isNotNull(TrafficOpsMemberDO::getLng);
        if (!CollectionUtils.isEmpty(roadNameList)) {
            for (String roadName : roadNameList) {
                memberDOLambdaQueryWrapperX.like(TrafficOpsMemberDO::getIntersectionName, roadName);
            }
        }
        List<TrafficOpsMemberDO> memberDOList = opsMemberMapper.selectList(memberDOLambdaQueryWrapperX);
        List<TrafficOpsPositionRespVO> memberPositionList = BeanUtils.toBean(memberDOList, TrafficOpsPositionRespVO.class);


//        return Stream.of(signalPositionList, electricPolicePositionList, bigLedPositionList, memberPositionList).flatMap(List::stream).collect(Collectors.toList());
        return Stream.of(memberPositionList, electricPolicePositionList).flatMap(List::stream).collect(Collectors.toList());
    }

    @Override
    public List<Map<String, Object>> getWxType(String affiliatedUnit, String equLabel) {
        if (StringUtils.isBlank(affiliatedUnit)) {
            affiliatedUnit = OpsConstants.STRING_ONE;
        }
        QueryWrapper<TrafficOpsMaintenanceDO> wrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(equLabel)) {
            wrapper.exists("select 1 from police_ops_electric_police p where traffic_ops_maintenance.equ_id = p.id and p.equ_lable = '" + equLabel + "'");
        }
        wrapper.select("fault_type, COUNT(*) as count").eq("affiliated_unit", affiliatedUnit)
                .groupBy("fault_type");

        return opsMaintenanceMapper.selectMaps(wrapper);
    }

    @Override
    public List<TrafficOpsMaintenanceRankDTO> analysisMaintenanceRanking() {
        return opsMaintenanceExtMapper.analysisMaintenanceRanking();
    }

    @Override
    public Map<String, Collection<Integer>> analysisNearlyThreeYearsMaintenanceCnt(String equType) {
        if (StringUtils.isEmpty(equType)) {
            return null;
        }

        // 今年
        Date date = new Date();
        Date startDate = DateUtil.beginOfYear(date);
        Date endDate = DateUtil.endOfYear(date);

        // 去年
        Date lastStartDate = DateUtil.beginOfYear(DateUtil.offsetYear(date, -1));
        Date lastEndDate = DateUtil.endOfYear(DateUtil.offsetYear(date, -1));

        // 前年
        Date beforeLastStartDate = DateUtil.beginOfYear(DateUtil.offsetYear(date, -2));
        Date beforeLastEndDate = DateUtil.endOfYear(DateUtil.offsetYear(date, -2));

        // 查询最两年统计数据
        List<Map<String, Object>> dataList = opsMaintenanceExtMapper.analysisNearlyThreeYearsMaintenanceCnt(equType,
                DateUtil.format(beforeLastStartDate, "yyyy-MM"), DateUtil.format(endDate, "yyyy-MM"));

        Map<String, Integer> thisYearXMap = CoordinateUtils.generateLineData(startDate, endDate);
        Map<String, Integer> lastYearXMap = CoordinateUtils.generateLineData(lastStartDate, lastEndDate);
        Map<String, Integer> beforeLastYearXMap = CoordinateUtils.generateLineData(beforeLastStartDate, beforeLastEndDate);

        for (Map<String, Object> map : dataList) {
            String months = (String) map.get("months");
            int cnt = 0;
            if (map.get("cnt") != null) {
                cnt = ((Long) map.get("cnt")).intValue();
            }
            if (thisYearXMap.containsKey(months)) {
                thisYearXMap.put(months, cnt);
            } else if (lastYearXMap.containsKey(months)) {
                lastYearXMap.put(months, cnt);
            } else if (beforeLastYearXMap.containsKey(months)) {
                beforeLastYearXMap.put(months, cnt);
            }
        }
        Map<String, Collection<Integer>> map = new HashMap<>();
        map.put(DateUtil.format(beforeLastStartDate, "yyyy"), beforeLastYearXMap.values());
        map.put(DateUtil.format(lastStartDate, "yyyy"), lastYearXMap.values());
        map.put(DateUtil.format(startDate, "yyyy"), thisYearXMap.values());
        return map;
    }

    @Override
    public List<Map<String, Collection<Integer>>> analysisElectricPoliceByLabel() {
        return opsAnalysisExtMapper.analysisElectricPoliceByLabel();
    }

    @Override
    public List<Map<String, Collection<Integer>>> analysisMonitorResultByEventType(String equType) {
        return opsAnalysisExtMapper.analysisMonitorResultByEventType(equType);
    }

    @Override
    public List<Map<String, Collection<Integer>>> analysisMonitorResultByKeyWord(String equType) {
        return opsAnalysisExtMapper.analysisMonitorResultByKeyWord(equType);
    }

    @Override
    public List<TrafficOpsMaintainPointRespVO> queryMaintenancePoint() {
        return opsMaintenanceExtMapper.queryMaintenancePoint();
    }

    @Override
    public List<TrafficOpsMaintainPointCountRespVO> queryMaintenancePointCount() {
        return opsMaintenanceExtMapper.queryMaintenancePointCount();
    }

    @Override
    public List<TrafficOpsMaintainPointCountRespVO> queryMaintenancePointStatusCount(String djType) {
        return opsMaintenanceExtMapper.queryMaintenancePointStatusCount(djType);
    }

    @Override
    public Map<String, Object> analysisMaintenanceDashboardCnt(String affiliatedUnit) {
        if (StringUtils.isEmpty(affiliatedUnit)) {
            affiliatedUnit = OpsConstants.STRING_ONE;
        }
        return opsMaintenanceExtMapper.analysisMaintenanceDashboardCnt(affiliatedUnit);
    }

    @Override
    public Map<String, Object> analysisMaintenanceFinishSituation(String affiliatedUnit) {
        if (StringUtils.isEmpty(affiliatedUnit)) {
            affiliatedUnit = OpsConstants.STRING_ONE;
        }
        return opsMaintenanceExtMapper.analysisMaintenanceFinishSituation(affiliatedUnit);
    }

    @Override
    public List<Map<String, Collection<Integer>>> analysisElectricPoliceMonitorByLabel(Integer hour) {
        if (hour == null)  {
            hour = 24;
        }

        return opsMaintenanceExtMapper.analysisElectricPoliceMonitorByLabel(hour);
    }

    @Override
    public List<TrafficOpsHealthRateDTO> analysisKhsbMonitorResult(Integer intervalDay) {
        List<TrafficOpsHealthRateDTO>  respList = new ArrayList<>();
        if (intervalDay == null)  {
            intervalDay = 7;
        }
        // 数据导入时间与当前时间差两天数据
        intervalDay = intervalDay + 3;

//        List<Map<String, Object>> list = opsAnalysisExtMapper.analysisKhsbMonitorResult(intervalDay);
//        if (CollectionUtils.isEmpty(list)) {
//            return respList;
//        }
//
//        // 查询集值考核设备总数
//        LambdaQueryWrapperX<TrafficOpsElectricPoliceDO> lambdaQueryWrapperX = new LambdaQueryWrapperX<>();
////        lambdaQueryWrapperX.isNotNull(TrafficOpsElectricPoliceDO::getKkbh);
//        lambdaQueryWrapperX.eq(TrafficOpsElectricPoliceDO::getDjType, "电子警察");
//        Long sumCnt = opsElectricPoliceMapper.selectCount(lambdaQueryWrapperX);
//        if (sumCnt == null || sumCnt == 0) {
//            return respList;
//        }
//
//        for (Map<String, Object> map : list) {
//            TrafficOpsHealthRateDTO rateDTO = new TrafficOpsHealthRateDTO();
//            String occurrenceTime = map.get("occurrence_time").toString();
//            Long normalCnt = (Long) map.get("normal_cnt");
//
//            rateDTO.setAnalysisTime(DateUtil.format(DateUtil.parse(occurrenceTime, "yyyyMMdd"), "yyyy-MM-dd"));
//            rateDTO.setNormalCnt(normalCnt == null ? 0 : normalCnt.intValue());
//            rateDTO.setSumCnt(sumCnt);
//            rateDTO.setHealthRate(String.format("%.2f", (rateDTO.getNormalCnt() * 1.0 / rateDTO.getSumCnt() * 100)));
//            respList.add(rateDTO);
//        }
        LambdaQueryWrapperX<TrafficMonitorResultsImportAnalysisDO> lambdaQueryWrapperX = new LambdaQueryWrapperX<>();
        lambdaQueryWrapperX.gt(TrafficMonitorResultsImportAnalysisDO::getAnalysisDate, DateUtil.beginOfDay(DateUtil.offsetDay(new Date(), -intervalDay)));
        lambdaQueryWrapperX.orderByAsc(TrafficMonitorResultsImportAnalysisDO::getAnalysisDate);
        List<TrafficMonitorResultsImportAnalysisDO> analysisDOList = trafficMonitorResultsImportAnalysisMapper.selectList(lambdaQueryWrapperX);
        if (CollectionUtils.isEmpty(analysisDOList)) {
            return respList;
        }

        respList = analysisDOList.stream().map(analysisDO -> {
            TrafficOpsHealthRateDTO rateDTO = new TrafficOpsHealthRateDTO();
            rateDTO.setAnalysisTime(DateUtil.format(analysisDO.getAnalysisDate(), "yyyy-MM-dd"));
            rateDTO.setNormalCnt(analysisDO.getNormalCnt());
            rateDTO.setSumCnt(analysisDO.getNormalCnt() + analysisDO.getAbnormalCnt());
            rateDTO.setHealthRate(String.format("%.2f", analysisDO.getHealthRate().multiply(new BigDecimal(100))));
            return rateDTO;
        }).collect(Collectors.toList());
        return respList;
    }

    @Override
    public BigDecimal calcVioValidSurveilAvgRate() {
        Map<String, BigDecimal> avgRateMap = opsAnalysisExtMapper.calcVioValidSurveilAvgRate();
        if (!CollectionUtils.isEmpty(avgRateMap)) {
            return avgRateMap.get("avg_rate");
        }
        return BigDecimal.ZERO;
    }

    @Override
    public Map<String, Integer> analysisNearlyOneYearsMaintenanceCnt(String affiliatedUnit) {
        if (StringUtils.isEmpty(affiliatedUnit)) {
            affiliatedUnit = OpsConstants.STRING_ONE;
        }

        // 今年
        Date endDate = new Date();
        Date startDate = DateUtil.offsetMonth(endDate, -11);

        // 查询最两年统计数据
        List<Map<String, Object>> dataList = opsMaintenanceExtMapper.analysisNearlyOneYearsMaintenanceCnt(affiliatedUnit,
                DateUtil.format(startDate, "yyyy-MM"), DateUtil.format(endDate, "yyyy-MM"));

        Map<String, Integer> thisYearXMap = CoordinateUtils.generateLineData(startDate, endDate);

        for (Map<String, Object> map : dataList) {
            String months = (String) map.get("months");
            int cnt = 0;
            if (map.get("cnt") != null) {
                cnt = ((Long) map.get("cnt")).intValue();
            }
            if (thisYearXMap.containsKey(months)) {
                thisYearXMap.put(months, cnt);
            }
        }
        return thisYearXMap;
    }

}