package com.holdtime.ops.module.entity.mapper;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import com.holdtime.ops.module.entity.TrafficDjMonitorStatisticsDO;
import org.apache.ibatis.annotations.Mapper;
import com.holdtime.ops.module.model.vo.*;

/**
 * 监测电警设备统计 Mapper
 *
 * @author 管理员
 */
@Mapper
public interface TrafficDjMonitorStatisticsMapper extends BaseMapperX<TrafficDjMonitorStatisticsDO> {

    default PageResult<TrafficDjMonitorStatisticsDO> selectPage(TrafficDjMonitorStatisticsPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<TrafficDjMonitorStatisticsDO>()
                .eqIfPresent(TrafficDjMonitorStatisticsDO::getIpAddr, reqVO.getIpAddr())
                .eqIfPresent(TrafficDjMonitorStatisticsDO::getEventType, reqVO.getEventType())
                .betweenIfPresent(TrafficDjMonitorStatisticsDO::getOccurrenceTime, reqVO.getOccurrenceTime())
                .eqIfPresent(TrafficDjMonitorStatisticsDO::getStatus, reqVO.getStatus())
                .betweenIfPresent(TrafficDjMonitorStatisticsDO::getCxTime, reqVO.getCxTime())
                .betweenIfPresent(TrafficDjMonitorStatisticsDO::getAvgTime, reqVO.getAvgTime())
                .betweenIfPresent(TrafficDjMonitorStatisticsDO::getCreateTime, reqVO.getCreateTime())
                .betweenIfPresent(TrafficDjMonitorStatisticsDO::getDeletedTime, reqVO.getDeletedTime())
                .orderByDesc(TrafficDjMonitorStatisticsDO::getId));
    }

}