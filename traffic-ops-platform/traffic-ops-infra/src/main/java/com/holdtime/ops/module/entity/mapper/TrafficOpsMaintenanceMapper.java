package com.holdtime.ops.module.entity.mapper;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import com.holdtime.ops.module.entity.TrafficOpsMaintenanceDO;
import org.apache.ibatis.annotations.Mapper;
import com.holdtime.ops.module.model.vo.*;

/**
 * 设备维保信息 Mapper
 *
 * @author 芋道源码
 */
@Mapper
public interface TrafficOpsMaintenanceMapper extends BaseMapperX<TrafficOpsMaintenanceDO> {

    default PageResult<TrafficOpsMaintenanceDO> selectPage(TrafficOpsMaintenancePageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<TrafficOpsMaintenanceDO>()
                .eqIfPresent(TrafficOpsMaintenanceDO::getAffiliatedUnit, reqVO.getAffiliatedUnit())
                .eqIfPresent(TrafficOpsMaintenanceDO::getEquType, reqVO.getEquType())
                .eqIfPresent(TrafficOpsMaintenanceDO::getEquId, reqVO.getEquId())
                .likeIfPresent(TrafficOpsMaintenanceDO::getEquAddr, reqVO.getEquAddr())
                .eqIfPresent(TrafficOpsMaintenanceDO::getEquLng, reqVO.getEquLng())
                .eqIfPresent(TrafficOpsMaintenanceDO::getEquLat, reqVO.getEquLat())
                .likeIfPresent(TrafficOpsMaintenanceDO::getProblem, reqVO.getProblem())
                .likeIfPresent(TrafficOpsMaintenanceDO::getFaultType, reqVO.getFaultType())
                .betweenIfPresent(TrafficOpsMaintenanceDO::getFaultDate, reqVO.getFaultDate())
                .likeIfPresent(TrafficOpsMaintenanceDO::getReportPersonnel, reqVO.getReportPersonnel())
                .likeIfPresent(TrafficOpsMaintenanceDO::getMaintenancePersonnel, reqVO.getMaintenancePersonnel())
                .eqIfPresent(TrafficOpsMaintenanceDO::getReportUserId, reqVO.getReportUserId())
                .betweenIfPresent(TrafficOpsMaintenanceDO::getReportTime, reqVO.getReportTime())
                .eqIfPresent(TrafficOpsMaintenanceDO::getReportDeptId, reqVO.getReportDeptId())
                .eqIfPresent(TrafficOpsMaintenanceDO::getReportDept, reqVO.getReportDept())
                .eqIfPresent(TrafficOpsMaintenanceDO::getDispatchUserId, reqVO.getDispatchUserId())
                .likeIfPresent(TrafficOpsMaintenanceDO::getDispatch, reqVO.getDispatch())
                .betweenIfPresent(TrafficOpsMaintenanceDO::getDispatchTime, reqVO.getDispatchTime())
                .eqIfPresent(TrafficOpsMaintenanceDO::getSuggestion, reqVO.getSuggestion())
                .eqIfPresent(TrafficOpsMaintenanceDO::getStatus, reqVO.getStatus())
                .likeIfPresent(TrafficOpsMaintenanceDO::getRemark, reqVO.getRemark())
                .likeIfPresent(TrafficOpsMaintenanceDO::getConfirmor, reqVO.getConfirmor())
                .betweenIfPresent(TrafficOpsMaintenanceDO::getConfirmDate, reqVO.getConfirmDate())
                .betweenIfPresent(TrafficOpsMaintenanceDO::getFinishTime, reqVO.getFinishTime())
                .betweenIfPresent(TrafficOpsMaintenanceDO::getCreateTime, reqVO.getCreateTime())
                .betweenIfPresent(TrafficOpsMaintenanceDO::getDeletedTime, reqVO.getDeletedTime())
                .eqIfPresent(TrafficOpsMaintenanceDO::getSource, reqVO.getSource())
                .likeIfPresent(TrafficOpsMaintenanceDO::getRejectPersonnel, reqVO.getRejectPersonnel())
                .likeIfPresent(TrafficOpsMaintenanceDO::getIpAddr, reqVO.getIpAddr())
                .betweenIfPresent(TrafficOpsMaintenanceDO::getRejectTime, reqVO.getRejectTime())
                .eqIfPresent(TrafficOpsMaintenanceDO::getProjectedRepair, reqVO.getProjectedRepair())
                .eqIfPresent(TrafficOpsMaintenanceDO::getActualRepair, reqVO.getActualRepair())
                .eqIfPresent(TrafficOpsMaintenanceDO::getDisposalMethod, reqVO.getActualRepair())
                .orderByDesc(TrafficOpsMaintenanceDO::getCreateTime));
    }

}