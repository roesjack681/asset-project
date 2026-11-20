package com.holdtime.ops.module.entity.mapper;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import com.holdtime.ops.module.entity.TrafficLightsDO;
import org.apache.ibatis.annotations.Mapper;
import com.holdtime.ops.module.model.vo.*;

/**
 * 信号灯信息 Mapper
 *
 * @author 芋道源码
 */
@Mapper
public interface TrafficLightsMapper extends BaseMapperX<TrafficLightsDO> {

    default PageResult<TrafficLightsDO> selectPage(TrafficLightsPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<TrafficLightsDO>()
                .eqIfPresent(TrafficLightsDO::getId, reqVO.getId())
                .eqIfPresent(TrafficLightsDO::getXhdfx, reqVO.getXhdfx())
                .eqIfPresent(TrafficLightsDO::getXjfx, reqVO.getXjfx())
                .eqIfPresent(TrafficLightsDO::getXhdzt, reqVO.getXhdzt())
                .eqIfPresent(TrafficLightsDO::getJcsj, reqVO.getJcsj())
                .eqIfPresent(TrafficLightsDO::getXjdz, reqVO.getXjdz())
                .eqIfPresent(TrafficLightsDO::getCzjg, reqVO.getCzjg())
                .eqIfPresent(TrafficLightsDO::getSfjc, reqVO.getSfjc())
                .eqIfPresent(TrafficLightsDO::getZb, reqVO.getZb())
                .eqIfPresent(TrafficLightsDO::getXhdxh, reqVO.getXhdxh())
                .eqIfPresent(TrafficLightsDO::getJcmb, reqVO.getJcmb())
                .eqIfPresent(TrafficLightsDO::getCh, reqVO.getCh())
                .eqIfPresent(TrafficLightsDO::getXhdsbqy, reqVO.getXhdsbqy())
                .eqIfPresent(TrafficLightsDO::getYdsbqy, reqVO.getYdsbqy())
                .orderByDesc(TrafficLightsDO::getId));
    }

}