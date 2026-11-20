package com.holdtime.ops.module.entity.mapper;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import com.holdtime.ops.module.entity.TrafficTslJunctionInfoDO;
import org.apache.ibatis.annotations.Mapper;
import com.holdtime.ops.module.model.vo.*;

/**
 * 信控交叉口信息 Mapper
 *
 * @author yangli
 */
@Mapper
public interface TrafficTslJunctionInfoMapper extends BaseMapperX<TrafficTslJunctionInfoDO> {

    default PageResult<TrafficTslJunctionInfoDO> selectPage(TrafficTslJunctionInfoPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<TrafficTslJunctionInfoDO>()
                .eqIfPresent(TrafficTslJunctionInfoDO::getRegionId, reqVO.getRegionId())
                .likeIfPresent(TrafficTslJunctionInfoDO::getJunctionName, reqVO.getJunctionName())
                .eqIfPresent(TrafficTslJunctionInfoDO::getControllerId, reqVO.getControllerId())
                .likeIfPresent(TrafficTslJunctionInfoDO::getControllerName, reqVO.getControllerName())
                .eqIfPresent(TrafficTslJunctionInfoDO::getTcpudp, reqVO.getTcpudp())
                .eqIfPresent(TrafficTslJunctionInfoDO::getProtocol, reqVO.getProtocol())
                .betweenIfPresent(TrafficTslJunctionInfoDO::getCreateTime, reqVO.getCreateTime())
                .betweenIfPresent(TrafficTslJunctionInfoDO::getDeletedTime, reqVO.getDeletedTime())
                .orderByDesc(TrafficTslJunctionInfoDO::getId));
    }

}