package com.holdtime.ops.module.entity.mapper;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import com.holdtime.ops.module.entity.TrafficOpsMemberDO;
import com.holdtime.ops.module.entity.TrafficOpsSignalDO;
import org.apache.ibatis.annotations.Mapper;
import com.holdtime.ops.module.model.vo.*;

/**
 * 杆件 Mapper
 *
 * @author 芋道源码
 */
@Mapper
public interface TrafficOpsMemberMapper extends BaseMapperX<TrafficOpsMemberDO> {

    default PageResult<TrafficOpsMemberDO> selectPage(TrafficOpsMemberPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<TrafficOpsMemberDO>()
                .likeIfPresent(TrafficOpsMemberDO::getEquName, reqVO.getEquName())
                .eqIfPresent(TrafficOpsMemberDO::getEquNumber, reqVO.getEquNumber())
                .eqIfPresent(TrafficOpsMemberDO::getEquType, reqVO.getEquType())
                .eqIfPresent(TrafficOpsMemberDO::getJibLength, reqVO.getJibLength())
                .likeIfPresent(TrafficOpsMemberDO::getRoadName, reqVO.getRoadName())
                .eqIfPresent(TrafficOpsMemberDO::getMemberType, reqVO.getMemberType())
                .likeIfPresent(TrafficOpsMemberDO::getIntersectionName, reqVO.getIntersectionName())
                .eqIfPresent(TrafficOpsMemberDO::getLng, reqVO.getLng())
                .eqIfPresent(TrafficOpsMemberDO::getLat, reqVO.getLat())
                .betweenIfPresent(TrafficOpsMemberDO::getCreateTime, reqVO.getCreateTime())
                .eqIfPresent(TrafficOpsMemberDO::getAdress, reqVO.getAdress())
                .betweenIfPresent(TrafficOpsMemberDO::getInstallDate, reqVO.getInstallDate())
                .eqIfPresent(TrafficOpsMemberDO::getManufacturer, reqVO.getManufacturer())
                .betweenIfPresent(TrafficOpsMemberDO::getDeletedTime, reqVO.getDeletedTime())
                .eqIfPresent(TrafficOpsMemberDO::getUnit, reqVO.getUnit())
                .eqIfPresent(TrafficOpsMemberDO::getHight, reqVO.getHight())
                .eqIfPresent(TrafficOpsMemberDO::getBarLength, reqVO.getBarLength())
                .likeIfPresent(TrafficOpsMemberDO::getDominion, reqVO.getDominion())
                .eqIfPresent(TrafficOpsMemberDO::getStatus, reqVO.getStatus())
                .eqIfPresent(TrafficOpsMemberDO::getSerialNum, reqVO.getSerialNum())
                .eqIfPresent(TrafficOpsMemberDO::getIsCheck, reqVO.getIsCheck())
                .likeIfPresent(TrafficOpsMemberDO::getCheckRemark, reqVO.getCheckRemark())
                .orderByDesc(TrafficOpsMemberDO::getId));
    }

}