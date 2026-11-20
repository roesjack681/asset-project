package com.holdtime.ops.module.entity.mapper;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import com.holdtime.ops.module.entity.TrafficOpsMaintenanceUnfixableFollowDO;
import org.apache.ibatis.annotations.Mapper;
import com.holdtime.ops.module.model.vo.*;

/**
 * 设备维保无法修复跟进 Mapper
 *
 * @author 管理员
 */
@Mapper
public interface TrafficOpsMaintenanceUnfixableFollowMapper extends BaseMapperX<TrafficOpsMaintenanceUnfixableFollowDO> {

    default PageResult<TrafficOpsMaintenanceUnfixableFollowDO> selectPage(TrafficOpsMaintenanceUnfixableFollowPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<TrafficOpsMaintenanceUnfixableFollowDO>()
                .eqIfPresent(TrafficOpsMaintenanceUnfixableFollowDO::getUnfixableId, reqVO.getUnfixableId())
                .eqIfPresent(TrafficOpsMaintenanceUnfixableFollowDO::getCoordinationContent, reqVO.getCoordinationContent())
                .betweenIfPresent(TrafficOpsMaintenanceUnfixableFollowDO::getCoordinationDate, reqVO.getCoordinationDate())
                .betweenIfPresent(TrafficOpsMaintenanceUnfixableFollowDO::getEstimatedRepairDate, reqVO.getEstimatedRepairDate())
                .betweenIfPresent(TrafficOpsMaintenanceUnfixableFollowDO::getCreateTime, reqVO.getCreateTime())
                .betweenIfPresent(TrafficOpsMaintenanceUnfixableFollowDO::getDeletedTime, reqVO.getDeletedTime())
                .orderByDesc(TrafficOpsMaintenanceUnfixableFollowDO::getId));
    }

}