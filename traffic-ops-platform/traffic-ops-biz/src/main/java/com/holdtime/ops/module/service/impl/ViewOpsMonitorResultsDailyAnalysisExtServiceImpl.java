package com.holdtime.ops.module.service.impl;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.fhs.common.utils.StringUtil;
import com.holdtime.ops.module.entity.TrafficOpsMemberDO;
import com.holdtime.ops.module.entity.ViewOpsMonitorResultsDailyAnalysisDO;
import com.holdtime.ops.module.entity.mapper.TrafficOpsMemberMapper;
import com.holdtime.ops.module.entity.mapper.ViewOpsMonitorResultsDailyAnalysisMapper;
import com.holdtime.ops.module.enums.OpsConstants;
import com.holdtime.ops.module.model.vo.ViewOpsMonitorResultsDailyAnalysisPageReqVO;
import com.holdtime.ops.module.model.vo.ViewOpsMonitorResultsDailyAnalysisRespVO;
import com.holdtime.ops.module.service.ViewOpsMonitorResultsDailyAnalysisExtService;
import com.holdtime.ops.module.utils.HanLPUtils;
import com.xingyuv.captcha.util.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 检测结果每日分析视图 Service 实现类
 *
 * @author 管理员
 */
@Service
@Validated
public class ViewOpsMonitorResultsDailyAnalysisExtServiceImpl implements ViewOpsMonitorResultsDailyAnalysisExtService {

    @Resource
    private ViewOpsMonitorResultsDailyAnalysisMapper viewOpsMonitorResultsDailyAnalysisMapper;
    @Resource
    private TrafficOpsMemberMapper trafficOpsMemberMapper;

    @Override
    public PageResult<ViewOpsMonitorResultsDailyAnalysisRespVO> getViewOpsMonitorResultsDailyAnalysisPage(ViewOpsMonitorResultsDailyAnalysisPageReqVO pageReqVO) {

        LambdaQueryWrapperX<ViewOpsMonitorResultsDailyAnalysisDO> lambdaQueryWrapperX = new LambdaQueryWrapperX<>();
        lambdaQueryWrapperX.eqIfPresent(ViewOpsMonitorResultsDailyAnalysisDO::getEquId, pageReqVO.getEquId());
        lambdaQueryWrapperX.eqIfPresent(ViewOpsMonitorResultsDailyAnalysisDO::getMemberBh, pageReqVO.getMemberBh());
        lambdaQueryWrapperX.likeIfPresent(ViewOpsMonitorResultsDailyAnalysisDO::getRoadName, pageReqVO.getRoadName());
        if (StringUtils.isNotBlank(pageReqVO.getOccurrenceDay())) {
            lambdaQueryWrapperX.and(wrapp -> {
                wrapp.eq(ViewOpsMonitorResultsDailyAnalysisDO::getOccurrenceDay,  pageReqVO.getOccurrenceDay());
                wrapp.or().isNull(ViewOpsMonitorResultsDailyAnalysisDO::getOccurrenceDay);
            });
        }

        if(!StringUtil.isEmpty(pageReqVO.getIntersectionName())){
            List<String> roadNameList = HanLPUtils.segmentForRoad(pageReqVO.getIntersectionName());
            if (!CollectionUtils.isEmpty(roadNameList)) {
                for (String roadName : roadNameList) {
                    lambdaQueryWrapperX.like(ViewOpsMonitorResultsDailyAnalysisDO::getIntersectionName, roadName);
                }
            }
        }
        lambdaQueryWrapperX.eqIfPresent(ViewOpsMonitorResultsDailyAnalysisDO::getDirection, pageReqVO.getDirection());
        lambdaQueryWrapperX.eqIfPresent(ViewOpsMonitorResultsDailyAnalysisDO::getIpAddr, pageReqVO.getIpAddr());
        lambdaQueryWrapperX.eqIfPresent(ViewOpsMonitorResultsDailyAnalysisDO::getEventType, pageReqVO.getEventType());
        lambdaQueryWrapperX.eqIfPresent(ViewOpsMonitorResultsDailyAnalysisDO::getDjType, pageReqVO.getDjType());
        if (OpsConstants.STRING_ZERO.equals(pageReqVO.getStatus())) { // 全断
            lambdaQueryWrapperX.eq(ViewOpsMonitorResultsDailyAnalysisDO::getNormalCnt, OpsConstants.STRING_ZERO);
        } else if (OpsConstants.STRING_ONE.equals(pageReqVO.getStatus())) { // 全通
            lambdaQueryWrapperX.eq(ViewOpsMonitorResultsDailyAnalysisDO::getAbnormalCnt, OpsConstants.STRING_ZERO);
        } else if (OpsConstants.STRING_TWO.equals(pageReqVO.getStatus())) {
            lambdaQueryWrapperX.ne(ViewOpsMonitorResultsDailyAnalysisDO::getNormalCnt, OpsConstants.STRING_ZERO);
            lambdaQueryWrapperX.ne(ViewOpsMonitorResultsDailyAnalysisDO::getAbnormalCnt, OpsConstants.STRING_ZERO);
        }

        if (!CollectionUtils.isEmpty(pageReqVO.getEquLableList())) {
            for (String equLabel : pageReqVO.getEquLableList()) {
                lambdaQueryWrapperX.like(ViewOpsMonitorResultsDailyAnalysisDO::getEquLable, equLabel);
            }
        }

        StringBuilder childSql = new StringBuilder();
        if (org.apache.commons.lang3.StringUtils.isNotBlank(pageReqVO.getKkbh())) {
            if ("true".equals(pageReqVO.getKkbh())) {
                childSql.append(" and e.kkbh is not null");
            } else {
                childSql.append(" and e.kkbh is null");
            }
            String existsSql = "select 1 from traffic_ops_electric_police e where e.id = v_ops_monitor_results_daily_analysis.equ_id" + childSql;
            lambdaQueryWrapperX.exists(existsSql);
        }

        PageResult<ViewOpsMonitorResultsDailyAnalysisRespVO> pageResult = BeanUtils.toBean(viewOpsMonitorResultsDailyAnalysisMapper.selectPage(pageReqVO, lambdaQueryWrapperX), ViewOpsMonitorResultsDailyAnalysisRespVO.class);

        if (pageResult == null || CollectionUtils.isEmpty(pageResult.getList())) {
            return new PageResult<>();
        }

        Map<String, List<ViewOpsMonitorResultsDailyAnalysisRespVO>> resultMap = pageResult.getList().stream().collect(
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