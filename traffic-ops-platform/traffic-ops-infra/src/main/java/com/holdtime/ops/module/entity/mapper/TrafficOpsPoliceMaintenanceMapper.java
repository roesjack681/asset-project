package com.holdtime.ops.module.entity.mapper;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import com.holdtime.ops.module.entity.TrafficOpsPoliceMaintenanceDO;
import org.apache.ibatis.annotations.Mapper;
import com.holdtime.ops.module.model.vo.*;

/**
 * 警用装备报修信息 Mapper
 *
 * @author 管理员
 */
@Mapper
public interface TrafficOpsPoliceMaintenanceMapper extends BaseMapperX<TrafficOpsPoliceMaintenanceDO> {

    default PageResult<TrafficOpsPoliceMaintenanceDO> selectPage(TrafficOpsPoliceMaintenancePageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<TrafficOpsPoliceMaintenanceDO>()
                .eqIfPresent(TrafficOpsPoliceMaintenanceDO::getReportPersonnel, reqVO.getReportPersonnel())
                .eqIfPresent(TrafficOpsPoliceMaintenanceDO::getReportUserId, reqVO.getReportUserId())
                .betweenIfPresent(TrafficOpsPoliceMaintenanceDO::getReportTime, reqVO.getReportTime())
                .eqIfPresent(TrafficOpsPoliceMaintenanceDO::getReportDeptId, reqVO.getReportDeptId())
                .eqIfPresent(TrafficOpsPoliceMaintenanceDO::getReportDept, reqVO.getReportDept())
                .eqIfPresent(TrafficOpsPoliceMaintenanceDO::getAuditorBh, reqVO.getAuditorBh())
                .betweenIfPresent(TrafficOpsPoliceMaintenanceDO::getAuditDate, reqVO.getAuditDate())
                .eqIfPresent(TrafficOpsPoliceMaintenanceDO::getAuditor, reqVO.getAuditor())
                .eqIfPresent(TrafficOpsPoliceMaintenanceDO::getAuditOpinions, reqVO.getAuditOpinions())
                .eqIfPresent(TrafficOpsPoliceMaintenanceDO::getMaintenanceUserId, reqVO.getMaintenanceUserId())
                .eqIfPresent(TrafficOpsPoliceMaintenanceDO::getMaintenancePersonnel, reqVO.getMaintenancePersonnel())
                .eqIfPresent(TrafficOpsPoliceMaintenanceDO::getMaintenancePhone, reqVO.getMaintenancePhone())
                .betweenIfPresent(TrafficOpsPoliceMaintenanceDO::getMaintenanceDate, reqVO.getMaintenanceDate())
                .eqIfPresent(TrafficOpsPoliceMaintenanceDO::getSuggestion, reqVO.getSuggestion())
                .eqIfPresent(TrafficOpsPoliceMaintenanceDO::getStatus, reqVO.getStatus())
                .eqIfPresent(TrafficOpsPoliceMaintenanceDO::getRemark, reqVO.getRemark())
                .betweenIfPresent(TrafficOpsPoliceMaintenanceDO::getCreateTime, reqVO.getCreateTime())
                .betweenIfPresent(TrafficOpsPoliceMaintenanceDO::getDeletedTime, reqVO.getDeletedTime())
                .orderByDesc(TrafficOpsPoliceMaintenanceDO::getId));
    }

}