package com.holdtime.ops.module.entity.mapper;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import com.holdtime.ops.module.entity.PoliceOpsElectricPoliceDO;
import org.apache.ibatis.annotations.Mapper;
import com.holdtime.ops.module.model.vo.*;

/**
 * 电警 Mapper
 *
 * @author 芋道源码
 */
@Mapper
public interface PoliceOpsElectricPoliceMapper extends BaseMapperX<PoliceOpsElectricPoliceDO> {

    default PageResult<PoliceOpsElectricPoliceDO> selectPage(PoliceOpsElectricPolicePageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<PoliceOpsElectricPoliceDO>()
                .eqIfPresent(PoliceOpsElectricPoliceDO::getEquNumber, reqVO.getEquNumber())
                .likeIfPresent(PoliceOpsElectricPoliceDO::getEquName, reqVO.getEquName())
                .eqIfPresent(PoliceOpsElectricPoliceDO::getEquType, reqVO.getEquType())
                .likeIfPresent(PoliceOpsElectricPoliceDO::getRoadName, reqVO.getRoadName())
                .likeIfPresent(PoliceOpsElectricPoliceDO::getIntersectionName, reqVO.getIntersectionName())
                .eqIfPresent(PoliceOpsElectricPoliceDO::getLng, reqVO.getLng())
                .eqIfPresent(PoliceOpsElectricPoliceDO::getLat, reqVO.getLat())
                .eqIfPresent(PoliceOpsElectricPoliceDO::getAdress, reqVO.getAdress())
                .betweenIfPresent(PoliceOpsElectricPoliceDO::getInstallDate, reqVO.getInstallDate())
                .eqIfPresent(PoliceOpsElectricPoliceDO::getManufacturer, reqVO.getManufacturer())
                .eqIfPresent(PoliceOpsElectricPoliceDO::getEquModel, reqVO.getEquModel())
                .eqIfPresent(PoliceOpsElectricPoliceDO::getUnit, reqVO.getUnit())
                .eqIfPresent(PoliceOpsElectricPoliceDO::getShootingRange, reqVO.getShootingRange())
                .eqIfPresent(PoliceOpsElectricPoliceDO::getLens, reqVO.getLens())
                .eqIfPresent(PoliceOpsElectricPoliceDO::getFocalLength, reqVO.getFocalLength())
                .eqIfPresent(PoliceOpsElectricPoliceDO::getAperture, reqVO.getAperture())
                .eqIfPresent(PoliceOpsElectricPoliceDO::getSoftwareVersion, reqVO.getSoftwareVersion())
                .eqIfPresent(PoliceOpsElectricPoliceDO::getInstallationMethod, reqVO.getInstallationMethod())
                .eqIfPresent(PoliceOpsElectricPoliceDO::getCommunicationModel, reqVO.getCommunicationModel())
                .eqIfPresent(PoliceOpsElectricPoliceDO::getProtocol, reqVO.getProtocol())
                .likeIfPresent(PoliceOpsElectricPoliceDO::getIpAddr, reqVO.getIpAddr())
                .eqIfPresent(PoliceOpsElectricPoliceDO::getGateway, reqVO.getGateway())
                .eqIfPresent(PoliceOpsElectricPoliceDO::getGjxAddr, reqVO.getGjxAddr())
                .eqIfPresent(PoliceOpsElectricPoliceDO::getGjxLng, reqVO.getGjxLng())
                .eqIfPresent(PoliceOpsElectricPoliceDO::getGjxLat, reqVO.getGjxLat())
                .eqIfPresent(PoliceOpsElectricPoliceDO::getDirection, reqVO.getDirection())
                .eqIfPresent(PoliceOpsElectricPoliceDO::getDjType, reqVO.getDjType())
                .eqIfPresent(PoliceOpsElectricPoliceDO::getLwBh, reqVO.getLwBh())
                .eqIfPresent(PoliceOpsElectricPoliceDO::getJzBh, reqVO.getJzBh())
                .eqIfPresent(PoliceOpsElectricPoliceDO::getDominion, reqVO.getDominion())
                .eqIfPresent(PoliceOpsElectricPoliceDO::getStatus, reqVO.getStatus())
                .eqIfPresent(PoliceOpsElectricPoliceDO::getEquImg, reqVO.getEquImg())
                .betweenIfPresent(PoliceOpsElectricPoliceDO::getCreateTime, reqVO.getCreateTime())
                .betweenIfPresent(PoliceOpsElectricPoliceDO::getDeletedTime, reqVO.getDeletedTime())
                .eqIfPresent(PoliceOpsElectricPoliceDO::getMemberBh, reqVO.getMemberBh())
                .eqIfPresent(PoliceOpsElectricPoliceDO::getIsCheck, reqVO.getIsCheck())
                .eqIfPresent(PoliceOpsElectricPoliceDO::getEquLable, reqVO.getEquLable())
                .likeIfPresent(PoliceOpsElectricPoliceDO::getUserName, reqVO.getUserName())
                .eqIfPresent(PoliceOpsElectricPoliceDO::getPassword, reqVO.getPassword())
                .eqIfPresent(PoliceOpsElectricPoliceDO::getAddressXh, reqVO.getAddressXh())
                .eqIfPresent(PoliceOpsElectricPoliceDO::getUnderWarranty, reqVO.getUnderWarranty())
                .betweenIfPresent(PoliceOpsElectricPoliceDO::getMaintenanceExpiryDate, reqVO.getMaintenanceExpiryDate())
                .likeIfPresent(PoliceOpsElectricPoliceDO::getPixel, reqVO.getPixel())
                .orderByDesc(PoliceOpsElectricPoliceDO::getId));
    }

}