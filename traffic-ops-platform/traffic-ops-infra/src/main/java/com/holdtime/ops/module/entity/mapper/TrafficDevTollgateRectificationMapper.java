package com.holdtime.ops.module.entity.mapper;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import com.holdtime.ops.module.entity.TrafficDevTollgateRectificationDO;
import org.apache.ibatis.annotations.Mapper;
import com.holdtime.ops.module.model.vo.*;

/**
 * 卡口质量评价 Mapper
 *
 * @author 管理员
 */
@Mapper
public interface TrafficDevTollgateRectificationMapper extends BaseMapperX<TrafficDevTollgateRectificationDO> {

    default PageResult<TrafficDevTollgateRectificationDO> selectPage(TrafficDevTollgateRectificationPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<TrafficDevTollgateRectificationDO>()
                .eqIfPresent(TrafficDevTollgateRectificationDO::getXh, reqVO.getXh())
                .eqIfPresent(TrafficDevTollgateRectificationDO::getKkbh, reqVO.getKkbh())
                .eqIfPresent(TrafficDevTollgateRectificationDO::getFxlx, reqVO.getFxlx())
                .eqIfPresent(TrafficDevTollgateRectificationDO::getJcsj, reqVO.getJcsj())
                .eqIfPresent(TrafficDevTollgateRectificationDO::getBdbyy, reqVO.getBdbyy())
                .eqIfPresent(TrafficDevTollgateRectificationDO::getJsl, reqVO.getJsl())
                .eqIfPresent(TrafficDevTollgateRectificationDO::getSbhpzl, reqVO.getSbhpzl())
                .eqIfPresent(TrafficDevTollgateRectificationDO::getCsbj, reqVO.getCsbj())
                .eqIfPresent(TrafficDevTollgateRectificationDO::getBjcsbj, reqVO.getBjcsbj())
                .eqIfPresent(TrafficDevTollgateRectificationDO::getKyl, reqVO.getKyl())
                .betweenIfPresent(TrafficDevTollgateRectificationDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(TrafficDevTollgateRectificationDO::getId));
    }

}