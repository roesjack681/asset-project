package com.holdtime.ops.module.entity.mapper;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import com.holdtime.ops.module.entity.ViewOpsDjMonitorStatisticsDO;
import org.apache.ibatis.annotations.Mapper;
import com.holdtime.ops.module.model.vo.*;

/**
 * 电警监控信息 Mapper
 *
 * @author 管理员
 */
@Mapper
public interface ViewOpsDjMonitorStatisticsMapper extends BaseMapperX<ViewOpsDjMonitorStatisticsDO> {

    default PageResult<ViewOpsDjMonitorStatisticsDO> selectPage(ViewOpsDjMonitorStatisticsPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<ViewOpsDjMonitorStatisticsDO>()
                .eqIfPresent(ViewOpsDjMonitorStatisticsDO::getIpAddr, reqVO.getIpAddr())
                .eqIfPresent(ViewOpsDjMonitorStatisticsDO::getEventType, reqVO.getEventType())
                .betweenIfPresent(ViewOpsDjMonitorStatisticsDO::getOccurrenceTime, reqVO.getOccurrenceTime())
                .eqIfPresent(ViewOpsDjMonitorStatisticsDO::getStatus, reqVO.getStatus())
                .betweenIfPresent(ViewOpsDjMonitorStatisticsDO::getCxTime, reqVO.getCxTime())
                .betweenIfPresent(ViewOpsDjMonitorStatisticsDO::getAvgTime, reqVO.getAvgTime())
                .betweenIfPresent(ViewOpsDjMonitorStatisticsDO::getCreateTime, reqVO.getCreateTime())
                .betweenIfPresent(ViewOpsDjMonitorStatisticsDO::getDeletedTime, reqVO.getDeletedTime())
                .orderByDesc(ViewOpsDjMonitorStatisticsDO::getId));
    }

}