package com.holdtime.ops.module.entity.mapper;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import com.holdtime.ops.module.entity.PoliceOpsMemberDO;
import org.apache.ibatis.annotations.Mapper;
import com.holdtime.ops.module.model.vo.*;

/**
 * 杆件 Mapper
 *
 * @author 芋道源码
 */
@Mapper
public interface PoliceOpsMemberMapper extends BaseMapperX<PoliceOpsMemberDO> {

    default PageResult<PoliceOpsMemberDO> selectPage(PoliceOpsMemberPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<PoliceOpsMemberDO>()
                .eqIfPresent(PoliceOpsMemberDO::getEquNumber, reqVO.getEquNumber())
                .likeIfPresent(PoliceOpsMemberDO::getEquName, reqVO.getEquName())
                .eqIfPresent(PoliceOpsMemberDO::getEquType, reqVO.getEquType())
                .likeIfPresent(PoliceOpsMemberDO::getRoadName, reqVO.getRoadName())
                .likeIfPresent(PoliceOpsMemberDO::getIntersectionName, reqVO.getIntersectionName())
                .eqIfPresent(PoliceOpsMemberDO::getLng, reqVO.getLng())
                .eqIfPresent(PoliceOpsMemberDO::getLat, reqVO.getLat())
                .eqIfPresent(PoliceOpsMemberDO::getAdress, reqVO.getAdress())
                .betweenIfPresent(PoliceOpsMemberDO::getInstallDate, reqVO.getInstallDate())
                .eqIfPresent(PoliceOpsMemberDO::getManufacturer, reqVO.getManufacturer())
                .eqIfPresent(PoliceOpsMemberDO::getUnit, reqVO.getUnit())
                .eqIfPresent(PoliceOpsMemberDO::getHight, reqVO.getHight())
                .eqIfPresent(PoliceOpsMemberDO::getBarLength, reqVO.getBarLength())
                .eqIfPresent(PoliceOpsMemberDO::getJibLength, reqVO.getJibLength())
                .eqIfPresent(PoliceOpsMemberDO::getMemberType, reqVO.getMemberType())
                .eqIfPresent(PoliceOpsMemberDO::getDominion, reqVO.getDominion())
                .eqIfPresent(PoliceOpsMemberDO::getStatus, reqVO.getStatus())
                .eqIfPresent(PoliceOpsMemberDO::getEquImg, reqVO.getEquImg())
                .betweenIfPresent(PoliceOpsMemberDO::getCreateTime, reqVO.getCreateTime())
                .betweenIfPresent(PoliceOpsMemberDO::getDeletedTime, reqVO.getDeletedTime())
//                .eqIfPresent(PoliceOpsMemberDO::getSerialNum, reqVO.getSerialNumber())
                .eqIfPresent(PoliceOpsMemberDO::getEquImg2, reqVO.getEquImg2())
                .eqIfPresent(PoliceOpsMemberDO::getEquImg3, reqVO.getEquImg3())
                .eqIfPresent(PoliceOpsMemberDO::getIsCheck, reqVO.getIsCheck())
                .eqIfPresent(PoliceOpsMemberDO::getCheckRemark, reqVO.getCheckRemark())
                .orderByDesc(PoliceOpsMemberDO::getId));
    }

}