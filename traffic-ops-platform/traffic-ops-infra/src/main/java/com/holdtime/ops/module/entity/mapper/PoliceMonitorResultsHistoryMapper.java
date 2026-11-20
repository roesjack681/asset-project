package com.holdtime.ops.module.entity.mapper;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import com.holdtime.ops.module.entity.PoliceMonitorResultsHistoryDO;
import org.apache.ibatis.annotations.Mapper;
import com.holdtime.ops.module.model.vo.*;

/**
 * 公安设备监测结果历史 Mapper
 *
 * @author yanxl
 */
@Mapper
public interface PoliceMonitorResultsHistoryMapper extends BaseMapperX<PoliceMonitorResultsHistoryDO> {

    default PageResult<PoliceMonitorResultsHistoryDO> selectPage(PoliceMonitorResultsHistoryPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<PoliceMonitorResultsHistoryDO>()
                .betweenIfPresent(PoliceMonitorResultsHistoryDO::getMonitorDate, reqVO.getMonitorDate())
                .eqIfPresent(PoliceMonitorResultsHistoryDO::getEquType, reqVO.getEquType())
                .eqIfPresent(PoliceMonitorResultsHistoryDO::getEquId, reqVO.getEquId())
                .eqIfPresent(PoliceMonitorResultsHistoryDO::getIpAddr, reqVO.getIpAddr())
                .likeIfPresent(PoliceMonitorResultsHistoryDO::getEquName, reqVO.getEquName())
                .eqIfPresent(PoliceMonitorResultsHistoryDO::getMemberBh, reqVO.getMemberBh())
                .eqIfPresent(PoliceMonitorResultsHistoryDO::getDirection, reqVO.getDirection())
                .eqIfPresent(PoliceMonitorResultsHistoryDO::getEventType, reqVO.getEventType())
                .betweenIfPresent(PoliceMonitorResultsHistoryDO::getOccurrenceTime, reqVO.getOccurrenceTime())
                .eqIfPresent(PoliceMonitorResultsHistoryDO::getStatus, reqVO.getStatus())
                .eqIfPresent(PoliceMonitorResultsHistoryDO::getResultImg, reqVO.getResultImg())
                .eqIfPresent(PoliceMonitorResultsHistoryDO::getIsRepairOrder, reqVO.getIsRepairOrder())
                .eqIfPresent(PoliceMonitorResultsHistoryDO::getMaintenanceId, reqVO.getMaintenanceId())
                .eqIfPresent(PoliceMonitorResultsHistoryDO::getMaintenanceUserId, reqVO.getMaintenanceUserId())
                .eqIfPresent(PoliceMonitorResultsHistoryDO::getMaintenancePersonnel, reqVO.getMaintenancePersonnel())
                .eqIfPresent(PoliceMonitorResultsHistoryDO::getMaintenancePhone, reqVO.getMaintenancePhone())
                .eqIfPresent(PoliceMonitorResultsHistoryDO::getEquSituation, reqVO.getEquSituation())
                .eqIfPresent(PoliceMonitorResultsHistoryDO::getEquDesc, reqVO.getEquDesc())
                .eqIfPresent(PoliceMonitorResultsHistoryDO::getEvidenceFileId, reqVO.getEvidenceFileId())
                .betweenIfPresent(PoliceMonitorResultsHistoryDO::getRemarkDate, reqVO.getRemarkDate())
                .eqIfPresent(PoliceMonitorResultsHistoryDO::getChangeSituation, reqVO.getChangeSituation())
                .betweenIfPresent(PoliceMonitorResultsHistoryDO::getCreateTime, reqVO.getCreateTime())
                .betweenIfPresent(PoliceMonitorResultsHistoryDO::getDeletedTime, reqVO.getDeletedTime())
                .orderByDesc(PoliceMonitorResultsHistoryDO::getId));
    }

}