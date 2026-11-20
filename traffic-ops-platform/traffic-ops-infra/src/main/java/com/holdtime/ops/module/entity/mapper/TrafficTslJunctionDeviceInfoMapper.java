package com.holdtime.ops.module.entity.mapper;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import com.holdtime.ops.module.entity.TrafficTslJunctionDeviceInfoDO;
import org.apache.ibatis.annotations.Mapper;
import com.holdtime.ops.module.model.vo.*;

/**
 * 信控交叉口设备信息 Mapper
 *
 * @author yangli
 */
@Mapper
public interface TrafficTslJunctionDeviceInfoMapper extends BaseMapperX<TrafficTslJunctionDeviceInfoDO> {

    default PageResult<TrafficTslJunctionDeviceInfoDO> selectPage(TrafficTslJunctionDeviceInfoPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<TrafficTslJunctionDeviceInfoDO>()
                .likeIfPresent(TrafficTslJunctionDeviceInfoDO::getControllerName, reqVO.getControllerName())
                .eqIfPresent(TrafficTslJunctionDeviceInfoDO::getJunctionId, reqVO.getJunctionId())
                .likeIfPresent(TrafficTslJunctionDeviceInfoDO::getJunctionName, reqVO.getJunctionName())
                .eqIfPresent(TrafficTslJunctionDeviceInfoDO::getDeviceIp, reqVO.getDeviceIp())
                .eqIfPresent(TrafficTslJunctionDeviceInfoDO::getOnline, reqVO.getOnline())
                .eqIfPresent(TrafficTslJunctionDeviceInfoDO::getState, reqVO.getState())
                .eqIfPresent(TrafficTslJunctionDeviceInfoDO::getMode, reqVO.getMode())
                .eqIfPresent(TrafficTslJunctionDeviceInfoDO::getProtocol, reqVO.getProtocol())
                .eqIfPresent(TrafficTslJunctionDeviceInfoDO::getCanPing, reqVO.getCanPing())
                .betweenIfPresent(TrafficTslJunctionDeviceInfoDO::getCreateTime, reqVO.getCreateTime())
                .betweenIfPresent(TrafficTslJunctionDeviceInfoDO::getDeletedTime, reqVO.getDeletedTime())
                .orderByDesc(TrafficTslJunctionDeviceInfoDO::getId));
    }

}