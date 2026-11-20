package com.holdtime.ops.module.entity.mapper;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import com.holdtime.ops.module.entity.TrafficOpsSignalDO;
import org.apache.ibatis.annotations.Mapper;
import com.holdtime.ops.module.model.vo.*;

/**
 * 信号灯 Mapper
 *
 * @author 芋道源码
 */
@Mapper
public interface TrafficOpsSignalMapper extends BaseMapperX<TrafficOpsSignalDO> {

    default PageResult<TrafficOpsSignalDO> selectPage(TrafficOpsSignalPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<TrafficOpsSignalDO>()
                .likeIfPresent(TrafficOpsSignalDO::getEquName, reqVO.getEquName())
                .eqIfPresent(TrafficOpsSignalDO::getEquType, reqVO.getEquType())
                .likeIfPresent(TrafficOpsSignalDO::getRoadName, reqVO.getRoadName())
                .likeIfPresent(TrafficOpsSignalDO::getIntersectionName, reqVO.getIntersectionName())
                .eqIfPresent(TrafficOpsSignalDO::getLng, reqVO.getLng())
                .eqIfPresent(TrafficOpsSignalDO::getLat, reqVO.getLat())
                .eqIfPresent(TrafficOpsSignalDO::getAdress, reqVO.getAdress())
                .betweenIfPresent(TrafficOpsSignalDO::getInstallDate, reqVO.getInstallDate())
                .eqIfPresent(TrafficOpsSignalDO::getNetworkType, reqVO.getNetworkType())
                .eqIfPresent(TrafficOpsSignalDO::getNetworkAddr, reqVO.getNetworkAddr())
                .eqIfPresent(TrafficOpsSignalDO::getPhase, reqVO.getPhase())
                .eqIfPresent(TrafficOpsSignalDO::getNetworkStatus, reqVO.getNetworkStatus())
                .eqIfPresent(TrafficOpsSignalDO::getManufacturer, reqVO.getManufacturer())
                .eqIfPresent(TrafficOpsSignalDO::getEquModel, reqVO.getEquModel())
                .eqIfPresent(TrafficOpsSignalDO::getEquNumber, reqVO.getEquNumber())
                .eqIfPresent(TrafficOpsSignalDO::getIpAddr, reqVO.getIpAddr())
                .eqIfPresent(TrafficOpsSignalDO::getMask, reqVO.getMask())
                .eqIfPresent(TrafficOpsSignalDO::getGateway, reqVO.getGateway())
                .eqIfPresent(TrafficOpsSignalDO::getBelongLoop, reqVO.getBelongLoop())
                .eqIfPresent(TrafficOpsSignalDO::getNetworkProtocol, reqVO.getNetworkProtocol())
                .eqIfPresent(TrafficOpsSignalDO::getServerIp, reqVO.getServerIp())
                .eqIfPresent(TrafficOpsSignalDO::getPanorama, reqVO.getPanorama())
                .eqIfPresent(TrafficOpsSignalDO::getAccessControl, reqVO.getAccessControl())
                .eqIfPresent(TrafficOpsSignalDO::getUnit, reqVO.getUnit())
                .likeIfPresent(TrafficOpsSignalDO::getDominion, reqVO.getDominion())
                .eqIfPresent(TrafficOpsSignalDO::getStatus, reqVO.getStatus())
                .betweenIfPresent(TrafficOpsSignalDO::getCreateTime, reqVO.getCreateTime())
                .betweenIfPresent(TrafficOpsSignalDO::getDeletedTime, reqVO.getDeletedTime())
                .orderByDesc(TrafficOpsSignalDO::getId));
    }

}