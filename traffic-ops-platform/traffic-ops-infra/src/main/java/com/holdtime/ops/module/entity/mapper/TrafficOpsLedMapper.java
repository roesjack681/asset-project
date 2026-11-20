package com.holdtime.ops.module.entity.mapper;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import com.holdtime.ops.module.entity.TrafficOpsLedDO;
import org.apache.ibatis.annotations.Mapper;
import com.holdtime.ops.module.model.vo.*;

/**
 * Led屏 Mapper
 *
 * @author 芋道源码
 */
@Mapper
public interface TrafficOpsLedMapper extends BaseMapperX<TrafficOpsLedDO> {

    default PageResult<TrafficOpsLedDO> selectPage(TrafficOpsLedPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<TrafficOpsLedDO>()
                .eqIfPresent(TrafficOpsLedDO::getEquNumber, reqVO.getEquNumber())
                .likeIfPresent(TrafficOpsLedDO::getEquName, reqVO.getEquName())
                .eqIfPresent(TrafficOpsLedDO::getEquType, reqVO.getEquType())
                .likeIfPresent(TrafficOpsLedDO::getRoadName, reqVO.getRoadName())
                .likeIfPresent(TrafficOpsLedDO::getIntersectionName, reqVO.getIntersectionName())
                .eqIfPresent(TrafficOpsLedDO::getLng, reqVO.getLng())
                .eqIfPresent(TrafficOpsLedDO::getLat, reqVO.getLat())
                .eqIfPresent(TrafficOpsLedDO::getAdress, reqVO.getAdress())
                .betweenIfPresent(TrafficOpsLedDO::getInstallDate, reqVO.getInstallDate())
                .eqIfPresent(TrafficOpsLedDO::getManufacturer, reqVO.getManufacturer())
                .eqIfPresent(TrafficOpsLedDO::getEquModel, reqVO.getEquModel())
                .eqIfPresent(TrafficOpsLedDO::getUnit, reqVO.getUnit())
                .eqIfPresent(TrafficOpsLedDO::getSize, reqVO.getSize())
                .eqIfPresent(TrafficOpsLedDO::getDisplayPurpose, reqVO.getDisplayPurpose())
                .betweenIfPresent(TrafficOpsLedDO::getCreateTime, reqVO.getCreateTime())
                .betweenIfPresent(TrafficOpsLedDO::getDeletedTime, reqVO.getDeletedTime())
                .orderByDesc(TrafficOpsLedDO::getId));
    }

}