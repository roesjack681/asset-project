package com.holdtime.ops.module.entity.mapper;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import com.holdtime.ops.module.entity.ViewOpsMonitorResultsDailyAnalysisDO;
import org.apache.ibatis.annotations.Mapper;
import com.holdtime.ops.module.model.vo.*;

/**
 * 检测结果每日分析视图 Mapper
 *
 * @author 管理员
 */
@Mapper
public interface ViewOpsMonitorResultsDailyAnalysisMapper extends BaseMapperX<ViewOpsMonitorResultsDailyAnalysisDO> {

    default PageResult<ViewOpsMonitorResultsDailyAnalysisDO> selectPage(ViewOpsMonitorResultsDailyAnalysisPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<ViewOpsMonitorResultsDailyAnalysisDO>()
                .eqIfPresent(ViewOpsMonitorResultsDailyAnalysisDO::getEquId, reqVO.getEquId())
                .eqIfPresent(ViewOpsMonitorResultsDailyAnalysisDO::getMemberBh, reqVO.getMemberBh())
                .likeIfPresent(ViewOpsMonitorResultsDailyAnalysisDO::getRoadName, reqVO.getRoadName())
                .likeIfPresent(ViewOpsMonitorResultsDailyAnalysisDO::getIntersectionName, reqVO.getIntersectionName())
                .eqIfPresent(ViewOpsMonitorResultsDailyAnalysisDO::getDirection, reqVO.getDirection())
                .eqIfPresent(ViewOpsMonitorResultsDailyAnalysisDO::getIpAddr, reqVO.getIpAddr())
                .eqIfPresent(ViewOpsMonitorResultsDailyAnalysisDO::getDjType, reqVO.getDjType())
                .eqIfPresent(ViewOpsMonitorResultsDailyAnalysisDO::getEquLable, reqVO.getEquLable())
                .eqIfPresent(ViewOpsMonitorResultsDailyAnalysisDO::getEventType, reqVO.getEventType())
                .eqIfPresent(ViewOpsMonitorResultsDailyAnalysisDO::getOccurrenceDay, reqVO.getOccurrenceDay())
                .eqIfPresent(ViewOpsMonitorResultsDailyAnalysisDO::getNormalCnt, reqVO.getNormalCnt())
                .eqIfPresent(ViewOpsMonitorResultsDailyAnalysisDO::getAbnormalCnt, reqVO.getAbnormalCnt())
                .eqIfPresent(ViewOpsMonitorResultsDailyAnalysisDO::getMonitorSumDays, reqVO.getMonitorSumDays())
                .eqIfPresent(ViewOpsMonitorResultsDailyAnalysisDO::getMonitorSumCnt, reqVO.getMonitorSumCnt())
                .eqIfPresent(ViewOpsMonitorResultsDailyAnalysisDO::getHealthRate, reqVO.getHealthRate())
                .orderByDesc(ViewOpsMonitorResultsDailyAnalysisDO::getEquId));
    }

}