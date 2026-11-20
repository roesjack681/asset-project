package com.holdtime.ops.module.entity.mapper;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import com.holdtime.ops.module.entity.TrafficOpsRoadDO;
import org.apache.ibatis.annotations.Mapper;
import com.holdtime.ops.module.model.vo.*;

/**
 * 道路信息 Mapper
 *
 * @author 芋道源码
 */
@Mapper
public interface TrafficOpsRoadMapper extends BaseMapperX<TrafficOpsRoadDO> {

    default PageResult<TrafficOpsRoadDO> selectPage(TrafficOpsRoadPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<TrafficOpsRoadDO>()
                .eqIfPresent(TrafficOpsRoadDO::getGlbm, reqVO.getGlbm())
                .eqIfPresent(TrafficOpsRoadDO::getDldm, reqVO.getDldm())
                .likeIfPresent(TrafficOpsRoadDO::getDlmc, reqVO.getDlmc())
                .eqIfPresent(TrafficOpsRoadDO::getDllx, reqVO.getDllx())
                .eqIfPresent(TrafficOpsRoadDO::getGlxzdj, reqVO.getGlxzdj())
                .eqIfPresent(TrafficOpsRoadDO::getQs, reqVO.getQs())
                .eqIfPresent(TrafficOpsRoadDO::getJs, reqVO.getJs())
                .eqIfPresent(TrafficOpsRoadDO::getXqlc, reqVO.getXqlc())
                .eqIfPresent(TrafficOpsRoadDO::getQsmc, reqVO.getQsmc())
                .eqIfPresent(TrafficOpsRoadDO::getJsmc, reqVO.getJsmc())
                .betweenIfPresent(TrafficOpsRoadDO::getCreateTime, reqVO.getCreateTime())
                .betweenIfPresent(TrafficOpsRoadDO::getDeletedTime, reqVO.getDeletedTime())
                .orderByDesc(TrafficOpsRoadDO::getId));
    }

}