package com.holdtime.ops.module.entity.mapper;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import com.holdtime.ops.module.entity.ViewOpsTempReportDO;
import org.apache.ibatis.annotations.Mapper;
import com.holdtime.ops.module.model.vo.*;

/**
 * VIEW Mapper
 *
 * @author 管理员
 */
@Mapper
public interface ViewOpsTempReportMapper extends BaseMapperX<ViewOpsTempReportDO> {

    default PageResult<ViewOpsTempReportDO> selectPage(ViewOpsTempReportPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<ViewOpsTempReportDO>()
                .eqIfPresent(ViewOpsTempReportDO::getEquId, reqVO.getEquId())
                .eqIfPresent(ViewOpsTempReportDO::getIpAddr, reqVO.getIpAddr())
                .eqIfPresent(ViewOpsTempReportDO::getSource, reqVO.getSource())
                .betweenIfPresent(ViewOpsTempReportDO::getCreateTime, reqVO.getCreateTime())
                .betweenIfPresent(ViewOpsTempReportDO::getDeletedTime, reqVO.getDeletedTime())
                .eqIfPresent(ViewOpsTempReportDO::getEquNumber, reqVO.getEquNumber())
                .likeIfPresent(ViewOpsTempReportDO::getEquName, reqVO.getEquName())
                .eqIfPresent(ViewOpsTempReportDO::getEquType, reqVO.getEquType())
                .likeIfPresent(ViewOpsTempReportDO::getRoadName, reqVO.getRoadName())
                .likeIfPresent(ViewOpsTempReportDO::getIntersectionName, reqVO.getIntersectionName())
                .eqIfPresent(ViewOpsTempReportDO::getLng, reqVO.getLng())
                .eqIfPresent(ViewOpsTempReportDO::getLat, reqVO.getLat())
                .eqIfPresent(ViewOpsTempReportDO::getAdress, reqVO.getAdress())
                .betweenIfPresent(ViewOpsTempReportDO::getInstallDate, reqVO.getInstallDate())
                .eqIfPresent(ViewOpsTempReportDO::getManufacturer, reqVO.getManufacturer())
                .eqIfPresent(ViewOpsTempReportDO::getEquModel, reqVO.getEquModel())
                .eqIfPresent(ViewOpsTempReportDO::getUnit, reqVO.getUnit())
                .eqIfPresent(ViewOpsTempReportDO::getShootingRange, reqVO.getShootingRange())
                .eqIfPresent(ViewOpsTempReportDO::getLens, reqVO.getLens())
                .eqIfPresent(ViewOpsTempReportDO::getFocalLength, reqVO.getFocalLength())
                .eqIfPresent(ViewOpsTempReportDO::getAperture, reqVO.getAperture())
                .eqIfPresent(ViewOpsTempReportDO::getSoftwareVersion, reqVO.getSoftwareVersion())
                .eqIfPresent(ViewOpsTempReportDO::getInstallationMethod, reqVO.getInstallationMethod())
                .eqIfPresent(ViewOpsTempReportDO::getCommunicationModel, reqVO.getCommunicationModel())
                .eqIfPresent(ViewOpsTempReportDO::getProtocol, reqVO.getProtocol())
                .eqIfPresent(ViewOpsTempReportDO::getGateway, reqVO.getGateway())
                .eqIfPresent(ViewOpsTempReportDO::getGjxAddr, reqVO.getGjxAddr())
                .eqIfPresent(ViewOpsTempReportDO::getGjxLng, reqVO.getGjxLng())
                .eqIfPresent(ViewOpsTempReportDO::getGjxLat, reqVO.getGjxLat())
                .eqIfPresent(ViewOpsTempReportDO::getDirection, reqVO.getDirection())
                .eqIfPresent(ViewOpsTempReportDO::getDjType, reqVO.getDjType())
                .eqIfPresent(ViewOpsTempReportDO::getLwBh, reqVO.getLwBh())
                .eqIfPresent(ViewOpsTempReportDO::getJzBh, reqVO.getJzBh())
                .eqIfPresent(ViewOpsTempReportDO::getDominion, reqVO.getDominion())
                .eqIfPresent(ViewOpsTempReportDO::getStatus, reqVO.getStatus())
                .eqIfPresent(ViewOpsTempReportDO::getEquImg, reqVO.getEquImg())
                .eqIfPresent(ViewOpsTempReportDO::getMemberBh, reqVO.getMemberBh())
                .eqIfPresent(ViewOpsTempReportDO::getIsCheck, reqVO.getIsCheck())
                .eqIfPresent(ViewOpsTempReportDO::getEquLable, reqVO.getEquLable())
                .likeIfPresent(ViewOpsTempReportDO::getUserName, reqVO.getUserName())
                .eqIfPresent(ViewOpsTempReportDO::getPassword, reqVO.getPassword())
                .eqIfPresent(ViewOpsTempReportDO::getAddressXh, reqVO.getAddressXh())
                .eqIfPresent(ViewOpsTempReportDO::getKkbh, reqVO.getKkbh())
                .eqIfPresent(ViewOpsTempReportDO::getSbbh, reqVO.getSbbh())
                .betweenIfPresent(ViewOpsTempReportDO::getMaintenanceExpiryDate, reqVO.getMaintenanceExpiryDate())
                .orderByDesc(ViewOpsTempReportDO::getId));
    }

}