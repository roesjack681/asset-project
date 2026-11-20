package com.holdtime.ops.module.entity.mapper;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import com.holdtime.ops.module.entity.TrafficCrossingDO;
import org.apache.ibatis.annotations.Mapper;
import com.holdtime.ops.module.model.vo.*;

/**
 * 路口信息 Mapper
 *
 * @author 芋道源码
 */
@Mapper
public interface TrafficCrossingMapper extends BaseMapperX<TrafficCrossingDO> {

    default PageResult<TrafficCrossingDO> selectPage(TrafficCrossingPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<TrafficCrossingDO>()
                .eqIfPresent(TrafficCrossingDO::getLkmc, reqVO.getLkmc())
                .eqIfPresent(TrafficCrossingDO::getLklx, reqVO.getLklx())
                .eqIfPresent(TrafficCrossingDO::getLkzb, reqVO.getLkzb())
                .eqIfPresent(TrafficCrossingDO::getQu, reqVO.getQu())
                .orderByDesc(TrafficCrossingDO::getId));
    }

}