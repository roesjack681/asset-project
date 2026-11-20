package com.holdtime.ops.module.entity.mapper;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import com.holdtime.ops.module.entity.TrafficTslRegionInfoDO;
import org.apache.ibatis.annotations.Mapper;
import com.holdtime.ops.module.model.vo.*;

/**
 * 信控区域信息 Mapper
 *
 * @author yangli
 */
@Mapper
public interface TrafficTslRegionInfoMapper extends BaseMapperX<TrafficTslRegionInfoDO> {

    default PageResult<TrafficTslRegionInfoDO> selectPage(TrafficTslRegionInfoPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<TrafficTslRegionInfoDO>()
                .likeIfPresent(TrafficTslRegionInfoDO::getRegionName, reqVO.getRegionName())
                .eqIfPresent(TrafficTslRegionInfoDO::getTcsId, reqVO.getTcsId())
                .betweenIfPresent(TrafficTslRegionInfoDO::getCreateTime, reqVO.getCreateTime())
                .betweenIfPresent(TrafficTslRegionInfoDO::getDeletedTime, reqVO.getDeletedTime())
                .orderByDesc(TrafficTslRegionInfoDO::getId));
    }

}