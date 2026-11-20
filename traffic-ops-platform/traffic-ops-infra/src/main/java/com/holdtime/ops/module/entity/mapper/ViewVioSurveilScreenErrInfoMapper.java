package com.holdtime.ops.module.entity.mapper;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import com.holdtime.ops.module.entity.ViewVioSurveilScreenErrInfoDO;
import org.apache.ibatis.annotations.Mapper;
import com.holdtime.ops.module.model.vo.*;

/**
 * 集指无效违法记录 Mapper
 *
 * @author 管理员
 */
@Mapper
public interface ViewVioSurveilScreenErrInfoMapper extends BaseMapperX<ViewVioSurveilScreenErrInfoDO> {

    default PageResult<ViewVioSurveilScreenErrInfoDO> selectPage(ViewVioSurveilScreenErrInfoPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<ViewVioSurveilScreenErrInfoDO>()
                .eqIfPresent(ViewVioSurveilScreenErrInfoDO::getSbbh, reqVO.getSbbh())
                .eqIfPresent(ViewVioSurveilScreenErrInfoDO::getHphm, reqVO.getHphm())
                .eqIfPresent(ViewVioSurveilScreenErrInfoDO::getHpzl, reqVO.getHpzl())
                .betweenIfPresent(ViewVioSurveilScreenErrInfoDO::getWfsj, reqVO.getWfsj())
                .eqIfPresent(ViewVioSurveilScreenErrInfoDO::getWfxw, reqVO.getWfxw())
                .likeIfPresent(ViewVioSurveilScreenErrInfoDO::getCwyy, reqVO.getCwyy())
                .eqIfPresent(ViewVioSurveilScreenErrInfoDO::getUrl1, reqVO.getUrl1())
                .eqIfPresent(ViewVioSurveilScreenErrInfoDO::getUrl2, reqVO.getUrl2())
                .eqIfPresent(ViewVioSurveilScreenErrInfoDO::getUrl3, reqVO.getUrl3())
                .betweenIfPresent(ViewVioSurveilScreenErrInfoDO::getLrsj, reqVO.getLrsj())
                .betweenIfPresent(ViewVioSurveilScreenErrInfoDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(ViewVioSurveilScreenErrInfoDO::getId));
    }

}