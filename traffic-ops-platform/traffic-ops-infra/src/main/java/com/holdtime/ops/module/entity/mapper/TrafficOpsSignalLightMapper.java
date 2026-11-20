package com.holdtime.ops.module.entity.mapper;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import com.holdtime.ops.module.entity.TrafficOpsSignalDO;
import com.holdtime.ops.module.entity.TrafficOpsSignalLightDO;
import org.apache.ibatis.annotations.Mapper;
import com.holdtime.ops.module.model.vo.*;

/**
 * 信号灯 Mapper
 *
 * @author 芋道源码
 */
@Mapper
public interface TrafficOpsSignalLightMapper extends BaseMapperX<TrafficOpsSignalLightDO> {

    default PageResult<TrafficOpsSignalLightDO> selectPage(TrafficOpsSignalLightPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<TrafficOpsSignalLightDO>()
                .eqIfPresent(TrafficOpsSignalLightDO::getEquNumber, reqVO.getEquNumber())
                .likeIfPresent(TrafficOpsSignalLightDO::getEquName, reqVO.getEquName())
                .eqIfPresent(TrafficOpsSignalLightDO::getEquType, reqVO.getEquType())
                .likeIfPresent(TrafficOpsSignalLightDO::getRoadName, reqVO.getRoadName())
                .likeIfPresent(TrafficOpsSignalLightDO::getIntersectionName, reqVO.getIntersectionName())
                .eqIfPresent(TrafficOpsSignalLightDO::getLng, reqVO.getLng())
                .eqIfPresent(TrafficOpsSignalLightDO::getLat, reqVO.getLat())
                .eqIfPresent(TrafficOpsSignalLightDO::getAdress, reqVO.getAdress())
                .betweenIfPresent(TrafficOpsSignalLightDO::getInstallDate, reqVO.getInstallDate())
                .eqIfPresent(TrafficOpsSignalLightDO::getManufacturer, reqVO.getManufacturer())
                .eqIfPresent(TrafficOpsSignalLightDO::getEquModel, reqVO.getEquModel())
                .eqIfPresent(TrafficOpsSignalLightDO::getUnit, reqVO.getUnit())
                .eqIfPresent(TrafficOpsSignalLightDO::getLedPattern, reqVO.getLedPattern())
                .eqIfPresent(TrafficOpsSignalLightDO::getLedType, reqVO.getLedType())
                .eqIfPresent(TrafficOpsSignalLightDO::getLedSize, reqVO.getLedSize())
                .eqIfPresent(TrafficOpsSignalLightDO::getLedHeadNum, reqVO.getLedHeadNum())
                .eqIfPresent(TrafficOpsSignalLightDO::getArrangement, reqVO.getArrangement())
                .eqIfPresent(TrafficOpsSignalLightDO::getSignalDisplayMode, reqVO.getSignalDisplayMode())
                .betweenIfPresent(TrafficOpsSignalLightDO::getCreateTime, reqVO.getCreateTime())
                .betweenIfPresent(TrafficOpsSignalLightDO::getDeletedTime, reqVO.getDeletedTime())
                .likeIfPresent(TrafficOpsSignalLightDO::getDominion, reqVO.getDominion())
                .eqIfPresent(TrafficOpsSignalLightDO::getStatus, reqVO.getStatus())
                .orderByDesc(TrafficOpsSignalLightDO::getId));
    }

}