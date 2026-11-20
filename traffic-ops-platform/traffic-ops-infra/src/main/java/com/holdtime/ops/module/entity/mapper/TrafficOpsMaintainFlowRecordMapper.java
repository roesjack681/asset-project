package com.holdtime.ops.module.entity.mapper;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import com.holdtime.ops.module.entity.TrafficOpsMaintainFlowRecordDO;
import org.apache.ibatis.annotations.Mapper;
import com.holdtime.ops.module.model.vo.*;

/**
 * 设备维保全流程 Mapper
 *
 * @author 管理员
 */
@Mapper
public interface TrafficOpsMaintainFlowRecordMapper extends BaseMapperX<TrafficOpsMaintainFlowRecordDO> {

    default PageResult<TrafficOpsMaintainFlowRecordDO> selectPage(TrafficOpsMaintainFlowRecordPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<TrafficOpsMaintainFlowRecordDO>()
                .eqIfPresent(TrafficOpsMaintainFlowRecordDO::getMaintenanceId, reqVO.getMaintenanceId())
                .eqIfPresent(TrafficOpsMaintainFlowRecordDO::getFlowType, reqVO.getFlowType())
                .eqIfPresent(TrafficOpsMaintainFlowRecordDO::getRegisterId, reqVO.getRegisterId())
                .eqIfPresent(TrafficOpsMaintainFlowRecordDO::getUserId, reqVO.getUserId())
                .likeIfPresent(TrafficOpsMaintainFlowRecordDO::getUserName, reqVO.getUserName())
                .eqIfPresent(TrafficOpsMaintainFlowRecordDO::getUserPhone, reqVO.getUserPhone())
                .eqIfPresent(TrafficOpsMaintainFlowRecordDO::getOperateAddress, reqVO.getOperateAddress())
                .eqIfPresent(TrafficOpsMaintainFlowRecordDO::getOperateLng, reqVO.getOperateLng())
                .eqIfPresent(TrafficOpsMaintainFlowRecordDO::getOperateLat, reqVO.getOperateLat())
                .eqIfPresent(TrafficOpsMaintainFlowRecordDO::getRemark, reqVO.getRemark())
                .betweenIfPresent(TrafficOpsMaintainFlowRecordDO::getCreateTime, reqVO.getCreateTime())
                .betweenIfPresent(TrafficOpsMaintainFlowRecordDO::getDeletedTime, reqVO.getDeletedTime())
                .orderByDesc(TrafficOpsMaintainFlowRecordDO::getId));
    }

}