package com.holdtime.ops.module.entity.mapper;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import com.holdtime.ops.module.entity.TrafficVioSurveilScreenDO;
import org.apache.ibatis.annotations.Mapper;
import com.holdtime.ops.module.model.vo.*;

/**
 * 违法抓拍有效记录 Mapper
 *
 * @author 管理员
 */
@Mapper
public interface TrafficVioSurveilScreenMapper extends BaseMapperX<TrafficVioSurveilScreenDO> {

    default PageResult<TrafficVioSurveilScreenDO> selectPage(TrafficVioSurveilScreenPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<TrafficVioSurveilScreenDO>()
                .eqIfPresent(TrafficVioSurveilScreenDO::getEquId, reqVO.getEquId())
                .eqIfPresent(TrafficVioSurveilScreenDO::getSbbh, reqVO.getSbbh())
                .eqIfPresent(TrafficVioSurveilScreenDO::getHphm, reqVO.getHphm())
                .eqIfPresent(TrafficVioSurveilScreenDO::getHpzl, reqVO.getHpzl())
                .eqIfPresent(TrafficVioSurveilScreenDO::getWfsj, reqVO.getWfsj())
                .eqIfPresent(TrafficVioSurveilScreenDO::getWfxw, reqVO.getWfxw())
                .betweenIfPresent(TrafficVioSurveilScreenDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(TrafficVioSurveilScreenDO::getId));
    }

}