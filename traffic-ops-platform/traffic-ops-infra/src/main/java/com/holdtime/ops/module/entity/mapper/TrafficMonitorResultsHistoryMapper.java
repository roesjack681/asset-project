package com.holdtime.ops.module.entity.mapper;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import com.holdtime.ops.module.entity.TrafficMonitorResultsHistoryDO;
import org.apache.ibatis.annotations.Mapper;
import com.holdtime.ops.module.model.vo.*;

/**
 * 设备监测结果历史 Mapper
 *
 * @author 管理员
 */
@Mapper
public interface TrafficMonitorResultsHistoryMapper extends BaseMapperX<TrafficMonitorResultsHistoryDO> {

    default PageResult<TrafficMonitorResultsHistoryDO> selectPage(TrafficMonitorResultsHistoryPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<TrafficMonitorResultsHistoryDO>()
                .eqIfPresent(TrafficMonitorResultsHistoryDO::getEquType, reqVO.getEquType())
                .eqIfPresent(TrafficMonitorResultsHistoryDO::getIpAddr, reqVO.getIpAddr())
                .eqIfPresent(TrafficMonitorResultsHistoryDO::getDirection, reqVO.getDirection())
                .eqIfPresent(TrafficMonitorResultsHistoryDO::getEventType, reqVO.getEventType())
                .betweenIfPresent(TrafficMonitorResultsHistoryDO::getOccurrenceTime, reqVO.getOccurrenceTime())
                .eqIfPresent(TrafficMonitorResultsHistoryDO::getStatus, reqVO.getStatus())
                .betweenIfPresent(TrafficMonitorResultsHistoryDO::getCreateTime, reqVO.getCreateTime())
                .betweenIfPresent(TrafficMonitorResultsHistoryDO::getDeletedTime, reqVO.getDeletedTime())
                .eqIfPresent(TrafficMonitorResultsHistoryDO::getSource, reqVO.getSource())
                .eqIfPresent(TrafficMonitorResultsHistoryDO::getEquId, reqVO.getEquId())
                .likeIfPresent(TrafficMonitorResultsHistoryDO::getRoadName, reqVO.getRoadName())
                .likeIfPresent(TrafficMonitorResultsHistoryDO::getIntersectionName, reqVO.getIntersectionName())
                .eqIfPresent(TrafficMonitorResultsHistoryDO::getMemberBh, reqVO.getMemberBh())
                .orderByDesc(TrafficMonitorResultsHistoryDO::getId));
    }

}