package com.holdtime.ops.module.entity.mapper;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import com.holdtime.ops.module.entity.TrafficOpsMaintainProgressDO;
import org.apache.ibatis.annotations.Mapper;
import com.holdtime.ops.module.model.vo.*;

/**
 * 设备维保进度 Mapper
 *
 * @author 芋道源码
 */
@Mapper
public interface TrafficOpsMaintainProgressMapper extends BaseMapperX<TrafficOpsMaintainProgressDO> {

    default PageResult<TrafficOpsMaintainProgressDO> selectPage(AppTrafficOpsMaintainProgressPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<TrafficOpsMaintainProgressDO>()
                .eqIfPresent(TrafficOpsMaintainProgressDO::getAffiliatedUnit, reqVO.getAffiliatedUnit())
                .eqIfPresent(TrafficOpsMaintainProgressDO::getMaintenanceId, reqVO.getMaintenanceId())
                .eqIfPresent(TrafficOpsMaintainProgressDO::getMaintenance, reqVO.getMaintenance())
                .eqIfPresent(TrafficOpsMaintainProgressDO::getMaintenanceUserId, reqVO.getMaintenanceUserId())
                .eqIfPresent(TrafficOpsMaintainProgressDO::getMaintenancePersonnel, reqVO.getMaintenancePersonnel())
                .eqIfPresent(TrafficOpsMaintainProgressDO::getMaintenancePhone, reqVO.getMaintenancePhone())
                .betweenIfPresent(TrafficOpsMaintainProgressDO::getMaintenanceDate, reqVO.getMaintenanceDate())
                .betweenIfPresent(TrafficOpsMaintainProgressDO::getCreateTime, reqVO.getCreateTime())
                .betweenIfPresent(TrafficOpsMaintainProgressDO::getDeletedTime, reqVO.getDeletedTime())
                .orderByDesc(TrafficOpsMaintainProgressDO::getId));
    }

}