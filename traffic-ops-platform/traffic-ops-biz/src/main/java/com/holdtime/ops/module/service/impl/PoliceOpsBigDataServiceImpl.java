package com.holdtime.ops.module.service.impl;

import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.holdtime.ops.module.entity.*;
import com.holdtime.ops.module.entity.mapper.*;
import com.holdtime.ops.module.enums.OpsConstants;
import com.holdtime.ops.module.mapper.PoliceOpsEquipInfoExtMapper;
import com.holdtime.ops.module.model.vo.*;
import com.holdtime.ops.module.service.PoliceOpsBigDataService;
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
public class PoliceOpsBigDataServiceImpl implements PoliceOpsBigDataService {

    @Resource
    private PoliceOpsMemberMapper policeOpsMemberMapper;
    @Resource
    private PoliceOpsElectricPoliceMapper policeOpsElectricPoliceMapper;
    @Resource
    private PoliceMonitorResultsMapper policeMonitorResultsMapper;
    @Resource
    private PoliceOpsEquipInfoExtMapper policeOpsEquipInfoExtMapper;

    @Override
    public PoliceOpsBigDataBaseRespVO getPoliceOpsBigDataBase(String equName, String dominion, String status) {
        PoliceOpsBigDataBaseRespVO respVo = new PoliceOpsBigDataBaseRespVO();

        List<String> equNameList = HanLPUtils.segmentForRoad(equName);
        LambdaQueryWrapperX<PoliceOpsElectricPoliceDO> electricPoliceDOLambdaQueryWrapperX = new LambdaQueryWrapperX<>();
        if (!CollectionUtils.isEmpty(equNameList)) {
            for (String segmentKey : equNameList) {
                electricPoliceDOLambdaQueryWrapperX.like(PoliceOpsElectricPoliceDO::getEquName, segmentKey);
            }
        }
        electricPoliceDOLambdaQueryWrapperX.likeIfPresent(PoliceOpsElectricPoliceDO::getDominion,dominion);
        electricPoliceDOLambdaQueryWrapperX.eqIfPresent(PoliceOpsElectricPoliceDO::getStatus,status);
        long electricPoliceNum = policeOpsElectricPoliceMapper.selectCount(electricPoliceDOLambdaQueryWrapperX);
        respVo.setElectricPoliceNum(electricPoliceNum + "");
        if(StringUtils.isEmpty(status)){
            List<PoliceOpsElectricPoliceDO> list = policeOpsElectricPoliceMapper.selectList(electricPoliceDOLambdaQueryWrapperX);
            Map<String, Long> statusMap = Optional.ofNullable(list).orElse(new ArrayList<>()).stream().filter(s -> StringUtils.isNotBlank(s.getStatus())).collect(Collectors.groupingBy(PoliceOpsElectricPoliceDO::getStatus, Collectors.counting()));
            if(!CollectionUtils.isEmpty(statusMap)) {
                respVo.setElectricPoliceZNum(statusMap.get(OpsConstants.EQU_STATUS_ZC) + "");
                respVo.setElectricPoliceWNum(statusMap.get(OpsConstants.EQU_STATUS_WXZ) + "");
                respVo.setElectricPoliceGNum(statusMap.get(OpsConstants.EQU_STATUS_GZ) + "");
            }
        }else{
            if(status.equals(OpsConstants.EQU_STATUS_ZC)){
                electricPoliceDOLambdaQueryWrapperX.eq(PoliceOpsElectricPoliceDO::getStatus,OpsConstants.EQU_STATUS_ZC);
                long electricPoliceZNum = policeOpsElectricPoliceMapper.selectCount(electricPoliceDOLambdaQueryWrapperX);
                respVo.setElectricPoliceZNum(electricPoliceZNum + "");
                respVo.setElectricPoliceWNum("0");
                respVo.setElectricPoliceGNum("0");
            }else if(status.equals(OpsConstants.EQU_STATUS_WXZ)){
                electricPoliceDOLambdaQueryWrapperX.eq(PoliceOpsElectricPoliceDO::getStatus,OpsConstants.EQU_STATUS_WXZ);
                long electricPoliceWNum = policeOpsElectricPoliceMapper.selectCount(electricPoliceDOLambdaQueryWrapperX);
                respVo.setElectricPoliceZNum("0");
                respVo.setElectricPoliceWNum(electricPoliceWNum + "");
                respVo.setElectricPoliceGNum("0");
            }else{
                electricPoliceDOLambdaQueryWrapperX.eq(PoliceOpsElectricPoliceDO::getStatus,OpsConstants.EQU_STATUS_GZ);
                long electricPoliceGNum = policeOpsElectricPoliceMapper.selectCount(electricPoliceDOLambdaQueryWrapperX);
                respVo.setElectricPoliceZNum("0");
                respVo.setElectricPoliceWNum("0");
                respVo.setElectricPoliceGNum(electricPoliceGNum + "");
            }
        }

        LambdaQueryWrapperX<PoliceOpsMemberDO> memberDOLambdaQueryWrapperX = new LambdaQueryWrapperX<>();
        if (!CollectionUtils.isEmpty(equNameList)) {
            for (String segmentKey : equNameList) {
                memberDOLambdaQueryWrapperX.like(PoliceOpsMemberDO::getEquName, segmentKey);
            }
        }
        memberDOLambdaQueryWrapperX.likeIfPresent(PoliceOpsMemberDO::getDominion,dominion);
        memberDOLambdaQueryWrapperX.eqIfPresent(PoliceOpsMemberDO::getStatus,status);
        long memberNum = policeOpsMemberMapper.selectCount(memberDOLambdaQueryWrapperX);
        respVo.setMemberNum(memberNum + "");
        if(StringUtils.isEmpty(status)){
            List<PoliceOpsMemberDO> list = policeOpsMemberMapper.selectList(memberDOLambdaQueryWrapperX);
            Map<String, Long> statusMap = Optional.ofNullable(list).orElse(new ArrayList<>()).stream().filter(s -> StringUtils.isNotBlank(s.getStatus())).collect(Collectors.groupingBy(PoliceOpsMemberDO::getStatus, Collectors.counting()));
            if(!CollectionUtils.isEmpty(statusMap)) {
                respVo.setMemberZNum(statusMap.get(OpsConstants.EQU_STATUS_ZC) + "");
                respVo.setMemberWNum(statusMap.get(OpsConstants.EQU_STATUS_WXZ) + "");
                respVo.setMemberGNum(statusMap.get(OpsConstants.EQU_STATUS_GZ) + "");
            }
        }else{
            if(status.equals(OpsConstants.EQU_STATUS_ZC)){
                memberDOLambdaQueryWrapperX.eq(PoliceOpsMemberDO::getStatus,OpsConstants.EQU_STATUS_ZC);
                long memberZNum = policeOpsMemberMapper.selectCount(memberDOLambdaQueryWrapperX);
                respVo.setMemberZNum(memberZNum + "");
                respVo.setMemberWNum("0");
                respVo.setMemberGNum("0");
            }else if(status.equals(OpsConstants.EQU_STATUS_WXZ)){
                memberDOLambdaQueryWrapperX.eq(PoliceOpsMemberDO::getStatus,OpsConstants.EQU_STATUS_WXZ);
                long memberWNum = policeOpsMemberMapper.selectCount(memberDOLambdaQueryWrapperX);
                respVo.setMemberZNum("0");
                respVo.setMemberWNum(memberWNum + "");
                respVo.setMemberGNum("0");
            }else{
                memberDOLambdaQueryWrapperX.eq(PoliceOpsMemberDO::getStatus,OpsConstants.EQU_STATUS_GZ);
                long memberGNum = policeOpsMemberMapper.selectCount(memberDOLambdaQueryWrapperX);
                respVo.setMemberZNum("0");
                respVo.setMemberWNum("0");
                respVo.setMemberGNum(memberGNum + "");
            }
        }

        // 0不能作被除数
        long equNum = electricPoliceNum + memberNum;
        respVo.setEquNum(equNum + "");
        if (equNum == 0) {
            equNum = 1;
        }

        DecimalFormat df = new DecimalFormat("#");
        respVo.setElectricPoliceProp(df.format(((double) electricPoliceNum/equNum)*100));
        respVo.setMemberProp(df.format(((double) memberNum/equNum)*100));

        return respVo;
    }

    @Override
    public List<PoliceOpsPositionRespVO> queryPoliceOpsPositionList(String equName) {
        List<String> equNameList = HanLPUtils.segmentForRoad(equName);

//        //电警
//        LambdaQueryWrapperX<PoliceOpsElectricPoliceDO> lambdaQueryWrapperX1 = new LambdaQueryWrapperX<>();
//        lambdaQueryWrapperX1.isNotNull(PoliceOpsElectricPoliceDO::getLat);
//        lambdaQueryWrapperX1.isNotNull(PoliceOpsElectricPoliceDO::getLng);
//        if (!CollectionUtils.isEmpty(equNameList)) {
//            for (String segmentKey : equNameList) {
//                lambdaQueryWrapperX1.like(PoliceOpsElectricPoliceDO::getEquName, segmentKey);
//            }
//        }
//        List<PoliceOpsElectricPoliceDO> electricPoliceDOList = policeOpsElectricPoliceMapper.selectList(lambdaQueryWrapperX1);
//        List<PoliceOpsPositionRespVO> electricPolicePositionList = BeanUtils.toBean(electricPoliceDOList, PoliceOpsPositionRespVO.class);

        //杆件
        LambdaQueryWrapperX<PoliceOpsMemberDO> memberDOLambdaQueryWrapperX = new LambdaQueryWrapperX<>();
        memberDOLambdaQueryWrapperX.isNotNull(PoliceOpsMemberDO::getLat);
        memberDOLambdaQueryWrapperX.isNotNull(PoliceOpsMemberDO::getLng);
        if (!CollectionUtils.isEmpty(equNameList)) {
            for (String segmentKey : equNameList) {
                memberDOLambdaQueryWrapperX.like(PoliceOpsMemberDO::getEquName, segmentKey);
            }
        }
        List<PoliceOpsMemberDO> memberDOList = policeOpsMemberMapper.selectList(memberDOLambdaQueryWrapperX);
        determineStatus(memberDOList);
        List<PoliceOpsPositionRespVO> memberPositionList = BeanUtils.toBean(memberDOList, PoliceOpsPositionRespVO.class);


        return Stream.of(memberPositionList).flatMap(List::stream).collect(Collectors.toList());
    }

    @Override
    public Map<String, Object> analysisPoliceOpsElectricCnt() {
        Map<String, Object> map = policeOpsEquipInfoExtMapper.analysisPoliceOpsElectricCnt();

        long memberCnt = policeOpsMemberMapper.selectCount();
        map.put("memberCnt", memberCnt);
        return map;
    }

    @Override
    public List<Map<String, Object>> analysisPoliceOpsElectricMaintenanceCnt() {
        return policeOpsEquipInfoExtMapper.analysisPoliceOpsElectricMaintenanceCnt();
    }

    @Override
    public List<Map<String, Collection<Integer>>> analysisElectricPoliceMonitorByLabel(Integer hour) {
        if (hour == null)  {
            hour = 24;
        }

        return policeOpsEquipInfoExtMapper.analysisPoliceElectricPoliceMonitorByLabel(hour);
    }

    @Override
    public Map<String, BigDecimal> analysisPoliceElectricPoliceOnOffAvgRate() {
        return policeOpsEquipInfoExtMapper.analysisPoliceElectricPoliceOnOffAvgRate();
    }

    private void determineStatus(List<PoliceOpsMemberDO> memberDOList) {
        List<String> memberIdList = memberDOList.stream().map(PoliceOpsMemberDO::getId).collect(Collectors.toList());
        LambdaQueryWrapperX<PoliceMonitorResultsDO> queryWrapper = new LambdaQueryWrapperX<>();
        queryWrapper.in(PoliceMonitorResultsDO::getMemberBh, memberIdList);
        List<PoliceMonitorResultsDO> resultsList = policeMonitorResultsMapper.selectList(queryWrapper);
        Map<String, List<PoliceMonitorResultsDO>> resultsByMemberId = resultsList.stream()
            .collect(Collectors.groupingBy(PoliceMonitorResultsDO::getMemberBh));

        for (PoliceOpsMemberDO memberDO : memberDOList) {
            List<PoliceMonitorResultsDO> results = resultsByMemberId.get(memberDO.getId());
            if (results != null && !results.isEmpty()) {
                memberDO.setStatus(determineMemberStatus(results));
            }
        }
    }

    private String determineMemberStatus(List<PoliceMonitorResultsDO> results) {
        boolean hasIpUnreachable = results.stream().anyMatch(r -> "IP不可达".equals(r.getStatus()));
        boolean hasNormal = results.stream().anyMatch(r -> "正常".equals(r.getStatus()));

        if (!hasIpUnreachable) {
            return "绿";
        } else if (!hasNormal) {
            return "红";
        } else {
            return "橙";
        }
    }
}