package com.holdtime.ops.module.entity.mapper;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import com.holdtime.ops.module.entity.TrafficVioSurveilScreenErrDO;
import org.apache.ibatis.annotations.Mapper;
import com.holdtime.ops.module.model.vo.*;

/**
 * 违法抓拍无效记录 Mapper
 *
 * @author 管理员
 */
@Mapper
public interface TrafficVioSurveilScreenErrMapper extends BaseMapperX<TrafficVioSurveilScreenErrDO> {

    default PageResult<TrafficVioSurveilScreenErrDO> selectPage(TrafficVioSurveilScreenErrPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<TrafficVioSurveilScreenErrDO>()
                .eqIfPresent(TrafficVioSurveilScreenErrDO::getEquId, reqVO.getEquId())
                .eqIfPresent(TrafficVioSurveilScreenErrDO::getSbbh, reqVO.getSbbh())
                .eqIfPresent(TrafficVioSurveilScreenErrDO::getHphm, reqVO.getHphm())
                .eqIfPresent(TrafficVioSurveilScreenErrDO::getHpzl, reqVO.getHpzl())
                .eqIfPresent(TrafficVioSurveilScreenErrDO::getWfsj, reqVO.getWfsj())
                .eqIfPresent(TrafficVioSurveilScreenErrDO::getWfxw, reqVO.getWfxw())
                .betweenIfPresent(TrafficVioSurveilScreenErrDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(TrafficVioSurveilScreenErrDO::getId));
    }

}