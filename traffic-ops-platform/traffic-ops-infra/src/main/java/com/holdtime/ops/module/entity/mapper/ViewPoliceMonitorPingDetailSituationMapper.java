package com.holdtime.ops.module.entity.mapper;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import com.holdtime.ops.module.entity.ViewPoliceMonitorPingDetailSituationDO;
import org.apache.ibatis.annotations.Mapper;
import com.holdtime.ops.module.model.vo.*;

/**
 * VIEW Mapper
 *
 * @author yanxl
 */
@Mapper
public interface ViewPoliceMonitorPingDetailSituationMapper extends BaseMapperX<ViewPoliceMonitorPingDetailSituationDO> {

    default PageResult<ViewPoliceMonitorPingDetailSituationDO> selectPage(ViewPoliceMonitorPingDetailSituationPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<ViewPoliceMonitorPingDetailSituationDO>()
                .eqIfPresent(ViewPoliceMonitorPingDetailSituationDO::getEquId, reqVO.getEquId())
                .eqIfPresent(ViewPoliceMonitorPingDetailSituationDO::getLatestOccurrenceDay, reqVO.getLatestOccurrenceDay())
                .eqIfPresent(ViewPoliceMonitorPingDetailSituationDO::getPreviousOccurrenceDay, reqVO.getPreviousOccurrenceDay())
                .eqIfPresent(ViewPoliceMonitorPingDetailSituationDO::getLatestPingStatus, reqVO.getLatestPingStatus())
                .eqIfPresent(ViewPoliceMonitorPingDetailSituationDO::getPreviousPingStatus, reqVO.getPreviousPingStatus())
                .eqIfPresent(ViewPoliceMonitorPingDetailSituationDO::getFinalPingStatus, reqVO.getFinalPingStatus())
                .orderByDesc(ViewPoliceMonitorPingDetailSituationDO::getEquId));
    }

}