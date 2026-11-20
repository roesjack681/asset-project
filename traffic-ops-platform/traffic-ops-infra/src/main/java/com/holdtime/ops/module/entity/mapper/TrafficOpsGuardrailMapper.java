package com.holdtime.ops.module.entity.mapper;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import com.holdtime.ops.module.entity.TrafficOpsGuardrailDO;
import org.apache.ibatis.annotations.Mapper;
import com.holdtime.ops.module.model.vo.*;

/**
 * 护栏 Mapper
 *
 * @author 芋道源码
 */
@Mapper
public interface TrafficOpsGuardrailMapper extends BaseMapperX<TrafficOpsGuardrailDO> {

    default PageResult<TrafficOpsGuardrailDO> selectPage(TrafficOpsGuardrailPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<TrafficOpsGuardrailDO>()
                .likeIfPresent(TrafficOpsGuardrailDO::getEquName, reqVO.getEquName())
                .eqIfPresent(TrafficOpsGuardrailDO::getEquType, reqVO.getEquType())
                .likeIfPresent(TrafficOpsGuardrailDO::getRoadName, reqVO.getRoadName())
                .likeIfPresent(TrafficOpsGuardrailDO::getIntersectionName, reqVO.getIntersectionName())
                .eqIfPresent(TrafficOpsGuardrailDO::getStartLng, reqVO.getStartLng())
                .eqIfPresent(TrafficOpsGuardrailDO::getStartLat, reqVO.getStartLat())
                .eqIfPresent(TrafficOpsGuardrailDO::getEndLng, reqVO.getEndLng())
                .eqIfPresent(TrafficOpsGuardrailDO::getEndLat, reqVO.getEndLat())
                .likeIfPresent(TrafficOpsGuardrailDO::getAdress, reqVO.getAdress())
                .betweenIfPresent(TrafficOpsGuardrailDO::getInstallDate, reqVO.getInstallDate())
                .eqIfPresent(TrafficOpsGuardrailDO::getUnit, reqVO.getUnit())
                .eqIfPresent(TrafficOpsGuardrailDO::getDominion, reqVO.getDominion())
                .eqIfPresent(TrafficOpsGuardrailDO::getStatus, reqVO.getStatus())
                .eqIfPresent(TrafficOpsGuardrailDO::getEquImg, reqVO.getEquImg())
                .eqIfPresent(TrafficOpsGuardrailDO::getPieces, reqVO.getPieces())
                .eqIfPresent(TrafficOpsGuardrailDO::getLength, reqVO.getLength())
                .eqIfPresent(TrafficOpsGuardrailDO::getRemark, reqVO.getRemark())
                .betweenIfPresent(TrafficOpsGuardrailDO::getCreateTime, reqVO.getCreateTime())
                .betweenIfPresent(TrafficOpsGuardrailDO::getDeletedTime, reqVO.getDeletedTime())
                .orderByDesc(TrafficOpsGuardrailDO::getId));
    }

}