package com.holdtime.ops.module.entity.mapper;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import com.holdtime.ops.module.entity.TrafficOpsMaintenanceTeamDO;
import org.apache.ibatis.annotations.Mapper;
import com.holdtime.ops.module.model.vo.*;

/**
 * 设备维保组员信息 Mapper
 *
 * @author 芋道源码
 */
@Mapper
public interface TrafficOpsMaintenanceTeamMapper extends BaseMapperX<TrafficOpsMaintenanceTeamDO> {

    default PageResult<TrafficOpsMaintenanceTeamDO> selectPage(TrafficOpsMaintenanceTeamPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<TrafficOpsMaintenanceTeamDO>()
                .eqIfPresent(TrafficOpsMaintenanceTeamDO::getMaintenanceId, reqVO.getMaintenanceId())
                .eqIfPresent(TrafficOpsMaintenanceTeamDO::getProgressId, reqVO.getProgressId())
                .eqIfPresent(TrafficOpsMaintenanceTeamDO::getMaintenanceUserId, reqVO.getMaintenanceUserId())
                .eqIfPresent(TrafficOpsMaintenanceTeamDO::getMaintenancePersonnel, reqVO.getMaintenancePersonnel())
                .eqIfPresent(TrafficOpsMaintenanceTeamDO::getMaintenancePhone, reqVO.getMaintenancePhone())
                .betweenIfPresent(TrafficOpsMaintenanceTeamDO::getCreateTime, reqVO.getCreateTime())
                .betweenIfPresent(TrafficOpsMaintenanceTeamDO::getDeletedTime, reqVO.getDeletedTime())
                .orderByDesc(TrafficOpsMaintenanceTeamDO::getId));
    }

}