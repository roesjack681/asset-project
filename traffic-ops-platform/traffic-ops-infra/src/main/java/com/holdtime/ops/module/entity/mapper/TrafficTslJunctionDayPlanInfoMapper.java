package com.holdtime.ops.module.entity.mapper;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import com.holdtime.ops.module.entity.TrafficTslJunctionDayPlanInfoDO;
import org.apache.ibatis.annotations.Mapper;
import com.holdtime.ops.module.model.vo.*;

/**
 * 信控交叉口日方案信息 Mapper
 *
 * @author yangli
 */
@Mapper
public interface TrafficTslJunctionDayPlanInfoMapper extends BaseMapperX<TrafficTslJunctionDayPlanInfoDO> {

    default PageResult<TrafficTslJunctionDayPlanInfoDO> selectPage(TrafficTslJunctionDayPlanInfoPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<TrafficTslJunctionDayPlanInfoDO>()
                .eqIfPresent(TrafficTslJunctionDayPlanInfoDO::getDayPlansId, reqVO.getDayPlansId())
                .eqIfPresent(TrafficTslJunctionDayPlanInfoDO::getJunctionId, reqVO.getJunctionId())
                .eqIfPresent(TrafficTslJunctionDayPlanInfoDO::getControllerId, reqVO.getControllerId())
                .eqIfPresent(TrafficTslJunctionDayPlanInfoDO::getFlag, reqVO.getFlag())
                .eqIfPresent(TrafficTslJunctionDayPlanInfoDO::getHolidayPlanUse, reqVO.getHolidayPlanUse())
                .eqIfPresent(TrafficTslJunctionDayPlanInfoDO::getSegmentCount, reqVO.getSegmentCount())
                .eqIfPresent(TrafficTslJunctionDayPlanInfoDO::getIsUse, reqVO.getIsUse())
                .betweenIfPresent(TrafficTslJunctionDayPlanInfoDO::getCreateTime, reqVO.getCreateTime())
                .betweenIfPresent(TrafficTslJunctionDayPlanInfoDO::getDeletedTime, reqVO.getDeletedTime())
                .orderByDesc(TrafficTslJunctionDayPlanInfoDO::getId));
    }

}