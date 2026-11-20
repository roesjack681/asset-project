package com.holdtime.ops.module.entity.mapper;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import com.holdtime.ops.module.entity.PoliceMonitorResultsDO;
import org.apache.ibatis.annotations.Mapper;
import com.holdtime.ops.module.model.vo.*;

/**
 * 公安设备监测结果 Mapper
 *
 * @author 管理员
 */
@Mapper
public interface PoliceMonitorResultsMapper extends BaseMapperX<PoliceMonitorResultsDO> {

    default PageResult<PoliceMonitorResultsDO> selectPage(PoliceMonitorResultsPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<PoliceMonitorResultsDO>()
                .eqIfPresent(PoliceMonitorResultsDO::getEquType, reqVO.getEquType())
                .eqIfPresent(PoliceMonitorResultsDO::getEquId, reqVO.getEquId())
                .eqIfPresent(PoliceMonitorResultsDO::getIpAddr, reqVO.getIpAddr())
                .likeIfPresent(PoliceMonitorResultsDO::getEquName, reqVO.getEquName())
                .eqIfPresent(PoliceMonitorResultsDO::getMemberBh, reqVO.getMemberBh())
                .eqIfPresent(PoliceMonitorResultsDO::getDirection, reqVO.getDirection())
                .eqIfPresent(PoliceMonitorResultsDO::getEventType, reqVO.getEventType())
                .betweenIfPresent(PoliceMonitorResultsDO::getOccurrenceTime, reqVO.getOccurrenceTime())
                .eqIfPresent(PoliceMonitorResultsDO::getStatus, reqVO.getStatus())
                .eqIfPresent(PoliceMonitorResultsDO::getResultImg, reqVO.getResultImg())
                .eqIfPresent(PoliceMonitorResultsDO::getIsRepairOrder, reqVO.getIsRepairOrder())
                .eqIfPresent(PoliceMonitorResultsDO::getMaintenanceId, reqVO.getMaintenanceId())
                .eqIfPresent(PoliceMonitorResultsDO::getMaintenanceUserId, reqVO.getMaintenanceUserId())
                .eqIfPresent(PoliceMonitorResultsDO::getMaintenancePersonnel, reqVO.getMaintenancePersonnel())
                .eqIfPresent(PoliceMonitorResultsDO::getMaintenancePhone, reqVO.getMaintenancePhone())
                .eqIfPresent(PoliceMonitorResultsDO::getSource, reqVO.getSource())
                .betweenIfPresent(PoliceMonitorResultsDO::getCreateTime, reqVO.getCreateTime())
                .betweenIfPresent(PoliceMonitorResultsDO::getDeletedTime, reqVO.getDeletedTime())
                .orderByDesc(PoliceMonitorResultsDO::getId));
    }

}