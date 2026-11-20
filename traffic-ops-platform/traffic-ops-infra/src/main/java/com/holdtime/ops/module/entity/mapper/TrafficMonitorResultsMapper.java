package com.holdtime.ops.module.entity.mapper;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import com.holdtime.ops.module.entity.TrafficMonitorResultsDO;
import org.apache.ibatis.annotations.Mapper;
import com.holdtime.ops.module.model.vo.*;

/**
 * 设备监测结果 Mapper
 *
 * @author 芋道源码
 */
@Mapper
public interface TrafficMonitorResultsMapper extends BaseMapperX<TrafficMonitorResultsDO> {

    default PageResult<TrafficMonitorResultsDO> selectPage(TrafficMonitorResultsPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<TrafficMonitorResultsDO>()
                .eqIfPresent(TrafficMonitorResultsDO::getEquType, reqVO.getEquType())
                .eqIfPresent(TrafficMonitorResultsDO::getIpAddr, reqVO.getIpAddr())
                .eqIfPresent(TrafficMonitorResultsDO::getDirection, reqVO.getDirection())
                .eqIfPresent(TrafficMonitorResultsDO::getEventType, reqVO.getEventType())
                .betweenIfPresent(TrafficMonitorResultsDO::getOccurrenceTime, reqVO.getOccurrenceTime())
                .eqIfPresent(TrafficMonitorResultsDO::getStatus, reqVO.getStatus())
                .eqIfPresent(TrafficMonitorResultsDO::getSource, reqVO.getSource())
                .eqIfPresent(TrafficMonitorResultsDO::getEquId, reqVO.getEquId())
                .eqIfPresent(TrafficMonitorResultsDO::getRoadName, reqVO.getRoadName())
                .eqIfPresent(TrafficMonitorResultsDO::getIntersectionName, reqVO.getIntersectionName())
                .eqIfPresent(TrafficMonitorResultsDO::getMemberBh, reqVO.getMemberBh())
                .betweenIfPresent(TrafficMonitorResultsDO::getCreateTime, reqVO.getCreateTime())
                .betweenIfPresent(TrafficMonitorResultsDO::getDeletedTime, reqVO.getDeletedTime())
                .orderByDesc(TrafficMonitorResultsDO::getId));
    }

}