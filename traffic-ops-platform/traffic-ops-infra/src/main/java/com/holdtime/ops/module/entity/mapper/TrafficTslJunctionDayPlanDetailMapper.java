package com.holdtime.ops.module.entity.mapper;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import com.holdtime.ops.module.entity.TrafficTslJunctionDayPlanDetailDO;
import org.apache.ibatis.annotations.Mapper;
import com.holdtime.ops.module.model.vo.*;

/**
 * 信控交叉口日方案详细信息 Mapper
 *
 * @author yangli
 */
@Mapper
public interface TrafficTslJunctionDayPlanDetailMapper extends BaseMapperX<TrafficTslJunctionDayPlanDetailDO> {

    default PageResult<TrafficTslJunctionDayPlanDetailDO> selectPage(TrafficTslJunctionDayPlanDetailPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<TrafficTslJunctionDayPlanDetailDO>()
                .eqIfPresent(TrafficTslJunctionDayPlanDetailDO::getSegmentId, reqVO.getSegmentId())
                .eqIfPresent(TrafficTslJunctionDayPlanDetailDO::getDayPlansId, reqVO.getDayPlansId())
                .eqIfPresent(TrafficTslJunctionDayPlanDetailDO::getJunctionId, reqVO.getJunctionId())
                .eqIfPresent(TrafficTslJunctionDayPlanDetailDO::getPlanHour, reqVO.getPlanHour())
                .eqIfPresent(TrafficTslJunctionDayPlanDetailDO::getPlanMinute, reqVO.getPlanMinute())
                .eqIfPresent(TrafficTslJunctionDayPlanDetailDO::getPlanId, reqVO.getPlanId())
                .eqIfPresent(TrafficTslJunctionDayPlanDetailDO::getActuId, reqVO.getActuId())
                .eqIfPresent(TrafficTslJunctionDayPlanDetailDO::getStageListId, reqVO.getStageListId())
                .eqIfPresent(TrafficTslJunctionDayPlanDetailDO::getPhaseDifference, reqVO.getPhaseDifference())
                .betweenIfPresent(TrafficTslJunctionDayPlanDetailDO::getCycleTime, reqVO.getCycleTime())
                .betweenIfPresent(TrafficTslJunctionDayPlanDetailDO::getCreateTime, reqVO.getCreateTime())
                .betweenIfPresent(TrafficTslJunctionDayPlanDetailDO::getDeletedTime, reqVO.getDeletedTime())
                .orderByDesc(TrafficTslJunctionDayPlanDetailDO::getId));
    }

}