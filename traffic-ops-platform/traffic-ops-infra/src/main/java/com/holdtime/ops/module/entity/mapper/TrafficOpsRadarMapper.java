package com.holdtime.ops.module.entity.mapper;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import com.holdtime.ops.module.entity.TrafficOpsRadarDO;
import com.holdtime.ops.module.entity.TrafficOpsSignalDO;
import org.apache.ibatis.annotations.Mapper;
import com.holdtime.ops.module.model.vo.*;

/**
 * 雷达 Mapper
 *
 * @author 芋道源码
 */
@Mapper
public interface TrafficOpsRadarMapper extends BaseMapperX<TrafficOpsRadarDO> {

    default PageResult<TrafficOpsRadarDO> selectPage(TrafficOpsRadarPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<TrafficOpsRadarDO>()
                .likeIfPresent(TrafficOpsRadarDO::getEquName, reqVO.getEquName())
                .eqIfPresent(TrafficOpsRadarDO::getEquNumber, reqVO.getEquNumber())
                .eqIfPresent(TrafficOpsRadarDO::getEquType, reqVO.getEquType())
                .eqIfPresent(TrafficOpsRadarDO::getSoftwareVersion, reqVO.getSoftwareVersion())
                .likeIfPresent(TrafficOpsRadarDO::getRoadName, reqVO.getRoadName())
                .likeIfPresent(TrafficOpsRadarDO::getIntersectionName, reqVO.getIntersectionName())
                .eqIfPresent(TrafficOpsRadarDO::getLng, reqVO.getLng())
                .eqIfPresent(TrafficOpsRadarDO::getLat, reqVO.getLat())
                .eqIfPresent(TrafficOpsRadarDO::getAdress, reqVO.getAdress())
                .betweenIfPresent(TrafficOpsRadarDO::getInstallDate, reqVO.getInstallDate())
                .eqIfPresent(TrafficOpsRadarDO::getManufacturer, reqVO.getManufacturer())
                .eqIfPresent(TrafficOpsRadarDO::getEquModel, reqVO.getEquModel())
                .eqIfPresent(TrafficOpsRadarDO::getUnit, reqVO.getUnit())
                .eqIfPresent(TrafficOpsRadarDO::getOperatingFrequency, reqVO.getOperatingFrequency())
                .eqIfPresent(TrafficOpsRadarDO::getDetectionRange, reqVO.getDetectionRange())
                .eqIfPresent(TrafficOpsRadarDO::getDetectionAngle, reqVO.getDetectionAngle())
                .eqIfPresent(TrafficOpsRadarDO::getInstallationMethod, reqVO.getInstallationMethod())
                .eqIfPresent(TrafficOpsRadarDO::getCommunicationModel, reqVO.getCommunicationModel())
                .eqIfPresent(TrafficOpsRadarDO::getProtocol, reqVO.getProtocol())
                .betweenIfPresent(TrafficOpsRadarDO::getCreateTime, reqVO.getCreateTime())
                .betweenIfPresent(TrafficOpsRadarDO::getDeletedTime, reqVO.getDeletedTime())
                .likeIfPresent(TrafficOpsRadarDO::getDominion, reqVO.getDominion())
                .eqIfPresent(TrafficOpsRadarDO::getStatus, reqVO.getStatus())
                .orderByDesc(TrafficOpsRadarDO::getId));
    }

}