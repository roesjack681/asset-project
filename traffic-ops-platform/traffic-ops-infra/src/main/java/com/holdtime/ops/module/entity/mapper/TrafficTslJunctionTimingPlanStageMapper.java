package com.holdtime.ops.module.entity.mapper;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import com.holdtime.ops.module.entity.TrafficTslJunctionTimingPlanStageDO;
import org.apache.ibatis.annotations.Mapper;
import com.holdtime.ops.module.model.vo.*;

/**
 * 信控交叉口配时方案阶段信息 Mapper
 *
 * @author yangli
 */
@Mapper
public interface TrafficTslJunctionTimingPlanStageMapper extends BaseMapperX<TrafficTslJunctionTimingPlanStageDO> {

    default PageResult<TrafficTslJunctionTimingPlanStageDO> selectPage(TrafficTslJunctionTimingPlanStagePageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<TrafficTslJunctionTimingPlanStageDO>()
                .eqIfPresent(TrafficTslJunctionTimingPlanStageDO::getJunctionId, reqVO.getJunctionId())
                .eqIfPresent(TrafficTslJunctionTimingPlanStageDO::getControllerId, reqVO.getControllerId())
                .eqIfPresent(TrafficTslJunctionTimingPlanStageDO::getStageId, reqVO.getStageId())
                .betweenIfPresent(TrafficTslJunctionTimingPlanStageDO::getGreeTime, reqVO.getGreeTime())
                .betweenIfPresent(TrafficTslJunctionTimingPlanStageDO::getMinGreeTime, reqVO.getMinGreeTime())
                .betweenIfPresent(TrafficTslJunctionTimingPlanStageDO::getMaxGreeTime, reqVO.getMaxGreeTime())
                .betweenIfPresent(TrafficTslJunctionTimingPlanStageDO::getYellowTime, reqVO.getYellowTime())
                .betweenIfPresent(TrafficTslJunctionTimingPlanStageDO::getAllRedTime, reqVO.getAllRedTime())
                .eqIfPresent(TrafficTslJunctionTimingPlanStageDO::getPedFlash, reqVO.getPedFlash())
                .eqIfPresent(TrafficTslJunctionTimingPlanStageDO::getPedRed, reqVO.getPedRed())
                .eqIfPresent(TrafficTslJunctionTimingPlanStageDO::getGreenFlash, reqVO.getGreenFlash())
                .betweenIfPresent(TrafficTslJunctionTimingPlanStageDO::getCreateTime, reqVO.getCreateTime())
                .betweenIfPresent(TrafficTslJunctionTimingPlanStageDO::getDeletedTime, reqVO.getDeletedTime())
                .orderByDesc(TrafficTslJunctionTimingPlanStageDO::getId));
    }

}