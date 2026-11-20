package com.holdtime.ops.module.entity.mapper;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import com.holdtime.ops.module.entity.TrafficMonitorResultsDailyDetailDO;
import org.apache.ibatis.annotations.Mapper;
import com.holdtime.ops.module.model.vo.*;

/**
 * 设备监测结果每日分析详情 Mapper
 *
 * @author 管理员
 */
@Mapper
public interface TrafficMonitorResultsDailyDetailMapper extends BaseMapperX<TrafficMonitorResultsDailyDetailDO> {

    default PageResult<TrafficMonitorResultsDailyDetailDO> selectPage(TrafficMonitorResultsDailyDetailPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<TrafficMonitorResultsDailyDetailDO>()
                .eqIfPresent(TrafficMonitorResultsDailyDetailDO::getAnalysisId, reqVO.getAnalysisId())
                .eqIfPresent(TrafficMonitorResultsDailyDetailDO::getEquType, reqVO.getEquType())
                .eqIfPresent(TrafficMonitorResultsDailyDetailDO::getIpAddr, reqVO.getIpAddr())
                .eqIfPresent(TrafficMonitorResultsDailyDetailDO::getDirection, reqVO.getDirection())
                .eqIfPresent(TrafficMonitorResultsDailyDetailDO::getEventType, reqVO.getEventType())
                .betweenIfPresent(TrafficMonitorResultsDailyDetailDO::getOccurrenceTime, reqVO.getOccurrenceTime())
                .eqIfPresent(TrafficMonitorResultsDailyDetailDO::getStatus, reqVO.getStatus())
                .eqIfPresent(TrafficMonitorResultsDailyDetailDO::getSource, reqVO.getSource())
                .eqIfPresent(TrafficMonitorResultsDailyDetailDO::getEquId, reqVO.getEquId())
                .likeIfPresent(TrafficMonitorResultsDailyDetailDO::getRoadName, reqVO.getRoadName())
                .likeIfPresent(TrafficMonitorResultsDailyDetailDO::getIntersectionName, reqVO.getIntersectionName())
                .eqIfPresent(TrafficMonitorResultsDailyDetailDO::getMemberBh, reqVO.getMemberBh())
                .betweenIfPresent(TrafficMonitorResultsDailyDetailDO::getCreateTime, reqVO.getCreateTime())
                .betweenIfPresent(TrafficMonitorResultsDailyDetailDO::getDeletedTime, reqVO.getDeletedTime())
                .orderByDesc(TrafficMonitorResultsDailyDetailDO::getId));
    }

}