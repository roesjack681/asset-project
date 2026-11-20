package com.holdtime.ops.module.entity.mapper;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import com.holdtime.ops.module.entity.TrafficOpsSignBoardDO;
import com.holdtime.ops.module.entity.TrafficOpsSignalDO;
import org.apache.ibatis.annotations.Mapper;
import com.holdtime.ops.module.model.vo.*;

/**
 * 标志牌 Mapper
 *
 * @author 芋道源码
 */
@Mapper
public interface TrafficOpsSignBoardMapper extends BaseMapperX<TrafficOpsSignBoardDO> {

    default PageResult<TrafficOpsSignBoardDO> selectPage(TrafficOpsSignBoardPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<TrafficOpsSignBoardDO>()
                .likeIfPresent(TrafficOpsSignBoardDO::getEquName, reqVO.getEquName())
                .eqIfPresent(TrafficOpsSignBoardDO::getEquType, reqVO.getEquType())
                .likeIfPresent(TrafficOpsSignBoardDO::getRoadName, reqVO.getRoadName())
                .likeIfPresent(TrafficOpsSignBoardDO::getIntersectionName, reqVO.getIntersectionName())
                .betweenIfPresent(TrafficOpsSignBoardDO::getInstallDate, reqVO.getInstallDate())
                .eqIfPresent(TrafficOpsSignBoardDO::getAdress, reqVO.getAdress())
                .eqIfPresent(TrafficOpsSignBoardDO::getLng, reqVO.getLng())
                .eqIfPresent(TrafficOpsSignBoardDO::getLat, reqVO.getLat())
                .eqIfPresent(TrafficOpsSignBoardDO::getDirection, reqVO.getDirection())
                .eqIfPresent(TrafficOpsSignBoardDO::getLabelContent, reqVO.getLabelContent())
                .eqIfPresent(TrafficOpsSignBoardDO::getSpecifications, reqVO.getSpecifications())
                .eqIfPresent(TrafficOpsSignBoardDO::getGgunit, reqVO.getGgunit())
                .eqIfPresent(TrafficOpsSignBoardDO::getNum, reqVO.getNum())
                .eqIfPresent(TrafficOpsSignBoardDO::getUnit, reqVO.getUnit())
                .eqIfPresent(TrafficOpsSignBoardDO::getDominion, reqVO.getDominion())
                .eqIfPresent(TrafficOpsSignBoardDO::getManufacturer, reqVO.getManufacturer())
                .eqIfPresent(TrafficOpsSignBoardDO::getInstallManufacturer, reqVO.getInstallManufacturer())
                .betweenIfPresent(TrafficOpsSignBoardDO::getCreateTime, reqVO.getCreateTime())
                .betweenIfPresent(TrafficOpsSignBoardDO::getDeletedTime, reqVO.getDeletedTime())
                .orderByDesc(TrafficOpsSignBoardDO::getId));
    }

}