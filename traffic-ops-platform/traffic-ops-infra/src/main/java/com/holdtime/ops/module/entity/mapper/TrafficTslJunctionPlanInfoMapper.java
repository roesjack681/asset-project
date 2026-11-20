package com.holdtime.ops.module.entity.mapper;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import com.holdtime.ops.module.entity.TrafficTslJunctionPlanInfoDO;
import org.apache.ibatis.annotations.Mapper;
import com.holdtime.ops.module.model.vo.*;

/**
 * 信控交叉口方案信息 Mapper
 *
 * @author yangli
 */
@Mapper
public interface TrafficTslJunctionPlanInfoMapper extends BaseMapperX<TrafficTslJunctionPlanInfoDO> {

    default PageResult<TrafficTslJunctionPlanInfoDO> selectPage(TrafficTslJunctionPlanInfoPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<TrafficTslJunctionPlanInfoDO>()
                .eqIfPresent(TrafficTslJunctionPlanInfoDO::getControllerId, reqVO.getControllerId())
                .eqIfPresent(TrafficTslJunctionPlanInfoDO::getWeekPlans, reqVO.getWeekPlans())
                .betweenIfPresent(TrafficTslJunctionPlanInfoDO::getHolidaysStartDate, reqVO.getHolidaysStartDate())
                .betweenIfPresent(TrafficTslJunctionPlanInfoDO::getHolidaysEndDate, reqVO.getHolidaysEndDate())
                .eqIfPresent(TrafficTslJunctionPlanInfoDO::getHolidayPlans, reqVO.getHolidayPlans())
                .betweenIfPresent(TrafficTslJunctionPlanInfoDO::getCreateTime, reqVO.getCreateTime())
                .betweenIfPresent(TrafficTslJunctionPlanInfoDO::getDeletedTime, reqVO.getDeletedTime())
                .orderByDesc(TrafficTslJunctionPlanInfoDO::getId));
    }

}