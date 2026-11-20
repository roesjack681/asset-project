package com.holdtime.ops.module.entity.mapper;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import com.holdtime.ops.module.entity.PoliceOpsElectricPoliceDO;
import com.holdtime.ops.module.entity.TrafficOpsElectricPoliceDO;
import com.holdtime.ops.module.entity.TrafficOpsSignalDO;
import org.apache.ibatis.annotations.Mapper;
import com.holdtime.ops.module.model.vo.*;

/**
 * 电警 Mapper
 *
 * @author 芋道源码
 */
@Mapper
public interface TrafficOpsElectricPoliceMapper extends BaseMapperX<TrafficOpsElectricPoliceDO> {

    default PageResult<TrafficOpsElectricPoliceDO> selectPage(TrafficOpsElectricPolicePageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<TrafficOpsElectricPoliceDO>()
                .eqIfPresent(TrafficOpsElectricPoliceDO::getEquNumber, reqVO.getEquNumber())
                .likeIfPresent(TrafficOpsElectricPoliceDO::getEquName, reqVO.getEquName())
                .eqIfPresent(TrafficOpsElectricPoliceDO::getEquType, reqVO.getEquType())
                .eqIfPresent(TrafficOpsElectricPoliceDO::getSoftwareVersion, reqVO.getSoftwareVersion())
                .likeIfPresent(TrafficOpsElectricPoliceDO::getRoadName, reqVO.getRoadName())
                .eqIfPresent(TrafficOpsElectricPoliceDO::getDirection, reqVO.getDirection())
                .likeIfPresent(TrafficOpsElectricPoliceDO::getIntersectionName, reqVO.getIntersectionName())
                .eqIfPresent(TrafficOpsElectricPoliceDO::getDjType, reqVO.getDjType())
                .eqIfPresent(TrafficOpsElectricPoliceDO::getLng, reqVO.getLng())
                .eqIfPresent(TrafficOpsElectricPoliceDO::getLwBh, reqVO.getLwBh())
                .eqIfPresent(TrafficOpsElectricPoliceDO::getLat, reqVO.getLat())
                .eqIfPresent(TrafficOpsElectricPoliceDO::getJzBh, reqVO.getJzBh())
                .eqIfPresent(TrafficOpsElectricPoliceDO::getAdress, reqVO.getAdress())
                .betweenIfPresent(TrafficOpsElectricPoliceDO::getInstallDate, reqVO.getInstallDate())
                .eqIfPresent(TrafficOpsElectricPoliceDO::getManufacturer, reqVO.getManufacturer())
                .betweenIfPresent(TrafficOpsElectricPoliceDO::getCreateTime, reqVO.getCreateTime())
                .eqIfPresent(TrafficOpsElectricPoliceDO::getEquModel, reqVO.getEquModel())
                .eqIfPresent(TrafficOpsElectricPoliceDO::getUnit, reqVO.getUnit())
                .eqIfPresent(TrafficOpsElectricPoliceDO::getShootingRange, reqVO.getShootingRange())
                .betweenIfPresent(TrafficOpsElectricPoliceDO::getDeletedTime, reqVO.getDeletedTime())
                .eqIfPresent(TrafficOpsElectricPoliceDO::getLens, reqVO.getLens())
                .eqIfPresent(TrafficOpsElectricPoliceDO::getFocalLength, reqVO.getFocalLength())
                .eqIfPresent(TrafficOpsElectricPoliceDO::getAperture, reqVO.getAperture())
                .eqIfPresent(TrafficOpsElectricPoliceDO::getInstallationMethod, reqVO.getInstallationMethod())
                .eqIfPresent(TrafficOpsElectricPoliceDO::getCommunicationModel, reqVO.getCommunicationModel())
                .eqIfPresent(TrafficOpsElectricPoliceDO::getProtocol, reqVO.getProtocol())
                .likeIfPresent(TrafficOpsElectricPoliceDO::getIpAddr, reqVO.getIpAddr())
                .eqIfPresent(TrafficOpsElectricPoliceDO::getGateway, reqVO.getGateway())
                .eqIfPresent(TrafficOpsElectricPoliceDO::getGjxAddr, reqVO.getGjxAddr())
                .eqIfPresent(TrafficOpsElectricPoliceDO::getGjxLng, reqVO.getGjxLng())
                .eqIfPresent(TrafficOpsElectricPoliceDO::getGjxLat, reqVO.getGjxLat())
                .likeIfPresent(TrafficOpsElectricPoliceDO::getDominion, reqVO.getDominion())
                .eqIfPresent(TrafficOpsElectricPoliceDO::getStatus, reqVO.getStatus())
                .eqIfPresent(TrafficOpsElectricPoliceDO::getIsCheck, reqVO.getIsCheck())
                .likeIfPresent(TrafficOpsElectricPoliceDO::getEquLable, reqVO.getEquLable())
                .eqIfPresent(TrafficOpsElectricPoliceDO::getUnderWarranty, reqVO.getUnderWarranty())
                .betweenIfPresent(TrafficOpsElectricPoliceDO::getMaintenanceExpiryDate, reqVO.getMaintenanceExpiryDate())
//                .likeIfPresent(TrafficOpsElectricPoliceDO::getPowerSupplyLocation, reqVO.getPowerSupplyLocation())
                .likeIfPresent(TrafficOpsElectricPoliceDO::getPixel, reqVO.getPixel())
                .eqIfPresent(TrafficOpsElectricPoliceDO::getShotDirection, reqVO.getShotDirection())
                .eqIfPresent(TrafficOpsElectricPoliceDO::getXjType, reqVO.getXjType())
                .orderByAsc(TrafficOpsElectricPoliceDO::getId));
    }

}