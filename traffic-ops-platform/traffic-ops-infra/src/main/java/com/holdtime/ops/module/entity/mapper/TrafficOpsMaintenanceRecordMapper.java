package com.holdtime.ops.module.entity.mapper;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import com.holdtime.ops.module.entity.TrafficOpsMaintenanceDO;
import com.holdtime.ops.module.entity.TrafficOpsMaintenanceRecordDO;
import org.apache.ibatis.annotations.Mapper;
import com.holdtime.ops.module.model.vo.*;

/**
 * 维保记录 Mapper
 *
 * @author yangli
 */
@Mapper
public interface TrafficOpsMaintenanceRecordMapper extends BaseMapperX<TrafficOpsMaintenanceRecordDO> {

    default PageResult<TrafficOpsMaintenanceRecordDO> selectPage(TrafficOpsMaintenanceRecordPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<TrafficOpsMaintenanceRecordDO>()
                .eqIfPresent(TrafficOpsMaintenanceRecordDO::getAffiliatedUnit, reqVO.getAffiliatedUnit())
                .eqIfPresent(TrafficOpsMaintenanceRecordDO::getEquType, reqVO.getEquType())
                .eqIfPresent(TrafficOpsMaintenanceRecordDO::getEquId, reqVO.getEquId())
                .likeIfPresent(TrafficOpsMaintenanceRecordDO::getEquAddr, reqVO.getEquAddr())
                .eqIfPresent(TrafficOpsMaintenanceRecordDO::getEquLng, reqVO.getEquLng())
                .eqIfPresent(TrafficOpsMaintenanceRecordDO::getEquLat, reqVO.getEquLat())
                .likeIfPresent(TrafficOpsMaintenanceRecordDO::getProblem, reqVO.getProblem())
                .likeIfPresent(TrafficOpsMaintenanceRecordDO::getFaultType, reqVO.getFaultType())
                .betweenIfPresent(TrafficOpsMaintenanceRecordDO::getFaultDate, reqVO.getFaultDate())
                .likeIfPresent(TrafficOpsMaintenanceRecordDO::getReportPersonnel, reqVO.getReportPersonnel())
                .likeIfPresent(TrafficOpsMaintenanceRecordDO::getMaintenancePersonnel, reqVO.getMaintenancePersonnel())
                .eqIfPresent(TrafficOpsMaintenanceRecordDO::getReportUserId, reqVO.getReportUserId())
                .betweenIfPresent(TrafficOpsMaintenanceRecordDO::getReportTime, reqVO.getReportTime())
                .eqIfPresent(TrafficOpsMaintenanceRecordDO::getReportDeptId, reqVO.getReportDeptId())
                .eqIfPresent(TrafficOpsMaintenanceRecordDO::getReportDept, reqVO.getReportDept())
                .eqIfPresent(TrafficOpsMaintenanceRecordDO::getDispatchUserId, reqVO.getDispatchUserId())
                .eqIfPresent(TrafficOpsMaintenanceRecordDO::getDispatch, reqVO.getDispatch())
                .betweenIfPresent(TrafficOpsMaintenanceRecordDO::getDispatchTime, reqVO.getDispatchTime())
                .eqIfPresent(TrafficOpsMaintenanceRecordDO::getSuggestion, reqVO.getSuggestion())
                .eqIfPresent(TrafficOpsMaintenanceRecordDO::getStatus, reqVO.getStatus())
                .likeIfPresent(TrafficOpsMaintenanceRecordDO::getRemark, reqVO.getRemark())
                .likeIfPresent(TrafficOpsMaintenanceRecordDO::getConfirmor, reqVO.getConfirmor())
                .betweenIfPresent(TrafficOpsMaintenanceRecordDO::getConfirmDate, reqVO.getConfirmDate())
                .betweenIfPresent(TrafficOpsMaintenanceRecordDO::getFinishTime, reqVO.getFinishTime())
                .betweenIfPresent(TrafficOpsMaintenanceRecordDO::getCreateTime, reqVO.getCreateTime())
                .betweenIfPresent(TrafficOpsMaintenanceRecordDO::getDeletedTime, reqVO.getDeletedTime())
                .eqIfPresent(TrafficOpsMaintenanceRecordDO::getSource, reqVO.getSource())
                .eqIfPresent(TrafficOpsMaintenanceRecordDO::getProjectedRepair, reqVO.getProjectedRepair())
                .eqIfPresent(TrafficOpsMaintenanceRecordDO::getActualRepair, reqVO.getActualRepair())
                .orderByDesc(TrafficOpsMaintenanceRecordDO::getId));
    }

}