package com.holdtime.ops.module.entity.mapper;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import com.holdtime.ops.module.entity.TrafficOpsBigledDO;
import com.holdtime.ops.module.entity.TrafficOpsSignalDO;
import org.apache.ibatis.annotations.Mapper;
import com.holdtime.ops.module.model.vo.*;

/**
 * 大屏 Mapper
 *
 * @author 芋道源码
 */
@Mapper
public interface TrafficOpsBigledMapper extends BaseMapperX<TrafficOpsBigledDO> {

    default PageResult<TrafficOpsBigledDO> selectPage(TrafficOpsBigledPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<TrafficOpsBigledDO>()
                .eqIfPresent(TrafficOpsBigledDO::getEquNumber, reqVO.getEquNumber())
                .likeIfPresent(TrafficOpsBigledDO::getEquName, reqVO.getEquName())
                .eqIfPresent(TrafficOpsBigledDO::getEquType, reqVO.getEquType())
                .likeIfPresent(TrafficOpsBigledDO::getRoadName, reqVO.getRoadName())
                .likeIfPresent(TrafficOpsBigledDO::getIntersectionName, reqVO.getIntersectionName())
                .eqIfPresent(TrafficOpsBigledDO::getLng, reqVO.getLng())
                .eqIfPresent(TrafficOpsBigledDO::getLat, reqVO.getLat())
                .eqIfPresent(TrafficOpsBigledDO::getDes, reqVO.getDes())
                .eqIfPresent(TrafficOpsBigledDO::getAdress, reqVO.getAdress())
                .eqIfPresent(TrafficOpsBigledDO::getIpAddr, reqVO.getIpAddr())
                .eqIfPresent(TrafficOpsBigledDO::getScreenOrientation, reqVO.getScreenOrientation())
                .eqIfPresent(TrafficOpsBigledDO::getSubnet, reqVO.getSubnet())
                .eqIfPresent(TrafficOpsBigledDO::getGateway, reqVO.getGateway())
                .eqIfPresent(TrafficOpsBigledDO::getPtzIp, reqVO.getPtzIp())
                .likeIfPresent(TrafficOpsBigledDO::getUsername, reqVO.getUsername())
                .eqIfPresent(TrafficOpsBigledDO::getPasswd, reqVO.getPasswd())
                .eqIfPresent(TrafficOpsBigledDO::getViolationCode, reqVO.getViolationCode())
                .eqIfPresent(TrafficOpsBigledDO::getInducedEncoding, reqVO.getInducedEncoding())
                .eqIfPresent(TrafficOpsBigledDO::getDpbh, reqVO.getDpbh())
                .eqIfPresent(TrafficOpsBigledDO::getRemark, reqVO.getRemark())
                .eqIfPresent(TrafficOpsBigledDO::getSize, reqVO.getSize())
                .betweenIfPresent(TrafficOpsBigledDO::getInstallDate, reqVO.getInstallDate())
                .eqIfPresent(TrafficOpsBigledDO::getManufacturer, reqVO.getManufacturer())
                .eqIfPresent(TrafficOpsBigledDO::getUnit, reqVO.getUnit())
                .betweenIfPresent(TrafficOpsBigledDO::getCreateTime, reqVO.getCreateTime())
                .betweenIfPresent(TrafficOpsBigledDO::getDeletedTime, reqVO.getDeletedTime())
                .likeIfPresent(TrafficOpsBigledDO::getDominion, reqVO.getDominion())
                .eqIfPresent(TrafficOpsBigledDO::getStatus, reqVO.getStatus())
                .orderByDesc(TrafficOpsBigledDO::getId));
    }

}