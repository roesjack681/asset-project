package com.holdtime.ops.module.entity.mapper;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import com.holdtime.ops.module.entity.TrafficVioSurveilDO;
import org.apache.ibatis.annotations.Mapper;
import com.holdtime.ops.module.model.vo.*;

/**
 * 违法抓拍待审记录 Mapper
 *
 * @author 管理员
 */
@Mapper
public interface TrafficVioSurveilMapper extends BaseMapperX<TrafficVioSurveilDO> {

    default PageResult<TrafficVioSurveilDO> selectPage(TrafficVioSurveilPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<TrafficVioSurveilDO>()
                .eqIfPresent(TrafficVioSurveilDO::getEquId, reqVO.getEquId())
                .eqIfPresent(TrafficVioSurveilDO::getSbbh, reqVO.getSbbh())
                .eqIfPresent(TrafficVioSurveilDO::getHphm, reqVO.getHphm())
                .eqIfPresent(TrafficVioSurveilDO::getHpzl, reqVO.getHpzl())
                .eqIfPresent(TrafficVioSurveilDO::getWfsj, reqVO.getWfsj())
                .eqIfPresent(TrafficVioSurveilDO::getWfxw, reqVO.getWfxw())
                .betweenIfPresent(TrafficVioSurveilDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(TrafficVioSurveilDO::getId));
    }

}