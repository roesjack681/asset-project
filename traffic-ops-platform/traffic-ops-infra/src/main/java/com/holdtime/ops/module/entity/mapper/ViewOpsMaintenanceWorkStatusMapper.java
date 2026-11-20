package com.holdtime.ops.module.entity.mapper;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import com.holdtime.ops.module.entity.ViewOpsMaintenanceWorkStatusDO;
import org.apache.ibatis.annotations.Mapper;
import com.holdtime.ops.module.model.vo.*;

/**
 * VIEW Mapper
 *
 * @author 芋道源码
 */
@Mapper
public interface ViewOpsMaintenanceWorkStatusMapper extends BaseMapperX<ViewOpsMaintenanceWorkStatusDO> {

    default PageResult<ViewOpsMaintenanceWorkStatusDO> selectPage(ViewOpsMaintenanceWorkStatusPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<ViewOpsMaintenanceWorkStatusDO>()
                .eqIfPresent(ViewOpsMaintenanceWorkStatusDO::getEquType, reqVO.getEquType())
                .eqIfPresent(ViewOpsMaintenanceWorkStatusDO::getEquId, reqVO.getEquId())
                .likeIfPresent(ViewOpsMaintenanceWorkStatusDO::getEquAddr, reqVO.getEquAddr())
                .eqIfPresent(ViewOpsMaintenanceWorkStatusDO::getEquLng, reqVO.getEquLng())
                .eqIfPresent(ViewOpsMaintenanceWorkStatusDO::getEquLat, reqVO.getEquLat())
                .likeIfPresent(ViewOpsMaintenanceWorkStatusDO::getProblem, reqVO.getProblem())
                .likeIfPresent(ViewOpsMaintenanceWorkStatusDO::getFaultType, reqVO.getFaultType())
                .betweenIfPresent(ViewOpsMaintenanceWorkStatusDO::getFaultDate, reqVO.getFaultDate())
                .likeIfPresent(ViewOpsMaintenanceWorkStatusDO::getReportPersonnel, reqVO.getReportPersonnel())
                .eqIfPresent(ViewOpsMaintenanceWorkStatusDO::getReportUserId, reqVO.getReportUserId())
                .betweenIfPresent(ViewOpsMaintenanceWorkStatusDO::getReportTime, reqVO.getReportTime())
                .eqIfPresent(ViewOpsMaintenanceWorkStatusDO::getReportDeptId, reqVO.getReportDeptId())
                .eqIfPresent(ViewOpsMaintenanceWorkStatusDO::getReportDept, reqVO.getReportDept())
                .eqIfPresent(ViewOpsMaintenanceWorkStatusDO::getDispatchUserId, reqVO.getDispatchUserId())
                .likeIfPresent(ViewOpsMaintenanceWorkStatusDO::getDispatch, reqVO.getDispatch())
                .betweenIfPresent(ViewOpsMaintenanceWorkStatusDO::getDispatchTime, reqVO.getDispatchTime())
                .eqIfPresent(ViewOpsMaintenanceWorkStatusDO::getDispatchAdress, reqVO.getDispatchAdress())
                .eqIfPresent(ViewOpsMaintenanceWorkStatusDO::getDispatchLng, reqVO.getDispatchLng())
                .eqIfPresent(ViewOpsMaintenanceWorkStatusDO::getDispatchLat, reqVO.getDispatchLat())
                .eqIfPresent(ViewOpsMaintenanceWorkStatusDO::getReceiveAdress, reqVO.getReceiveAdress())
                .eqIfPresent(ViewOpsMaintenanceWorkStatusDO::getReceiveLng, reqVO.getReceiveLng())
                .eqIfPresent(ViewOpsMaintenanceWorkStatusDO::getReceiveLat, reqVO.getReceiveLat())
                .betweenIfPresent(ViewOpsMaintenanceWorkStatusDO::getReceiveDate, reqVO.getReceiveDate())
                .eqIfPresent(ViewOpsMaintenanceWorkStatusDO::getSuggestion, reqVO.getSuggestion())
                .likeIfPresent(ViewOpsMaintenanceWorkStatusDO::getCancelPersonnel, reqVO.getCancelPersonnel())
                .eqIfPresent(ViewOpsMaintenanceWorkStatusDO::getCancelUserId, reqVO.getCancelUserId())
                .betweenIfPresent(ViewOpsMaintenanceWorkStatusDO::getCancelTime, reqVO.getCancelTime())
                .eqIfPresent(ViewOpsMaintenanceWorkStatusDO::getCancelReason, reqVO.getCancelReason())
                .eqIfPresent(ViewOpsMaintenanceWorkStatusDO::getStatus, reqVO.getStatus())
                .eqIfPresent(ViewOpsMaintenanceWorkStatusDO::getRemark, reqVO.getRemark())
                .eqIfPresent(ViewOpsMaintenanceWorkStatusDO::getUrgencyLevel, reqVO.getUrgencyLevel())
                .eqIfPresent(ViewOpsMaintenanceWorkStatusDO::getDeadline, reqVO.getDeadline())
                .eqIfPresent(ViewOpsMaintenanceWorkStatusDO::getMaintenanceUserId, reqVO.getMaintenanceUserId())
                .eqIfPresent(ViewOpsMaintenanceWorkStatusDO::getMaintenancePersonnel, reqVO.getMaintenancePersonnel())
                .orderByDesc(ViewOpsMaintenanceWorkStatusDO::getId));
    }

}