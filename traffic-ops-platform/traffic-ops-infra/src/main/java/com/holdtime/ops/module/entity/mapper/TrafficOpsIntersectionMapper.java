package com.holdtime.ops.module.entity.mapper;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import com.holdtime.ops.module.entity.TrafficOpsIntersectionDO;
import org.apache.ibatis.annotations.Mapper;
import com.holdtime.ops.module.model.vo.*;

/**
 * 交叉口信息 Mapper
 *
 * @author 芋道源码
 */
@Mapper
public interface TrafficOpsIntersectionMapper extends BaseMapperX<TrafficOpsIntersectionDO> {

    default PageResult<TrafficOpsIntersectionDO> selectPage(TrafficOpsIntersectionPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<TrafficOpsIntersectionDO>()
                .likeIfPresent(TrafficOpsIntersectionDO::getGlbm, reqVO.getGlbm())
                .eqIfPresent(TrafficOpsIntersectionDO::getDldm, reqVO.getDldm())
                .eqIfPresent(TrafficOpsIntersectionDO::getXzqh, reqVO.getXzqh())
                .eqIfPresent(TrafficOpsIntersectionDO::getLddm, reqVO.getLddm())
                .likeIfPresent(TrafficOpsIntersectionDO::getDlmc, reqVO.getDlmc())
                .likeIfPresent(TrafficOpsIntersectionDO::getLdmc, reqVO.getLdmc())
                .eqIfPresent(TrafficOpsIntersectionDO::getQslc, reqVO.getQslc())
                .eqIfPresent(TrafficOpsIntersectionDO::getJslc, reqVO.getJslc())
                .betweenIfPresent(TrafficOpsIntersectionDO::getCreateTime, reqVO.getCreateTime())
                .betweenIfPresent(TrafficOpsIntersectionDO::getDeletedTime, reqVO.getDeletedTime())
                .orderByDesc(TrafficOpsIntersectionDO::getId));
    }

}