package com.holdtime.ops.module.entity.mapper;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import com.holdtime.ops.module.entity.TrafficCamdetectionlogDO;
import org.apache.ibatis.annotations.Mapper;
import com.holdtime.ops.module.model.vo.*;

/**
 * 设备状态检测 Mapper
 *
 * @author 芋道源码
 */
@Mapper
public interface TrafficCamdetectionlogMapper extends BaseMapperX<TrafficCamdetectionlogDO> {

    default PageResult<TrafficCamdetectionlogDO> selectPage(TrafficCamdetectionlogPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<TrafficCamdetectionlogDO>()
                .eqIfPresent(TrafficCamdetectionlogDO::getLkid, reqVO.getId())
                .eqIfPresent(TrafficCamdetectionlogDO::getSbid, reqVO.getSbid())
                .eqIfPresent(TrafficCamdetectionlogDO::getXjzt, reqVO.getXjzt())
                .eqIfPresent(TrafficCamdetectionlogDO::getJcsj, reqVO.getJcsj())
                .orderByDesc(TrafficCamdetectionlogDO::getId));
    }

}