package com.holdtime.ops.module.entity.mapper;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import com.holdtime.ops.module.entity.TrafficLightsdetectionlogDO;
import org.apache.ibatis.annotations.Mapper;
import com.holdtime.ops.module.model.vo.*;

/**
 * 信号灯检测信息 Mapper
 *
 * @author 芋道源码
 */
@Mapper
public interface TrafficLightsdetectionlogMapper extends BaseMapperX<TrafficLightsdetectionlogDO> {

    default PageResult<TrafficLightsdetectionlogDO> selectPage(TrafficLightsdetectionlogPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<TrafficLightsdetectionlogDO>()
                .eqIfPresent(TrafficLightsdetectionlogDO::getLkid, reqVO.getId())
                .eqIfPresent(TrafficLightsdetectionlogDO::getSbid, reqVO.getSbid())
                .eqIfPresent(TrafficLightsdetectionlogDO::getJcjg, reqVO.getJcjg())
                .eqIfPresent(TrafficLightsdetectionlogDO::getJcjgex, reqVO.getJcjgex())
                .eqIfPresent(TrafficLightsdetectionlogDO::getJctp, reqVO.getJctp())
                .eqIfPresent(TrafficLightsdetectionlogDO::getJcsj, reqVO.getJcsj())
                .eqIfPresent(TrafficLightsdetectionlogDO::getPdjg, reqVO.getPdjg())
                .orderByDesc(TrafficLightsdetectionlogDO::getId));
    }

}