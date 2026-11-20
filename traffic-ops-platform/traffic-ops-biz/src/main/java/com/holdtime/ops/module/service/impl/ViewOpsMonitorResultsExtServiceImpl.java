package com.holdtime.ops.module.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fhs.common.utils.StringUtil;
import com.holdtime.ops.module.entity.*;
import com.holdtime.ops.module.entity.mapper.*;
import com.holdtime.ops.module.enums.OpsConstants;
import com.holdtime.ops.module.mapper.OpsAnalysisExtMapper;
import com.holdtime.ops.module.model.dto.TrafficOpsMonitorDetailResultDTO;
import com.holdtime.ops.module.model.dto.TrafficOpsMonitorHourStatusDTO;
import com.holdtime.ops.module.model.vo.*;
import com.holdtime.ops.module.service.ViewOpsMonitorResultsExtService;
import com.holdtime.ops.module.utils.HanLPUtils;
import com.xingyuv.captcha.util.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;


/**
 * VIEW Service 实现类
 *
 * @author 管理员
 */
@Service
@Validated
public class ViewOpsMonitorResultsExtServiceImpl implements ViewOpsMonitorResultsExtService {

    @Resource
    private ViewOpsMonitorResultsMapper viewOpsMonitorResultsMapper;

    @Resource
    private TrafficOpsMemberMapper trafficOpsMemberMapper;
    @Resource
    private OpsAnalysisExtMapper opsAnalysisExtMapper;
    @Resource
    private TrafficMonitorResultsMapper monitorResultsMapper;
    @Resource
    private TrafficMonitorResultsDailyAnalysisMapper monitorResultsDailyAnalysisMapper;
    @Resource
    private ViewOpsMonitorSituationMapper viewOpsMonitorSituationMapper;

    @Override
    public PageResult<ViewOpsMonitorResultsExtRespVO> getViewOpsMonitorResultsPage(ViewOpsMonitorResultsPageReqVO pageReqVO) {
        LambdaQueryWrapperX<ViewOpsMonitorResultsDO> lambdaQueryWrapperX = new LambdaQueryWrapperX<>();
        lambdaQueryWrapperX.eqIfPresent(ViewOpsMonitorResultsDO::getEquId, pageReqVO.getEquId());
        lambdaQueryWrapperX.eqIfPresent(ViewOpsMonitorResultsDO::getMemberBh, pageReqVO.getMemberBh());
        lambdaQueryWrapperX.likeIfPresent(ViewOpsMonitorResultsDO::getRoadName, pageReqVO.getRoadName());

        if(!StringUtil.isEmpty(pageReqVO.getIntersectionName())){
            List<String> roadNameList = HanLPUtils.segmentForRoad(pageReqVO.getIntersectionName());
            if (!CollectionUtils.isEmpty(roadNameList)) {
                for (String roadName : roadNameList) {
                    lambdaQueryWrapperX.like(ViewOpsMonitorResultsDO::getIntersectionName, roadName);
                }
            }
        }
        lambdaQueryWrapperX.eqIfPresent(ViewOpsMonitorResultsDO::getDirection, pageReqVO.getDirection());
        lambdaQueryWrapperX.eqIfPresent(ViewOpsMonitorResultsDO::getIpAddr, pageReqVO.getIpAddr());
        lambdaQueryWrapperX.eqIfPresent(ViewOpsMonitorResultsDO::getEventType, pageReqVO.getEventType());
        if (OpsConstants.STRING_ZERO.equals(pageReqVO.getStatus())) { // 全断
            lambdaQueryWrapperX.eq(ViewOpsMonitorResultsDO::getNormalCnt, OpsConstants.STRING_ZERO);
        } else if (OpsConstants.STRING_ONE.equals(pageReqVO.getStatus())) { // 全通
            lambdaQueryWrapperX.eq(ViewOpsMonitorResultsDO::getAbnormalCnt, OpsConstants.STRING_ZERO);
        } else if (OpsConstants.STRING_TWO.equals(pageReqVO.getStatus())) {
            lambdaQueryWrapperX.ne(ViewOpsMonitorResultsDO::getNormalCnt, OpsConstants.STRING_ZERO);
            lambdaQueryWrapperX.ne(ViewOpsMonitorResultsDO::getAbnormalCnt, OpsConstants.STRING_ZERO);
        }

        StringBuilder childSql = new StringBuilder();
        boolean hasChild = false;
        if (org.apache.commons.lang3.StringUtils.isNotBlank(pageReqVO.getKkbh())) {
            if ("true".equals(pageReqVO.getKkbh())) {
                childSql.append(" and e.kkbh is not null");
            } else {
                childSql.append(" and e.kkbh is null");
            }
            hasChild = true;
        }

        if (!CollectionUtils.isEmpty(pageReqVO.getEquLableList())) {
            hasChild = true;
            for (String equLabel : pageReqVO.getEquLableList()) {
                childSql.append(" and e.equ_lable like '%").append(equLabel).append("%'");
            }
        }

        if (org.apache.commons.lang3.StringUtils.isNotBlank(pageReqVO.getDjType())) {
            childSql.append(" and e.dj_type = '").append(pageReqVO.getDjType()).append("'");
            hasChild = true;
        }

        if (hasChild) {
            String existsSql = "select 1 from traffic_ops_electric_police e where e.id = v_ops_monitor_results.equ_id" + childSql;
            lambdaQueryWrapperX.exists(existsSql);
        }

        if (StringUtils.isNotBlank(pageReqVO.getStatusChangeDesc())) {
            lambdaQueryWrapperX.exists("select 1 from v_ops_monitor_situation s where v_ops_monitor_results.equ_id = s.equ_id and s.status_change_desc = '" + pageReqVO.getStatusChangeDesc() + "'");
        }


        PageResult<ViewOpsMonitorResultsExtRespVO> pageResult = BeanUtils.toBean(viewOpsMonitorResultsMapper.selectPage(pageReqVO, lambdaQueryWrapperX), ViewOpsMonitorResultsExtRespVO.class);

        if (pageResult == null || CollectionUtils.isEmpty(pageResult.getList())) {
            return new PageResult<>();
        }

        Map<String, List<ViewOpsMonitorResultsExtRespVO>> resultMap = pageResult.getList().stream().collect(
                Collectors.groupingBy(dto -> StringUtils.isBlank(dto.getMemberBh()) ? "未绑定" : dto.getMemberBh(), LinkedHashMap::new, Collectors.toList()));


        LambdaQueryWrapper<TrafficOpsMemberDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(TrafficOpsMemberDO::getId, resultMap.keySet());
        List<TrafficOpsMemberDO> memberList = trafficOpsMemberMapper.selectList(queryWrapper);

        Map<String, TrafficOpsMemberDO> memberMap = Optional.ofNullable(memberList).orElse(new ArrayList<>()).stream().collect(Collectors.toMap(TrafficOpsMemberDO::getId, member -> member));

        List<String> equIdList = pageResult.getList().stream().map(ViewOpsMonitorResultsExtRespVO::getEquId).collect(Collectors.toList());
        LambdaQueryWrapperX<ViewOpsMonitorSituationDO> situationDOLambdaQueryWrapperX = new LambdaQueryWrapperX<>();
        situationDOLambdaQueryWrapperX.in(ViewOpsMonitorSituationDO::getEquId, equIdList);
        List<ViewOpsMonitorSituationDO> situationDOList =  viewOpsMonitorSituationMapper.selectList(situationDOLambdaQueryWrapperX);
        Map<String, ViewOpsMonitorSituationDO> situationDOMap = Optional.ofNullable(situationDOList).orElse(new ArrayList<>()).stream().collect(Collectors.toMap(ViewOpsMonitorSituationDO::getEquId, detailDO -> detailDO,  (k1, k2) -> k1));


        pageResult.getList().forEach(dto -> {
            TrafficOpsMemberDO memberDO = memberMap.get(dto.getMemberBh());
            if (memberDO != null) {
                String serialNum = "";
                if (memberDO.getSerialNum() != null) {
                    serialNum = String.format("%03d", memberDO.getSerialNum());
                }
                String memberName = memberDO.getRoadName() + "/" + memberDO.getIntersectionName() + "/" + memberDO.getAdress() + "/" + serialNum;
                dto.setMemberName(memberName);
            } else {
                dto.setMemberName("");
            }

            if (dto.getNormalCnt().intValue() > 0 && dto.getAbnormalCnt().intValue() == 0) {
                dto.setStatusName("全通");
            } else if (dto.getNormalCnt().intValue() == 0 && dto.getAbnormalCnt().intValue() > 0) {
                dto.setStatusName("全断");
            } else {
                dto.setStatusName("时断时通");
            }

            ViewOpsMonitorSituationDO situationDO = situationDOMap.get(dto.getEquId());
            if (situationDO != null) {
                dto.setStatusChangeDesc(situationDO.getStatusChangeDesc());
            }
        });

        return pageResult;

    }

    @Override
    public PageResult<TrafficOpsMonitorDetailResultDTO> analysisMonitorDetailResult(Page<?> page, String ipAddr, String eventType,
                                                                                    String startDate, String endDate) {
        if (StringUtils.isBlank(startDate)) {
            startDate = null;
        }
        if (StringUtils.isBlank(endDate)) {
            endDate = null;
        }
        IPage<TrafficOpsMonitorDetailResultDTO> resultDTOIPage = opsAnalysisExtMapper.analysisMonitorDetailResult(page, ipAddr, eventType, startDate, endDate);
        if (resultDTOIPage == null || CollectionUtils.isEmpty(resultDTOIPage.getRecords())) {
            return new PageResult<>();
        }

        resultDTOIPage.getRecords().forEach(dto -> {
            // 查询每日检查状态
            LambdaQueryWrapper<TrafficMonitorResultsDO> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(TrafficMonitorResultsDO::getEquId, dto.getEquId());
            queryWrapper.exists("select 1 from traffic_monitor_results r where traffic_monitor_results.id = r.id and date_format(occurrence_time, '%Y-%m-%d') = '" + dto.getMonitorDay() + "'");
            queryWrapper.eq(TrafficMonitorResultsDO::getEventType, dto.getEventType());
            queryWrapper.orderByAsc(TrafficMonitorResultsDO::getOccurrenceTime);
            List<TrafficMonitorResultsDO> list = monitorResultsMapper.selectList(queryWrapper);
            List<TrafficOpsMonitorHourStatusDTO> statusList = Optional.ofNullable(list).orElse(new ArrayList<>()).stream().map(monitorResultsDO -> {
                TrafficOpsMonitorHourStatusDTO statusDTO = new TrafficOpsMonitorHourStatusDTO();
                statusDTO.setMonitorDay(DateUtil.format(monitorResultsDO.getOccurrenceTime(), "HH:mm"));
                statusDTO.setStatus(monitorResultsDO.getStatus());
                return statusDTO;
            }).collect(Collectors.toList());
            dto.setHourStatusResultList(statusList);
        });
        return new PageResult<>(resultDTOIPage.getRecords(), page.getTotal());
    }

    @Override
    public PageResult<TrafficMonitorResultsDailyAnalysisExtRespVO> getMonitorResultsDailyAnalysisPage(TrafficMonitorResultsDailyAnalysisExtPageReqVO pageReqVO) {
        LambdaQueryWrapperX<TrafficMonitorResultsDailyAnalysisDO> lambdaQueryWrapperX = new LambdaQueryWrapperX<TrafficMonitorResultsDailyAnalysisDO>()
                .betweenIfPresent(TrafficMonitorResultsDailyAnalysisDO::getAnalysisDate, pageReqVO.getAnalysisDate())
                .eqIfPresent(TrafficMonitorResultsDailyAnalysisDO::getEquId, pageReqVO.getEquId())
                .eqIfPresent(TrafficMonitorResultsDailyAnalysisDO::getMemberBh, pageReqVO.getMemberBh())
                .likeIfPresent(TrafficMonitorResultsDailyAnalysisDO::getRoadName, pageReqVO.getRoadName())
                .eqIfPresent(TrafficMonitorResultsDailyAnalysisDO::getDirection, pageReqVO.getDirection())
                .eqIfPresent(TrafficMonitorResultsDailyAnalysisDO::getIpAddr, pageReqVO.getIpAddr())
                .eqIfPresent(TrafficMonitorResultsDailyAnalysisDO::getDjType, pageReqVO.getDjType())
                .eqIfPresent(TrafficMonitorResultsDailyAnalysisDO::getEquLable, pageReqVO.getEquLable())
                .eqIfPresent(TrafficMonitorResultsDailyAnalysisDO::getEventType, pageReqVO.getEventType())
                .eqIfPresent(TrafficMonitorResultsDailyAnalysisDO::getNormalCnt, pageReqVO.getNormalCnt())
                .eqIfPresent(TrafficMonitorResultsDailyAnalysisDO::getAbnormalCnt, pageReqVO.getAbnormalCnt())
                .eqIfPresent(TrafficMonitorResultsDailyAnalysisDO::getMonitorSumDays, pageReqVO.getMonitorSumDays())
                .eqIfPresent(TrafficMonitorResultsDailyAnalysisDO::getMonitorSumCnt, pageReqVO.getMonitorSumCnt())
                .eqIfPresent(TrafficMonitorResultsDailyAnalysisDO::getHealthRate, pageReqVO.getHealthRate())
                .betweenIfPresent(TrafficMonitorResultsDailyAnalysisDO::getCreateTime, pageReqVO.getCreateTime())
                .betweenIfPresent(TrafficMonitorResultsDailyAnalysisDO::getDeletedTime, pageReqVO.getDeletedTime())
                .orderByDesc(TrafficMonitorResultsDailyAnalysisDO::getId);

        if(!StringUtil.isEmpty(pageReqVO.getIntersectionName())){
            List<String> roadNameList = HanLPUtils.segmentForRoad(pageReqVO.getIntersectionName());
            if (!CollectionUtils.isEmpty(roadNameList)) {
                for (String roadName : roadNameList) {
                    lambdaQueryWrapperX.like(TrafficMonitorResultsDailyAnalysisDO::getIntersectionName, roadName);
                }
            }
        }
        if (OpsConstants.STRING_ZERO.equals(pageReqVO.getStatus())) { // 全断
            lambdaQueryWrapperX.eq(TrafficMonitorResultsDailyAnalysisDO::getNormalCnt, OpsConstants.STRING_ZERO);
        } else if (OpsConstants.STRING_ONE.equals(pageReqVO.getStatus())) { // 全通
            lambdaQueryWrapperX.eq(TrafficMonitorResultsDailyAnalysisDO::getAbnormalCnt, OpsConstants.STRING_ZERO);
        } else if (OpsConstants.STRING_TWO.equals(pageReqVO.getStatus())) {
            lambdaQueryWrapperX.ne(TrafficMonitorResultsDailyAnalysisDO::getNormalCnt, OpsConstants.STRING_ZERO);
            lambdaQueryWrapperX.ne(TrafficMonitorResultsDailyAnalysisDO::getAbnormalCnt, OpsConstants.STRING_ZERO);
        }

        if (!CollectionUtils.isEmpty(pageReqVO.getEquLableList())) {
            for (String equLabel : pageReqVO.getEquLableList()) {
                lambdaQueryWrapperX.like(TrafficMonitorResultsDailyAnalysisDO::getEquLable, equLabel);
            }
        }

        PageResult<TrafficMonitorResultsDailyAnalysisExtRespVO> pageResult = BeanUtils.toBean(monitorResultsDailyAnalysisMapper.selectPage(pageReqVO, lambdaQueryWrapperX), TrafficMonitorResultsDailyAnalysisExtRespVO.class);

        if (pageResult == null || CollectionUtils.isEmpty(pageResult.getList())) {
            return new PageResult<>();
        }

        Map<String, List<TrafficMonitorResultsDailyAnalysisExtRespVO>> resultMap = pageResult.getList().stream().collect(
                Collectors.groupingBy(dto -> StringUtils.isBlank(dto.getMemberBh()) ? "未绑定" : dto.getMemberBh(), LinkedHashMap::new, Collectors.toList()));


        LambdaQueryWrapper<TrafficOpsMemberDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(TrafficOpsMemberDO::getId, resultMap.keySet());
        List<TrafficOpsMemberDO> memberList = trafficOpsMemberMapper.selectList(queryWrapper);

        Map<String, TrafficOpsMemberDO> memberMap = Optional.ofNullable(memberList).orElse(new ArrayList<>()).stream().collect(Collectors.toMap(TrafficOpsMemberDO::getId, member -> member));


        pageResult.getList().forEach(dto -> {
            TrafficOpsMemberDO memberDO = memberMap.get(dto.getMemberBh());
            if (memberDO != null) {
                String serialNum = "";
                if (memberDO.getSerialNum() != null) {
                    serialNum = String.format("%03d", memberDO.getSerialNum());
                }
                String memberName = memberDO.getRoadName() + "/" + memberDO.getIntersectionName() + "/" + memberDO.getAdress() + "/" + serialNum;
                dto.setMemberName(memberName);
            }
        });

        return pageResult;
    }

}