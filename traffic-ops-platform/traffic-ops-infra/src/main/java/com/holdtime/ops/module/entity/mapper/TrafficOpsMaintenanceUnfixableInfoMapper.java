package com.holdtime.ops.module.entity.mapper;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import com.holdtime.ops.module.entity.TrafficOpsMaintenanceUnfixableInfoDO;
import org.apache.ibatis.annotations.Mapper;
import com.holdtime.ops.module.model.vo.*;

/**
 * 设备维保无法修复信息 Mapper
 *
 * @author 管理员
 */
@Mapper
public interface TrafficOpsMaintenanceUnfixableInfoMapper extends BaseMapperX<TrafficOpsMaintenanceUnfixableInfoDO> {

    default PageResult<TrafficOpsMaintenanceUnfixableInfoDO> selectPage(TrafficOpsMaintenanceUnfixableInfoPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<TrafficOpsMaintenanceUnfixableInfoDO>()
                .eqIfPresent(TrafficOpsMaintenanceUnfixableInfoDO::getUnfixableType, reqVO.getUnfixableType())
                .eqIfPresent(TrafficOpsMaintenanceUnfixableInfoDO::getUnfixableReason, reqVO.getUnfixableReason())
                .eqIfPresent(TrafficOpsMaintenanceUnfixableInfoDO::getConstructionContacts, reqVO.getConstructionContacts())
                .eqIfPresent(TrafficOpsMaintenanceUnfixableInfoDO::getPhone, reqVO.getPhone())
                .eqIfPresent(TrafficOpsMaintenanceUnfixableInfoDO::getEstimatedRepairDate, reqVO.getEstimatedRepairDate())
                .betweenIfPresent(TrafficOpsMaintenanceUnfixableInfoDO::getCreateTime, reqVO.getCreateTime())
                .betweenIfPresent(TrafficOpsMaintenanceUnfixableInfoDO::getDeletedTime, reqVO.getDeletedTime())
                .orderByDesc(TrafficOpsMaintenanceUnfixableInfoDO::getId));
    }

}