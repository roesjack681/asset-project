package com.holdtime.ops.module.entity.mapper;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import com.holdtime.ops.module.entity.TrafficTslJunctionTimingPlanDO;
import org.apache.ibatis.annotations.Mapper;
import com.holdtime.ops.module.model.vo.*;

/**
 * 信控交叉口配时方案信息 Mapper
 *
 * @author yangli
 */
@Mapper
public interface TrafficTslJunctionTimingPlanMapper extends BaseMapperX<TrafficTslJunctionTimingPlanDO> {

    default PageResult<TrafficTslJunctionTimingPlanDO> selectPage(TrafficTslJunctionTimingPlanPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<TrafficTslJunctionTimingPlanDO>()
                .eqIfPresent(TrafficTslJunctionTimingPlanDO::getPlanId, reqVO.getPlanId())
                .eqIfPresent(TrafficTslJunctionTimingPlanDO::getJunctionId, reqVO.getJunctionId())
                .eqIfPresent(TrafficTslJunctionTimingPlanDO::getControllerId, reqVO.getControllerId())
                .eqIfPresent(TrafficTslJunctionTimingPlanDO::getFlag, reqVO.getFlag())
                .eqIfPresent(TrafficTslJunctionTimingPlanDO::getStageListId, reqVO.getStageListId())
                .eqIfPresent(TrafficTslJunctionTimingPlanDO::getStageCount, reqVO.getStageCount())
                .eqIfPresent(TrafficTslJunctionTimingPlanDO::getDirect, reqVO.getDirect())
                .betweenIfPresent(TrafficTslJunctionTimingPlanDO::getCycleTime, reqVO.getCycleTime())
                .eqIfPresent(TrafficTslJunctionTimingPlanDO::getPhaseDifference, reqVO.getPhaseDifference())
                .eqIfPresent(TrafficTslJunctionTimingPlanDO::getIsUse, reqVO.getIsUse())
                .betweenIfPresent(TrafficTslJunctionTimingPlanDO::getCreateTime, reqVO.getCreateTime())
                .betweenIfPresent(TrafficTslJunctionTimingPlanDO::getDeletedTime, reqVO.getDeletedTime())
                .orderByDesc(TrafficTslJunctionTimingPlanDO::getId));
    }

}