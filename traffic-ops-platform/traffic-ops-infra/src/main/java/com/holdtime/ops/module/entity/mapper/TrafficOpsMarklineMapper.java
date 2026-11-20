package com.holdtime.ops.module.entity.mapper;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import com.holdtime.ops.module.entity.TrafficOpsMarklineDO;
import org.apache.ibatis.annotations.Mapper;
import com.holdtime.ops.module.model.vo.*;

/**
 * 标线 Mapper
 *
 * @author 芋道源码
 */
@Mapper
public interface TrafficOpsMarklineMapper extends BaseMapperX<TrafficOpsMarklineDO> {

    default PageResult<TrafficOpsMarklineDO> selectPage(TrafficOpsMarklinePageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<TrafficOpsMarklineDO>()
                .likeIfPresent(TrafficOpsMarklineDO::getRoadName, reqVO.getRoadName())
                .eqIfPresent(TrafficOpsMarklineDO::getRoadParts, reqVO.getRoadParts())
                .eqIfPresent(TrafficOpsMarklineDO::getMarklineSort, reqVO.getMarklineSort())
                .eqIfPresent(TrafficOpsMarklineDO::getMarklineNum, reqVO.getMarklineNum())
                .eqIfPresent(TrafficOpsMarklineDO::getLineWidth, reqVO.getLineWidth())
                .eqIfPresent(TrafficOpsMarklineDO::getSpecifications, reqVO.getSpecifications())
                .eqIfPresent(TrafficOpsMarklineDO::getProportion, reqVO.getProportion())
                .eqIfPresent(TrafficOpsMarklineDO::getArea, reqVO.getArea())
                .eqIfPresent(TrafficOpsMarklineDO::getPaintingUnit, reqVO.getPaintingUnit())
                .eqIfPresent(TrafficOpsMarklineDO::getDrawingProcess, reqVO.getDrawingProcess())
                .eqIfPresent(TrafficOpsMarklineDO::getDominion, reqVO.getDominion())
                .betweenIfPresent(TrafficOpsMarklineDO::getApplicationTime, reqVO.getApplicationTime())
                .betweenIfPresent(TrafficOpsMarklineDO::getCreateTime, reqVO.getCreateTime())
                .betweenIfPresent(TrafficOpsMarklineDO::getDeletedTime, reqVO.getDeletedTime())
                .orderByDesc(TrafficOpsMarklineDO::getApplicationTime));
    }

}