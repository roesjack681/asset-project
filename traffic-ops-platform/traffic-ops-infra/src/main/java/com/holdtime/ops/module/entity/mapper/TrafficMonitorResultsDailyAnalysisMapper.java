package com.holdtime.ops.module.entity.mapper;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import com.holdtime.ops.module.entity.TrafficMonitorResultsDailyAnalysisDO;
import org.apache.ibatis.annotations.Mapper;
import com.holdtime.ops.module.model.vo.*;

/**
 * 设备监测结果每日分析 Mapper
 *
 * @author 管理员
 */
@Mapper
public interface TrafficMonitorResultsDailyAnalysisMapper extends BaseMapperX<TrafficMonitorResultsDailyAnalysisDO> {

    default PageResult<TrafficMonitorResultsDailyAnalysisDO> selectPage(TrafficMonitorResultsDailyAnalysisPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<TrafficMonitorResultsDailyAnalysisDO>()
                .betweenIfPresent(TrafficMonitorResultsDailyAnalysisDO::getAnalysisDate, reqVO.getAnalysisDate())
                .eqIfPresent(TrafficMonitorResultsDailyAnalysisDO::getEquId, reqVO.getEquId())
                .eqIfPresent(TrafficMonitorResultsDailyAnalysisDO::getMemberBh, reqVO.getMemberBh())
                .likeIfPresent(TrafficMonitorResultsDailyAnalysisDO::getRoadName, reqVO.getRoadName())
                .likeIfPresent(TrafficMonitorResultsDailyAnalysisDO::getIntersectionName, reqVO.getIntersectionName())
                .eqIfPresent(TrafficMonitorResultsDailyAnalysisDO::getDirection, reqVO.getDirection())
                .eqIfPresent(TrafficMonitorResultsDailyAnalysisDO::getIpAddr, reqVO.getIpAddr())
                .eqIfPresent(TrafficMonitorResultsDailyAnalysisDO::getDjType, reqVO.getDjType())
                .eqIfPresent(TrafficMonitorResultsDailyAnalysisDO::getEquLable, reqVO.getEquLable())
                .eqIfPresent(TrafficMonitorResultsDailyAnalysisDO::getEventType, reqVO.getEventType())
                .eqIfPresent(TrafficMonitorResultsDailyAnalysisDO::getNormalCnt, reqVO.getNormalCnt())
                .eqIfPresent(TrafficMonitorResultsDailyAnalysisDO::getAbnormalCnt, reqVO.getAbnormalCnt())
                .eqIfPresent(TrafficMonitorResultsDailyAnalysisDO::getMonitorSumDays, reqVO.getMonitorSumDays())
                .eqIfPresent(TrafficMonitorResultsDailyAnalysisDO::getMonitorSumCnt, reqVO.getMonitorSumCnt())
                .eqIfPresent(TrafficMonitorResultsDailyAnalysisDO::getHealthRate, reqVO.getHealthRate())
                .betweenIfPresent(TrafficMonitorResultsDailyAnalysisDO::getCreateTime, reqVO.getCreateTime())
                .betweenIfPresent(TrafficMonitorResultsDailyAnalysisDO::getDeletedTime, reqVO.getDeletedTime())
                .orderByDesc(TrafficMonitorResultsDailyAnalysisDO::getId));
    }

}