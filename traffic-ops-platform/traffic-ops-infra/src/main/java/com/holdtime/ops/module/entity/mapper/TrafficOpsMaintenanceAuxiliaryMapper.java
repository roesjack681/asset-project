package com.holdtime.ops.module.entity.mapper;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import com.holdtime.ops.module.entity.TrafficOpsMaintenanceAuxiliaryDO;
import org.apache.ibatis.annotations.Mapper;
import com.holdtime.ops.module.model.vo.*;

/**
 * 护栏辅助设备维护详细 Mapper
 *
 * @author yanxl
 */
@Mapper
public interface TrafficOpsMaintenanceAuxiliaryMapper extends BaseMapperX<TrafficOpsMaintenanceAuxiliaryDO> {

    default PageResult<TrafficOpsMaintenanceAuxiliaryDO> selectPage(TrafficOpsMaintenanceAuxiliaryPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<TrafficOpsMaintenanceAuxiliaryDO>()
                .eqIfPresent(TrafficOpsMaintenanceAuxiliaryDO::getMaintenanceId, reqVO.getMaintenanceId())
                .eqIfPresent(TrafficOpsMaintenanceAuxiliaryDO::getGuardrailId, reqVO.getGuardrailId())
                .eqIfPresent(TrafficOpsMaintenanceAuxiliaryDO::getAuxiliaryId, reqVO.getAuxiliaryId())
                .likeIfPresent(TrafficOpsMaintenanceAuxiliaryDO::getAuxiliaryName, reqVO.getAuxiliaryName())
                .eqIfPresent(TrafficOpsMaintenanceAuxiliaryDO::getAuxiliaryNum, reqVO.getAuxiliaryNum())
                .betweenIfPresent(TrafficOpsMaintenanceAuxiliaryDO::getCreateTime, reqVO.getCreateTime())
                .betweenIfPresent(TrafficOpsMaintenanceAuxiliaryDO::getDeletedTime, reqVO.getDeletedTime())
                .orderByDesc(TrafficOpsMaintenanceAuxiliaryDO::getId));
    }

}